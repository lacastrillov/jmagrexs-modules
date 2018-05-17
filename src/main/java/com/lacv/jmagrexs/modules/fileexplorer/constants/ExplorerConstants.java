/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.fileexplorer.constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author desarrollador
 */
@Component
public class ExplorerConstants {
    
    @Autowired
    @Value("${static.domain.url}")
    public String LOCAL_DOMAIN;
    
    @Autowired
    @Value("${static.folder}")
    public String LOCAL_DIR;
    
    public static final String ROOT_FOLDER= "recursos/";

}
