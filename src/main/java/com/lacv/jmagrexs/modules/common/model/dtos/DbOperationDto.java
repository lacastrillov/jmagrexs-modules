/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.common.model.dtos;

import com.lacv.jmagrexs.annotation.ColumnWidth;
import com.lacv.jmagrexs.annotation.LabelField;
import com.lacv.jmagrexs.annotation.NotNull;
import com.lacv.jmagrexs.annotation.Order;
import com.lacv.jmagrexs.annotation.ReadOnly;
import com.lacv.jmagrexs.annotation.Size;
import com.lacv.jmagrexs.annotation.TextField;
import com.lacv.jmagrexs.annotation.TypeFormField;
import com.lacv.jmagrexs.domain.BaseDto;
import com.lacv.jmagrexs.enums.FieldType;
import com.lacv.jmagrexs.modules.security.model.dtos.UserDto;
import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 *
 * @author lcastrillo
 */
@LabelField("name")
public class DbOperationDto implements BaseDto {

    private static final long serialVersionUID = 1L;
    
    @Order(1)
    @ReadOnly
    @ColumnWidth(100)
    @TextField("Id")
    private Long id;
    
    @Order(2)
    @NotNull
    @Size(min=1,max=200)
    @ColumnWidth(200)
    @TextField("Name")
    private String name;
    
    @Order(3)
    @Size(max=45)
    @ColumnWidth(100)
    @TextField("Type")
    @TypeFormField(value = FieldType.LIST, data = {"create", "update", "update_byfilter", "delete", "delete_byfilter"})
    private String type;
    
    @Order(4)
    @ColumnWidth(200)
    @TypeFormField(value = FieldType.DATETIME)
    @TextField("Registration Date")
    private Date registrationDate;
    
    @Order(5)
    @ColumnWidth(150)
    @TextField("Record Time")
    private Time recordTime;
    
    @Order(6)
    @Size(max=2147483647)
    @ColumnWidth(200)
    @TypeFormField(value = FieldType.TEXT_AREA)
    @TextField("Data In")
    private String dataIn;
    
    @Order(7)
    @Size(max=2147483647)
    @ColumnWidth(200)
    @TypeFormField(value = FieldType.TEXT_AREA)
    @TextField("Data Out")
    private String dataOut;
    
    @Order(8)
    @Size(max=255)
    @ColumnWidth(200)
    @TextField("Entity Ref")
    private String entityRef;
    
    @Order(9)
    @Size(max=100)
    @ColumnWidth(200)
    @TextField("Entity Id")
    private String entityId;
    
    @Order(10)
    @ColumnWidth(100)
    @TextField("Duration")
    @TypeFormField(value = FieldType.DURATION)
    private Integer duration;
    
    @Order(11)
    @Size(max=45)
    @ColumnWidth(150)
    @TextField("Status")
    @TypeFormField(value = FieldType.LIST, data = {"Creado", "Procesando", "Detenido", "Terminado"})
    private String status;
    
    @Order(12)
    @Size(max=300)
    @ColumnWidth(300)
    @TextField("Message")
    private String message;
    
    @Order(13)
    @NotNull
    @TextField("Usuario")
    private UserDto user;
    
    @Order(14)
    @ColumnWidth(200)
    @TextField("Massive Operation")
    private MassiveOperationDto massiveOperation;
    

    public DbOperationDto() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        this.id = (Long) id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name =  name;
    }

    public String getDataOut() {
        return dataOut;
    }

    public void setDataOut(String dataOut) {
        this.dataOut =  dataOut;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration =  duration;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId =  entityId;
    }

    public String getEntityRef() {
        return entityRef;
    }

    public void setEntityRef(String entityRef) {
        this.entityRef =  entityRef;
    }

    public MassiveOperationDto getMassiveOperation() {
        return massiveOperation;
    }

    public void setMassiveOperation(MassiveOperationDto massiveOperation) {
        this.massiveOperation =  massiveOperation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message =  message;
    }

    public String getDataIn() {
        return dataIn;
    }

    public void setDataIn(String dataIn) {
        this.dataIn =  dataIn;
    }

    public Time getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Time recordTime) {
        this.recordTime =  recordTime;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate =  registrationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status =  status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type =  type;
    }
    
    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
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
        if (!(object instanceof DbOperationDto)) {
            return false;
        }
        DbOperationDto other = (DbOperationDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacv.jmagrexs.modules.common.model.dtos.DbOperationDto[ id=" + id + " ]";
    }
    
}
