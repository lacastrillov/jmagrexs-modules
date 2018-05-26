/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.entityexplorer.model.mappers;

import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.mapper.EntityMapperImpl;
import com.lacv.jmagrexs.modules.entityexplorer.model.dtos.WebEntityDto;
import com.lacv.jmagrexs.modules.entityexplorer.model.entities.WebEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author lcastrillo
 */
@Component("webEntityMapper")
public class WebEntityMapper extends EntityMapperImpl<WebEntity, WebEntityDto> implements EntityMapper<WebEntity, WebEntityDto> {
    
    @Autowired
    WebEntityTypeMapper webEntityTypeMapper;

    
    @Override
    public WebEntityDto entityToDto(WebEntity entity) {
        WebEntityDto dto= new WebEntityDto();
        if(entity!=null){
            dto.setAuthor(entity.getAuthor());
            dto.setCreationDate(entity.getCreationDate());
            dto.setEntityId(entity.getEntityId());
            dto.setEntityOrder(entity.getEntityOrder());
            dto.setId(entity.getId());
            dto.setLocation(entity.getLocation());
            dto.setModificationDate(entity.getModificationDate());
            dto.setName(entity.getName());
            dto.setPath(entity.getPath());
            dto.setStatus(entity.getStatus());
            dto.setWebEntity(entityToDto(entity.getWebEntity()));
            dto.setWebEntityType(webEntityTypeMapper.entityToDto(entity.getWebEntityType()));
        }
        return dto;
    }
    
    /**
     *
     * @param entities
     * @return
     */
    @Override
    public List<WebEntityDto> listEntitiesToListDtos(List<WebEntity> entities){
        List<WebEntityDto> dtos= new ArrayList<>();
        if(entities!=null){
            for(WebEntity entity: entities){
                dtos.add(entityToDto(entity));
            }
        }
        return dtos;
    }
    
    @Override
    public WebEntity dtoToEntity(WebEntityDto dto) {
        WebEntity entity= new WebEntity();
        if(dto!=null){
            entity.setAuthor(dto.getAuthor());
            entity.setCreationDate(dto.getCreationDate());
            entity.setEntityId(dto.getEntityId());
            entity.setEntityOrder(dto.getEntityOrder());
            entity.setId(dto.getId());
            entity.setModificationDate(dto.getModificationDate());
            entity.setName(dto.getName());
            entity.setStatus(dto.getStatus());
            entity.setWebEntity(dtoToEntity(dto.getWebEntity()));
            entity.setWebEntityType(webEntityTypeMapper.dtoToEntity(dto.getWebEntityType()));
        }
        return entity;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<WebEntity> listDtosToListEntities(List<WebEntityDto> dtos){
        List<WebEntity> entities= new ArrayList<>();
        if(entities!=null){
            for(WebEntityDto dto: dtos){
                entities.add(dtoToEntity(dto));
            }
        }
        return entities;
    }

}
