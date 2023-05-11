package com.ganaseguros.recursos.erp.service;

import com.ganaseguros.recursos.erp.dao.*;
import com.ganaseguros.recursos.erp.dto.*;
import com.ganaseguros.recursos.erp.entity.*;
import com.ganaseguros.recursos.erp.rest_template.EnvioAsientoRestTemplate;
import com.ganaseguros.recursos.transversal.dao.IDominioDao;
import com.ganaseguros.recursos.transversal.entity.DominioEntity;
import com.ganaseguros.recursos.utils.dto.ResponseDto;
import com.ganaseguros.recursos.utils.funciones.FuncionesFechas;
import com.ganaseguros.recursos.utils.funciones.FuncionesGenerales;
import com.google.gson.Gson;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AsientosContablesService {

    @Value("${dir.archivo.contables}")
    private String dirArchivoContables;

    @Autowired
    ITablaIntermediaDao iTablaIntermediaDao;
    @Autowired
    IAsientoCabDao iAsientoCabDao;
    @Autowired
    IAsientoDetDao iAsientoDetDao;

    @Autowired
    IAsientoEnviadoDao iAsientoEnviadoDao;

    @Autowired
    IDominioDao iDominioDao;

    @Autowired
    IColumnasReporteDao iColumnasReporteDao;

    @Autowired
    EnvioAsientoRestTemplate envioAsientoRestTemplate;

    @Autowired
    IViewReporteDao iViewReporteDao;

    @Autowired
    IViewRamoDao iViewRamoDao;


    @Transactional
    public ResponseDto cargarArchivoAsiento(Long pDominioId, @RequestParam(required = true) MultipartFile file) {
        ResponseDto res = new ResponseDto();
        try {

            Optional<DominioEntity> objDominio = iDominioDao.getDominioByDominioId(pDominioId);
            if (!objDominio.isPresent()) {
                res.setCodigo("1001");
                res.setMensaje("Dominio no existe");
                return res;
            }
            String vNombreArchivo = objDominio.get().getDominioId() + "_" + objDominio.get().getDescripcion() + ".xlsx";


            res.setCodigo("1000");
            res.setMensaje("con exito");
            return res;
        } catch (Exception ex) {
            res.setCodigo("1001");
            res.setMensaje("Error: " + ex.toString());
            return res;
        }
    }

    @Transactional
    public ResponseDto cargarArchivoAsientoOld(MultipartFile file, Long pDominioId, Long pUsuarioId) {
        ResponseDto res = new ResponseDto();
        List<TablaIntermediaEntity> lstTabInter = new ArrayList<>();
        try {

            iAsientoDetDao.deleteAll();
            iAsientoCabDao.deleteAll();

            InputStream x = file.getInputStream();

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(x);
            int cantidadHojas = workbook.getNumberOfSheets();
            for (int i = 0; i < cantidadHojas; i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rowIterator = sheet.iterator();

                Row row;
                int contadorFila = 0;
                List<FilaTablaIntermediaDto> lstFilaTablaIntermediaDto = new ArrayList<>();
                FilaTablaIntermediaDto filaTablaIntermediaDto;
                Long codRepo = 0L;
                while (rowIterator.hasNext()) { // recorre fila
                    row = rowIterator.next();


                    if (contadorFila == 0) {
                        codRepo = Math.round(row.getCell(1).getNumericCellValue());
                        row = rowIterator.next();
                    }
                    // Obtenemos el iterator que permite recorres todas las celdas de una fila
                    Iterator<Cell> cellIterator = row.cellIterator();
                    Cell celda;
                    int contadorColumna = 0;
                    Long codRamo = 0L;

                    while (cellIterator.hasNext()) { // recorre columna
                        celda = cellIterator.next();
                        switch (contadorColumna) {
                            case 0:
                                if (contadorFila > 0) {
                                    codRamo = Math.round(celda.getNumericCellValue());
                                }
                                break;
                            case 1:
                                break;
                            default:
                                filaTablaIntermediaDto = new FilaTablaIntermediaDto();
                                if (contadorFila == 0) {
                                    String codConcepto = Math.round(celda.getNumericCellValue()) + "";
                                    filaTablaIntermediaDto.setCodigoConcepto(codConcepto);
                                    lstFilaTablaIntermediaDto.add(filaTablaIntermediaDto);
                                } else {
                                    FilaTablaIntermediaDto obj = lstFilaTablaIntermediaDto.get(contadorColumna - 2);
                                    obj.setMonto(celda.getNumericCellValue() + "");
                                    lstFilaTablaIntermediaDto.set(contadorColumna - 2, obj);
                                }

                        }
                        contadorColumna++;
                    }
                    if (lstTabInter.isEmpty()) {
                        lstTabInter = iTablaIntermediaDao.findAll();
                    }
                    if (contadorFila > 0) {
                        Long codigoRamo = codRamo;
                        Long codigoRepo = codRepo;
                        List<TablaIntermediaEntity> lstRes = lstTabInter.stream()
                                .filter(datoIntermedio -> codigoRamo == datoIntermedio.getCodRamo() && codigoRepo == datoIntermedio.getCodReporte())
                                .collect(Collectors.toList());


                        AsientoCabTemEntity insertCab = new AsientoCabTemEntity();
                        insertCab.setCodReporte(codRepo);
                        insertCab.setCodRamo(codigoRamo);
                        insertCab.setTaxDate("01/01/2000");
                        insertCab.setDueDate("01/01/2000");
                        insertCab.setReferenceDate("01/01/2000");
                        insertCab.setUsuarioRegistroId(pUsuarioId);
                        insertCab.setTipoReporteId(pDominioId);
                        insertCab.setFechaRegistro(new Date());
                        insertCab.setEstadoId(1000L);
                        iAsientoCabDao.save(insertCab);
                        List<AsientoDetTemEntity> insertDetList = new ArrayList<>();
                        AsientoDetTemEntity insertDet;
                        for (FilaTablaIntermediaDto objFila : lstFilaTablaIntermediaDto) {
                            String codConcepto = FuncionesGenerales.padLeftZeros(objFila.getCodigoConcepto(), 5);
                            List<TablaIntermediaEntity> lstRes2 = lstRes.stream().filter(l -> codConcepto.equals(l.getCodigoConcepto())).collect(Collectors.toList());
                            if (lstRes2.size() == 1) {
                                insertDet = new AsientoDetTemEntity();
                                insertDet.setAsientoCabTemId(insertCab.getAsientoCabTemId());
                                insertDet.setCodigoConcepto(objFila.getCodigoConcepto());
                                insertDet.setAmount(new Double(objFila.getMonto()));
                                insertDet.setEstadoId(1000L);
                                insertDetList.add(insertDet);
                            }
                        }
                        iAsientoDetDao.saveAll(insertDetList);
                    }
                    contadorFila++;
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        res.setCodigo("1000");
        res.setMensaje("con exito");
        return res;


    }

    @Transactional
    public ResponseDto cargarArchivoAsiento(MultipartFile file, Long pTipoReporteId, Long pUsuarioId) {
        ResponseDto res = new ResponseDto();
        String mensaje = "";
        List<TablaIntermediaEntity> lstTabInter = new ArrayList<>();
        List<ColumnasReporteEntity> lstColumnasReporte = new ArrayList<>();
        int contadorColumna = 0;
        int contadorFila = 0;
        Long codRepo = 0L;
        Long codRamo = 0L;
        try {

            iAsientoDetDao.deleteAll();
            iAsientoCabDao.deleteAll();
            lstColumnasReporte = iColumnasReporteDao.findAll();

            List<ColumnasReporteEntity> lstColumnasPorTipoReporte = lstColumnasReporte.stream().filter(r -> r.getTipoReporteId().equals(pTipoReporteId)).collect(Collectors.toList());
            if (lstColumnasPorTipoReporte.isEmpty()) {
                res.setCodigo("1001");
                res.setMensaje("no existe columnas excel para el reporte de tipo: " + iDominioDao.getDominioByDominioId(pTipoReporteId).get().getDescripcion());
                return res;
            }

            InputStream x = file.getInputStream();

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(x);
            int cantidadHojas = workbook.getNumberOfSheets();
            for (int i = 0; i < cantidadHojas; i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rowIterator = sheet.iterator();

                Row row;
                contadorFila = 0;
                List<FilaTablaIntermediaDto> lstFilaTablaIntermediaDto = new ArrayList<>();
                FilaTablaIntermediaDto filaTablaIntermediaDto;
                codRepo = 0L;
                while (rowIterator.hasNext()) { // recorre fila
                    row = rowIterator.next();

                    if (contadorFila == 1) {
                        try {
                            codRepo = Math.round(row.getCell(1).getNumericCellValue());
                            final Long codRepoo = codRepo;
                            if (lstColumnasPorTipoReporte.stream().filter(r -> r.getCodReporte().equals(codRepoo)).collect(Collectors.toList()).isEmpty()) {
                                mensaje = mensaje + "PESTAÑA: " + (i + 1) + " codigo de reporte: " + codRepo + " no pertenes al tipo de reporte: " + iDominioDao.getDominioByDominioId(pTipoReporteId).get().getDescripcion() + "\n";
                                break;
                            }
                        } catch (Exception ex) {
                            //... registrar log
                            mensaje = mensaje + "PESTAÑA: " + (i + 1) + " no se peude leer el codigo de reporte \n";
                            break;
                        }
                    }
                    // Obtenemos el iterator que permite recorres todas las celdas de una fila
                    Iterator<Cell> cellIterator = row.cellIterator();
                    Cell celda;

                    contadorColumna = 0;
                    codRamo = 0L;
                    List<Long> lstCodigoConcepto = new ArrayList<>();
                    while (cellIterator.hasNext()) { // recorre columna                                                                                                                                while (cellIterator.hasNext()) { // recorre columna
                        celda = cellIterator.next();
                        switch (contadorColumna) {
                            case 0:
                                if (contadorFila > 3)
                                    codRamo = Math.round(celda.getNumericCellValue());
                                break;
                            case 1:
                                break;
                            default:
                                int columnaExcel = contadorColumna - 1;
                                if (contadorFila == 3) {
                                    lstCodigoConcepto = this.obtieneCodigoConcepto(lstColumnasReporte, columnaExcel, codRepo.intValue());
                                    for (Long codConcep : lstCodigoConcepto) {
                                        filaTablaIntermediaDto = new FilaTablaIntermediaDto();
                                        filaTablaIntermediaDto.setCodigoConcepto(codConcep + "");
                                        filaTablaIntermediaDto.setColumnaExcel(contadorColumna - 1);
                                        lstFilaTablaIntermediaDto.add(filaTablaIntermediaDto);
                                    }
                                }

                                boolean existeColumna = !lstColumnasReporte.stream().filter(r -> r.getColummaExcel().intValue() == columnaExcel).collect(Collectors.toList()).isEmpty();
                                if (contadorFila > 3 && existeColumna) {
                                    lstCodigoConcepto = this.obtieneCodigoConcepto(lstColumnasReporte, columnaExcel, codRepo.intValue());

                                    for (Long codConcep : lstCodigoConcepto) {
                                        String monto = "0";
                                        try {
                                            monto = celda.getStringCellValue();
                                        } catch (Exception ex) {
                                            monto = celda.getNumericCellValue() + "";
                                        } finally {
                                            lstFilaTablaIntermediaDto = this.modificarMonto(lstFilaTablaIntermediaDto, codConcep, monto);
                                        }

                                    }
                                }

                        }
                        contadorColumna++;
                        System.out.println("columna: " + contadorColumna);
                    }


                    if (lstTabInter.isEmpty()) {
                        lstTabInter = iTablaIntermediaDao.findAll();
                    }
                    if (contadorFila > 3) {
                        Long codigoRamo = codRamo;
                        Long codigoRepo = codRepo;

                        List<TablaIntermediaEntity> lstRes = lstTabInter.stream()
                                .filter(datoIntermedio -> codigoRamo == datoIntermedio.getCodRamo() && codigoRepo == datoIntermedio.getCodReporte())
                                .collect(Collectors.toList());

                        if (lstRes.isEmpty()) {
                            mensaje = mensaje + " no existe registros en la tabla intermedia \n ";
                        }


                        AsientoCabTemEntity insertCab = new AsientoCabTemEntity();
                        insertCab.setCodReporte(codRepo);
                        insertCab.setCodRamo(codigoRamo);
                        insertCab.setTaxDate("2023-03-01");
                        insertCab.setDueDate("2023-03-01");
                        insertCab.setReferenceDate("2023-03-01");
                        insertCab.setUsuarioRegistroId(pUsuarioId);
                        insertCab.setTipoReporteId(pTipoReporteId);
                        insertCab.setNombreArchivo(file.getOriginalFilename());
                        insertCab.setFechaRegistro(new Date());
                        insertCab.setEstadoId(1000L);
                        iAsientoCabDao.save(insertCab);
                        List<AsientoDetTemEntity> insertDetList = new ArrayList<>();
                        AsientoDetTemEntity insertDet;
                        for (FilaTablaIntermediaDto objFila : lstFilaTablaIntermediaDto) {
                            String codConcepto = FuncionesGenerales.padLeftZeros(objFila.getCodigoConcepto(), 5);
                            List<TablaIntermediaEntity> lstRes2 = lstRes.stream().filter(l -> codConcepto.equals(l.getCodigoConcepto())).collect(Collectors.toList());
                            if (lstRes2.size() == 1) {
                                insertDet = new AsientoDetTemEntity();
                                insertDet.setAsientoCabTemId(insertCab.getAsientoCabTemId());
                                insertDet.setCodigoConcepto(objFila.getCodigoConcepto());
                                insertDet.setColumnaExcel(objFila.getColumnaExcel());
                                try {
                                    insertDet.setAmount(new Double(objFila.getMonto()));
                                } catch (Exception ex) {
                                    if(!objFila.getMonto().trim().equals(""))
                                        mensaje = mensaje + "el valor: "+objFila.getMonto()+" no se epude convertir en Numero (tipo Moneda) para el reporte :"+codRepo+" y ramo: "+codRamo+"\n";
                                    insertDet.setAmount(new Double(0));
                                }
                                insertDet.setEstadoId(1000L);
                                insertDetList.add(insertDet);
                            }
                        }
                        if (insertDetList.isEmpty()) {
                            mensaje = mensaje + " no existe asientos para codigo reporte: " + codigoRepo + " y ramo: " + codigoRamo + " \n";
                        }
                        iAsientoDetDao.saveAll(insertDetList);
                    }
                    contadorFila++;
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        res.setCodigo("1000");
        if (mensaje.isEmpty()) {
            res.setMensaje("se ha cargado el archivo con exito");
        } else {
            res.setMensaje("se ha cargado el archivo con las siguientes observaciones:\n\n " + mensaje);
        }

        return res;


    }

    private List<Long> obtieneCodigoConcepto(List<ColumnasReporteEntity> lstColumnas, int columnaExcel, int codRepo) {
        List<Long> codigoConceptos = new ArrayList<>();
        try {
            for (ColumnasReporteEntity obj : lstColumnas) {
                if (columnaExcel == obj.getColummaExcel().intValue() && codRepo == obj.getCodReporte()) {
                    codigoConceptos.add(obj.getCodConcepto());
                }
            }
            return codigoConceptos;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    private List<FilaTablaIntermediaDto> modificarMonto(List<FilaTablaIntermediaDto> lst, Long codConcepto, String monto) {
        List<FilaTablaIntermediaDto> lstModificado = new ArrayList<>();
        try {
            for (FilaTablaIntermediaDto obj : lst) {
                if (obj.getCodigoConcepto().equals(codConcepto + "")) {
                    obj.setMonto(monto);
                }
                lstModificado.add(obj);
            }
            return lstModificado;
        } catch (Exception ex) {
            return lst;
        }
    }

    public ResponseDto getReportesByUsuarioIdAndTipoReporteId(Long pUsuarioId, Long pTipoReporteId) {
        ResponseDto res = new ResponseDto();
        try {
            List<Object[]> lstReportes;
            if (pTipoReporteId > 0) {
                lstReportes = iAsientoCabDao.findReportesTemByUsuarioId(pUsuarioId, pTipoReporteId);
            } else {
                lstReportes = iAsientoCabDao.findReportesTemByUsuarioId(pUsuarioId);
            }

            if (lstReportes.size() == 0) {
                res.setCodigo("1001");
                res.setMensaje("No existe reportes para el usuario: " + pUsuarioId);
                return res;
            }
            List<ReporteDto> lstReporteDto = new ArrayList<>();
            ReporteDto objReporteDto;
            for (Object[] obj : lstReportes) {
                objReporteDto = new ReporteDto();
                objReporteDto.setCodReporte(new Long(obj[0] + ""));
                objReporteDto.setNombreReporte(obj[1] + "");
                objReporteDto.setTipoMoneda(obj[2] + "");
                objReporteDto.setTipoReporteId(new Long(obj[3] + ""));
                objReporteDto.setTipoReporte(obj[4] + "");
                lstReporteDto.add(objReporteDto);
            }

            res.setCodigo("1000");
            res.setMensaje("con exito");
            res.setElementoGenerico(lstReporteDto);
            return res;
        } catch (Exception ex) {
            res.setCodigo("1001");
            res.setMensaje(ex.toString());
            return res;
        }
    }

    public ResponseDto obtenerAsientoCabPorReporteId(Long pReporteId) {
        ResponseDto res = new ResponseDto();
        try {
            List<Object[]> lstRamos = iAsientoCabDao.findAsientoCabTemByReporteId(pReporteId);
            List<AsientoCabTemDto> lstRamoDto = new ArrayList<>();
            if (lstRamos.size() == 0) {
                res.setCodigo("1001");
                res.setMensaje("No existe Asientos Cab");
                return res;
            }
            AsientoCabTemDto objRamoDto;
            for (Object[] obj : lstRamos) {
                objRamoDto = new AsientoCabTemDto();
                objRamoDto.setAsientoCabTemId(new Long(obj[0] + ""));
                objRamoDto.setCodReporte(new Long(obj[1] + ""));
                objRamoDto.setCodRamo(new Long(obj[2] + ""));
                objRamoDto.setRamo(obj[3] + "");
                objRamoDto.setTaxDate(obj[4] + "");
                objRamoDto.setDueDate(obj[5] + "");
                objRamoDto.setReferenceDate(obj[6] + "");
                objRamoDto.setNroasientoSap(obj[7] + "");
                objRamoDto.setJsonEnviadoSap(obj[8] + "");
                objRamoDto.setJsonRespuestaSap(obj[9] + "");
                lstRamoDto.add(objRamoDto);
            }

            res.setCodigo("1000");
            res.setMensaje("con exito");
            res.setElementoGenerico(lstRamoDto);
            return res;
        } catch (Exception ex) {
            res.setCodigo("1001");
            res.setMensaje(ex.toString());
            return res;
        }
    }

    public ResponseDto getAsientosDetByAsientosCabId(List<Long> pAsientosCabId) {
        ResponseDto res = new ResponseDto();
        try {
            List<Object[]> lstAsiento = iAsientoDetDao.findAsientosDetTemByAsientosCabId(pAsientosCabId);
            List<AsientoDetTemDto> lstAsientoDto = new ArrayList<>();
            if (lstAsiento.size() == 0) {
                res.setCodigo("1001");
                res.setMensaje("No existe asientos detalle");
                return res;
            }
            AsientoDetTemDto objAsientoDto;
            for (Object[] obj : lstAsiento) {
                objAsientoDto = new AsientoDetTemDto();
                objAsientoDto.setAsientoDetTemId(new Long(obj[0] + ""));
                objAsientoDto.setAsientoCabTemId(new Long(obj[1] + ""));
                objAsientoDto.setCodigoConcepto(new Long(obj[2] + ""));
                objAsientoDto.setColumnaExcel(new Integer(obj[3] + ""));
                objAsientoDto.setAmountDebe(obj[4] + "");
                objAsientoDto.setAmountHaber(obj[5] + "");
                objAsientoDto.setDescripcionConcepto(obj[6] + "");
                objAsientoDto.setDescripcionCuenta(obj[7] + "");
                objAsientoDto.setDebeHaber(obj[8] + "");
                lstAsientoDto.add(objAsientoDto);
            }

            res.setCodigo("1000");
            res.setMensaje("con exito");
            res.setElementoGenerico(lstAsientoDto);
            return res;
        } catch (Exception ex) {
            res.setCodigo("1001");
            res.setMensaje(ex.toString());
            return res;
        }
    }


    public ResponseDto obtenerToken() {

        return envioAsientoRestTemplate.obtenerToken();

    }

    public ResponseDto enviarErpAsientoContable(ResponseDto res, RequestAsientoDto req) {
        try {
            Map<String, Object> asiento = req.getAsiento();
            if (!res.getCodigo().equals("1000")) {
                return res;
            }
            String token = (String) res.getElementoGenerico();
            Gson g = new Gson();
            res = envioAsientoRestTemplate.enviarErpAsientoContable(token, asiento);
            Optional<AsientoCabTemEntity> obj = iAsientoCabDao.findById(req.getAsientoCabTemId());
            Optional<ViewReporteEntity> objRep = iViewReporteDao.findById(obj.get().getCodReporte());
            Optional<ViewRamoEntity> objRamo = iViewRamoDao.findById(obj.get().getCodRamo());

            if (obj.isPresent()) {
                AsientoCabTemEntity update = obj.get();
                ResponseEntity<Map> resp = (ResponseEntity<Map>) res.getElementoGenerico();
                update.setNroasientoSap((String) resp.getBody().get("data"));
                update.setJsonEnviadoSap(g.toJson(asiento));
                update.setJsonRespuestaSap(g.toJson(resp.getBody()));
                iAsientoCabDao.save(update);
                AsientosEnviadosEntity insertAsientoEnviado = new AsientosEnviadosEntity();
                insertAsientoEnviado.setCodRamo(obj.get().getCodRamo());
                insertAsientoEnviado.setCodReporte(obj.get().getCodReporte());
                insertAsientoEnviado.setNombreReporte(objRep.get().getNombreReporte());
                insertAsientoEnviado.setNombreRamo(objRamo.get().getNombreRamo());
                insertAsientoEnviado.setUsuarioId(req.getUsuarioId());

                if (res.getElementoGenerico() != null) {
                    //ResponseEntity<Map> dataRespSap = ( ResponseEntity<Map>)res.getElementoGenerico();
                    insertAsientoEnviado.setNroAsiento((String) resp.getBody().get("data"));
                    insertAsientoEnviado.setJsonRecibido(g.toJson(resp.getBody()));
                }

                insertAsientoEnviado.setFechaDesde(req.getFechaDesde());
                insertAsientoEnviado.setFechaHasta(req.getFechaHasta());
                insertAsientoEnviado.setFechaEnviado(new Date());
                insertAsientoEnviado.setJsonEnviado(g.toJson(asiento));
                insertAsientoEnviado.setNombreArchivo(update.getNombreArchivo());
                insertAsientoEnviado.setEstadoAsientoId(update.getNroasientoSap() != null ? 1040L : 1041L);
                insertAsientoEnviado.setTipoReporteId(update.getTipoReporteId());
                insertAsientoEnviado.setEstadoId(1000L);
                iAsientoEnviadoDao.save(insertAsientoEnviado);
                return res;
            }else{
                res.setCodigo("1001");
                res.setMensaje("Envio fallido de asiento contable , AsientoCabTemID:"+req.getAsientoCabTemId());
                return res;
            }

        } catch (Exception ex) {
            res.setCodigo("1001");
            res.setMensaje(ex.toString());
            return res;
        }
    }

    /*public ResponseDto enviarErpAsientoContableTodos( Long usuarioId,Long pTipoReporteId) {

        ResponseDto resp = new ResponseDto();
        try{
            ResponseDto  res = this.obtenerToken();
            if(!res.getCodigo().equals("1000")){
                return res;
            }


            ResponseDto objResCab =  this.getReportesByUsuarioIdAndTipoReporteId(usuarioId,pTipoReporteId);
            if(!objResCab.getCodigo().equals("1000")){
                return objResCab;
            }
            List<ReporteDto>  lstRepo = (List<ReporteDto>)objResCab.getElementoGenerico();
            int cantidadAsientoEnviado=0;
            for (ReporteDto objRepo : lstRepo) {

                ResponseDto resCab  =   this.obtenerAsientoCabPorReporteId(objRepo.getCodReporte());
                if(!resCab.getCodigo().equals("1000")){
                    return resCab;
                }
                List<AsientoCabTemDto> lst = (List<AsientoCabTemDto>)resCab.getElementoGenerico();


                if(objRepo.getCodReporte()==8){
                    System.out.println("existe detalle");
                }
                List<AsientoCabTemDto>  lstAsientoCab = lst.stream().filter(r -> r.getNroasientoSap().equals("null")).collect(Collectors.toList());
                ResponseDto resDetCab = this.getAsientosDetByAsientosCabId(this.obtenerListaAsientoCabId(lstAsientoCab));
                List<AsientoDetTemDto> lstAsientoDet = (List<AsientoDetTemDto>)resDetCab.getElementoGenerico();

                for (AsientoCabTemDto objCab :lstAsientoCab) {

                    List<AsientoDetTemDto> lstDet = lstAsientoDet.stream().sorted(Comparator.comparingLong(AsientoDetTemDto::getCodigoConcepto)).filter(r -> r.getAsientoCabTemId().equals(objCab.getAsientoCabTemId())).collect(Collectors.toList());
                    if(lstDet.isEmpty()){
                        resp.setCodigo("1001");
                        resp.setMensaje("No existe datos para el ramo: '"+objCab.getCodRamo()+" - "+objCab.getRamo()+"'");
                        return resp;
                    }
                    Map<String, Object> objMapCab = new HashMap<>();
                    objMapCab.put("TaxDate",objCab.getTaxDate());
                    objMapCab.put("DueDate", objCab.getDueDate());
                    objMapCab.put("ReferenceDate",objCab.getReferenceDate());
                    objMapCab.put("Memo",objRepo.getNombreReporte());
                    List<Map<String, Object>> lstMapDetalle = new ArrayList<>();
                    for (AsientoDetTemDto objDet:lstDet) {
                        Map<String, Object> objMapDet = new HashMap<>();
                        objMapDet.put("CodigoRamo",objCab.getCodRamo());
                        objMapDet.put("CodigoReporte",objCab.getCodReporte());
                        objMapDet.put("AccountCode",objDet.getCodigoConcepto());
                        objMapDet.put("ShortName",objDet.getDescripcionConcepto());
                        objMapDet.put("LineMemo",objDet.getDescripcionCuenta());
                        objMapDet.put("Amount",objDet.getDebeHaber().equals("D")?
                                Double.parseDouble(objDet.getAmountDebe()):
                                Double.parseDouble(objDet.getAmountHaber()));
                        objMapDet.put("CostingCode","");
                        objMapDet.put("CostingCode2","");
                        objMapDet.put("CostingCode3","");
                        lstMapDetalle.add(objMapDet);


                    }
                    objMapCab.put("JournalEntryLine",lstMapDetalle);
                    objMapCab.put("References","Enviado desde: App Web - Ganaseguros");
                    cantidadAsientoEnviado++;
                    this.enviarErpAsientoContable(res,objMapCab, objCab.getAsientoCabTemId(),usuarioId);
                }
            }
            resp.setCodigo("1000");
            resp.setMensaje(cantidadAsientoEnviado==0?"No existen asientos para enviar":"se  han enviado "+cantidadAsientoEnviado+" asientos");
            return resp;
        }catch (Exception ex){
            resp.setCodigo("1001");
            resp.setMensaje(ex.toString());
            return resp;
        }
    }*/
    private List<Long> obtenerListaAsientoCabId(List<AsientoCabTemDto> lstDto) {
        List<Long> lstRes = new ArrayList<>();
        try {
            for (AsientoCabTemDto obj : lstDto) {
                lstRes.add(obj.getAsientoCabTemId());
            }
        } catch (Exception ex) {

        }
        return lstRes;
    }

    public ResponseDto obtenerAsientosEnviadosTodos() {
        ResponseDto res = new ResponseDto();
        try {
            List<AsientoEnviadoDto> lstAsientoEnviadoDto = iAsientoEnviadoDao.obtenerAsientoEnviadoTodos();
            res.setCodigo("1000");
            res.setMensaje("Todo OK");
            res.setElementoGenerico(lstAsientoEnviadoDto);
            return res;
        } catch (Exception ex) {
            res.setCodigo("1001");
            res.setMensaje(ex.toString());
            return res;
        }
    }

    public ResponseDto modificarAsientoDetalle(AsientoDetTemDto asientoDetTemDto, Long asientoDetTemId) {
        ResponseDto res = new ResponseDto();
        try {
            AsientoDetTemEntity update = iAsientoDetDao.findById(asientoDetTemId).get();
            update.setAmount(asientoDetTemDto.getDebeHaber().equals("D") ? new Double(asientoDetTemDto.getAmountDebe()) : new Double(asientoDetTemDto.getAmountHaber()));
            update.setCodigoConcepto(asientoDetTemDto.getCodigoConcepto() + "");
            iAsientoDetDao.save(update);
            res.setCodigo("1000");
            res.setMensaje("Todo OK");
            //res.setElementoGenerico(lstAsientoEnviadoDto);
            return res;
        } catch (Exception ex) {
            res.setCodigo("1001");
            res.setMensaje(ex.toString());
            return res;
        }
    }

    public ResponseDto obtenerCantidadAsientos(Long pUsuarioId, Long pTipoReporteId) {
        ResponseDto res = new ResponseDto();
        try {
            List<Object[]> lstCantidad = iAsientoCabDao.obtenerCantidadAsientos(pUsuarioId, pTipoReporteId);
            CantidadAsientoDto objCantidadAsientoDto = new CantidadAsientoDto();
            objCantidadAsientoDto.setCantidadEviados(new Integer(lstCantidad.get(0)[0] + ""));
            objCantidadAsientoDto.setCantidadNoEviados(new Integer(lstCantidad.get(0)[1] + ""));
            objCantidadAsientoDto.setCantidadTodos(new Integer(lstCantidad.get(0)[2] + ""));
            res.setCodigo("1000");
            res.setMensaje("Todo OK");
            res.setElementoGenerico(objCantidadAsientoDto);
            return res;
        } catch (Exception ex) {
            res.setCodigo("1001");
            res.setMensaje(ex.toString());
            return res;
        }
    }

}
