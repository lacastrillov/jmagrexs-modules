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
import com.lacv.jmagrexs.modules.fileexplorer.model.dtos.WebFileDto;
import com.lacv.jmagrexs.util.Formats;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
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
    
    Tika tika = new Tika();
    

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
                //String location = explorerConstants.getLocalStaticFolder() + explorerConstants.getLocalRootFolder() + webFile.getPath();
                //FileService.renameFile(location + webFile.getName(), location + jsonObject.getString("name"));
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
                
                //FileService.move(sourceFile, destFile);
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
        try {
            List<WebFile> listEntities = service.findByJSONFilters(filter, null, null, null, null, null);
            List<WebFileDto> listDtos = mapper.listEntitiesToListDtos(listEntities);
            
            for(WebFile webFile: listEntities){
                webFileService.deleteWebFileInDepth(webFile);
            }
            return Util.getResultListCallback(listDtos, (long)listDtos.size(),"Eliminaci&oacute;n de " + entityRef + " realizada...", true);
        } catch (Exception e) {
            LOGGER.error("delete " + entityRef, e);
            return Util.getResultListCallback(new ArrayList(), 0L,"Error en eliminaci&oacute;n de " + entityRef + ": " + e.getMessage(), true);
        }
    }
    
    @RequestMapping(value = "/download/**/{fileName:.+}", method = {RequestMethod.GET})
    public void download(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
            String realLocation= webFileService.getStaticFileLocation(request.getRequestURI());
            if(realLocation!=null){
                String extension = FilenameUtils.getExtension(fileName);

                File file= new File(realLocation);
                String mimeType = tika.detect(file);
                if(Formats.getContentTypeByExtension(extension)!=null){
                    mimeType= Formats.getContentTypeByExtension(extension);
                }
                FileInputStream fis = new FileInputStream(realLocation);
                response.setContentType(mimeType);
                response.setHeader("Content-Length", String.valueOf(fis.available()));
                response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");

                int fileSize = 1024*1024;
                int read;
                byte[] bytes = new byte[fileSize];
                while ((read = fis.read(bytes)) != -1) {
                    response.getOutputStream().write(bytes, 0, read);
                }
                response.getOutputStream().flush();
            }else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Archivo no encontrado");
            }
        } catch (Exception ex) {
            LOGGER.error("ERRROR getResizeImage", ex);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Archivo no encontrado");
        }
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
                    String realLocation= webFileService.getStaticFileLocation(location);
                    if(FileService.existsFile(realLocation)){
                        System.out.println("imageExist= true");
                        File imageFile= new File(realLocation);
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
    
    @RequestMapping(value = "/readFile.htm", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public byte[] readFile(@RequestParam(required = true) String fileUrl, HttpServletRequest request) {
        String content="";
        WebFile webFile= webFileService.findByPath(webFileService.getPathFromFileUrl(fileUrl));
        if(webFile!=null){
            try {
                String realLocation= webFileService.getStaticFileLocation(webFile);
                content= FileService.getTextFile(realLocation);
            } catch (IOException ex) {
                LOGGER.error("readFile ",ex);
            }
        }
        return Util.getStringBytes(content);
    }
    
    @RequestMapping(value = "/writeFile.htm", method = RequestMethod.POST)
    @ResponseBody
    public String writeFile(@RequestParam(required = true) String fileUrl, @RequestParam(required = true) String content, HttpServletRequest request) {
        WebFile webFile= webFileService.findByPath(webFileService.getPathFromFileUrl(fileUrl));
        if(webFile!=null){
            try {
                String realLocation= webFileService.getStaticFileLocation(webFile);
                FileService.setTextFile(content, realLocation);
                
                webFile.setModificationDate(new Date());
                webFile.setSize(content.getBytes().length);
                webFileService.update(webFile);
                
                return "Contenido guardado";
            } catch (IOException ex) {
                LOGGER.error("writeFile ",ex);
            }
        }
        return "Error al guardar";
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
                childs.put(webFile.getId()+"::"+webFile.getName(), webFileService.exploreInDepth(webFile));
            }
            tree.put("0::Raíz", childs);
            resultData=gson.toJson(tree);
        } catch (Exception e) {
            LOGGER.error("getNavigationTreeData " + entityRef, e);
            resultData = "Error in getNavigationTreeData";
        }
        return Util.getStringBytes(resultData);
    }

}
