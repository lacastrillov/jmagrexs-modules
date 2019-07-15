/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.fileexplorer.services;


import com.lacv.jmagrexs.modules.fileexplorer.daos.WebFileJpa;
import com.lacv.jmagrexs.modules.fileexplorer.model.mappers.WebFileMapper;
import com.lacv.jmagrexs.dao.GenericDao;
import com.lacv.jmagrexs.dao.Parameters;
import com.lacv.jmagrexs.service.EntityServiceImpl;
import com.lacv.jmagrexs.util.FileService;
import com.lacv.jmagrexs.util.Formats;
import com.google.api.services.storage.model.StorageObject;
import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.components.ExplorerConstants;
import com.lacv.jmagrexs.modules.fileexplorer.model.entities.WebFile;
import com.lacv.jmagrexs.modules.security.model.entities.User;
import com.lacv.jmagrexs.modules.security.services.UserService;
import com.lacv.jmagrexs.modules.security.services.bussiness.SecurityService;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lcastrillo
 */
@Service("webFileService")
public class WebFileServiceImpl extends EntityServiceImpl<WebFile> implements WebFileService {
    
    @Autowired
    public WebFileJpa webFileJpa;
    
    @Autowired
    public WebFileMapper webFileMapper;
    
    @Autowired
    public SecurityService securityService;
    
    @Autowired
    public UserService userService;
    
    @Autowired
    public ExplorerConstants explorerConstants;
    
    private final Map<String, WebFile> cachedWebFiles= new HashMap<>();
    
    
    @Override
    public GenericDao getGenericDao(){
        return webFileJpa;
    }
    
    @Override
    public EntityMapper getEntityMapper() {
        return webFileMapper;
    }
    
    
    @Override
    @Transactional(readOnly= true)
    public WebFile findByPath(String path){
        WebFile parentWebFile=null;
        Integer userId= null;
        if(path.startsWith(explorerConstants.getLocalRootUserFolder())){
            path= path.replaceFirst(explorerConstants.getLocalRootUserFolder(), "");
            String username= path.substring(0, path.indexOf("/"));
            path= path.replaceFirst(username+"/", "");
            User user= userService.loadByParameter("username", username);
            userId= (user!=null)? user.getId(): null;
        }
        String[] folders= path.split("/");
        if(folders.length>0){
            for(String folder: folders){
                if(!folder.equals("")){
                    Parameters p= new Parameters();
                    p.whereEqual("webFile", parentWebFile);
                    p.whereEqual("name", folder);
                    p.whereEqual("user", userId);
                    parentWebFile= super.loadByParameters(p);
                    if(parentWebFile==null){
                        return null;
                    }
                }
            }
        }
        return parentWebFile;
    }
    
    @Override
    @Transactional(readOnly= true)
    public String getStaticFileLocation(String fileUrl){
        if(fileUrl!=null){
            WebFile webFile= findByPath(getPathFromFileUrl(fileUrl));
            return getStaticFileLocation(webFile);
        }else{
            return null;
        }
    }
    
    @Override
    public String getStaticFileLocation(WebFile webFile){
        if(webFile!=null){
            //GET USER FOLDER
            String userFolder= getUserFolder(webFile);
            //GET FILE NAME
            String fileName= getFileName(webFile);
            return explorerConstants.getLocalStaticFolder() + userFolder + fileName;
        }else{
            return null;
        }
    }
    
