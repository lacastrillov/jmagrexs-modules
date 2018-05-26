/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.security.model.mappers;

import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.mapper.EntityMapperImpl;
import com.lacv.jmagrexs.modules.security.model.dtos.WebresourceRoleDto;
import com.lacv.jmagrexs.modules.security.model.entities.WebresourceRole;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author lcastrillo
 */
@Component("webresourceRoleMapper")
public class WebresourceRoleMapper extends EntityMapperImpl<WebresourceRole, WebresourceRoleDto> implements EntityMapper<WebresourceRole, WebresourceRoleDto> {
    
    @Autowired
    RoleMapper roleMapper;
    
    @Autowired
    WebResourceMapper webResourceMapper;

    
    @Override
    public WebresourceRoleDto entityToDto(WebresourceRole entity) {
        WebresourceRoleDto dto= new WebresourceRoleDto();
        if(entity!=null){
            dto.setId(entity.getId());
            dto.setRole(roleMapper.entityToDto(entity.getRole()));
            dto.setWebResource(webResourceMapper.entityToDto(entity.getWebResource()));
        }
        return dto;
    }
    
    /**
     *
     * @param entities
     * @return
     */
    @Override
    public List<WebresourceRoleDto> listEntitiesToListDtos(List<WebresourceRole> entities){
        List<WebresourceRoleDto> dtos= new ArrayList<>();
        if(entities!=null){
            for(WebresourceRole entity: entities){
                dtos.add(entityToDto(entity));
            }
        }
        return dtos;
    }
    
    @Override
    public WebresourceRole dtoToEntity(WebresourceRoleDto dto) {
        WebresourceRole entity= new WebresourceRole();
        if(dto!=null){
            entity.setId(dto.getId());
            entity.setRole(roleMapper.dtoToEntity(dto.getRole()));
            entity.setWebResource(webResourceMapper.dtoToEntity(dto.getWebResource()));
        }
        return entity;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<WebresourceRole> listDtosToListEntities(List<WebresourceRoleDto> dtos){
        List<WebresourceRole> entities= new ArrayList<>();
        if(entities!=null){
            for(WebresourceRoleDto dto: dtos){
                entities.add(dtoToEntity(dto));
            }
        }
        return entities;
    }

}
