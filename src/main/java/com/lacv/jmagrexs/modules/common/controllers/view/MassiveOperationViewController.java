/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.common.controllers.view;

import com.lacv.jmagrexs.modules.common.model.dtos.MassiveOperationDto;
import com.lacv.jmagrexs.modules.common.model.mappers.MassiveOperationMapper;
import com.lacv.jmagrexs.modules.common.services.MassiveOperationService;
import com.lacv.jmagrexs.controller.view.ExtEntityController;
import com.lacv.jmagrexs.dto.MenuItem;
import com.lacv.jmagrexs.dto.ProcessButton;
import com.lacv.jmagrexs.dto.config.EntityConfig;
import com.lacv.jmagrexs.modules.common.model.dtos.CancelMassiveOperationPDto;
import com.lacv.jmagrexs.modules.common.model.entities.DbOperation;
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
@RequestMapping(value="/vista/massiveOperation")
public class MassiveOperationViewController extends ExtEntityController {
    
    @Autowired
    MassiveOperationService massiveOperationService;
    
    @Autowired
    MassiveOperationMapper massiveOperationMapper;
    
    @Autowired
    SecurityService securityService;
    
    
    @PostConstruct
    public void init(){
        EntityConfig view= new EntityConfig("massiveOperation", massiveOperationService, MassiveOperationDto.class);
        view.setSingularEntityTitle("Operaci&oacute;n Masiva");
        view.setPluralEntityTitle("Operaciones Masivas");
        view.setMultipartFormData(false);
        view.setVisibleSeeAllButton(false);
        view.setDefaultOrder("id", "DESC");
        view.setGridAutoReloadInterval(5000);
        view.setLabelPlusId(true);
        view.addChildExtView("dbOperation", DbOperation.class, EntityConfig.TCV_1_TO_N);
        
        ProcessButton cancelMassiveOperationButton= new ProcessButton();
        cancelMassiveOperationButton.setMainProcessRef("processMassiveOperation");
        cancelMassiveOperationButton.setProcessName("cancelMassiveOperation");
        cancelMassiveOperationButton.setProcessTitle("Cancelar Operaci&oacute;n Masiva");
        cancelMassiveOperationButton.addSourceByDestinationField("id", "id");
        cancelMassiveOperationButton.setDtoClass(CancelMassiveOperationPDto.class);
        cancelMassiveOperationButton.setIconUrl("/libimg/ext/icon-red.png");
        view.addProcessButton(cancelMassiveOperationButton);
        
        super.addControlMapping(view);
        
        MenuItem menuItem= new MenuItem("massiveOperation", "Gestionar Operaciones Masivas");
        
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
