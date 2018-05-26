/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.entityexplorer.daos;


import com.lacv.jmagrexs.modules.entityexplorer.daos.WebEntityTypeJpa;
import com.lacv.jmagrexs.modules.entityexplorer.model.entities.WebEntityType;
import com.lacv.jmagrexs.dao.JPAGenericDao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lacastrillov
 */
@Repository
public class WebEntityTypeJpaController extends JPAGenericDao<WebEntityType> implements WebEntityTypeJpa {

}