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
import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
    @Override
    public GenericDao getGenericDao(){
        return massiveOperationJpa;
    }
    
    @Override
    public EntityMapper getEntityMapper(){
        return massiveOperationMapper;
    }
    
}