/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.security.model.mappers;

import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.mapper.EntityMapperImpl;
import com.lacv.jmagrexs.modules.security.model.dtos.RoleAuthorizationDto;
import com.lacv.jmagrexs.modules.security.model.entities.RoleAuthorization;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author lcastrillo
 */
@Component("roleAuthorizationMapper")
public class RoleAuthorizationMapper extends EntityMapperImpl<RoleAuthorization, RoleAuthorizationDto> implements EntityMapper<RoleAuthorization, RoleAuthorizationDto> {
    
    @Autowired
    AuthorizationMapper authorizationMapper;
    
    @Autowired
    RoleMapper roleMapper;

    
    @Override
    public RoleAuthorizationDto entityToDto(RoleAuthorization entity) {
        RoleAuthorizationDto dto= new RoleAuthorizationDto();
        if(entity!=null){
            dto.setAuthorization(authorizationMapper.entityToDto(entity.getAuthorization()));
            dto.setId(entity.getId());
            dto.setRole(roleMapper.entityToDto(entity.getRole()));
        }
        return dto;
    }
    
    /**
     *
     * @param entities
     * @return
     */
    @Override
    public List<RoleAuthorizationDto> listEntitiesToListDtos(List<RoleAuthorization> entities){
        List<RoleAuthorizationDto> dtos= new ArrayList<>();
        if(entities!=null){
            for(RoleAuthorization entity: entities){
                dtos.add(entityToDto(entity));
            }
        }
        return dtos;
    }
    
    @Override
    public RoleAuthorization dtoToEntity(RoleAuthorizationDto dto) {
        RoleAuthorization entity= new RoleAuthorization();
        if(dto!=null){
            entity.setAuthorization(authorizationMapper.dtoToEntity(dto.getAuthorization()));
            entity.setId(dto.getId());
            entity.setRole(roleMapper.dtoToEntity(dto.getRole()));
        }
        return entity;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<RoleAuthorization> listDtosToListEntities(List<RoleAuthorizationDto> dtos){
        List<RoleAuthorization> entities= new ArrayList<>();
        if(entities!=null){
            for(RoleAuthorizationDto dto: dtos){
                entities.add(dtoToEntity(dto));
            }
        }
        return entities;
    }

}
