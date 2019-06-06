/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.fileexplorer.controllers.rest;

import com.lacv.jmagrexs.modules.fileexplorer.model.entities.WebFile;
import com.lacv.jmagrexs.modules.fileexplorer.model.mappers.WebFileMapper;
import com.lacv.jmagrexs.modules.fileexplorer.services.WebFileService;
import com.lacv.jmagrexs.controller.rest.RestEntityController;
import com.lacv.jmagrexs.dao.Parameters;
import com.lacv.jmagrexs.enums.WebFileType;
import com.lacv.jmagrexs.util.FileService;
import com.lacv.jmagrexs.util.Util;
import com.google.gson.Gson;
import com.lacv.jmagrexs.components.ExplorerConstants;
import com.lacv.jmagrexs.modules.fileexplorer.model.dtos.WebFileDto;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author lcastrillo
 */
@Controller
@RequestMapping(value = "/rest/webFile")
public class WebFileRestController extends RestEntityController {

    @Autowired
    WebFileService webFileService;

    @Autowired
    WebFileMapper webFileMapper;
    
    @Autowired
    ExplorerConstants explorerConstants;
    

    @PostConstruct
    public void init() {
        super.addControlMapping("webFile", webFileService, webFileMapper);
    }

    @RequestMapping(value = "/create.htm")
    @ResponseBody
    @Override
    public byte[] create(@RequestParam String data, HttpServletRequest request) {
        String resultData;
        try {
            JSONObject jsonObject = new JSONObject(data);
            WebFile webFile = null;
            WebFile parentWebFile = null;
            if(jsonObject.has("webFile")){
                parentWebFile= webFileService.loadById(jsonObject.getLong("webFile"));
            }

            if (jsonObject.getString("type").equals(WebFileType.folder.name())) {
                webFile = webFileService.createFolder(parentWebFile, jsonObject.getString("name"));
            } else if (jsonObject.getString("type").equals(WebFileType.file.name())) {
                webFile = webFileService.createEmptyFile(parentWebFile, jsonObject.getString("name"));
            }

            WebFileDto dto = (WebFileDto) mapper.entityToDto(webFile);
            resultData = Util.getOperationCallback(dto, "Creaci&oacute;n de " + entityRef + " realizada...", true);
        } catch (Exception e) {
            LOGGER.error("create " + entityRef, e);
            resultData = Util.getOperationCallback(null, "Error en creaci&oacute;n de " + entityRef + ": " + e.getMessage(), false);
        }
        return Util.getStringBytes(resultData);
    }

    @RequestMapping(value = "/update.htm")
    @ResponseBody
    @Override
    public byte[] update(@RequestParam String data, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject(data);

        if (jsonObject.has("id") && jsonObject.has("name")) {
            WebFile webFile = webFileService.loadById(jsonObject.getLong("id"));
            if (!webFile.getName().equals(jsonObject.getString("name"))) {
                String location = explorerConstants.getLocalStaticFolder() + explorerConstants.getLocalRootFolder() + webFile.getPath();
                FileService.renameFile(location + webFile.getName(), location + jsonObject.getString("name"));
            }
        }

        return super.update(data, request);
    }
    
    @RequestMapping(value = "/update/byfilter.htm")
    @ResponseBody
    @Override
    public byte[] updateByFilter(@RequestParam(required= false) String filter, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject(filter);
        String resultData;
        try{
            Long destWebFileId= jsonObject.getJSONObject("uv").getLong("webFile");
            WebFile destWebFile= webFileService.loadById(destWebFileId);
            String destLocation= explorerConstants.getLocalStaticFolder() + explorerConstants.getLocalRootFolder() + ((destWebFile!=null)?destWebFile.getPath():"");
            
            JSONArray fileIdToMove= jsonObject.getJSONObject("in").getJSONArray("id");
            for(int i=0; i<fileIdToMove.length(); i++){
                WebFile sourceWebFile= webFileService.loadById(fileIdToMove.getLong(i));
                String sourceLocation= explorerConstants.getLocalStaticFolder() + explorerConstants.getLocalRootFolder() + sourceWebFile.getPath();
                File sourceFile= new File(sourceLocation + sourceWebFile.getName());
                File destFile= new File(destLocation + ((destWebFile!=null)?destWebFile.getName():""));
                
                FileService.move(sourceFile, destFile);
            }
            
            return super.updateByFilter(filter, request);
        }catch(Exception e){
            resultData= Util.getOperationCallback(null, "Error moviendo los archivos " + e.getMessage(), false);
        }
        return Util.getStringBytes(resultData);
    }

