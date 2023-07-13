package com.ganaseguros.recursos.erp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "asiento_cab_tem", schema = "erp")
public class AsientoCabTemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long asientoCabTemId;

    @Column(name = "cod_reporte")
    private Long codReporte;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "extension")
    private String extension;

    @Column(name = "primer_nombre_razon_social")
    private String primerNombreRazonSocial;

    @Column(name = "segundo_nombre")
    private String segundoNombre;

    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @Column(name = "apellido_casado")
    private String apellidoCasado;

    @Column(name = "group_code")
    private String groupCode;

    @Column(name = "vat_group")
    private String vatGroup;

    @Column(name = "debitor_account")
    private String debitorAccount;

    @Column(name = "group_num")
    private String groupNum;

    @Column(name = "numero_siniestro")
    private String numeroSiniestro;

    @Column(name = "numero_poliza")
    private String numeroPoliza;

    @Column(name = "monto_siniestro_pagar")
    private String montoSiniestroPagar;

    @Column(name = "codigo_autorizacion")
    private String codigoAutorizacion;

    @Column(name = "nro_factura")
    private String nroFactura;

    @Column(name = "siniestro_id")
    private String siniestroId;

    @Column(name = "cod_ramo")
    private Long codRamo;

    @Column(name = "tax_date")
    private String taxDate;

    @Column(name = "due_date")
    private String dueDate;

    @Column(name = "reference_date")
    private String referenceDate;

    @Column(name = "nroasiento_sap")
    private String nroasientoSap;

    @Column(name = "json_enviado_sap")
    private String jsonEnviadoSap;

    @Column(name = "json_respuesta_sap")
    private String jsonRespuestaSap;

    @Column(name = "tipo_reporte_id", nullable = false)
    private Long tipoReporteId;

    @Column(name = "nombre_archivo", nullable = false)
    private String nombreArchivo;

    @Column(name = "fecha_registro", nullable = false)
    private Date fechaRegistro;

    @Column(name = "usuario_registro_id", nullable = false)
    private Long usuarioRegistroId;

    @Column(name = "estado_id", nullable = false)
    private Long estadoId;

}
