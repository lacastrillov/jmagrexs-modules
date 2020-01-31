/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.fileexplorer.controllers.view;

import com.lacv.jmagrexs.modules.fileexplorer.model.dtos.WebFileDto;
import com.lacv.jmagrexs.modules.fileexplorer.model.mappers.WebFileMapper;
import com.lacv.jmagrexs.modules.fileexplorer.services.WebFileService;
import com.lacv.jmagrexs.controller.view.ExtFileExplorerController;
import com.lacv.jmagrexs.dto.GridTemplate;
import com.lacv.jmagrexs.dto.MenuItem;
import com.lacv.jmagrexs.dto.config.FileExplorerConfig;
import com.lacv.jmagrexs.enums.PageType;
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
@RequestMapping(value="/vista/webFile")
public class WebFileViewController extends ExtFileExplorerController {
    
    @Autowired
    WebFileService webFileService;
    
    @Autowired
    WebFileMapper webFileMapper;
    
    @Autowired
    SecurityService securityService;
    
    
    @PostConstruct
    public void init(){
        FileExplorerConfig view= new FileExplorerConfig("webFile", webFileService, WebFileDto.class);
        view.setSingularEntityTitle("Archivo Web");
        view.setPluralEntityTitle("Archivos Web");
        view.setMultipartFormData(true);
        view.setDefaultOrder("type", "ASC");
        
        GridTemplate gridTemplate= new GridTemplate("webFile.vm");
        gridTemplate.setNumColumns(6);
        view.setGridTemplate(gridTemplate);
        view.setActiveGridTemplate(true);
        view.setActiveGridTemplateAsParent(true);
        
        super.addControlMapping(view);
        
        MenuItem menuItem= new MenuItem("webFile", "Explorador de Archivos", 3);
        menuItem.setPageType(PageType.FILE_EXPLORER);
        
        MenuItem menuParent1= new MenuItem("Gestor de Contenidos");
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