    @RequestMapping(value = "/delete/byfilter.htm", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @Override
    public String deleteByFilter(@RequestParam String filter) {
        String result = super.deleteByFilter(filter);
        JSONObject jsonResult = new JSONObject(result);
        if (jsonResult.getBoolean("success")) {
            JSONArray webFiles = jsonResult.getJSONArray("data");
            for (int i = 0; i < webFiles.length(); i++) {
                JSONObject webFile = webFiles.getJSONObject(i);
                String path = (webFile.has("path")) ? webFile.getString("path") : "";
                LOGGER.info("path: " + path);
                String location = explorerConstants.getLocalStaticFolder() + explorerConstants.getLocalRootFolder() + path;

                FileService.deleteFile(location + webFile.getString("name"));
            }
        }

        return result;
    }
    
    @RequestMapping(value = "/resizeimage/{maxWidth}/{maxHeight}/{imageName:.+}", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<byte[]> resizeImage(@PathVariable int maxWidth, @PathVariable int maxHeight, @PathVariable String imageName, @RequestParam String location) {
        final HttpHeaders headers = new HttpHeaders();
        try {
            String extension = FilenameUtils.getExtension(location);
            String contentType = null;
            if (extension.equalsIgnoreCase("png")) {
                contentType= MediaType.IMAGE_PNG_VALUE;
                headers.setContentType(MediaType.IMAGE_PNG);
            } else if (extension.equalsIgnoreCase("gif")) {
                contentType= MediaType.IMAGE_GIF_VALUE;
                headers.setContentType(MediaType.IMAGE_GIF);
            } else if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")){
                contentType= MediaType.IMAGE_JPEG_VALUE;
                headers.setContentType(MediaType.IMAGE_JPEG);
            }

            if(contentType!=null){
                BufferedImage bufferedImage= null;
                if(FileService.getDomainFromURL(location).equals(explorerConstants.getLocalStaticDomain())){
                    location= location.replace(explorerConstants.getLocalStaticDomain(), explorerConstants.getLocalStaticFolder());
                    if(FileService.existsFile(location)){
                        System.out.println("imageExist= true");
                        File imageFile= new File(location);
                        bufferedImage= ImageIO.read(imageFile);
                    }
                }else{
                    if (FileService.existsFileUrl(location)) {
                        System.out.println("imageExist= true");
                        URI uri = new URI(location.replace(" ", "%20"));
                        URL url = uri.toURL();
                        bufferedImage= ImageIO.read(url);
                    }
                }

                //Obtener Imagen redimensionada
                if(bufferedImage!=null){
                    BufferedImage resizeImage = FileService.resizeImage(bufferedImage, maxWidth, maxHeight);
                    InputStream is = FileService.bufferedImageToInputStream(resizeImage, contentType);

                    headers.setCacheControl("public, max-age=600000");
                    ResponseEntity<byte[]> image = new ResponseEntity<>(IOUtils.toByteArray(is), headers, HttpStatus.CREATED);
                    is.close();

                    return image;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("ERRROR getResizeImage", ex);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Archivo no encontrado");
    }


    @Override
    public String saveFilePart(int slice, String fieldName, String fileName, String fileType, int fileSize, InputStream filePart, Object idParent) {
        try {
            WebFile parentWebFile = null;
            if (!idParent.toString().equals("undefined")) {
                parentWebFile = webFileService.loadById(new Long(idParent.toString()));
            }
            webFileService.createByFileData(parentWebFile, slice, fileName, fileType, fileSize, filePart);

            return "Archivo " + fileName + " almacenado correctamente";
        } catch (Exception ex) {
            LOGGER.error("saveFile ", ex);
            return ex.getMessage();
        }
    }
    
    @RequestMapping(value = "/getNavigationTreeData.htm")
    @ResponseBody
    public byte[] getNavigationTreeData() {
        String resultData;
        try {
            Gson gson= new Gson();
            Map tree = new LinkedHashMap();
            Map childs= new LinkedHashMap();
            Parameters p= new Parameters();
            p.whereIsNull("webFile");
            p.whereEqual("type", "folder");
            p.orderBy("name", "ASC");
            List<WebFile> webFiles= webFileService.findByParameters(p);
            for(WebFile webFile: webFiles){
                childs.put(webFile.getId()+"::"+webFile.getName(), exploreInDepth(webFile));
            }
            tree.put("0::Raíz", childs);
            resultData=gson.toJson(tree);
        } catch (Exception e) {
            LOGGER.error("getNavigationTreeData " + entityRef, e);
            resultData = "Error in getNavigationTreeData";
        }
        return Util.getStringBytes(resultData);
    }
    
    private Map exploreInDepth(WebFile webFile){
        Map child= new LinkedHashMap();
        Parameters p= new Parameters();
        p.whereEqual("webFile", webFile);
        p.whereEqual("type", "folder");
        p.orderBy("name", "ASC");
        List<WebFile> webFiles= webFileService.findByParameters(p);
        for(WebFile childWebFile: webFiles){
            child.put(childWebFile.getId()+"::"+childWebFile.getName(), exploreInDepth(childWebFile));
        }
        return child;
    }

}
