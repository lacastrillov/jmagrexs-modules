/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.mail.services;


import com.lacv.jmagrexs.modules.mail.daos.MailJpa;
import com.lacv.jmagrexs.modules.mail.model.mappers.MailMapper;
import com.lacv.jmagrexs.dao.GenericDao;
import com.lacv.jmagrexs.mapper.EntityMapper;
import com.lacv.jmagrexs.modules.mail.model.entities.Mail;
import com.lacv.jmagrexs.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lcastrillo
 */
@Service("mailService")
public class MailServiceImpl extends EntityServiceImpl<Mail> implements MailService {
    
    @Autowired
    public MailJpa mailJpa;
    
    @Autowired
    public MailMapper mailMapper;
    
    @Override
    public GenericDao getGenericDao(){
        return mailJpa;
    }

    @Override
    public EntityMapper getEntityMapper() {
        return mailMapper;
    }
    
}
