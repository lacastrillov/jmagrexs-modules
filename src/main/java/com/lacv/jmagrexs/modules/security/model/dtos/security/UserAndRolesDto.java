/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.security.model.dtos.security;


import com.lacv.jmagrexs.modules.security.model.dtos.RoleDto;
import com.lacv.jmagrexs.modules.security.model.dtos.UserDto;
import java.util.List;

/**
 *
 * @author grupot
 */
public class UserAndRolesDto {
    
    private UserDto user;
    
    private List<RoleDto> roles;

    /**
     * @return the user
     */
    public UserDto getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserDto user) {
        this.user = user;
    }

    /**
     * @return the roles
     */
    public List<RoleDto> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }
    
}
