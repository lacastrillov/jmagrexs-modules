/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.security.model.mappers;

import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.mapper.EntityMapperImpl;
import com.lacv.jmagrexs.modules.security.model.dtos.UserRoleDto;
import com.lacv.jmagrexs.modules.security.model.entities.UserRole;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author lcastrillo
 */
@Component("userRoleMapper")
public class UserRoleMapper extends EntityMapperImpl<UserRole, UserRoleDto> implements EntityMapper<UserRole, UserRoleDto> {
    
    @Autowired
    RoleMapper roleMapper;
    
    @Autowired
    UserMapper userMapper;

    
    @Override
    public UserRoleDto entityToDto(UserRole entity) {
        UserRoleDto dto= new UserRoleDto();
        if(entity!=null){
            dto.setId(entity.getId());
            dto.setRole(roleMapper.entityToDto(entity.getRole()));
            dto.setUser(userMapper.entityToDto(entity.getUser()));
        }
        return dto;
    }
    
    /**
     *
     * @param entities
     * @return
     */
    @Override
    public List<UserRoleDto> listEntitiesToListDtos(List<UserRole> entities){
        List<UserRoleDto> dtos= new ArrayList<>();
        if(entities!=null){
            for(UserRole entity: entities){
                dtos.add(entityToDto(entity));
            }
        }
        return dtos;
    }
    
    @Override
    public UserRole dtoToEntity(UserRoleDto dto) {
        UserRole entity= new UserRole();
        if(dto!=null){
            entity.setId(dto.getId());
            entity.setRole(roleMapper.dtoToEntity(dto.getRole()));
            entity.setUser(userMapper.dtoToEntity(dto.getUser()));
        }
        return entity;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<UserRole> listDtosToListEntities(List<UserRoleDto> dtos){
        List<UserRole> entities= new ArrayList<>();
        if(entities!=null){
            for(UserRoleDto dto: dtos){
                entities.add(dtoToEntity(dto));
            }
        }
        return entities;
    }

}
