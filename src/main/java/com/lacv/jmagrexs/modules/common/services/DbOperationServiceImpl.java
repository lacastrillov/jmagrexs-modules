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
import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
    @Override
    public GenericDao getGenericDao(){
        return dbOperationJpa;
    }
    
    @Override
    public EntityMapper getEntityMapper(){
        return dbOperationMapper;
    }
    
}