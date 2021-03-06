/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.security.controllers.view;

import com.lacv.jmagrexs.modules.security.model.dtos.WebResourceDto;
import com.lacv.jmagrexs.modules.security.model.mappers.WebResourceMapper;
import com.lacv.jmagrexs.modules.security.services.WebResourceService;
import com.lacv.jmagrexs.controller.view.ExtEntityController;
import com.lacv.jmagrexs.dto.MenuItem;
import com.lacv.jmagrexs.dto.config.EntityConfig;
import com.lacv.jmagrexs.modules.security.model.entities.WebresourceAuthorization;
import com.lacv.jmagrexs.modules.security.model.entities.WebresourceRole;
import com.lacv.jmagrexs.modules.security.services.bussiness.SecurityService;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author lacastrillov
 */
@Controller
@RequestMapping(value="/vista/webResource")
public class WebResourceViewController extends ExtEntityController {
    
    @Autowired
    WebResourceService webResourceService;
    
    @Autowired
    WebResourceMapper webResourceMapper;
    
    @Autowired
    SecurityService securityService;
    
    
    @PostConstruct
    public void init(){
        EntityConfig view= new EntityConfig("webResource", webResourceService, WebResourceDto.class);
        view.setSingularEntityTitle("Recurso Web");
        view.setPluralEntityTitle("Recursos Web");
        view.addChildExtView("webresourceRole", WebresourceRole.class, EntityConfig.TCV_N_TO_N);
        view.addChildExtView("webresourceAuthorization", WebresourceAuthorization.class, EntityConfig.TCV_N_TO_N);
        view.setDefaultOrderBy("module");
        view.setDefaultOrderDir("ASC");
        super.addControlMapping(view);
        
        MenuItem menuItem= new MenuItem("webResource", "Gestionar Recursos Web", 6);
        
        MenuItem menuParent1= new MenuItem("Seguridad");
        menuParent1.addSubMenu(menuItem);
        
        MenuItem menuParent= new MenuItem("Sistema");
        menuParent.addSubMenu(menuParent1);
        
        menuComponent.addItemMenu(menuParent);
    }
    
    @Override
    public List<MenuItem> configureVisibilityMenu(List<MenuItem> menuData){
        return securityService.configureVisibilityMenu(menuData);
    }
    
}
