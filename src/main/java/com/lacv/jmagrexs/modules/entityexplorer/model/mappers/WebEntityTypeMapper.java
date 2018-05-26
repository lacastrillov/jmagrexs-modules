/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.entityexplorer.model.mappers;

import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.mapper.EntityMapperImpl;
import com.lacv.jmagrexs.modules.entityexplorer.model.dtos.WebEntityTypeDto;
import com.lacv.jmagrexs.modules.entityexplorer.model.entities.WebEntityType;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author lcastrillo
 */
@Component("webEntityTypeMapper")
public class WebEntityTypeMapper extends EntityMapperImpl<WebEntityType, WebEntityTypeDto> implements EntityMapper<WebEntityType, WebEntityTypeDto> {

    
    @Override
    public WebEntityTypeDto entityToDto(WebEntityType entity) {
        WebEntityTypeDto dto= new WebEntityTypeDto();
        if(entity!=null){
            dto.setClassName(entity.getClassName());
            dto.setEntityName(entity.getEntityName());
            dto.setEntityOrder(entity.getEntityOrder());
            dto.setEntityRef(entity.getEntityRef());
            dto.setIcon(entity.getIcon());
            dto.setId(entity.getId());
            dto.setServiceName(entity.getServiceName());
            dto.setStatus(entity.getStatus());
        }
        return dto;
    }
    
    /**
     *
     * @param entities
     * @return
     */
    @Override
    public List<WebEntityTypeDto> listEntitiesToListDtos(List<WebEntityType> entities){
        List<WebEntityTypeDto> dtos= new ArrayList<>();
        if(entities!=null){
            for(WebEntityType entity: entities){
                dtos.add(entityToDto(entity));
            }
        }
        return dtos;
    }
    
    @Override
    public WebEntityType dtoToEntity(WebEntityTypeDto dto) {
        WebEntityType entity= new WebEntityType();
        if(dto!=null){
            entity.setClassName(dto.getClassName());
            entity.setEntityName(dto.getEntityName());
            entity.setEntityOrder(dto.getEntityOrder());
            entity.setEntityRef(dto.getEntityRef());
            entity.setIcon(dto.getIcon());
            entity.setId(dto.getId());
            entity.setServiceName(dto.getServiceName());
            entity.setStatus(dto.getStatus());
        }
        return entity;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<WebEntityType> listDtosToListEntities(List<WebEntityTypeDto> dtos){
        List<WebEntityType> entities= new ArrayList<>();
        if(entities!=null){
            for(WebEntityTypeDto dto: dtos){
                entities.add(dtoToEntity(dto));
            }
        }
        return entities;
    }

}
