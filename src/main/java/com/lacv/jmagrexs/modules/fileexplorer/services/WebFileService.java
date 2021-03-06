/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lacv.jmagrexs.modules.fileexplorer.services;

import com.lacv.jmagrexs.modules.fileexplorer.model.entities.WebFile;
import com.lacv.jmagrexs.service.EntityService;
import com.google.api.services.storage.model.StorageObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;



/**
 *
 * @author lacastrillov
 */
public interface WebFileService extends EntityService<WebFile> {
    
    WebFile findByPath(String path);
    
    String getStaticFileLocation(String fileUrl);
    
    String getStaticFileLocation(WebFile webFile);
    
    String getPathFromFileUrl(String fileUrl);
    
    WebFile createByFileData(WebFile parentWebFile, int slice, String fileName, String fileType, int fileSize, InputStream is, Integer user) throws IOException;
    
    WebFile createByStorageObject(StorageObject object, WebFile parent, String location, Integer user);
    
    WebFile createFolder(WebFile parentWebFile, String folderName, Integer user);
    
    WebFile createEmptyFile(WebFile parentWebFile, String fileName, Integer user);
    
    WebFile createDirectoriesIfMissing(String path, Integer user);
    
    boolean renameWebFile(WebFile webFile, String newFileName);
    
    boolean deleteIfExist(WebFile parentWebFile, String fileName);
    
    boolean deleteWebFileInDepth(WebFile webFile) throws IOException;
    
    Map exploreInDepth(WebFile webFile);
    
}
