/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.entityexplorer.mappers;

import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.mapper.EntityMapperImpl;
import com.lacv.jmagrexs.modules.entityexplorer.dtos.WebEntityTypeDto;
import com.lacv.jmagrexs.modules.entityexplorer.entities.WebEntityType;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
            dto.setEntityRef(entity.getEntityRef());
            dto.setEntityOrder(entity.getEntityOrder());
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
    
}