    @Override
    public String getPathFromFileUrl(String fileUrl){
        try {
            String path = URLDecoder.decode(fileUrl, StandardCharsets.UTF_8.name());
            path=  path.replaceFirst(explorerConstants.getLocalStaticDomain(), "");
            path= (path.startsWith("/"))?path.replaceFirst("/", ""):path;
            if(path.startsWith(explorerConstants.getLocalRootFolder())){
                path=  path.replaceFirst(explorerConstants.getLocalRootFolder(), "");
            }
            return path;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(WebFileServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    @Transactional(value = TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public WebFile createByFileData(WebFile parentWebFile, int slice, String fileName, String fileType, int fileSize, InputStream is, Integer user)
            throws IOException {
        
        WebFile webFile= new WebFile();
        webFile.setWebFile(parentWebFile);

        if(slice==0){
            webFile.setName(fileName);
            webFile.setType(fileType);
            webFile.setSize(fileSize);
            webFile.setIcon(Formats.getSimpleContentType(fileType));
            webFile.setCreationDate(new Date());
            webFile.setModificationDate(new Date());
            webFile.setAuthor(getUsername());
            webFile.setUser(user);

            deleteIfExist(parentWebFile, fileName);
            createForced(webFile);
            
            deleteFile(webFile);
        }else{
            webFile= getCachedWebFile(parentWebFile, fileName, fileType);
        }

        addPartToFile(webFile, fileSize, is);

        return webFile;
    }

    @Override
    @Transactional(value = TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public WebFile createByStorageObject(StorageObject object, WebFile parent, String location, Integer user) {
        WebFile webFile= new WebFile();
        
        webFile.setName(object.getName());
        webFile.setSize(object.getSize().intValue());
        webFile.setType(object.getContentType());
        webFile.setIcon(Formats.getSimpleContentType(object.getContentType()));
        webFile.setCreationDate(new Date());
        webFile.setModificationDate(new Date());
        webFile.setAuthor(getUsername());
        webFile.setUser(user);
        
        super.createForced(webFile);
        return webFile;
    }

    @Override
    @Transactional(value = TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public WebFile createFolder(WebFile parentWebFile, String folderName, Integer user) {
        if(!folderName.equals("")){
            WebFile webFile= new WebFile();
            webFile.setName(folderName);
            webFile.setCreationDate(new Date());
            webFile.setType("folder");
            webFile.setIcon("folder");
            webFile.setModificationDate(new Date());
            webFile.setSize(1);
            webFile.setWebFile(parentWebFile);
            webFile.setAuthor(getUsername());
            webFile.setUser(user);
            
            super.createForced(webFile);

            return webFile;
        }
        return null;
    }

    @Override
    @Transactional(value = TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public WebFile createEmptyFile(WebFile parentWebFile, String fileName, Integer user) {
        if(!fileName.equals("")){
            String extension= FilenameUtils.getExtension(fileName);
            WebFile webFile= new WebFile();
            webFile.setName(fileName);
            webFile.setCreationDate(new Date());
            webFile.setType(extension);
            webFile.setIcon(Formats.getSimpleContentType(extension));
            webFile.setModificationDate(new Date());
            webFile.setSize(1);
            webFile.setWebFile(parentWebFile);
            webFile.setAuthor(getUsername());
            webFile.setUser(user);
            
            super.createForced(webFile);

            createFile(webFile);

            return webFile;
        }else{
            return null;
        }
    }
    
    @Override
    @Transactional(value = TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public WebFile createDirectoriesIfMissing(String path, Integer user){
        WebFile parentWebFile=null, webFile=null;
        String[] folders= path.split("/");
        
        if(folders.length>0){
            for(String folder: folders){
                if(!folder.equals("")){
                    Parameters p= new Parameters();
                    p.whereEqual("webFile", parentWebFile);
                    p.whereEqual("name", folder);
                    p.whereEqual("user", user);
                    webFile= super.loadByParameters(p);
                    if(webFile==null){
                        webFile= createFolder(parentWebFile, folder, user);
                    }
                    parentWebFile= webFile;
                }
            }
        }
        return webFile;
    }
    
    @Override
    @Transactional(value = TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public boolean renameWebFile(WebFile webFile, String newFileName){
        String folder= explorerConstants.getLocalStaticFolder() + getUserFolder(webFile);
        String oldLocation= folder + getFileName(webFile);
        String extension= FilenameUtils.getExtension(newFileName);
        webFile.setName(newFileName);
        webFile.setIcon(Formats.getSimpleContentType(extension));
        String newLocation= folder + getFileName(webFile);
        
        if(!oldLocation.equals(newLocation) && FileService.existsFile(oldLocation)){
            FileService.renameFile(oldLocation, newLocation);
            update(webFile);
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    @Transactional(value = TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public boolean deleteIfExist(WebFile parentWebFile, String fileName){
        Parameters p= new Parameters();
        p.whereEqual("webFile", parentWebFile);
        p.whereEqual("name", fileName);
        List<WebFile> webFileInFolder= super.findByParameters(p);
        if(webFileInFolder.size()>0){
            for(WebFile WebFile: webFileInFolder){
                super.remove(WebFile);
            }
            return true;
        }
        return false;
    }
    
    @Override
    @Transactional(value = TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public boolean deleteWebFileInDepth(WebFile webFile) throws IOException {
        if(webFile!=null){
            if(!webFile.getType().equals("folder")){
                deleteFile(webFile);
            }else{
                Parameters p= new Parameters();
                p.whereEqual("webFile", webFile);
                List<WebFile> webFiles= findByParameters(p);
                for(WebFile childWebFile: webFiles){
                    deleteWebFileInDepth(childWebFile);
                }
            }
            super.remove(webFile);
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    @Transactional(readOnly= true)
    public Map exploreInDepth(WebFile webFile){
        Map child= new LinkedHashMap();
        Parameters p= new Parameters();
        p.whereEqual("webFile", webFile);
        p.whereEqual("type", "folder");
        p.orderBy("name", "ASC");
        List<WebFile> webFiles= findByParameters(p);
        for(WebFile childWebFile: webFiles){
            child.put(childWebFile.getId()+"::"+childWebFile.getName(), exploreInDepth(childWebFile));
        }
        return child;
    }
    
    @Transactional(readOnly= true)
    private WebFile getCachedWebFile(WebFile parentWebFile, String fileName, String fileType){
        String wfKey= "pwf"+parentWebFile.getId()+"_fn"+fileName+"_ft"+fileType;
        if(!cachedWebFiles.containsKey(wfKey)){
            Parameters p= new Parameters();
            p.whereEqual("webFile", parentWebFile);
            p.whereEqual("name", fileName);
            p.whereEqual("type", fileType);
            WebFile webFile= super.loadByParameters(p);
            cachedWebFiles.put(wfKey, webFile);
        }
        return cachedWebFiles.get(wfKey);
    }
    
    private void createFile(WebFile webFile){
        //GET USER FOLDER
        String userFolder= getUserFolder(webFile);
        
        //GET FILE NAME
        String fileName= getFileName(webFile);
        
        String location= explorerConstants.getLocalStaticFolder() + userFolder;
        
        if(!FileService.existsFile(location)){
            FileService.createFolder(location);
        }
        
        FileService.createFile(location + fileName);
        System.out.println("CREATE "+location+fileName);
    }
    
    private void addPartToFile(WebFile webFile, int fileSize, InputStream is) throws IOException{
        //GET USER FOLDER
        String userFolder= getUserFolder(webFile);
        
        //GET FILE NAME
        String fileName= getFileName(webFile);
        
        String location= explorerConstants.getLocalStaticFolder() + userFolder;
        
        if(!FileService.existsFile(location)){
            FileService.createFolder(location);
        }
        
        FileService.addPartToFile(fileName, location, fileSize, is);
        System.out.println("CREATE "+location+fileName);
    }
    
    private void deleteFile(WebFile webFile) throws IOException{
        //GET USER FOLDER
        String userFolder= getUserFolder(webFile);
        
        //GET FILE NAME
        String fileName= getFileName(webFile);
        
        String location= explorerConstants.getLocalStaticFolder() + userFolder;
        FileService.deleteFile(location + fileName);
        System.out.println("DELETE "+location+fileName);
    }
    
    private String getUserFolder(WebFile webFile){
        JSONObject infoUser= new JSONObject();
        if(webFile.getUser()!=null){
            infoUser.put("user", "u"+webFile.getUser());
        }else{
            infoUser.put("user", "public");
        }
        return EncryptDecryptWF.encrypt(infoUser.toString()) + "/";
    }
    
    private String getFileName(WebFile webFile){
        String extension = FilenameUtils.getExtension(webFile.getName());
        JSONObject infoFile= new JSONObject();
        infoFile.put("file", "f"+webFile.getId());
        return EncryptDecryptWF.encrypt(infoFile.toString())+"."+extension;
    }
    
    private String getUsername(){
        if(securityService.getCurrentUser()!=null){
            return securityService.getCurrentUser().getUsername();
        }else{
            return "";
        }
    }
    
}
