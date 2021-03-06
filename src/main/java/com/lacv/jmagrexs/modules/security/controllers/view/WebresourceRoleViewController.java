/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.security.controllers.view;

import com.lacv.jmagrexs.modules.security.model.dtos.WebresourceRoleDto;
import com.lacv.jmagrexs.modules.security.model.mappers.WebresourceRoleMapper;
import com.lacv.jmagrexs.modules.security.services.WebresourceRoleService;
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
@RequestMapping(value="/vista/webresourceRole")
public class WebresourceRoleViewController extends ExtEntityController {
    
    @Autowired
    WebresourceRoleService webresourceRoleService;
    
    @Autowired
    WebresourceRoleMapper webresourceRoleMapper;
    
    
    @PostConstruct
    public void init(){
        EntityConfig view= new EntityConfig("webresourceRole", webresourceRoleService, WebresourceRoleDto.class);
        view.setSingularEntityTitle("Rol de Recurso Web");
        view.setPluralEntityTitle("Roles de Recursos Web");
        view.activateNNMulticheckChild("role");
        super.addControlMapping(view);
        
        MenuItem menuItem= new MenuItem("webresourceRole", "Gestionar Recursos Web por Roles", 7);
        
        MenuItem menuParent1= new MenuItem("Seguridad");
        menuParent1.addSubMenu(menuItem);
        
        MenuItem menuParent= new MenuItem("Sistema");
        menuParent.addSubMenu(menuParent1);
        
        menuComponent.addItemMenu(menuParent);
    }
    
    
}
