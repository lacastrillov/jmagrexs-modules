package com.lacv.jmagrexs.modules.security.services.bussiness;

import com.lacv.jmagrexs.dto.MenuItem;
import com.lacv.jmagrexs.modules.security.model.entities.User;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;


/**
 *
 * Interfaz que expone los servicios de seguridad
 *
 * @author Harley Aranda / Edison Neira Todos los derechos reservados
 * @Version 1.0
 */
public interface SecurityService {

    Authentication authenticate(Authentication a) throws AuthenticationException;
    
    String connect(User user) throws AuthenticationException;
    
    String connect(String username, String password) throws AuthenticationException;
    
    String connect(String basicAuthorization) throws AuthenticationException;
    
    String connectByToken(String token) throws AuthenticationException;
    
    UserDetails getUserDetails();
    
    User getCurrentUser();
    
    String getBasicAuthorization();
    
    String generateAuthenticationToken(int minutesDuration);
    
    boolean checkAccessResource(String requestURI);
    
    List<MenuItem> configureVisibilityMenu(List<MenuItem> menuData);
    
    void reconfigureAccessControl();

}
