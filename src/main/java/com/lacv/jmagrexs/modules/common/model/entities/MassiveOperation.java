/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.common.model.entities;

import com.lacv.jmagrexs.domain.BaseEntity;
import com.lacv.jmagrexs.modules.security.model.entities.User;
import java.sql.Time;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
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
@Table(name = "sys_massive_operation")
@NamedQueries({
    @NamedQuery(name = "MassiveOperation.findAll", query = "SELECT m FROM MassiveOperation m")})
public class MassiveOperation implements BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "type")
    private String type;
    @Size(max = 200)
    @Column(name = "source_file")
    private String sourceFile;
    @Column(name = "registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;
    @Column(name = "record_time")
    private Time recordTime;
    @Column(name = "total")
    private Integer total;
    @Column(name = "processed")
    private Integer processed;
    @Column(name = "percentage")
    private Integer percentage;
    @Column(name = "total_successful")
    private Integer totalSuccessful;
    @Column(name = "total_failed")
    private Integer totalFailed;
    @Column(name = "duration")
    private Integer duration;
    @Size(max = 45)
    @Column(name = "status")
    private String status;
    @Size(max = 300)
    @Column(name = "message")
    private String message;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JoinFetch(JoinFetchType.OUTER)
    @ManyToOne(optional = false)
    private User user;
    @OneToMany(mappedBy = "massiveOperation")
    private List<DbOperation> dbOperationList;

    public MassiveOperation() {
    }

    public MassiveOperation(Integer id) {
        this.id = id;
    }

    public MassiveOperation(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        this.id = (Integer) id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Time getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Time recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getProcessed() {
        return processed;
    }

    public void setProcessed(Integer processed) {
        this.processed = processed;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Integer getTotalSuccessful() {
        return totalSuccessful;
    }

    public void setTotalSuccessful(Integer totalSuccessful) {
        this.totalSuccessful = totalSuccessful;
    }

    public Integer getTotalFailed() {
        return totalFailed;
    }

    public void setTotalFailed(Integer totalFailed) {
        this.totalFailed = totalFailed;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<DbOperation> getDbOperationList() {
        return dbOperationList;
    }

    public void setDbOperationList(List<DbOperation> dbOperationList) {
        this.dbOperationList = dbOperationList;
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
        if (!(object instanceof MassiveOperation)) {
            return false;
        }
        MassiveOperation other = (MassiveOperation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacv.agogui.model.entities.MassiveOperation[ id=" + id + " ]";
    }
    
}
