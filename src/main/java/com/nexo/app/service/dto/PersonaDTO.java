package com.nexo.app.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.nexo.app.domain.Persona} entity.
 */
public class PersonaDTO implements Serializable {

    private Long id;

    private String nombre;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String docIdentidad;

    private LocalDate fechaNac;

    private String sexo;

    private String nacionalidad;

    private String email;

    private String telefono;

    private String estado;

    private Boolean natural;

    private String nombreComercial;

    private Double scoreComprador;

    private Double scoreVendedor;


    private Long paisId;

    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getDocIdentidad() {
        return docIdentidad;
    }

    public void setDocIdentidad(String docIdentidad) {
        this.docIdentidad = docIdentidad;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean isNatural() {
        return natural;
    }

    public void setNatural(Boolean natural) {
        this.natural = natural;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public Double getScoreComprador() {
        return scoreComprador;
    }

    public void setScoreComprador(Double scoreComprador) {
        this.scoreComprador = scoreComprador;
    }

    public Double getScoreVendedor() {
        return scoreVendedor;
    }

    public void setScoreVendedor(Double scoreVendedor) {
        this.scoreVendedor = scoreVendedor;
    }

    public Long getPaisId() {
        return paisId;
    }

    public void setPaisId(Long paisId) {
        this.paisId = paisId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonaDTO personaDTO = (PersonaDTO) o;
        if (personaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellidoPaterno='" + getApellidoPaterno() + "'" +
            ", apellidoMaterno='" + getApellidoMaterno() + "'" +
            ", docIdentidad='" + getDocIdentidad() + "'" +
            ", fechaNac='" + getFechaNac() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", nacionalidad='" + getNacionalidad() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", estado='" + getEstado() + "'" +
            ", natural='" + isNatural() + "'" +
            ", nombreComercial='" + getNombreComercial() + "'" +
            ", scoreComprador=" + getScoreComprador() +
            ", scoreVendedor=" + getScoreVendedor() +
            ", pais=" + getPaisId() +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
