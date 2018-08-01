package com.lacv.jmagrexs.modules.security.controllers.portal;

import com.lacv.jmagrexs.dao.Parameters;
import com.lacv.jmagrexs.modules.security.model.dtos.RoleDto;
import com.lacv.jmagrexs.modules.security.model.dtos.UserDto;
import com.lacv.jmagrexs.modules.security.model.dtos.UserRoleDto;
import com.lacv.jmagrexs.modules.security.model.dtos.security.UserAndRolesDto;
import com.lacv.jmagrexs.modules.security.model.entities.User;
import com.lacv.jmagrexs.modules.security.model.entities.UserRole;
import com.lacv.jmagrexs.modules.security.model.mappers.UserMapper;
import com.lacv.jmagrexs.modules.security.model.mappers.UserRoleMapper;
import com.lacv.jmagrexs.modules.security.services.UserRoleService;
import com.lacv.jmagrexs.util.JSONService;
import com.lacv.jmagrexs.modules.security.services.bussiness.SecurityService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/account")
public class AccountController {
    
    public static final Logger LOGGER = Logger.getLogger(AccountController.class);
    
    @Autowired
    SecurityService securityService;
    
    @Autowired
    UserRoleService userRoleService;
    
    @Autowired
    UserMapper userMapper;
    
    @Autowired
    UserRoleMapper userRoleMapper;
    
    
    
    @RequestMapping(value = "/home", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView getHome(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String redirect) {
        if(redirect!=null && !redirect.equals("user")){
            try {
                String page= new String(Base64.decodeBase64(redirect), StandardCharsets.UTF_8);
                Integer index=0;
                HttpSession session= request.getSession();
                if(session.getAttribute("redirect")==null || !session.getAttribute("redirect").toString().equals(redirect)){
                    session.setAttribute("redirect", redirect);
                    session.setAttribute("index", index);
                }else{
                    index= (Integer)session.getAttribute("index")+1;
                    session.setAttribute("index", index);
                }
                LOGGER.info(page+" "+index);
                
                if(index<3){
                    response.sendRedirect(page);
                }else{
                    ModelAndView mav= new ModelAndView("pageRedirect");
                    mav.addObject("page", page);
                    return mav;
                }
            } catch (IOException ex) {
                LOGGER.error("ERROR getHome", ex);
            }
            return null;
        }else{
            UserAndRolesDto userAndRoles= getUserAndRoles();
            if(userAndRoles!=null){
                String homePage=null;
                for(RoleDto role: userAndRoles.getRoles()){
                    if(role.getHomePage()!=null){
                        homePage= role.getHomePage();
                        break;
                    }
                }
                if(homePage!=null){
                    return new ModelAndView("redirect:"+homePage);
                } else {
                    return new ModelAndView("redirect:/");
                }
            }else{
                return new ModelAndView("redirect:/account/login");
            }
        }
    }
    
    @RequestMapping(value = "/ajax/authenticate", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String authenticate(
            @RequestParam(required = false) String j_username,
            @RequestParam(required = false) String j_password,
            @RequestParam(required = false) String basicAuthorization) {
        
        Map data= new HashMap();
        try{
            if(j_username!=null && j_password!=null){
                securityService.connect(j_username, j_password);
            }else{
                securityService.connect(basicAuthorization);
            }
            UserAndRolesDto userAndRoles= getUserAndRoles();
            data.put("success", true);
            data.put("user", userAndRoles.getUser());
            data.put("roles", userAndRoles.getRoles());
            
            return JSONService.objectToJson(data);
        }catch(AuthenticationException ex){
            data.put("success", false);
            data.put("message", "Usuario y/o contraseña incorrectos");
        }
        return JSONService.objectToJson(data);
    }
    
    @RequestMapping(value = "/ajax/generateAthenticationToken", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String generateAthenticationToken(
            @RequestParam(required = true) String j_username,
            @RequestParam(required = true) String j_password) {
        
        Map data= new HashMap();
        try{
            if(j_username!=null && j_password!=null){
                securityService.connect(j_username, j_password);
            }
            int minutesDuration= 10;
            String token= securityService.generateAuthenticationToken(minutesDuration);
            
            data.put("success", true);
            data.put("token", token);
        }catch(AuthenticationException ex){
            data.put("success", false);
            data.put("message", "Usuario y/o contraseña incorrectos");
        }
        return JSONService.objectToJson(data);
    }
    
    @RequestMapping(value = "/ajax/generateAthenticationBasicToken", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String generateAthenticationBasicToken(
            @RequestParam(required = true) String j_username,
            @RequestParam(required = true) String j_password) {
        
        Map data= new HashMap();
        try{
            if(j_username!=null && j_password!=null){
                securityService.connect(j_username, j_password);
            }
            String token= securityService.getBasicAuthorization();
            
            data.put("success", true);
            data.put("token", token);
        }catch(AuthenticationException ex){
            data.put("success", false);
            data.put("message", "Usuario y/o contraseña incorrectos");
        }
        return JSONService.objectToJson(data);
    }
    
    @RequestMapping(value = "/ajax/logout", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String logout(HttpSession session) {
        Map data= new HashMap();
        try{
            session.invalidate();
            data.put("success", true);
            data.put("message", "Logout done");
            return JSONService.objectToJson(data);
        }catch(AuthenticationException ex){
            data.put("success", false);
            data.put("message", "Logout Error");
        }
        return JSONService.objectToJson(data);
    }
    
    @RequestMapping(value = "/ajax/userInSession", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String userInSession() {
        Map data= new HashMap();
        UserAndRolesDto userAndRoles= getUserAndRoles();
        if(userAndRoles!=null){
            data.put("session", true);
            data.put("ba", securityService.getBasicAuthorization());
            data.put("user", userAndRoles.getUser());
            data.put("roles", userAndRoles.getRoles());
            
            return JSONService.objectToJson(data);
        }else{
            data.put("session", false);
            data.put("message", "No hay usuario en sesion");
        }
        return JSONService.objectToJson(data);
    }
    
    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response) {
        User user= securityService.getCurrentUser();
        if(user!=null){
            return getHome(request, response, "user");
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/denied", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView getDenied() {
        ModelAndView mav = new ModelAndView("denied");
        return mav;
    }
    
    @RequestMapping(value = "/reconfigureAccessControl", method = RequestMethod.GET)
    @ResponseBody
    public String reconfigureAccessControl() {
        securityService.reconfigureAccessControl();
        return "Success";
    }
    
    private UserAndRolesDto getUserAndRoles(){
        UserAndRolesDto userAndRoles= new UserAndRolesDto();
        User user= securityService.getCurrentUser();
        if(user!=null){
            UserDto userDto= (UserDto) userMapper.entityToDto(user);
            Parameters p= new Parameters();
            p.whereEqual("user", user);
            p.orderBy("role.priorityCheck", "ASC");
            List<UserRole> userRoles= userRoleService.findByParameters(p);
            List<UserRoleDto> userRolesDto= (List<UserRoleDto>) userRoleMapper.listEntitiesToListDtos(userRoles);
            List<RoleDto> roles= new ArrayList<>();
            for(UserRoleDto userRole: userRolesDto){
                roles.add(userRole.getRole());
            }
            userAndRoles.setUser(userDto);
            userAndRoles.setRoles(roles);
            
            return userAndRoles;
        }else{
            return null;
        }
    }
    
}
