package com.codenbugs.sistemacongresos.modelos;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class Usuario {

    private Integer id;
    private String nombreCompleto;
    private String organizacion;
    private String email;
    private String telefono;
    private String identificacion;
    private byte[] foto;
    private String password;
    private boolean activo;
    private boolean esAdministradorSistema;
    private BigDecimal saldo;
    private Timestamp fechaRegistro;

    public Usuario() {
    }

    // Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
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

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isEsAdministradorSistema() {
        return esAdministradorSistema;
    }

    public void setEsAdministradorSistema(boolean esAdministradorSistema) {
        this.esAdministradorSistema = esAdministradorSistema;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        // No incluimos password ni foto
        return "Usuario{"
                + "id=" + id
                + ", nombreCompleto='" + nombreCompleto + '\''
                + ", organizacion='" + organizacion + '\''
                + ", email='" + email + '\''
                + ", telefono='" + telefono + '\''
                + ", identificacion='" + identificacion + '\''
                + ", activo=" + activo
                + ", esAdministradorSistema=" + esAdministradorSistema
                + ", saldo=" + saldo
                + ", fechaRegistro=" + fechaRegistro
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuario)) {
            return false;
        }
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
