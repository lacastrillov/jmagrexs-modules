/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.security.controllers.view;

import com.lacv.jmagrexs.modules.security.model.dtos.WebresourceAuthorizationDto;
import com.lacv.jmagrexs.modules.security.model.mappers.WebresourceAuthorizationMapper;
import com.lacv.jmagrexs.modules.security.services.WebresourceAuthorizationService;
import com.lacv.jmagrexs.controller.view.ExtEntityController;
import com.lacv.jmagrexs.dto.config.EntityConfig;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author lacastrillov
 */
@Controller
@RequestMapping(value="/vista/webresourceAuthorization")
public class WebresourceAuthorizationViewController extends ExtEntityController {
    
    @Autowired
    WebresourceAuthorizationService webresourceAuthorizationService;
    
    @Autowired
    WebresourceAuthorizationMapper webresourceAuthorizationMapper;
    
    
    @PostConstruct
    public void init(){
        EntityConfig view= new EntityConfig("webresourceAuthorization", webresourceAuthorizationService, WebresourceAuthorizationDto.class);
        view.setSingularEntityTitle("Autorizaci&oacute;n de Recurso Web");
        view.setPluralEntityTitle("Autorizaciones de Recursos Web");
        view.activateNNMulticheckChild("authorization");
        super.addControlMapping(view);
    }
    
    
}
