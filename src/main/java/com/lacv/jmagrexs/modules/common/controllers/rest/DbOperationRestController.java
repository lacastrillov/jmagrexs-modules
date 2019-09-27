/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.common.controllers.rest;


import com.lacv.jmagrexs.modules.common.model.mappers.DbOperationMapper;
import com.lacv.jmagrexs.modules.common.services.DbOperationService;
import com.lacv.jmagrexs.controller.rest.RestEntityController;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author lcastrillo
 */
@Controller
@RequestMapping(value="/rest/dbOperation")
public class DbOperationRestController extends RestEntityController {
    
    @Autowired
    DbOperationService dbOperationService;
    
    @Autowired
    DbOperationMapper dbOperationMapper;
    
    
    @PostConstruct
    public void init(){
        super.addControlMapping("dbOperation", dbOperationService, dbOperationMapper);
    }
    
    @Override
    public String saveFilePart(int slice, String fieldName, String fileName, String fileType, int fileSize, InputStream is, Object idParent, Boolean sessionUpload){
        return "Almacenamiento de archivo no implementado!!";
    }
    
}