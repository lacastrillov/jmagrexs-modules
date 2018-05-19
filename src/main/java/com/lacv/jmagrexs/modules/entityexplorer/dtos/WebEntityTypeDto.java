/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.entityexplorer.dtos;

import com.lacv.jmagrexs.annotation.ColumnWidth;
import com.lacv.jmagrexs.annotation.HideField;
import com.lacv.jmagrexs.annotation.LabelField;
import com.lacv.jmagrexs.annotation.NotNull;
import com.lacv.jmagrexs.annotation.Order;
import com.lacv.jmagrexs.annotation.ReadOnly;
import com.lacv.jmagrexs.annotation.Size;
import com.lacv.jmagrexs.annotation.TextField;
import com.lacv.jmagrexs.annotation.TypeFormField;
import com.lacv.jmagrexs.domain.BaseEntity;
import com.lacv.jmagrexs.enums.FieldType;
import com.lacv.jmagrexs.enums.HideView;

/**
 *
 * @author lcastrillo
 */
@LabelField("entityName")
public class WebEntityTypeDto implements BaseEntity {

    private static final long serialVersionUID = 1L;
    
    @Order(1)
    @ReadOnly
    @ColumnWidth(100)
    @TextField("Id")
    private Integer id;
    
    @Order(2)
    @NotNull
    @Size(min=1,max=100)
    @ColumnWidth(200)
    @TextField("Nombre Entidad")
    private String entityName;
    
    @Order(3)
    @NotNull
    @Size(min=1,max=100)
    @ColumnWidth(120)
    @TextField("Ref Entidad")
    private String entityRef;
    
    @Order(4)
    @ColumnWidth(130)
    @TextField("Orden Entidad")
    private Integer entityOrder;
    
    @Order(5)
    @ColumnWidth(130)
    @Size(max = 100)
    @TextField("Icono")
    @HideField({HideView.FILTER})
    private String icon;
    
    @Order(6)
    @Size(max=400)
    @ColumnWidth(350)
    @TextField("Nombre Clase")
    private String className;
    
    @Order(7)
    @Size(max=100)
    @ColumnWidth(200)
    @TextField("Nombre Servicio")
    private String serviceName;
    
    @Order(8)
    @Size(max=45)
    @ColumnWidth(100)
    @TextField("Estado")
    @TypeFormField(value = FieldType.MULTI_SELECT, data = {"Active", "Inactive", "Locked", "Deleted"})
    private String status;
    

    public WebEntityTypeDto() {
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className =  className;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName =  entityName;
    }

    public String getEntityRef() {
        return entityRef;
    }

    public void setEntityRef(String entityRef) {
        this.entityRef =  entityRef;
    }
    
    public Integer getEntityOrder() {
        return entityOrder;
    }

    public void setEntityOrder(Integer entityOrder) {
        this.entityOrder = entityOrder;
    }
    
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        this.id = (Integer) id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName =  serviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status =  status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WebEntityTypeDto)) {
            return false;
        }
        WebEntityTypeDto other = (WebEntityTypeDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacv.agogui.model.dtos.WebEntityTypeDto[ id=" + id + " ]";
    }
    
}
