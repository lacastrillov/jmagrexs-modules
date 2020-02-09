/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.common.services;


import com.lacv.jmagrexs.modules.common.daos.MassiveOperationJpa;
import com.lacv.jmagrexs.modules.common.model.entities.MassiveOperation;
import com.lacv.jmagrexs.modules.common.model.mappers.MassiveOperationMapper;
import com.lacv.jmagrexs.dao.GenericDao;
import com.lacv.jmagrexs.interfaces.MassiveOperationInterface;
import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.modules.security.model.entities.User;
import com.lacv.jmagrexs.modules.security.services.bussiness.SecurityService;
import com.lacv.jmagrexs.service.EntityServiceImpl;
import com.lacv.jmagrexs.util.Util;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lcastrillo
 */
@Service("massiveOperationService")
public class MassiveOperationServiceImpl extends EntityServiceImpl<MassiveOperation> implements MassiveOperationService {
    
    @Autowired
    public MassiveOperationJpa massiveOperationJpa;
    
    @Autowired
    public MassiveOperationMapper massiveOperationMapper;
    
    @Autowired
    public SecurityService securityService;
    
    
    @Override
    public GenericDao getGenericDao(){
        return massiveOperationJpa;
    }
    
    @Override
    public EntityMapper getEntityMapper(){
        return massiveOperationMapper;
    }
    
    /**
     * 
     * @param entityRef
     * @param type
     * @param total
     * @param message
     * @return 
     */
    @Override
    @Transactional(value = TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public MassiveOperationInterface start(String entityRef, String type, int total, String message){
        MassiveOperation massiveOperation= new MassiveOperation();
        massiveOperation.setName("Massive Operation "+entityRef);
        massiveOperation.setType(type);
        massiveOperation.setMessage(message);
        massiveOperation.setStatus("Creado");
        massiveOperation.setUser(getUser());
        massiveOperation.setRecordTime(Util.getCurrentTime(0));
        massiveOperation.setRegistrationDate(new Date());
        massiveOperation.setTotal(total);
        massiveOperation.setDuration(0);
        massiveOperation.setPercentage(0);
        massiveOperation.setProcessed(0);
        massiveOperation.setTotalFailed(0);
        massiveOperation.setTotalSuccessful(0);
        super.create(massiveOperation);
        
        return massiveOperation;
    }
    
    /**
     * 
     * @param massiveOperation
     * @param success 
     */
    @Override
    @Transactional(value = TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public void move(MassiveOperationInterface massiveOperation, Boolean success){
        super.reload((MassiveOperation) massiveOperation);
        long duration= new Date().getTime() - massiveOperation.getRegistrationDate().getTime();
        massiveOperation.setDuration((int)duration);
        massiveOperation.setProcessed(massiveOperation.getProcessed()+1);
        if(success){
            massiveOperation.setTotalSuccessful(massiveOperation.getTotalSuccessful()+1);
        }else{
            massiveOperation.setTotalFailed(massiveOperation.getTotalFailed()+1);
        }
        if(massiveOperation.getTotal()>0){
            massiveOperation.setPercentage(100*massiveOperation.getProcessed()/massiveOperation.getTotal());
        }
        if(!massiveOperation.getStatus().equals("Cancelado")){
            massiveOperation.setStatus("Procesando");
        }
        super.update((MassiveOperation)massiveOperation);
    }
    
    /**
     * 
     * @param massiveOperation 
     * @param message 
     */
    @Override
    @Transactional(value = TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public void end(MassiveOperationInterface massiveOperation, String message){
        massiveOperation.setMessage(message);
        if(!massiveOperation.getStatus().equals("Cancelado")){
            massiveOperation.setStatus("Terminado");
        }
        super.update((MassiveOperation)massiveOperation);
    }
    
    /**
     * 
     * @return currentUser in Session
     */
    private User getUser(){
        if(securityService!=null){
            return securityService.getCurrentUser();
        }
        return null;
    }
    
}
