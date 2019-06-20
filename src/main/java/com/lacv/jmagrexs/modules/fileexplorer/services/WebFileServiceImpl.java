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
import com.lacv.jmagrexs.util.JwtUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
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
    public ExplorerConstants explorerConstants;
    
    private final JwtUtil jwt= new JwtUtil();
    
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
        String[] folders= path.split("/");
        
        if(folders.length>0){
            for(String folder: folders){
                if(!folder.equals("")){
                    Parameters p= new Parameters();
                    p.whereEqual("webFile", parentWebFile);
                    p.whereEqual("name", folder);
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
        try {
            String requestURI= URLDecoder.decode(fileUrl, StandardCharsets.UTF_8.name());
            requestURI=  requestURI.replaceFirst(explorerConstants.getLocalStaticDomain()+"/", "");
            requestURI=  requestURI.replaceFirst(explorerConstants.getLocalRootFolder(), "");
            WebFile webFile= findByPath(requestURI);
            if(webFile!=null){
                return getRealFileLocation(webFile);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(WebFileServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    @Transactional(value = TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public WebFile createByFileData(WebFile parentWebFile, int slice, String fileName, String fileType, int fileSize, InputStream is) throws IOException {
        WebFile webFile= new WebFile();
        webFile.setWebFile(parentWebFile);
        //String path= webFile.getPath();
        //String location= explorerConstants.getLocalStaticFolder() + explorerConstants.getLocalRootFolder() + path;

        if(slice==0){
            webFile.setName(fileName);
            webFile.setType(fileType);
            webFile.setSize(fileSize);
            webFile.setIcon(Formats.getSimpleContentType(fileType));
            webFile.setCreationDate(new Date());
            webFile.setModificationDate(new Date());

            deleteIfExist(parentWebFile, fileName);
            createForced(webFile);

            //FileService.deleteFile(location + fileName);
            deleteFile(webFile);
        }else{
            webFile= getCachedWebFile(parentWebFile, fileName, fileType);
        }

        //FileService.addPartToFile(fileName, location, fileSize, is);
        addPartToFile(webFile, fileSize, is);

        return webFile;
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

    @Override
    @Transactional(value = TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public WebFile createByStorageObject(StorageObject object, WebFile parent, String location) {
        WebFile webFile= new WebFile();
        
        webFile.setName(object.getName());
        webFile.setSize(object.getSize().intValue());
        webFile.setType(object.getContentType());
        webFile.setIcon(Formats.getSimpleContentType(object.getContentType()));
        webFile.setCreationDate(new Date());
        webFile.setModificationDate(new Date());
        
        super.create(webFile);
        return webFile;
    }

    @Override
    @Transactional(value = TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public WebFile createFolder(WebFile parentWebFile, String folderName) {
        if(!folderName.equals("")){
            WebFile webFile= new WebFile();
            webFile.setName(folderName);
            webFile.setCreationDate(new Date());
            webFile.setType("folder");
            webFile.setIcon("folder");
            webFile.setModificationDate(new Date());
            webFile.setSize(1);
            webFile.setWebFile(parentWebFile);
            super.createForced(webFile);

            //String path= webFile.getPath();
            //String location= explorerConstants.getLocalStaticFolder() + explorerConstants.getLocalRootFolder() + path;
            //FileService.createFolder(location + webFile.getName());
            return webFile;
        }
        return null;
    }

    @Override
    @Transactional(value = TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public WebFile createEmptyFile(WebFile parentWebFile, String fileName) {
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
            super.create(webFile);

            //String path= webFile.getPath();
            //String location= explorerConstants.getLocalStaticFolder() + explorerConstants.getLocalRootFolder() + path;
            //FileService.createFile(location + webFile.getName());
            createFile(webFile);

            return webFile;
        }else{
            return null;
        }
    }
    
    @Override
    @Transactional(value = TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public WebFile createDirectoriesIfMissing(String path){
        WebFile parentWebFile=null, webFile=null;
        String[] folders= path.split("/");
        
        if(folders.length>0){
            for(String folder: folders){
                if(!folder.equals("")){
                    Parameters p= new Parameters();
                    p.whereEqual("webFile", parentWebFile);
                    p.whereEqual("name", folder);
                    webFile= super.loadByParameters(p);
                    if(webFile==null){
                        webFile= createFolder(parentWebFile, folder);
                    }
                    parentWebFile= webFile;
                }
            }
        }
        return webFile;
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
    
    public boolean deleteWebFile(WebFile webFile){
        
        return true;
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
    
    @Override
    public String getRealFileLocation(WebFile webFile){
        //GET USER FOLDER
        String userFolder= getUserFolder(webFile);
        
        //GET FILE NAME
        String fileName= getFileName(webFile);
        
        return explorerConstants.getLocalStaticFolder() + userFolder + fileName;
    }
    
    private String getUserFolder(WebFile webFile){
        JSONObject infoUser= new JSONObject();
        if(webFile.getUser()!=null){
            infoUser.put("user", "user"+webFile.getUser());
        }else{
            infoUser.put("user", "public");
        }
        return Base64.getEncoder().encodeToString(infoUser.toString().getBytes()).replaceAll("=", "") + "/";
        //jwt.generateJSONToken(infoUser, SecurityConstants.SECURITY_SEED_PASSW);
    }
    
    private String getFileName(WebFile webFile){
        String extension = FilenameUtils.getExtension(webFile.getName());
        JSONObject infoFile= new JSONObject();
        System.out.println("file"+webFile.getId());
        infoFile.put("file", "file"+webFile.getId());
        return Base64.getEncoder().encodeToString(infoFile.toString().getBytes()).replaceAll("=", "")+"."+extension;
        //jwt.generateJSONToken(infoFile, SecurityConstants.SECURITY_SEED_PASSW)+"."+extension;
    }
    
    
}
