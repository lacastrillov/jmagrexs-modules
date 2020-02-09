/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.common.controllers.rest;


import com.lacv.jmagrexs.annotation.DoProcess;
import com.lacv.jmagrexs.controller.rest.RestProcessController;
import com.lacv.jmagrexs.modules.common.model.dtos.BasicResultDto;
import com.lacv.jmagrexs.modules.common.model.dtos.CancelMassiveOperationPDto;
import com.lacv.jmagrexs.modules.common.model.entities.LogProcess;
import com.lacv.jmagrexs.modules.common.model.entities.MassiveOperation;
import com.lacv.jmagrexs.modules.common.services.LogProcessService;
import com.lacv.jmagrexs.modules.common.services.MassiveOperationService;
import com.lacv.jmagrexs.modules.security.services.bussiness.SecurityService;
import com.lacv.jmagrexs.modules.security.model.entities.User;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author lcastrillo
 */
@Controller
@RequestMapping(value="/rest/processMassiveOperation")
public class MassiveOperationProcessRestController extends RestProcessController {
    
    @Autowired
    MassiveOperationService massiveOperationService;
    
    @Autowired(required=false)
    LogProcessService logProcessService;
    
    @Autowired
    SecurityService securityService;
    
    @PostConstruct
    public void init(){
        super.addControlProcess("processMassiveOperation", LogProcess.class, logProcessService);
    }
    
    @Override
    public String getClientId(){
        User user= securityService.getCurrentUser();
        return user.getUsername();
    }
    
    @DoProcess
    public BasicResultDto cancelMassiveOperation(CancelMassiveOperationPDto cancelMassiveOperation){
        BasicResultDto result= new BasicResultDto();
        result.setUsername(getClientId());
        MassiveOperation massiveOperation= massiveOperationService.loadById(cancelMassiveOperation.getId());
        if(massiveOperation!=null){
            if(!massiveOperation.getStatus().equals("Terminado")){
                massiveOperation.setStatus("Cancelado");
                massiveOperationService.update(massiveOperation);
                result.setMessage("La operaci&oacute;n masiva fue cancelada");
                result.setSuccess(true);
            }else{
                result.setMessage("La operaci&oacute;n masiva ya termino");
                result.setSuccess(false);
            }
        }else{
            result.setMessage("La operaci&oacute;n masiva no existe!!!");
            result.setSuccess(false);
        }
        
        return result;
    }
    
}
