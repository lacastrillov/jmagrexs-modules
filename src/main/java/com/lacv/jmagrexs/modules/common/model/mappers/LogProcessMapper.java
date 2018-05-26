/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.common.model.mappers;

import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.mapper.EntityMapperImpl;
import com.lacv.jmagrexs.modules.common.model.dtos.LogProcessDto;
import com.lacv.jmagrexs.modules.common.model.entities.LogProcess;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author lcastrillo
 */
@Component("logProcessMapper")
public class LogProcessMapper extends EntityMapperImpl<LogProcess, LogProcessDto> implements EntityMapper<LogProcess, LogProcessDto> {

    
    @Override
    public LogProcessDto entityToDto(LogProcess entity) {
        LogProcessDto dto= new LogProcessDto();
        if(entity!=null){
            dto.setClientId(entity.getClientId());
            dto.setDataIn(entity.getDataIn());
            dto.setDataOut(entity.getDataOut());
            dto.setDuration(entity.getDuration());
            dto.setId(entity.getId());
            dto.setMainProcessRef(entity.getMainProcessRef());
            dto.setMessage(entity.getMessage());
            dto.setOutputDataFormat(entity.getOutputDataFormat());
            dto.setProcessName(entity.getProcessName());
            dto.setRecordTime(entity.getRecordTime());
            dto.setRegistrationDate(entity.getRegistrationDate());
            dto.setSuccess(entity.getSuccess());
        }
        return dto;
    }
    
    /**
     *
     * @param entities
     * @return
     */
    @Override
    public List<LogProcessDto> listEntitiesToListDtos(List<LogProcess> entities){
        List<LogProcessDto> dtos= new ArrayList<>();
        if(entities!=null){
            for(LogProcess entity: entities){
                dtos.add(entityToDto(entity));
            }
        }
        return dtos;
    }
    
    @Override
    public LogProcess dtoToEntity(LogProcessDto dto) {
        LogProcess entity= new LogProcess();
        if(dto!=null){
            entity.setClientId(dto.getClientId());
            entity.setDataIn(dto.getDataIn());
            entity.setDataOut(dto.getDataOut());
            entity.setDuration(dto.getDuration());
            entity.setId(dto.getId());
            entity.setMainProcessRef(dto.getMainProcessRef());
            entity.setMessage(dto.getMessage());
            entity.setOutputDataFormat(dto.getOutputDataFormat());
            entity.setProcessName(dto.getProcessName());
            entity.setRecordTime(dto.getRecordTime());
            entity.setRegistrationDate(dto.getRegistrationDate());
            entity.setSuccess(dto.getSuccess());
        }
        return entity;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<LogProcess> listDtosToListEntities(List<LogProcessDto> dtos){
        List<LogProcess> entities= new ArrayList<>();
        if(entities!=null){
            for(LogProcessDto dto: dtos){
                entities.add(dtoToEntity(dto));
            }
        }
        return entities;
    }

}
