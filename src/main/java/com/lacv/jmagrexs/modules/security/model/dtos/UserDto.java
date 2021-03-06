/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacv.jmagrexs.modules.security.model.dtos;

import com.lacv.jmagrexs.annotation.ColumnWidth;
import com.lacv.jmagrexs.annotation.GroupField;
import com.lacv.jmagrexs.annotation.HideField;
import com.lacv.jmagrexs.annotation.ImageResize;
import com.lacv.jmagrexs.annotation.LabelField;
import com.lacv.jmagrexs.annotation.NotNull;
import com.lacv.jmagrexs.annotation.Order;
import com.lacv.jmagrexs.annotation.ReadOnly;
import com.lacv.jmagrexs.annotation.Size;
import com.lacv.jmagrexs.annotation.TextField;
import com.lacv.jmagrexs.annotation.TypeFormField;
import com.lacv.jmagrexs.domain.BaseDto;
import com.lacv.jmagrexs.enums.FieldType;
import com.lacv.jmagrexs.enums.HideView;
import java.util.Date;

/**
 *
 * @author grupot
 */
@LabelField("firstName")
public class UserDto implements BaseDto {

    private static final long serialVersionUID = 1L;
    
    @Order(1)
    @ColumnWidth(100)
    @ReadOnly
    private Integer id;
    
    @Order(2)
    @Size(max=100)
    @NotNull
    @GroupField("Datos Personales")
    @TextField("Nombre")
    private String firstName;
    
    @Order(3)
    @Size(max=100)
    @NotNull
    @GroupField("Datos Personales")
    @TextField("Apellidos")
    private String lastName;
    
    @Order(4)
    @TypeFormField(value = FieldType.LIST, data = {"CC:Cédula de ciudadania","TI:Tarjeta de identidad","CE:Cédula de Extranjeria","NIT:NIT","PAS:Pasaporte"})
    @GroupField("Datos Personales")
    @TextField("Tipo Documento")
    private String documentType;
    
    @Order(5)
    @GroupField("Datos Personales")
    @TextField("Documento Id")
    private Long idDocument;
    
    @Order(6)
    @Size(max=200)
    @TypeFormField(FieldType.IMAGE_FILE_UPLOAD)
    @ImageResize({"300,300", "500,500", "800,800"})
    @HideField({HideView.GRID, HideView.FILTER})
    @GroupField("Datos Personales")
    @TextField("Foto perfil")
    private String urlPhoto;
    
    @Order(7)
    @Size(max=1)
    @TypeFormField(value = FieldType.LIST, data = {"F:Femenino", "M:Masculino"})
    @GroupField("Datos Personales")
    @TextField("Genero")
    private String gender;
    
    @Order(8)
    @HideField({HideView.FILTER, HideView.GRID})
    @GroupField("Datos Personales")
    @TextField("Fecha Nacimieto")
    private Date birthday;
    
    @Order(9)
    @Size(max=100)
    @GroupField("Datos de Contacto")
    @TextField("Tel&eacute;fono")
    private String phone;
    
    @Order(10)
    @Size(max=100)
    @GroupField("Datos de Contacto")
    @TextField("Celular")
    private String cell;
    
    @Order(11)
    @Size(max=100)
    @NotNull
    @TypeFormField(FieldType.EMAIL)
    @GroupField("Datos de Contacto")
    @TextField("Correo")
    private String email;
    
    @Order(12)
    @GroupField("Datos de Contacto")
    @TextField("Ciudad")
    private String city;
    
    @Order(13)
    @HideField({HideView.FILTER, HideView.GRID})
    @GroupField("Datos de Contacto")
    @TextField("Direcci&oacute;n")
    private String address;
    
    @Order(14)
    @Size(max=100)
    @GroupField("Datos de Cuenta")
    @TextField("Usuario")
    private String username;
    
    @Order(15)
    @Size(max=60)
    @ReadOnly
    @TypeFormField(FieldType.PASSWORD)
    @HideField({HideView.FILTER, HideView.GRID, HideView.FORM})
    @GroupField("Datos de Cuenta")
    @TextField("Contrase&ntilde;a")
    private String password;
    
    @Order(16)
    @Size(max=200)
    @TypeFormField(FieldType.URL)
    @HideField({HideView.GRID, HideView.FILTER})
    @GroupField("Datos de Cuenta")
    @TextField("P&aacute;gina")
    private String link;
    
    @Order(17)
    @Size(max=200)
    @HideField({HideView.FILTER, HideView.GRID})
    @GroupField("Datos de Cuenta")
    @TextField("Token Usuario")
    private String tokenUser;
    
    @Order(18)
    @Size(max=45)
    @TypeFormField(value = FieldType.MULTI_SELECT, data = {"Active", "Inactive", "Locked", "Deleted"})
    @GroupField("Datos de Cuenta")
    @TextField("Estado")
    private String status;
    
    @Order(19)
    @TextField("Verificado")
    @TypeFormField(FieldType.ON_OFF)
    @GroupField("Datos de Cuenta")
    private Boolean verified;
    
    @Order(20)
    @ReadOnly
    @HideField({HideView.FILTER, HideView.GRID})
    @GroupField("Datos de Cuenta")
    @TextField("Intentos fallidos")
    private Integer failedAttempts;
    
    @Order(21)
    @HideField({HideView.GRID})
    @GroupField("Datos de Cuenta")
    @TextField("Expiraci&oacute;n contrase&ntilde;a")
    private Date passwordExpiration;
    
    @Order(22)
    @GroupField("Datos de Cuenta")
    @TextField("Fecha Registro")
    private Date registrationDate;
    
    @Order(23)
    @ReadOnly
    @TypeFormField(FieldType.DATETIME)
    @GroupField("Datos de Cuenta")
    @TextField("Ultimo login")
    private Date lastLogin;
    

    public UserDto() {
    }

    public UserDto(Integer id) {
        this.id = id;
    }

    public UserDto(Integer id, String email) {
        this.id = id;
        this.email = email;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        this.id = (Integer) id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Long idDocument) {
        this.idDocument = idDocument;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public String getUrlPhoto() {
        return urlPhoto;
    }
    
    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public String getTokenUser() {
        return tokenUser;
    }

    public void setTokenUser(String tokenUser) {
        this.tokenUser = tokenUser;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Integer getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(Integer failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public Date getPasswordExpiration() {
        return passwordExpiration;
    }

    public void setPasswordExpiration(Date passwordExpiration) {
        this.passwordExpiration = passwordExpiration;
    }
    
    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
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
        if (!(object instanceof UserDto)) {
            return false;
        }
        UserDto other = (UserDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacv.marketplatform.entities.UserDto[ id=" + id + " ]";
    }
    
}
