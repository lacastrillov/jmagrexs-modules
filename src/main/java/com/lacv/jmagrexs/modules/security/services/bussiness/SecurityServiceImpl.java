/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.security.services.bussiness;

import com.google.gson.Gson;
import com.lacv.jmagrexs.dto.MenuItem;
import com.lacv.jmagrexs.dto.UserByToken;
import com.lacv.jmagrexs.modules.security.constants.SecurityConstants;
import com.lacv.jmagrexs.util.AESEncrypt;
import com.lacv.jmagrexs.util.JwtUtil;
import com.lacv.jmagrexs.modules.security.model.dtos.security.UserDetailsDto;
import com.lacv.jmagrexs.modules.security.model.dtos.security.WebResourceAuthorities;
import com.lacv.jmagrexs.modules.security.model.entities.User;
import com.lacv.jmagrexs.modules.security.model.entities.UserRole;
import com.lacv.jmagrexs.modules.security.services.RoleAuthorizationService;
import com.lacv.jmagrexs.modules.security.services.UserRoleService;
import com.lacv.jmagrexs.modules.security.services.UserService;
import com.lacv.jmagrexs.modules.security.services.WebResourceService;
import com.lacv.jmagrexs.modules.security.services.WebresourceAuthorizationService;
import com.lacv.jmagrexs.modules.security.services.WebresourceRoleService;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

/**
 *
 * @author lacastrillov
 */
@Service("securityService")
public class SecurityServiceImpl implements AuthenticationProvider, SecurityService, UserDetailsService {
    
    protected static final Logger LOGGER = Logger.getLogger(SecurityServiceImpl.class);
    
    AESEncrypt myInstance= AESEncrypt.getDefault(SecurityConstants.SECURITY_SALT);

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;
    
    @Autowired
    RoleAuthorizationService roleAuthorizationService;
    
    @Autowired
    WebResourceService webResourceService;
    
    @Autowired
    WebresourceAuthorizationService webResourceAuthorizationService;
    
    @Autowired
    WebresourceRoleService webResourceRoleService;
    
    private Map<String, WebResourceAuthorities> specificWebResources;
    
