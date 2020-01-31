/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.security.controllers.view;

import com.lacv.jmagrexs.modules.security.model.dtos.RoleAuthorizationDto;
import com.lacv.jmagrexs.modules.security.model.mappers.RoleAuthorizationMapper;
import com.lacv.jmagrexs.modules.security.services.RoleAuthorizationService;
import com.lacv.jmagrexs.controller.view.ExtEntityController;
import com.lacv.jmagrexs.dto.MenuItem;
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
@RequestMapping(value="/vista/roleAuthorization")
public class RoleAuthorizationViewController extends ExtEntityController {
    
    @Autowired
    RoleAuthorizationService roleAuthorizationService;
    
    @Autowired
    RoleAuthorizationMapper roleAuthorizationMapper;
    
    
    @PostConstruct
    public void init(){
        EntityConfig view= new EntityConfig("roleAuthorization", roleAuthorizationService, RoleAuthorizationDto.class);
        view.setSingularEntityTitle("Autorizaci&oacute;n de Rol");
        view.setPluralEntityTitle("Autorizaciones de Roles");
        view.activateNNMulticheckChild("authorization");
        super.addControlMapping(view);
        
        MenuItem menuItem= new MenuItem("roleAuthorization", "Gestionar Autorizaciones de Roles", 3);
        
        MenuItem menuParent1= new MenuItem("Seguridad", 1);
        menuParent1.addSubMenu(menuItem);
        
        MenuItem menuParent= new MenuItem("Sistema");
        menuParent.addSubMenu(menuParent1);
        
        menuComponent.addItemMenu(menuParent);
    }
    
    
}
