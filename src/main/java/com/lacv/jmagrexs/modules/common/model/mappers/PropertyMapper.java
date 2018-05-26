/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.common.model.mappers;

import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.mapper.EntityMapperImpl;
import com.lacv.jmagrexs.modules.common.model.dtos.PropertyDto;
import com.lacv.jmagrexs.modules.common.model.entities.Property;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author lcastrillo
 */
@Component("propertyMapper")
public class PropertyMapper extends EntityMapperImpl<Property, PropertyDto> implements EntityMapper<Property, PropertyDto> {

    
    @Override
    public PropertyDto entityToDto(Property entity) {
        PropertyDto dto= new PropertyDto();
        if(entity!=null){
            dto.setId(entity.getId());
            dto.setKey(entity.getKey());
            dto.setStatus(entity.getStatus());
            dto.setType(entity.getType());
            dto.setValue(entity.getValue());
        }
        return dto;
    }
    
    /**
     *
     * @param entities
     * @return
     */
    @Override
    public List<PropertyDto> listEntitiesToListDtos(List<Property> entities){
        List<PropertyDto> dtos= new ArrayList<>();
        if(entities!=null){
            for(Property entity: entities){
                dtos.add(entityToDto(entity));
            }
        }
        return dtos;
    }
    
    @Override
    public Property dtoToEntity(PropertyDto dto) {
        Property entity= new Property();
        if(dto!=null){
            entity.setId(dto.getId());
            entity.setKey(dto.getKey());
            entity.setStatus(dto.getStatus());
            entity.setType(dto.getType());
            entity.setValue(dto.getValue());
        }
        return entity;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<Property> listDtosToListEntities(List<PropertyDto> dtos){
        List<Property> entities= new ArrayList<>();
        if(entities!=null){
            for(PropertyDto dto: dtos){
                entities.add(dtoToEntity(dto));
            }
        }
        return entities;
    }

}
