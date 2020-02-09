/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.common.model.entities;

import com.lacv.jmagrexs.domain.BaseEntity;
import com.lacv.jmagrexs.interfaces.DbOperationInterface;
import com.lacv.jmagrexs.interfaces.MassiveOperationInterface;
import com.lacv.jmagrexs.modules.security.model.entities.User;
import java.sql.Time;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

/**
 *
 * @author e11001a
 */
@Entity
@Table(name = "sys_db_operation")
@NamedQueries({
    @NamedQuery(name = "DbOperation.findAll", query = "SELECT d FROM DbOperation d")})
public class DbOperation implements DbOperationInterface {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "type")
    private String type;
    @Column(name = "registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;
    @Column(name = "record_time")
    private Time recordTime;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "data_new")
    private String dataNew;
    @Size(max = 255)
    @Column(name = "entity_ref")
    private String entityRef;
    @Size(max = 100)
    @Column(name = "entity_id")
    private String entityId;
    @Size(max = 45)
    @Column(name = "success")
    private Boolean success;
    @Size(max = 300)
    @Column(name = "message")
    private String message;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JoinFetch(JoinFetchType.OUTER)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "massive_operation_id", referencedColumnName = "id")
    @ManyToOne
    private MassiveOperation massiveOperation;

    public DbOperation() {
    }

    public DbOperation(Long id) {
        this.id = id;
    }

    public DbOperation(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        this.id = (Long) id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Date getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public Time getRecordTime() {
        return recordTime;
    }

    @Override
    public void setRecordTime(Time recordTime) {
        this.recordTime = recordTime;
    }

    @Override
    public String getDataNew() {
        return dataNew;
    }

    @Override
    public void setDataNew(String dataNew) {
        this.dataNew = dataNew;
    }

    @Override
    public String getEntityRef() {
        return entityRef;
    }

    @Override
    public void setEntityRef(String entityRef) {
        this.entityRef = entityRef;
    }

    @Override
    public String getEntityId() {
        return entityId;
    }

    @Override
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public Boolean getSuccess() {
        return success;
    }

    @Override
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(BaseEntity user) {
        this.user = (User)user;
    }

    @Override
    public MassiveOperation getMassiveOperation() {
        return massiveOperation;
    }

    @Override
    public void setMassiveOperation(MassiveOperationInterface massiveOperation) {
        this.massiveOperation = (MassiveOperation) massiveOperation;
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
        if (!(object instanceof DbOperation)) {
            return false;
        }
        DbOperation other = (DbOperation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacv.agogui.model.entities.DbOperation[ id=" + id + " ]";
    }
    
}
