/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.entityexplorer.controllers.rest;


import com.lacv.jmagrexs.modules.entityexplorer.model.mappers.WebEntityTypeMapper;
import com.lacv.jmagrexs.modules.entityexplorer.services.WebEntityTypeService;
import com.lacv.jmagrexs.controller.rest.RestEntityController;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author lcastrillo
 */
@Controller
@RequestMapping(value="/rest/webEntityType")
public class WebEntityTypeRestController extends RestEntityController {
    
    @Autowired
    WebEntityTypeService webEntityTypeService;
    
    @Autowired
    WebEntityTypeMapper webEntityTypeMapper;
    
    
    @PostConstruct
    public void init(){
        super.addControlMapping("webEntityType", webEntityTypeService, webEntityTypeMapper);
    }
    
}