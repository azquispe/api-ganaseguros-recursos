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
    @Column(name = "asiento_cab_tem_id")
    private Long asientoCabTemId;

    @Column(name = "cod_reporte")
    private Long codReporte;

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


    @Column(name = "tipo_reporte_id")
    private Long tipoReporteId;

    @Column(name = "nombre_archivo")
    private String nombreArchivo;


    @Column(name = "fecha_registro")
    private Date fechaRegistro;

    @Column(name = "usuario_registro_id")
    private Long usuarioRegistroId;

    @Column(name = "estado_id")
    private Long estadoId;
}
