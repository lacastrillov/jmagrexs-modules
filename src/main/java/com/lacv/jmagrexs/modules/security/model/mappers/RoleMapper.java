/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.security.model.mappers;

import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.mapper.EntityMapperImpl;
import com.lacv.jmagrexs.modules.security.model.dtos.RoleDto;
import com.lacv.jmagrexs.modules.security.model.entities.Role;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author lcastrillo
 */
@Component("roleMapper")
public class RoleMapper extends EntityMapperImpl<Role, RoleDto> implements EntityMapper<Role, RoleDto> {

    
    @Override
    public RoleDto entityToDto(Role entity) {
        RoleDto dto= new RoleDto();
        if(entity!=null){
            dto.setDescription(entity.getDescription());
            dto.setHomePage(entity.getHomePage());
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setPriorityCheck(entity.getPriorityCheck());
        }
        return dto;
    }
    
    /**
     *
     * @param entities
     * @return
     */
    @Override
    public List<RoleDto> listEntitiesToListDtos(List<Role> entities){
        List<RoleDto> dtos= new ArrayList<>();
        if(entities!=null){
            for(Role entity: entities){
                dtos.add(entityToDto(entity));
            }
        }
        return dtos;
    }
    
    @Override
    public Role dtoToEntity(RoleDto dto) {
        Role entity= new Role();
        if(dto!=null){
            entity.setDescription(dto.getDescription());
            entity.setHomePage(dto.getHomePage());
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setPriorityCheck(dto.getPriorityCheck());
        }
        return entity;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<Role> listDtosToListEntities(List<RoleDto> dtos){
        List<Role> entities= new ArrayList<>();
        if(entities!=null){
            for(RoleDto dto: dtos){
                entities.add(dtoToEntity(dto));
            }
        }
        return entities;
    }

}
