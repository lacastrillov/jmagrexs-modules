/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.security.controllers.rest;


import com.lacv.jmagrexs.modules.security.services.RoleService;
import com.lacv.jmagrexs.controller.rest.RestSessionController;
import com.lacv.jmagrexs.domain.BaseEntity;
import com.lacv.jmagrexs.modules.security.model.entities.UserRole;
import com.lacv.jmagrexs.modules.security.model.mappers.RoleMapper;
import com.lacv.jmagrexs.modules.security.services.UserRoleService;
import com.lacv.jmagrexs.modules.security.services.bussiness.SecurityService;
import java.util.List;
import javax.annotation.PostConstruct;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author lcastrillo
 */
@Controller
@RequestMapping(value="/rest/role")
public class RoleRestController extends RestSessionController {
    
    @Autowired
    RoleService roleService;
    
    @Autowired
    UserRoleService userRoleService;
    
    @Autowired
    RoleMapper roleMapper;
    
    @Autowired
    SecurityService securityService;
    
    
    @PostConstruct
    public void init(){
        super.addControlMapping("role", roleService, roleMapper);
    }
    
    @Override
    public JSONObject addSessionSearchFilter(JSONObject jsonFilters){
        JSONArray roleIds= new JSONArray();
        List<UserRole> userRoles= userRoleService.findByParameter("user", securityService.getCurrentUser());
        for(UserRole userRole: userRoles){
            roleIds.put(userRole.getRole().getId());
        }
        jsonFilters.getJSONObject("in").put("id", roleIds);
                
        return jsonFilters;
    }

    @Override
    public JSONObject addSessionReportFilter(String reportName, JSONObject jsonFilters) {
        return jsonFilters;
    }

    @Override
    public boolean canSessionLoad(BaseEntity entity) {
        return true;
    }

    @Override
    public boolean canSessionUpdate(BaseEntity entity) {
        return false;
    }
    
    @Override
    public boolean canSessionCreate(BaseEntity entity){
        return false;
    }

    @Override
    public boolean canSessionDelete(BaseEntity entity) {
        return false;
    }

    @Override
    public boolean canSessionUpdateByFilters(JSONObject jsonFilters) {
        return false;
    }

    @Override
    public boolean canSessionDeleteByFilters(JSONObject jsonFilters) {
        return false;
    }

    @Override
    public boolean canSessionImportData(List<BaseEntity> entities) {
        return false;
    }
    
    
}
