/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.security.controllers.rest;


import com.lacv.jmagrexs.controller.rest.RestSessionController;
import com.lacv.jmagrexs.domain.BaseEntity;
import com.lacv.jmagrexs.modules.fileexplorer.model.entities.WebFile;
import com.lacv.jmagrexs.modules.security.model.entities.User;
import com.lacv.jmagrexs.modules.security.model.mappers.UserMapper;
import com.lacv.jmagrexs.modules.security.services.UserService;
import com.lacv.jmagrexs.modules.fileexplorer.services.WebFileService;
import com.lacv.jmagrexs.modules.security.services.bussiness.SecurityService;
import java.io.InputStream;
import java.util.List;
import javax.annotation.PostConstruct;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author lcastrillo
 */
@Controller
@RequestMapping(value="/rest/user")
public class UserRestController extends RestSessionController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    UserMapper userMapper;
    
    @Autowired
    SecurityService securityService;
    
    @Autowired
    WebFileService webFileService;
    
    
    @PostConstruct
    public void init(){
        super.addControlMapping("user", userService, userMapper);
    }
    
    @Override
    public String saveFilePart(int slice, String fieldName, String fileName, String fileType, int fileSize, InputStream is, Object idParent, Boolean sessionUpload) {
        String path= "imagenes/usuario/";
        WebFile parentWebFile= webFileService.createDirectoriesIfMissing(path, null);
        
        try {
            Integer userId= (sessionUpload!=null && sessionUpload)? securityService.getCurrentUser().getId():null;
            String imageName= idParent + "_" +fileName.replaceAll(" ", "_");
            WebFile webFile= webFileService.createByFileData(parentWebFile, slice, imageName, fileType, fileSize, is, userId);
            
            User user = userService.loadById(idParent);
            user.setUrlPhoto(webFile.getLocation());
            userService.update(user);
            
            return "Archivo " + imageName + " almacenado correctamente";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    
    @Override
    public String saveResizedImage(String fieldName, String fileName, String fileType, int width, int height, int fileSize, InputStream is, Object idParent, Boolean sessionUpload){
        String path= "imagenes/usuario/";
        WebFile parentWebFile= webFileService.createDirectoriesIfMissing(path, null);
        
        try {
            Integer userId= (sessionUpload!=null && sessionUpload)? securityService.getCurrentUser().getId():null;
            String imageName= idParent + "_" + width + "x" + height + "_" +fileName.replaceAll(" ", "_");
            webFileService.createByFileData(parentWebFile, 0, imageName, fileType, fileSize, is, userId);
            
            return "Archivo " + imageName + " almacenado correctamente";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    
    @Override
    public JSONObject addSessionSearchFilter(JSONObject jsonFilters) {
        jsonFilters.getJSONObject("eq").put("id", securityService.getCurrentUser().getId());
        return jsonFilters;
    }

    @Override
    public JSONObject addSessionReportFilter(String reportName, JSONObject jsonFilters) {
        return jsonFilters;
    }
    
    @Override
    public boolean canSessionLoad(BaseEntity entity){
        User user= (User) entity;
        return securityService.getCurrentUser().getId().equals(user.getId());
    }
    
    @Override
    public boolean canSessionCreate(BaseEntity entity){
        return false;
    }
    
    @Override
    public boolean canSessionUpdate(BaseEntity entity){
        User user= (User) entity;
        return securityService.getCurrentUser().getId().equals(user.getId());
    }

    @Override
    public boolean canSessionDelete(BaseEntity entity) {
        return false;
    }

    @Override
    public boolean canSessionUpdateByFilters(JSONObject jsonFilters) {
        return false;
    }

    @Override
    public boolean canSessionDeleteByFilters(JSONObject jsonFilters) {
        return false;
    }
    
    @Override
    public boolean canSessionImportData(List<BaseEntity> entities){
        return false;
    }
    
}
