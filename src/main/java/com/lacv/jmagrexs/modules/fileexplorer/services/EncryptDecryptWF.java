/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.fileexplorer.services;

import com.lacv.jmagrexs.modules.security.constants.SecurityConstants;
import com.lacv.jmagrexs.util.AESEncrypt;
import java.util.Base64;

/**
 *
 * @author e11001a
 */
public class EncryptDecryptWF {
    
    static AESEncrypt myInstance= AESEncrypt.getDefault(SecurityConstants.SECURITY_SALT);
    
    public static String encrypt(String text){
        String c0= myInstance.encrypt(text, SecurityConstants.SECURITY_SEED_PASSW);
        String c1= Base64.getEncoder().encodeToString(c0.getBytes()).replaceAll("=", "");
        return c1;
    }
    
    public static String decrypt(String code){
        String d0= new String(Base64.getDecoder().decode(code));
        String d1= myInstance.decrypt(d0, SecurityConstants.SECURITY_SEED_PASSW);
        return d1;
    }
    
}
