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
public class MassiveOperationDto implements BaseDto {

    private static final long serialVersionUID = 1L;
    
    @Order(1)
    @ReadOnly
    @ColumnWidth(100)
    @TextField("Id")
    private Integer id;
    
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
    @TypeFormField(value = FieldType.LIST, data = {"SAVE", "DELETE"})
    private String type;
    
    @Order(4)
    @Size(max=200)
    @ColumnWidth(200)
    @TypeFormField(FieldType.FILE_UPLOAD)
    @TextField("Source File")
    private String sourceFile;
    
    @Order(5)
    @ColumnWidth(100)
    @TextField("Total")
    private Integer total;
    
    @Order(6)
    @ColumnWidth(300)
    @TypeFormField(value = FieldType.PERCENTAJE)
    @TextField("Percentage")
    private Integer percentage;
    
    @Order(7)
    @ColumnWidth(100)
    @TextField("Processed")
    private Integer processed;
    
    @Order(8)
    @ColumnWidth(130)
    @TextField("Total Successful")
    private Integer totalSuccessful;
    
    @Order(9)
    @ColumnWidth(100)
    @TextField("Total Failed")
    private Integer totalFailed;
    
    @Order(10)
    @ColumnWidth(200)
    @TypeFormField(value = FieldType.DATETIME)
    @TextField("Registration Date")
    private Date registrationDate;
    
    @Order(11)
    @ColumnWidth(150)
    @TextField("Record Time")
    private Time recordTime;
    
    @Order(12)
    @ColumnWidth(100)
    @TextField("Duration")
    @TypeFormField(value = FieldType.DURATION)
    private Integer duration;
    
    @Order(13)
    @Size(max=45)
    @ColumnWidth(150)
    @TextField("Status")
    @TypeFormField(value = FieldType.LIST, data = {"Creado", "Procesando", "Detenido", "Terminado"})
    private String status;
    
    @Order(14)
    @Size(max=300)
    @ColumnWidth(300)
    @TextField("Message")
    private String message;
    
    @Order(15)
    @NotNull
    @TextField("Usuario")
    private UserDto user;
    
    private List<DbOperationDto> dbOperationList;

    public MassiveOperationDto() {
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
        this.name =  name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration =  duration;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message =  message;
    }

    public List<DbOperationDto> getDbOperationList() {
        return dbOperationList;
    }

    public void setDbOperationList(List<DbOperationDto> dbOperationList) {
        this.dbOperationList =  dbOperationList;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage =  percentage;
    }

    public Integer getProcessed() {
        return processed;
    }

    public void setProcessed(Integer processed) {
        this.processed =  processed;
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

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile =  sourceFile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status =  status;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total =  total;
    }

    public Integer getTotalFailed() {
        return totalFailed;
    }

    public void setTotalFailed(Integer totalFailed) {
        this.totalFailed =  totalFailed;
    }

    public Integer getTotalSuccessful() {
        return totalSuccessful;
    }

    public void setTotalSuccessful(Integer totalSuccessful) {
        this.totalSuccessful =  totalSuccessful;
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
        if (!(object instanceof MassiveOperationDto)) {
            return false;
        }
        MassiveOperationDto other = (MassiveOperationDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacv.jmagrexs.modules.common.model.dtos.MassiveOperationDto[ id=" + id + " ]";
    }
    
}
