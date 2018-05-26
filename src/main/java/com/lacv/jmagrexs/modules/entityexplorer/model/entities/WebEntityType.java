/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.entityexplorer.model.entities;

import com.lacv.jmagrexs.domain.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author lacastrillov
 */
@Entity
@Table(name = "web_entity_type")
@NamedQueries({
    @NamedQuery(name = "WebEntityType.findAll", query = "SELECT w FROM WebEntityType w")})
public class WebEntityType implements BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "entity_name")
    private String entityName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "entity_ref")
    private String entityRef;
    @Column(name = "entity_order")
    private Integer entityOrder;
    @Size(max = 100)
    @Column(name = "icon")
    private String icon;
    @Size(max = 400)
    @Column(name = "class_name")
    private String className;
    @Size(max = 100)
    @Column(name = "service_name")
    private String serviceName;
    @Size(max = 45)
    @Column(name = "status")
    private String status;

    public WebEntityType() {
    }

    public WebEntityType(Integer id) {
        this.id = id;
    }

    public WebEntityType(Integer id, String entityName, String entityRef) {
        this.id = id;
        this.entityName = entityName;
        this.entityRef = entityRef;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        this.id = (Integer) id;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityRef() {
        return entityRef;
    }

    public void setEntityRef(String entityRef) {
        this.entityRef = entityRef;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof WebEntityType)) {
            return false;
        }
        WebEntityType other = (WebEntityType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacv.agogui.model.entities.WebEntityType[ id=" + id + " ]";
    }
    
}
