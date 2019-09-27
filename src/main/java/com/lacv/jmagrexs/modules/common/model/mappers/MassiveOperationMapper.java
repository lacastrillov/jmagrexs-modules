/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.common.model.mappers;

import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.mapper.EntityMapperImpl;
import com.lacv.jmagrexs.modules.common.model.dtos.MassiveOperationDto;
import com.lacv.jmagrexs.modules.common.model.entities.MassiveOperation;
import com.lacv.jmagrexs.modules.security.model.mappers.UserMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author lcastrillo
 */
@Component("massiveOperationMapper")
public class MassiveOperationMapper extends EntityMapperImpl<MassiveOperation, MassiveOperationDto> implements EntityMapper<MassiveOperation, MassiveOperationDto> {

    @Autowired
    UserMapper userMapper;
    
    @Override
    public MassiveOperationDto entityToDto(MassiveOperation entity) {
        MassiveOperationDto dto= new MassiveOperationDto();
        if(entity!=null){
            dto.setDuration(entity.getDuration());
            dto.setId(entity.getId());
            dto.setMessage(entity.getMessage());
            dto.setName(entity.getName());
            dto.setPercentage(entity.getPercentage());
            dto.setProcessed(entity.getProcessed());
            dto.setRecordTime(entity.getRecordTime());
            dto.setRegistrationDate(entity.getRegistrationDate());
            dto.setSourceFile(entity.getSourceFile());
            dto.setStatus(entity.getStatus());
            dto.setTotal(entity.getTotal());
            dto.setTotalFailed(entity.getTotalFailed());
            dto.setTotalSuccessful(entity.getTotalSuccessful());
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
    public List<MassiveOperationDto> listEntitiesToListDtos(List<MassiveOperation> entities){
        List<MassiveOperationDto> dtos= new ArrayList<>();
        if(entities!=null){
            for(MassiveOperation entity: entities){
                dtos.add(entityToDto(entity));
            }
        }
        return dtos;
    }
    
    @Override
    public MassiveOperation dtoToEntity(MassiveOperationDto dto) {
        MassiveOperation entity= new MassiveOperation();
        if(dto!=null){
            entity.setDuration(dto.getDuration());
            entity.setId(dto.getId());
            entity.setMessage(dto.getMessage());
            entity.setName(dto.getName());
            entity.setPercentage(dto.getPercentage());
            entity.setProcessed(dto.getProcessed());
            entity.setRecordTime(dto.getRecordTime());
            entity.setRegistrationDate(dto.getRegistrationDate());
            entity.setSourceFile(dto.getSourceFile());
            entity.setStatus(dto.getStatus());
            entity.setTotal(dto.getTotal());
            entity.setTotalFailed(dto.getTotalFailed());
            entity.setTotalSuccessful(dto.getTotalSuccessful());
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
    public List<MassiveOperation> listDtosToListEntities(List<MassiveOperationDto> dtos){
        List<MassiveOperation> entities= new ArrayList<>();
        if(entities!=null){
            for(MassiveOperationDto dto: dtos){
                entities.add(dtoToEntity(dto));
            }
        }
        return entities;
    }

}
