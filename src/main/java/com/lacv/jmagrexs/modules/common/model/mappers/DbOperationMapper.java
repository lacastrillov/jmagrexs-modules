/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.common.model.mappers;

import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.mapper.EntityMapperImpl;
import com.lacv.jmagrexs.modules.common.model.dtos.DbOperationDto;
import com.lacv.jmagrexs.modules.common.model.entities.DbOperation;
import com.lacv.jmagrexs.modules.common.model.entities.MassiveOperation;
import com.lacv.jmagrexs.modules.security.model.mappers.UserMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author lcastrillo
 */
@Component("dbOperationMapper")
public class DbOperationMapper extends EntityMapperImpl<DbOperation, DbOperationDto> implements EntityMapper<DbOperation, DbOperationDto> {
    
    @Autowired
    MassiveOperationMapper massiveOperationMapper;
    
    @Autowired
    UserMapper userMapper;

    
    @Override
    public DbOperationDto entityToDto(DbOperation entity) {
        DbOperationDto dto= new DbOperationDto();
        if(entity!=null){
            dto.setDataNew(entity.getDataNew());
            dto.setEntityId(entity.getEntityId());
            dto.setEntityRef(entity.getEntityRef());
            dto.setId(entity.getId());
            dto.setMassiveOperation(massiveOperationMapper.entityToDto((MassiveOperation) entity.getMassiveOperation()));
            dto.setMessage(entity.getMessage());
            dto.setName(entity.getName());
            dto.setRecordTime(entity.getRecordTime());
            dto.setRegistrationDate(entity.getRegistrationDate());
            dto.setSuccess(entity.getSuccess());
            dto.setType(entity.getType());
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
    public List<DbOperationDto> listEntitiesToListDtos(List<DbOperation> entities){
        List<DbOperationDto> dtos= new ArrayList<>();
        if(entities!=null){
            for(DbOperation entity: entities){
                dtos.add(entityToDto(entity));
            }
        }
        return dtos;
    }
    
    @Override
    public DbOperation dtoToEntity(DbOperationDto dto) {
        DbOperation entity= new DbOperation();
        if(dto!=null){
            entity.setDataNew(dto.getDataNew());
            entity.setEntityId(dto.getEntityId());
            entity.setEntityRef(dto.getEntityRef());
            entity.setId(dto.getId());
            entity.setMassiveOperation(massiveOperationMapper.dtoToEntity(dto.getMassiveOperation()));
            entity.setMessage(dto.getMessage());
            entity.setName(dto.getName());
            entity.setRecordTime(dto.getRecordTime());
            entity.setRegistrationDate(dto.getRegistrationDate());
            entity.setSuccess(dto.getSuccess());
            entity.setType(dto.getType());
            entity.setUser(userMapper.dtoToEntity(dto.getUser()));
        }
        return entity;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<DbOperation> listDtosToListEntities(List<DbOperationDto> dtos){
        List<DbOperation> entities= new ArrayList<>();
        if(entities!=null){
            for(DbOperationDto dto: dtos){
                entities.add(dtoToEntity(dto));
            }
        }
        return entities;
    }

}
