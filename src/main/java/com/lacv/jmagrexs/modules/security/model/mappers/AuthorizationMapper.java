/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.security.model.mappers;

import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.mapper.EntityMapperImpl;
import com.lacv.jmagrexs.modules.security.model.dtos.AuthorizationDto;
import com.lacv.jmagrexs.modules.security.model.entities.Authorization;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author lcastrillo
 */
@Component("authorizationMapper")
public class AuthorizationMapper extends EntityMapperImpl<Authorization, AuthorizationDto> implements EntityMapper<Authorization, AuthorizationDto> {

    
    @Override
    public AuthorizationDto entityToDto(Authorization entity) {
        AuthorizationDto dto= new AuthorizationDto();
        if(entity!=null){
            dto.setDescription(entity.getDescription());
            dto.setId(entity.getId());
            dto.setName(entity.getName());
        }
        return dto;
    }
    
    /**
     *
     * @param entities
     * @return
     */
    @Override
    public List<AuthorizationDto> listEntitiesToListDtos(List<Authorization> entities){
        List<AuthorizationDto> dtos= new ArrayList<>();
        if(entities!=null){
            for(Authorization entity: entities){
                dtos.add(entityToDto(entity));
            }
        }
        return dtos;
    }
    
    @Override
    public Authorization dtoToEntity(AuthorizationDto dto) {
        Authorization entity= new Authorization();
        if(dto!=null){
            entity.setDescription(dto.getDescription());
            entity.setId(dto.getId());
            entity.setName(dto.getName());
        }
        return entity;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<Authorization> listDtosToListEntities(List<AuthorizationDto> dtos){
        List<Authorization> entities= new ArrayList<>();
        if(entities!=null){
            for(AuthorizationDto dto: dtos){
                entities.add(dtoToEntity(dto));
            }
        }
        return entities;
    }

}
