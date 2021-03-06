/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.security.model.entities;

import com.lacv.jmagrexs.domain.BaseEntity;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

/**
 *
 * @author grupot
 */
@Entity
@Table(name = "sys_role_authorization")
@NamedQueries({
    @NamedQuery(name = "RoleAuthorization.findAll", query = "SELECT r FROM RoleAuthorization r")})
public class RoleAuthorization implements BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @JoinFetch(JoinFetchType.OUTER)
    @ManyToOne(optional = false)
    private Role role;
    @JoinColumn(name = "authorization_id", referencedColumnName = "id")
    @JoinFetch(JoinFetchType.OUTER)
    @ManyToOne(optional = false)
    private Authorization authorization;

    public RoleAuthorization() {
    }

    public RoleAuthorization(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        this.id = (Integer) id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
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
        if (!(object instanceof RoleAuthorization)) {
            return false;
        }
        RoleAuthorization other = (RoleAuthorization) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacv.marketplatform.entities.RoleAuthorization[ id=" + id + " ]";
    }
    
}
