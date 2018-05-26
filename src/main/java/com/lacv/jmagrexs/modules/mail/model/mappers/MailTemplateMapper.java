/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.mail.model.mappers;

import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.mapper.EntityMapperImpl;
import com.lacv.jmagrexs.modules.mail.model.dtos.MailTemplateDto;
import com.lacv.jmagrexs.modules.mail.model.entities.MailTemplate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author lcastrillo
 */
@Component("mailTemplateMapper")
public class MailTemplateMapper extends EntityMapperImpl<MailTemplate, MailTemplateDto> implements EntityMapper<MailTemplate, MailTemplateDto> {

    
    @Override
    public MailTemplateDto entityToDto(MailTemplate entity) {
        MailTemplateDto dto= new MailTemplateDto();
        if(entity!=null){
            dto.setAlias(entity.getAlias());
            dto.setContent(entity.getContent());
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setStatus(entity.getStatus());
            dto.setTotalSent(entity.getTotalSent());
        }
        return dto;
    }
    
    /**
     *
     * @param entities
     * @return
     */
    @Override
    public List<MailTemplateDto> listEntitiesToListDtos(List<MailTemplate> entities){
        List<MailTemplateDto> dtos= new ArrayList<>();
        if(entities!=null){
            for(MailTemplate entity: entities){
                dtos.add(entityToDto(entity));
            }
        }
        return dtos;
    }
    
    @Override
    public MailTemplate dtoToEntity(MailTemplateDto dto) {
        MailTemplate entity= new MailTemplate();
        if(dto!=null){
            entity.setAlias(dto.getAlias());
            entity.setContent(dto.getContent());
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setStatus(dto.getStatus());
            entity.setTotalSent(dto.getTotalSent());
        }
        return entity;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<MailTemplate> listDtosToListEntities(List<MailTemplateDto> dtos){
        List<MailTemplate> entities= new ArrayList<>();
        if(entities!=null){
            for(MailTemplateDto dto: dtos){
                entities.add(dtoToEntity(dto));
            }
        }
        return entities;
    }

}
