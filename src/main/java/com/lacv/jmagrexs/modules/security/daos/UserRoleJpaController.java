/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.security.daos;


import com.lacv.jmagrexs.modules.security.model.entities.UserRole;
import com.lacv.jmagrexs.dao.JPAGenericDao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lacastrillov
 */
@Repository
public class UserRoleJpaController extends JPAGenericDao<UserRole> implements UserRoleJpa {

    
}
