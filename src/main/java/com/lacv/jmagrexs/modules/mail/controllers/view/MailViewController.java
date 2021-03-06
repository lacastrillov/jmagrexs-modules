/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.mail.controllers.view;

import com.lacv.jmagrexs.modules.mail.model.dtos.MailDto;
import com.lacv.jmagrexs.modules.mail.model.mappers.MailMapper;
import com.lacv.jmagrexs.modules.mail.services.MailService;
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
@RequestMapping(value="/vista/mail")
public class MailViewController extends ExtEntityController {
    
    @Autowired
    MailService mailService;
    
    @Autowired
    MailMapper mailMapper;
    
    @Autowired
    SecurityService securityService;
    
    
    @PostConstruct
    public void init(){
        EntityConfig view= new EntityConfig("mail", mailService, MailDto.class);
        view.setSingularEntityTitle("Correo");
        view.setPluralEntityTitle("Correos");
        view.setEditableForm(false);
        view.setEditableGrid(false);
        view.setVisibleSeeAllButton(true);
        super.addControlMapping(view);
        
        MenuItem menuItem= new MenuItem("mail", "Gestionar Correos", 1);
        
        MenuItem menuParent1= new MenuItem("Correos");
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
