/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.fileexplorer.model.mappers;

import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.mapper.EntityMapperImpl;
import com.lacv.jmagrexs.modules.fileexplorer.model.dtos.WebFileDto;
import com.lacv.jmagrexs.modules.fileexplorer.model.entities.WebFile;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author lcastrillo
 */
@Component("webFileMapper")
public class WebFileMapper extends EntityMapperImpl<WebFile, WebFileDto> implements EntityMapper<WebFile, WebFileDto> {

    
    @Override
    public WebFileDto entityToDto(WebFile entity) {
        WebFileDto dto= new WebFileDto();
        if(entity!=null){
            dto.setAccess(entity.getAccess());
            dto.setAuthor(entity.getAuthor());
            dto.setCreationDate(entity.getCreationDate());
            dto.setIcon(entity.getIcon());
            dto.setId(entity.getId());
            dto.setLocation(entity.getLocation());
            dto.setModificationDate(entity.getModificationDate());
            dto.setName(entity.getName());
            dto.setPath(entity.getPath());
            dto.setSize(entity.getSize());
            dto.setType(entity.getType());
            dto.setWebFile(entityToDto(entity.getWebFile()));
        }
        return dto;
    }
    
    /**
     *
     * @param entities
     * @return
     */
    @Override
    public List<WebFileDto> listEntitiesToListDtos(List<WebFile> entities){
        List<WebFileDto> dtos= new ArrayList<>();
        if(entities!=null){
            for(WebFile entity: entities){
                dtos.add(entityToDto(entity));
            }
        }
        return dtos;
    }
    
    @Override
    public WebFile dtoToEntity(WebFileDto dto) {
        WebFile entity= new WebFile();
        if(dto!=null){
            entity.setAccess(dto.getAccess());
            entity.setAuthor(dto.getAuthor());
            entity.setCreationDate(dto.getCreationDate());
            entity.setIcon(dto.getIcon());
            entity.setId(dto.getId());
            entity.setModificationDate(dto.getModificationDate());
            entity.setName(dto.getName());
            entity.setSize(dto.getSize());
            entity.setType(dto.getType());
            entity.setWebFile(dtoToEntity(dto.getWebFile()));
        }
        return entity;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<WebFile> listDtosToListEntities(List<WebFileDto> dtos){
        List<WebFile> entities= new ArrayList<>();
        if(entities!=null){
            for(WebFileDto dto: dtos){
                entities.add(dtoToEntity(dto));
            }
        }
        return entities;
    }

}
