/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.leadtable.model.mappers;

import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.mapper.EntityMapperImpl;
import com.lacv.jmagrexs.modules.leadtable.model.dtos.LeadTableDto;
import com.lacv.jmagrexs.modules.leadtable.model.entities.LeadTable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author lcastrillo
 */
@Component("leadTableMapper")
public class LeadTableMapper extends EntityMapperImpl<LeadTable, LeadTableDto> implements EntityMapper<LeadTable, LeadTableDto> {

    
    @Override
    public LeadTableDto entityToDto(LeadTable entity) {
        LeadTableDto dto= new LeadTableDto();
        if(entity!=null){
            dto.setDescription(entity.getDescription());
            dto.setFileUpload(entity.getFileUpload());
            dto.setId(entity.getId());
            dto.setLink(entity.getLink());
            dto.setName(entity.getName());
            dto.setStatus(entity.getStatus());
            dto.setTableAlias(entity.getTableAlias());
        }
        return dto;
    }
    
    /**
     *
     * @param entities
     * @return
     */
    @Override
    public List<LeadTableDto> listEntitiesToListDtos(List<LeadTable> entities){
        List<LeadTableDto> dtos= new ArrayList<>();
        if(entities!=null){
            for(LeadTable entity: entities){
                dtos.add(entityToDto(entity));
            }
        }
        return dtos;
    }
    
    @Override
    public LeadTable dtoToEntity(LeadTableDto dto) {
        LeadTable entity= new LeadTable();
        if(dto!=null){
            entity.setDescription(dto.getDescription());
            entity.setFileUpload(dto.getFileUpload());
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setStatus(dto.getStatus());
            entity.setTableAlias(dto.getTableAlias());
        }
        return entity;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<LeadTable> listDtosToListEntities(List<LeadTableDto> dtos){
        List<LeadTable> entities= new ArrayList<>();
        if(entities!=null){
            for(LeadTableDto dto: dtos){
                entities.add(dtoToEntity(dto));
            }
        }
        return entities;
    }

}