    private Map<String, WebResourceAuthorities> generalWebResources;
    
    
    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        User user = getUser(a.getName());
        if (user != null){
            String contrasena= myInstance.decrypt(user.getPassword(), SecurityConstants.SECURITY_SEED_PASSW);
            if (contrasena!=null && contrasena.equals(user.getId()+"#"+a.getCredentials())) {
                UserDetailsDto userDetails = entityToUserDetail(user);
                if (userDetails.isEnabled() == false) {
                    throw new BadCredentialsException("Error, el usuario esta inactivo");
                } else if (userDetails.isAccountNonLocked() == false) {
                    throw new BadCredentialsException("Error, la cuenta de usuario esta bloqueada");
                }
                Authentication autentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                user.setFailedAttempts(0);
                user.setLastLogin(new Date());
                userService.update(user);
                return autentication;
            } else {
                user.setFailedAttempts(((user.getFailedAttempts()!=null)?user.getFailedAttempts():0) + 1);
                userService.update(user);
            }
        }
        throw new BadCredentialsException("Usuario y/o clave incorrectos");
    }

    @Override
    public String connect(User user) {
        UserDetailsDto userDetails = entityToUserDetail(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        try{
            return RequestContextHolder.currentRequestAttributes().getSessionId();
        }catch(IllegalStateException e){
            return "SESSIONID-NF";
        }
    }
    
    @Override
    public String connect(String username, String password) throws AuthenticationException {
        User user= getUser(username);
        if (user != null){
            String contrasena= myInstance.decrypt(user.getPassword(), SecurityConstants.SECURITY_SEED_PASSW);
            if (contrasena!=null && contrasena.equals(user.getId()+"#"+password)) {
                return connect(user);
            }
        }
        throw new BadCredentialsException("Usuario y/o clave incorrectos");
    }
    
    @Override
    public String connect(String basicAuthorization) throws AuthenticationException {
        if (basicAuthorization != null && basicAuthorization.startsWith("Basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = basicAuthorization.substring("Basic".length()).trim();
            String credentials = new String(Base64.decodeBase64(base64Credentials), Charset.forName("UTF-8"));
            // credentials = username:password
            String[] values = credentials.split(":", 2);
            return connect(values[0], values[1]);
        }
        throw new BadCredentialsException("Autorizacion incorrecta");
    }
    
    @Override
    public String connectByToken(String token) throws AuthenticationException {
        if(token!=null){
            JwtUtil jwt= new JwtUtil();
            long currentTime= new Date().getTime();
            UserByToken userByToken= jwt.parseToken(token, SecurityConstants.SECURITY_SEED_PASSW);
            if(userByToken!=null && (userByToken.getExpiration()==-1 || currentTime < userByToken.getExpiration())){
                return connect(userByToken.getUsername(), userByToken.getPassword());
            }
        }
        throw new BadCredentialsException("Token invalido o expirado");
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        List<UserRole> userRoles = userRoleService.findByParameter("user", user);
        for (UserRole usuerRoleList : userRoles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+usuerRoleList.getRole().getName()));
        }
        
        Map mapParameters= new HashMap();
        mapParameters.put("userId", user.getId());
        List<Map<String,Object>> authorizations= webResourceService.findByNameQuery("authorizations", mapParameters);
        for (Map<String,Object> authorization : authorizations) {
            authorities.add(new SimpleGrantedAuthority("OP_"+authorization.get("name")));
        }
        
        return authorities;
    }
    
    @Override
    public UserDetailsDto getUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null){
            try{
                UserDetailsDto userDetails = (UserDetailsDto) authentication.getPrincipal();
                return userDetails;
            }catch(Exception e){
            }
        }
        return null;
    }

    @Override
    public User getCurrentUser() {
        UserDetailsDto userDetails = getUserDetails();
        if(userDetails!=null){
            return userDetails.getUser();
        }
        return null;
    }
    
    @Override
    public String getBasicAuthorization(){
        User user= getCurrentUser();
        if(user!=null){
            String contrasena= myInstance.decrypt(user.getPassword(), SecurityConstants.SECURITY_SEED_PASSW);
            String credentials= user.getUsername()+":"+contrasena;
            String authorization= new String(Base64.encodeBase64(credentials.getBytes()), Charset.forName("UTF-8"));
            return "Basic "+authorization;
        }
        return null;
    }
    
    @Override
    public String generateAuthenticationToken(int minutesDuration){
        JwtUtil jwt= new JwtUtil();
        User user= getCurrentUser();
        Date currentTime= new Date();
        
        UserByToken userByToken= new UserByToken();
        userByToken.setUsername(user.getUsername());
        userByToken.setPassword(myInstance.decrypt(user.getPassword(), SecurityConstants.SECURITY_SEED_PASSW));
        
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentTime);
        userByToken.setCreation(cal1.getTimeInMillis());
        
        if(minutesDuration!=-1){
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(currentTime);
            cal2.set(Calendar.MINUTE, cal2.get(Calendar.MINUTE)+minutesDuration);
            userByToken.setExpiration(cal2.getTimeInMillis());
        }else{
            userByToken.setExpiration(minutesDuration);
        }
        
        return jwt.generateToken(userByToken, SecurityConstants.SECURITY_SEED_PASSW);
    }
    
    private UserDetailsDto entityToUserDetail(User user) {
        UserDetailsDto userDetails = new UserDetailsDto();
        long idUsuariolong = user.getId();
        int idUsuarioInt = (int) idUsuariolong;
        userDetails.setId(idUsuarioInt);
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(user.getPassword());
        userDetails.setNombre(user.getFirstName());
        userDetails.setApellidos(user.getLastName());
        userDetails.setImgPerfil(user.getUrlPhoto());
        userDetails.setUser(user);
        
        validateUserDetails(userDetails, user);

        userDetails.setAuthorities(getGrantedAuthorities(user));

        return userDetails;
    }

    private void validateUserDetails(UserDetailsDto userDetails, User user) {
        if (user.getStatus().equals("Locked")) {
            userDetails.setAccountNonLocked(false);
        } else {
            userDetails.setAccountNonLocked(true);
        }

        if (user.getStatus().equals("Active")) {
            userDetails.setEnabled(true);
        } else {
            userDetails.setEnabled(false);
        }

        userDetails.setCredentialsNonExpired(true);
        userDetails.setAccountNonExpired(true);
    }

    private User getUser(String username) {
        User user = userService.loadByParameter("username", username);

        return user;
    }
    
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = getUser(userName);
        if (user != null){
            UserDetailsDto userDetails = entityToUserDetail(user);
            
            return userDetails;
        }
        return null;
    }
    
    @Override
    public boolean checkAccessResource(String requestURI) {
        UserDetailsDto userDetails= getUserDetails();
        
        //Check Specific resources
        WebResourceAuthorities webResourceAuthorities= specificWebResources.get(requestURI);
        if(webResourceAuthorities==null){
            for(Map.Entry<String, WebResourceAuthorities> entry : generalWebResources.entrySet()){
                try{
                    Pattern p = Pattern.compile(entry.getKey());
                    Matcher m = p.matcher(requestURI);
                    if(m.find()){
                        webResourceAuthorities= entry.getValue();
                        break;
                    }
                }catch(Exception e){
                    LOGGER.error("ERROR Pattern - Matcher...", e);
                }
            }
        }
        
        if(webResourceAuthorities!=null && webResourceAuthorities.getIsPublic()==false){
        
            //Verificar si tiene una autorizacion
            if(webResourceAuthorities.getAuthorizations().size()>0){
                for(String authorization : webResourceAuthorities.getAuthorizations()){
                    if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("OP_"+authorization))){
                        return true;
                    }
                }
                return false;
            }else{
                //Verificar si tiene un Rol
                if(webResourceAuthorities.getRoles().size()>0){
                    for(String role: webResourceAuthorities.getRoles()){
                        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_"+role))){
                            return true;
                        }
                    }
                    return false;
                }
            }
            if(userDetails==null){
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public List<MenuItem> configureVisibilityMenu(List<MenuItem> menuData) {
        for (MenuItem itemParent : menuData) {
            checkVisibilityMenuItem(itemParent);
        }
        return menuData;
    }
    
    private boolean checkVisibilityMenuItem(MenuItem menuItem){
        if(menuItem.getType().equals(MenuItem.CHILD)){
            String requestURI= menuItem.getHref();
            boolean visible= checkAccessResource(requestURI);
            menuItem.setVisible(visible);
            return visible;
        }else{
            boolean visible= false;
            for(MenuItem subMenuItem: menuItem.getSubMenus()){
                boolean visibleItem= checkVisibilityMenuItem(subMenuItem);
                if(visibleItem){
                    visible= true;
                }
            }
            menuItem.setVisible(visible);
            return visible;
        }
    }

    @Override
    @PostConstruct
    public void reconfigureAccessControl(){
        LOGGER.info("Reconfigure Access Control...");
        specificWebResources= new HashMap<>();
        generalWebResources= new HashMap<>();
        
        List<Map<String,Object>> webResourceAuthorityList= webResourceService.findByNameQuery("webResourceAuthorities", new HashMap());
        
        for(Map<String,Object> wra: webResourceAuthorityList){
            String path= (String) wra.get("path");
            Integer isPublic= (Integer) wra.get("is_public");
            String type= (String) wra.get("type");
            String level= (String) wra.get("level");
            String authority= (String) wra.get("authority");
            
            if(type.equals("specific")){
                if(specificWebResources.get(path)==null){
                    WebResourceAuthorities webResourceAuthorities= new WebResourceAuthorities(path);
                    webResourceAuthorities.setIsPublic(isPublic==1);
                    specificWebResources.put(path, webResourceAuthorities);
                }
                if(level.equals("authorization")){
                    specificWebResources.get(path).addAuthorization(authority);
                }else if(level.equals("role")){
                    specificWebResources.get(path).addRole(authority);
                }
            }else if(type.equals("general")){
                if(generalWebResources.get(path)==null){
                    WebResourceAuthorities webResourceAuthorities= new WebResourceAuthorities(path);
                    webResourceAuthorities.setIsPublic(isPublic==1);
                    generalWebResources.put(path, webResourceAuthorities);
                }
                if(level.equals("authorization")){
                    generalWebResources.get(path).addAuthorization(authority);
                }else if(level.equals("role")){
                    generalWebResources.get(path).addRole(authority);
                }
            }
        }
        
        Gson gs= new Gson();
        LOGGER.info("########### SPECIFIC WEB RESOURCE ###########");
        LOGGER.info(gs.toJson(specificWebResources));
        LOGGER.info("########### GENERAL WEB RESOURCE ###########");
        LOGGER.info(gs.toJson(generalWebResources));
    }
    
    @Override
    public boolean supports(Class<?> type) {
        return true;
    }
    
    public static void main(String[] args){
        AESEncrypt myInstance= AESEncrypt.getDefault(SecurityConstants.SECURITY_SALT);
        String contrasena= myInstance.decrypt("SDBxlmqlQJGkhV6lD0rN/A==", SecurityConstants.SECURITY_SEED_PASSW);
        System.out.println(contrasena);
        
        System.out.println(myInstance.encrypt("1#calsat321", SecurityConstants.SECURITY_SEED_PASSW));
        
        JwtUtil jwt= new JwtUtil();
        Date currentTime= new Date();
        
        UserByToken userByToken= new UserByToken();
        userByToken.setUsername("magento");
        userByToken.setPassword("M493n70");
        
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentTime);
        userByToken.setCreation(cal1.getTimeInMillis());
        userByToken.setExpiration(-1);
        
        String token= jwt.generateToken(userByToken, SecurityConstants.SECURITY_SEED_PASSW);
        System.out.println(token);
}
    
}
