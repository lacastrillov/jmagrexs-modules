/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.leadtable.controllers.rest;


import com.lacv.jmagrexs.controller.rest.RestDirectController;
import com.lacv.jmagrexs.modules.fileexplorer.model.entities.WebFile;
import com.lacv.jmagrexs.service.JdbcDirectService;
import com.lacv.jmagrexs.modules.fileexplorer.services.WebFileService;
import com.lacv.jmagrexs.modules.security.services.bussiness.SecurityService;
import java.io.InputStream;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author lcastrillo
 */
@Controller
@RequestMapping(value="/rest/direct")
public class DirectRestController extends RestDirectController {
    
    @Autowired
    JdbcDirectService jdbcDirectService;
    
    @Autowired
    WebFileService webFileService;
    
    @Autowired
    SecurityService securityService;
    
    
    @Override
    public String saveFilePart(String tableName, String fieldName, String fileName, String fileType, int fileSize, InputStream is, Integer idEntity, Boolean sessionUpload) {
        try {
            Integer userId= (sessionUpload!=null && sessionUpload)? securityService.getCurrentUser().getId():null;
            String path= "archivos/"+tableName.replaceFirst("lt_", "");
            WebFile parentWebFile= webFileService.createDirectoriesIfMissing(path, null);
            String newFileName= idEntity + "_" +fieldName+"."+FilenameUtils.getExtension(fileName);
            WebFile webFile= webFileService.createByFileData(parentWebFile, 0, newFileName, fileType, fileSize, is, userId);
            
            Map<String,Object> entity = jdbcDirectService.loadByParameter(tableName, "id", idEntity);
            entity.put(fieldName, webFile.getLocation());
            jdbcDirectService.updateByParameter(tableName, entity, "id", idEntity);
            
            return "Archivo " + newFileName + " almacenado correctamente";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    
}
