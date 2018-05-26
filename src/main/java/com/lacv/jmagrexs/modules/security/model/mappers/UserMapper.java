/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.security.model.mappers;

import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.mapper.EntityMapperImpl;
import com.lacv.jmagrexs.modules.security.model.dtos.UserDto;
import com.lacv.jmagrexs.modules.security.model.entities.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author lcastrillo
 */
@Component("userMapper")
public class UserMapper extends EntityMapperImpl<User, UserDto> implements EntityMapper<User, UserDto> {

    
    @Override
    public UserDto entityToDto(User entity) {
        UserDto dto= new UserDto();
        if(entity!=null){
            dto.setAddress(entity.getAddress());
            dto.setBirthday(entity.getBirthday());
            dto.setCell(entity.getCell());
            dto.setCity(entity.getCity());
            dto.setDocumentType(entity.getDocumentType());
            dto.setEmail(entity.getEmail());
            dto.setFailedAttempts(entity.getFailedAttempts());
            dto.setFirstName(entity.getFirstName());
            dto.setGender(entity.getGender());
            dto.setId(entity.getId());
            dto.setIdDocument(entity.getIdDocument());
            dto.setLastLogin(entity.getLastLogin());
            dto.setLastName(entity.getLastName());
            dto.setLink(entity.getLink());
            dto.setPassword(entity.getPassword());
            dto.setPasswordExpiration(entity.getPasswordExpiration());
            dto.setPhone(entity.getPhone());
            dto.setRegistrationDate(entity.getRegistrationDate());
            dto.setStatus(entity.getStatus());
            dto.setTokenUser(entity.getTokenUser());
            dto.setUrlPhoto(entity.getUrlPhoto());
            dto.setUsername(entity.getUsername());
            dto.setVerified(entity.getVerified());
        }
        return dto;
    }
    
    /**
     *
     * @param entities
     * @return
     */
    @Override
    public List<UserDto> listEntitiesToListDtos(List<User> entities){
        List<UserDto> dtos= new ArrayList<>();
        if(entities!=null){
            for(User entity: entities){
                dtos.add(entityToDto(entity));
            }
        }
        return dtos;
    }
    
    @Override
    public User dtoToEntity(UserDto dto) {
        User entity= new User();
        if(dto!=null){
            entity.setAddress(dto.getAddress());
            entity.setBirthday(dto.getBirthday());
            entity.setCell(dto.getCell());
            entity.setCity(dto.getCity());
            entity.setDocumentType(dto.getDocumentType());
            entity.setEmail(dto.getEmail());
            entity.setFailedAttempts(dto.getFailedAttempts());
            entity.setFirstName(dto.getFirstName());
            entity.setGender(dto.getGender());
            entity.setId(dto.getId());
            entity.setIdDocument(dto.getIdDocument());
            entity.setLastLogin(dto.getLastLogin());
            entity.setLastName(dto.getLastName());
            entity.setLink(dto.getLink());
            entity.setPassword(dto.getPassword());
            entity.setPasswordExpiration(dto.getPasswordExpiration());
            entity.setPhone(dto.getPhone());
            entity.setRegistrationDate(dto.getRegistrationDate());
            entity.setStatus(dto.getStatus());
            entity.setTokenUser(dto.getTokenUser());
            entity.setUrlPhoto(dto.getUrlPhoto());
            entity.setUsername(dto.getUsername());
            entity.setVerified(dto.getVerified());
        }
        return entity;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<User> listDtosToListEntities(List<UserDto> dtos){
        List<User> entities= new ArrayList<>();
        if(entities!=null){
            for(UserDto dto: dtos){
                entities.add(dtoToEntity(dto));
            }
        }
        return entities;
    }

}
