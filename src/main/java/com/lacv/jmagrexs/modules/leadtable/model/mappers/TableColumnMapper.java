/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.leadtable.model.mappers;

import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.mapper.EntityMapperImpl;
import com.lacv.jmagrexs.modules.leadtable.model.dtos.TableColumnDto;
import com.lacv.jmagrexs.modules.leadtable.model.entities.TableColumn;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author lcastrillo
 */
@Component("tableColumnMapper")
public class TableColumnMapper extends EntityMapperImpl<TableColumn, TableColumnDto> implements EntityMapper<TableColumn, TableColumnDto> {
    
    @Autowired
    LeadTableMapper leadTableMapper;

    
    @Override
    public TableColumnDto entityToDto(TableColumn entity) {
        TableColumnDto dto= new TableColumnDto();
        if(entity!=null){
            dto.setColumnAlias(entity.getColumnAlias());
            dto.setColumnOrder(entity.getColumnOrder());
            dto.setColumnSize(entity.getColumnSize());
            dto.setDataType(entity.getDataType());
            dto.setDefaultValue(entity.getDefaultValue());
            dto.setFieldType(entity.getFieldType());
            dto.setId(entity.getId());
            dto.setLeadTable(leadTableMapper.entityToDto(entity.getLeadTable()));
            dto.setName(entity.getName());
            dto.setNotNull(entity.getNotNull());
            dto.setOptions(entity.getOptions());
            dto.setWidth(entity.getWidth());
        }
        return dto;
    }
    
    /**
     *
     * @param entities
     * @return
     */
    @Override
    public List<TableColumnDto> listEntitiesToListDtos(List<TableColumn> entities){
        List<TableColumnDto> dtos= new ArrayList<>();
        if(entities!=null){
            for(TableColumn entity: entities){
                dtos.add(entityToDto(entity));
            }
        }
        return dtos;
    }
    
    @Override
    public TableColumn dtoToEntity(TableColumnDto dto) {
        TableColumn entity= new TableColumn();
        if(dto!=null){
            entity.setColumnAlias(dto.getColumnAlias());
            entity.setColumnOrder(dto.getColumnOrder());
            entity.setColumnSize(dto.getColumnSize());
            entity.setDataType(dto.getDataType());
            entity.setDefaultValue(dto.getDefaultValue());
            entity.setFieldType(dto.getFieldType());
            entity.setId(dto.getId());
            entity.setLeadTable(leadTableMapper.dtoToEntity(dto.getLeadTable()));
            entity.setName(dto.getName());
            entity.setNotNull(dto.getNotNull());
            entity.setOptions(dto.getOptions());
            entity.setWidth(dto.getWidth());
        }
        return entity;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<TableColumn> listDtosToListEntities(List<TableColumnDto> dtos){
        List<TableColumn> entities= new ArrayList<>();
        if(entities!=null){
            for(TableColumnDto dto: dtos){
                entities.add(dtoToEntity(dto));
            }
        }
        return entities;
    }

}
