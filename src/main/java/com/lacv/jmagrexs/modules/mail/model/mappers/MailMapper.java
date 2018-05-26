/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.mail.model.mappers;

import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.mapper.EntityMapperImpl;
import com.lacv.jmagrexs.modules.mail.model.dtos.MailDto;
import com.lacv.jmagrexs.modules.mail.model.entities.Mail;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author lcastrillo
 */
@Component("mailMapper")
public class MailMapper extends EntityMapperImpl<Mail, MailDto> implements EntityMapper<Mail, MailDto> {
    
    @Autowired
    MailTemplateMapper mailTemplateMapper;

    
    @Override
    public MailDto entityToDto(Mail entity) {
        MailDto dto= new MailDto();
        if(entity!=null){
            dto.setId(entity.getId());
            dto.setMailFrom(entity.getMailFrom());
            dto.setMailTemplate(mailTemplateMapper.entityToDto(entity.getMailTemplate()));
            dto.setMailTo(entity.getMailTo());
            dto.setMessage(entity.getMessage());
            dto.setResult(entity.getResult());
            dto.setSendDate(entity.getSendDate());
            dto.setSubject(entity.getSubject());
        }
        return dto;
    }
    
    /**
     *
     * @param entities
     * @return
     */
    @Override
    public List<MailDto> listEntitiesToListDtos(List<Mail> entities){
        List<MailDto> dtos= new ArrayList<>();
        if(entities!=null){
            for(Mail entity: entities){
                dtos.add(entityToDto(entity));
            }
        }
        return dtos;
    }
    
    @Override
    public Mail dtoToEntity(MailDto dto) {
        Mail entity= new Mail();
        if(dto!=null){
            entity.setId(dto.getId());
            entity.setMailFrom(dto.getMailFrom());
            entity.setMailTemplate(mailTemplateMapper.dtoToEntity(dto.getMailTemplate()));
            entity.setMailTo(dto.getMailTo());
            entity.setMessage(dto.getMessage());
            entity.setResult(dto.getResult());
            entity.setSendDate(dto.getSendDate());
            entity.setSubject(dto.getSubject());
        }
        return entity;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<Mail> listDtosToListEntities(List<MailDto> dtos){
        List<Mail> entities= new ArrayList<>();
        if(entities!=null){
            for(MailDto dto: dtos){
                entities.add(dtoToEntity(dto));
            }
        }
        return entities;
    }

}
