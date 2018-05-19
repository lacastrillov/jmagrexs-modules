/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.entityexplorer.services;


import com.lacv.jmagrexs.modules.entityexplorer.daos.WebEntityTypeJpa;
import com.lacv.jmagrexs.modules.entityexplorer.entities.WebEntityType;
import com.lacv.jmagrexs.modules.entityexplorer.mappers.WebEntityTypeMapper;
import com.lacv.jmagrexs.modules.entityexplorer.services.WebEntityTypeService;
import com.lacv.jmagrexs.dao.GenericDao;
import com.lacv.jmagrexs.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lcastrillo
 */
@Service("webEntityTypeService")
public class WebEntityTypeServiceImpl extends EntityServiceImpl<WebEntityType> implements WebEntityTypeService {
    
    @Autowired
    public WebEntityTypeJpa webEntityTypeJpa;
    
    @Autowired
    public WebEntityTypeMapper webEntityTypeMapper;
    
    @Override
    public GenericDao getGenericDao(){
        return webEntityTypeJpa;
    }
    
}