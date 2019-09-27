/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.common.controllers.rest;


import com.lacv.jmagrexs.modules.common.model.mappers.MassiveOperationMapper;
import com.lacv.jmagrexs.modules.common.services.MassiveOperationService;
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
@RequestMapping(value="/rest/massiveOperation")
public class MassiveOperationRestController extends RestEntityController {
    
    @Autowired
    MassiveOperationService massiveOperationService;
    
    @Autowired
    MassiveOperationMapper massiveOperationMapper;
    
    
    @PostConstruct
    public void init(){
        super.addControlMapping("massiveOperation", massiveOperationService, massiveOperationMapper);
    }
    
    @Override
    public String saveFilePart(int slice, String fieldName, String fileName, String fileType, int fileSize, InputStream is, Object idParent, Boolean sessionUpload){
        return "Almacenamiento de archivo no implementado!!";
    }
    
}