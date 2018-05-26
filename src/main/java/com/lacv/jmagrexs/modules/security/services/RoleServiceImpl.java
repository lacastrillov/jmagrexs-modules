/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.security.services;


import com.lacv.jmagrexs.modules.security.daos.RoleJpa;
import com.lacv.jmagrexs.modules.security.model.mappers.RoleMapper;
import com.lacv.jmagrexs.dao.GenericDao;
import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.modules.security.model.entities.Role;
import com.lacv.jmagrexs.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lcastrillo
 */
@Service("roleService")
public class RoleServiceImpl extends EntityServiceImpl<Role> implements RoleService {
    
    @Autowired
    public RoleJpa roleJpa;
    
    @Autowired
    public RoleMapper roleMapper;
    
    @Override
    public GenericDao getGenericDao(){
        return roleJpa;
    }

    @Override
    public EntityMapper getEntityMapper() {
        return roleMapper;
    }
    
}
