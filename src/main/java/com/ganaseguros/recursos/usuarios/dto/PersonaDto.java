package com.ganaseguros.recursos.usuarios.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
public class PersonaDto {
    private Long personaId;
    private Long generoId;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String apellidoEsposo;
    private String numeroDocumento;
    private String complemento;
    private Long ciudadExpedidoId;
    private String numeroCelular;
    private String correoElectronico;
    private Date fechaNacimiento;
    private Date fechaRegistro;
    private Date fechaModificacion;

    private Long usuarioId;
    private String ciudadExpedido;
    private String genero;

    public PersonaDto(Long personaId, Long generoId, String nombres, String apellidoPaterno, String apellidoMaterno, String apellidoEsposo, String numeroDocumento, String complemento, Long ciudadExpedidoId, String numeroCelular, String correoElectronico, Date fechaNacimiento, Date fechaRegistro, Date fechaModificacion, Long usuarioId, String ciudadExpedido, String genero) {
        this.personaId = personaId;
        this.generoId = generoId;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.apellidoEsposo = apellidoEsposo;
        this.numeroDocumento = numeroDocumento;
        this.complemento = complemento;
        this.ciudadExpedidoId = ciudadExpedidoId;
        this.numeroCelular = numeroCelular;
        this.correoElectronico = correoElectronico;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
        this.fechaModificacion = fechaModificacion;
        this.usuarioId = usuarioId;
        this.ciudadExpedido = ciudadExpedido;
        this.genero = genero;
    }
}
