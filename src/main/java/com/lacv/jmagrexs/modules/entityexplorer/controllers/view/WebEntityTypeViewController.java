/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.entityexplorer.controllers.view;

import com.lacv.jmagrexs.modules.entityexplorer.dtos.WebEntityTypeDto;
import com.lacv.jmagrexs.modules.entityexplorer.mappers.WebEntityTypeMapper;
import com.lacv.jmagrexs.modules.entityexplorer.services.WebEntityTypeService;
import com.lacv.jmagrexs.controller.view.ExtEntityController;
import com.lacv.jmagrexs.dto.MenuItem;
import com.lacv.jmagrexs.dto.config.EntityConfig;
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
@RequestMapping(value="/vista/webEntityType")
public class WebEntityTypeViewController extends ExtEntityController {
    
    @Autowired
    WebEntityTypeService webEntityTypeService;
    
    @Autowired
    WebEntityTypeMapper webEntityTypeMapper;
    
    @Autowired
    SecurityService securityService;
    
    
    @PostConstruct
    public void init(){
        EntityConfig view= new EntityConfig("webEntityType", webEntityTypeService, WebEntityTypeDto.class);
        view.setSingularEntityTitle("Tipo Entidad");
        view.setPluralEntityTitle("Tipos de Entidades");
        view.setMultipartFormData(false);
        view.setVisibleSeeAllButton(false);
        view.setDefaultOrder("entityName", "ASC");
        super.addControlMapping(view);
        
        MenuItem menuParent= new MenuItem("Gestor de Contenidos", 1);
        MenuItem menuItem= new MenuItem("webEntityType", "Gestionar Tipos de Entidades", 1);
        menuParent.addSubMenu(menuItem);
        menuComponent.addItemMenu(menuParent);
    }
    
    @Override
    public List<MenuItem> configureVisibilityMenu(List<MenuItem> menuData){
        return securityService.configureVisibilityMenu(menuData);
    }
    
}