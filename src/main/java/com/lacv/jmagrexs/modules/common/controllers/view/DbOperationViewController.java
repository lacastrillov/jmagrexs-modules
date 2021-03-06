/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.common.controllers.view;

import com.lacv.jmagrexs.modules.common.model.dtos.DbOperationDto;
import com.lacv.jmagrexs.modules.common.model.mappers.DbOperationMapper;
import com.lacv.jmagrexs.modules.common.services.DbOperationService;
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
@RequestMapping(value="/vista/dbOperation")
public class DbOperationViewController extends ExtEntityController {
    
    @Autowired
    DbOperationService dbOperationService;
    
    @Autowired
    DbOperationMapper dbOperationMapper;
    
    @Autowired
    SecurityService securityService;
    
    
    @PostConstruct
    public void init(){
        EntityConfig view= new EntityConfig("dbOperation", dbOperationService, DbOperationDto.class);
        view.setSingularEntityTitle("Operaci&oacute;n de Base de Datos");
        view.setPluralEntityTitle("Operaciones de Base de Datos");
        view.setMultipartFormData(false);
        view.setVisibleSeeAllButton(false);
        view.setDefaultOrder("id", "DESC");
        super.addControlMapping(view);
        
        MenuItem menuItem= new MenuItem("dbOperation", "Gestionar Operaciones de Base de Datos");
        
        MenuItem menuParent1= new MenuItem("Registros", 3);
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
