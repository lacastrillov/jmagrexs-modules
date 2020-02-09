/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.common.services;


import com.lacv.jmagrexs.modules.common.daos.DbOperationJpa;
import com.lacv.jmagrexs.modules.common.model.entities.DbOperation;
import com.lacv.jmagrexs.modules.common.model.mappers.DbOperationMapper;
import com.lacv.jmagrexs.dao.GenericDao;
import com.lacv.jmagrexs.domain.BaseDto;
import com.lacv.jmagrexs.interfaces.MassiveOperationInterface;
import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.modules.security.model.entities.User;
import com.lacv.jmagrexs.modules.security.services.bussiness.SecurityService;
import com.lacv.jmagrexs.service.EntityServiceImpl;
import com.lacv.jmagrexs.util.JSONService;
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
@Service("dbOperationService")
public class DbOperationServiceImpl extends EntityServiceImpl<DbOperation> implements DbOperationService {
    
    @Autowired
    public DbOperationJpa dbOperationJpa;
    
    @Autowired
    public DbOperationMapper dbOperationMapper;
    
    @Autowired
    public MassiveOperationService massiveOperationService;
    
    @Autowired
    public SecurityService securityService;
    
    
    @Override
    public GenericDao getGenericDao(){
        return dbOperationJpa;
    }
    
    @Override
    public EntityMapper getEntityMapper(){
        return dbOperationMapper;
    }
    
    
    /**
     * 
     * @param entityRef
     * @param type
     * @param object
     * @param message
     * @param success
     * @param massiveOperation 
     */
    @Override
    @Transactional(value = TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public void save(String entityRef, String type, Object object, String message, Boolean success, MassiveOperationInterface massiveOperation){
        DbOperation dbOperation= new DbOperation();
        dbOperation.setEntityRef(entityRef);
        dbOperation.setName(type+" "+entityRef);
        dbOperation.setType(type);
        dbOperation.setMessage(message);
        dbOperation.setSuccess(success);
        dbOperation.setMassiveOperation(massiveOperation);
        dbOperation.setRegistrationDate(new Date());
        dbOperation.setRecordTime(Util.getCurrentTime(0));
        dbOperation.setUser(getUser());
        if(object!=null){
            if(BaseDto.class.isAssignableFrom(object.getClass())){
                BaseDto dto= (BaseDto) object;
                dbOperation.setDataNew(JSONService.objectToJson(dto));
                if(dto.getId()!=null) dbOperation.setEntityId(dto.getId().toString());
            }else if(object.getClass()==String.class){
                dbOperation.setDataNew((String)object);
            }
        }
        super.create(dbOperation);
        
        if(massiveOperation!=null){
            massiveOperationService.move(massiveOperation, success);
        }
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
