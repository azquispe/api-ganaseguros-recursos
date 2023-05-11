package com.ganaseguros.recursos.core.service;

import com.ganaseguros.recursos.core.dto.ReporteDto;
import com.ganaseguros.recursos.core.rest_template.ReportesRestTemplate;
import com.ganaseguros.recursos.erp.dao.*;
import com.ganaseguros.recursos.erp.dto.ColumnasReporteDto;
import com.ganaseguros.recursos.erp.dto.RequestAsientoDto;
import com.ganaseguros.recursos.erp.entity.ColumnasReporteEntity;
import com.ganaseguros.recursos.erp.entity.ViewRamoEntity;
import com.ganaseguros.recursos.transversal.dao.IDominioDao;
import com.ganaseguros.recursos.utils.dto.ResponseDto;
import com.ganaseguros.recursos.utils.funciones.FuncionesFechas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportesService {

    @Autowired
    ReportesRestTemplate reportesRestTemplate;

    @Autowired
    IColumnasReporteDao iColumnasReporteDao;

    @Autowired
    IAsientoCabDao iAsientoCabDao;

    @Autowired
    IAsientoDetDao iAsientoDetDao;

    @Autowired
    IDominioDao iDominioDao;

    @Autowired
    IViewRamoDao iViewRamoDao;

    @Autowired
    IViewReporteDao iViewReporteDao;


    public ResponseDto obtenerReporteCore(Long pUsuarioId,Long pTipoReporteId,String pFechaInicio,String pFechaFin) {
        ResponseDto res = new ResponseDto();
        try {

            // obtiene reportes y columnas  para el tipo de reportes
            List<ColumnasReporteDto> lstColumnasDtoPorTipoReporte = iColumnasReporteDao.obtenerColumnasReporte().stream().filter(r -> r.getTipoReporteId().equals(pTipoReporteId)).collect(Collectors.toList());

            // obtiene solo codigo reporte
            List<ColumnasReporteDto> lstColumnasReporteDtoAgrupado =  obtenerCodigoReporteDeColumnasReporte(lstColumnasDtoPorTipoReporte);

            // obtiene ramos
            List<ViewRamoEntity> lstRamos = iViewRamoDao.findAll();

            if (lstColumnasReporteDtoAgrupado.isEmpty()) {
                res.setCodigo("1001");
                res.setMensaje("no existe columnas  para el reporte de tipo: " + iDominioDao.getDominioByDominioId(pTipoReporteId).get().getDescripcion());
                return res;
            }

            // obteien token del core
            /*res =  reportesRestTemplate.obtenerToken();
            if(!res.getCodigo().equals("1000")){
                return res;
            }
            String token = res.getElementoGenerico().toString();*/
            String token ="";

            // limpiamos tablas temporales
            iAsientoDetDao.deleteAll();
            iAsientoCabDao.deleteAll();

            for (ColumnasReporteDto objRepo: lstColumnasReporteDtoAgrupado) {
                for (ViewRamoEntity objRamo:lstRamos) {
                    Map<String, Object> request = this.construirRequest(objRepo.getCodReporteCore(), objRamo.getRramoId().toString(), objRepo.getTipoMoneda(),pFechaInicio,pFechaFin );
                    res = reportesRestTemplate.obtieneReporteCore(token,request);
                    if(!res.getCodigo().equals("1000")){
                        continue;
                    }
                    List<ReporteDto> lstReporte = (List<ReporteDto>)res.getElementoGenerico();

                }
            }

            return res;
        } catch (Exception ex) {

            res.setCodigo("1001");
            res.setMensaje(ex.toString());
            return res;
        }
    }
    private  Map<String, Object> construirRequest(String  pCodReporteCore, String pRamo, String pMoneda, String  pFechaInicio, String pfechaFin){
        Map<String, Object> reportType = new HashMap<>();
        reportType.put("type","Insurance_Policy_with_Rating_Details__c");
        reportType.put("label","Insurance Policy with Rating Details");

        List<String> detailColumns = new ArrayList<>();
        detailColumns.add("Account.Name");
        detailColumns.add("InsurancePolicy.Producer.Sucursal__c.Name");
        detailColumns.add("InsurancePolicy.Product.vlocity_ins__LineOfBusiness__c");
        detailColumns.add("InsurancePolicy.CurrencyIsoCode");
        detailColumns.add("InsurancePolicy.Producer.Account.Name__lookup");
        detailColumns.add("clb_ins_PolicyRatingDetail__c.clb_ins_PrimaTotalPoliza__c");
        detailColumns.add("clb_ins_PolicyRatingDetail__c.clb_ins_PrimaNetaPoliza__c");
        detailColumns.add("clb_ins_PolicyRatingDetail__c.clb_ins_ImpuestoIVA__c");
        detailColumns.add("clb_ins_PolicyRatingDetail__c.clb_ins_ImpuestoIT__c");
        detailColumns.add("clb_ins_PolicyRatingDetail__c.clb_ins_Val_FondoProteccionAsegurado__c");
        detailColumns.add("clb_ins_PolicyRatingDetail__c.clb_ins_CostoAdquisicion__c");
        detailColumns.add("clb_ins_PolicyRatingDetail__c.clb_ins_AportesAPS__c");
        detailColumns.add("clb_ins_PolicyRatingDetail__c.clb_ins_PorComisionCobranza__c");
        detailColumns.add("clb_ins_PolicyRatingDetail__c.clb_ins_AporteABA__c");
        detailColumns.add("clb_ins_PolicyRatingDetail__c.clb_ins_Val_RecargoeInflacion__c");
        detailColumns.add("clb_ins_PolicyRatingDetail__c.clb_ins_ComisionCobranza__c");
        detailColumns.add("clb_ins_PolicyRatingDetail__c.clb_ins_ComisionBancaria__c");
        detailColumns.add("clb_ins_PolicyRatingDetail__c.clb_ins_ImpuestoITF__c");
        detailColumns.add("clb_ins_PolicyRatingDetail__c.clb_ins_ImpuestoIUEBE__c");
        detailColumns.add("InsurancePolicy.Product.Cod_Ramo__c");
        detailColumns.add("InsurancePolicy.EffectiveDate");
        detailColumns.add( "clb_ins_PolicyRatingDetail__c.clb_insVAFallecimientoCC__c");


        Map<String, Object> reportFiltersObj1 = new HashMap<>();
        reportFiltersObj1.put("value",pRamo);

        reportFiltersObj1.put("operator","equals");
        reportFiltersObj1.put("column","InsurancePolicy.Product.Cod_Ramo__c");

        Map<String, Object> reportFiltersObj2 = new HashMap<>();
        reportFiltersObj2.put("value",pMoneda);
        reportFiltersObj2.put("operator","equals");
        reportFiltersObj2.put("column","InsurancePolicy.CurrencyIsoCode");

        List<Map<String, Object>> reportFilters = new ArrayList<>();
        reportFilters.add(reportFiltersObj1);
        reportFilters.add(reportFiltersObj2);

        List<String> aggregates = new ArrayList<>();
        aggregates.add("s!clb_ins_PolicyRatingDetail__c.clb_ins_PrimaTotalPoliza__c");
        aggregates.add("s!clb_ins_PolicyRatingDetail__c.clb_ins_PrimaNetaPoliza__c");
        aggregates.add("s!clb_ins_PolicyRatingDetail__c.clb_ins_ImpuestoIVA__c");
        aggregates.add("s!clb_ins_PolicyRatingDetail__c.clb_ins_ImpuestoIT__c");
        aggregates.add("s!clb_ins_PolicyRatingDetail__c.clb_ins_Val_FondoProteccionAsegurado__c");
        aggregates.add("s!clb_ins_PolicyRatingDetail__c.clb_ins_CostoAdquisicion__c");
        aggregates.add("s!clb_ins_PolicyRatingDetail__c.clb_ins_AportesAPS__c");
        aggregates.add("s!clb_ins_PolicyRatingDetail__c.clb_ins_PorComisionCobranza__c");
        aggregates.add("s!clb_ins_PolicyRatingDetail__c.clb_ins_AporteABA__c");
        aggregates.add("s!clb_ins_PolicyRatingDetail__c.clb_ins_Val_RecargoeInflacion__c");
        aggregates.add("s!clb_ins_PolicyRatingDetail__c.clb_ins_ComisionCobranza__c");
        aggregates.add("s!clb_ins_PolicyRatingDetail__c.clb_ins_ComisionBancaria__c");
        aggregates.add("s!clb_ins_PolicyRatingDetail__c.clb_ins_ImpuestoITF__c");
        aggregates.add("s!clb_ins_PolicyRatingDetail__c.clb_ins_ImpuestoIUEBE__c");
        aggregates.add("FORMULA1");
        aggregates.add("FORMULA6");

        Map<String, Object> groupingsDownObj = new HashMap<>();
        groupingsDownObj.put("dateGranularity","None");
        groupingsDownObj.put("name","InsurancePolicy.clb_ins_dsg_PolicyNumber__c");
        groupingsDownObj.put("sortAggregate",null);
        groupingsDownObj.put("sortOrder","Asc");

        List<Map<String, Object>> groupingsDown = new ArrayList<>();
        groupingsDown.add(groupingsDownObj);

        Map<String, Object> standardDateFilter = new HashMap<>();
        standardDateFilter.put("column","InsurancePolicy.EffectiveDate");
        standardDateFilter.put("durationValue","CUSTOM");
        /*standardDateFilter.put("endDate","2023-02-28");
        standardDateFilter.put("startDate","2023-02-01");*/
        standardDateFilter.put("endDate", pFechaInicio);
        standardDateFilter.put("startDate", pfechaFin);

        Map<String, Object> FORMULA6 = new HashMap<>();
        FORMULA6.put("acrossGroup",null);
        FORMULA6.put("acrossGroupType","all");
        FORMULA6.put("crossBlock",false);
        FORMULA6.put("decimalPlaces",2);
        FORMULA6.put("description",null);
        FORMULA6.put("downGroup",null);
        FORMULA6.put("downGroupType","all");
        FORMULA6.put("formula","clb_ins_PolicyRatingDetail__c.clb_ins_AporteABA__c:SUM + clb_ins_PolicyRatingDetail__c.clb_ins_Val_RecargoeInflacion__c:SUM + clb_ins_PolicyRatingDetail__c.clb_ins_ImpuestoIUEBE__c:SUM + clb_ins_PolicyRatingDetail__c.clb_ins_ComisionBancaria__c:SUM + clb_ins_PolicyRatingDetail__c.clb_ins_ITransaccionesITF__c:SUM + clb_ins_PolicyRatingDetail__c.clb_ins_ComisionCobranza__c:SUM");
        FORMULA6.put("formulaType","number");
        FORMULA6.put("label","Recargo");
        FORMULA6.put("reportType","Insurance_Policy_with_Rating_Details__c");

        Map<String, Object> FORMULA1 = new HashMap<>();
        FORMULA1.put("acrossGroup",null);
        FORMULA1.put("acrossGroupType","all");
        FORMULA1.put("crossBlock",false);
        FORMULA1.put("decimalPlaces",2);
        FORMULA1.put("description",null);
        FORMULA1.put("downGroup",null);
        FORMULA1.put("downGroupType","all");
        FORMULA1.put("formula","clb_ins_PolicyRatingDetail__c.clb_insVAFallecimientoCC__c:SUM + clb_ins_PolicyRatingDetail__c.clb_insVAFallecimientoAc__c:SUM +clb_ins_PolicyRatingDetail__c.clb_insVARentaDesempleo__c:SUM + clb_ins_PolicyRatingDetail__c.clb_insVARentaInvalidez__c:SUM + clb_ins_PolicyRatingDetail__c.clb_insVAGastosSepelio__c:SUM + clb_ins_PolicyRatingDetail__c.clb_insVAEnfermedadez__c:SUM + clb_ins_PolicyRatingDetail__c.clb_insVAGastosMedicos__c:SUM + clb_ins_PolicyRatingDetail__c.clb_insVARentaHospitalizacion__c:SUM + clb_ins_PolicyRatingDetail__c.clb_insVARoboAsalto__c:SUM + clb_ins_PolicyRatingDetail__c.clb_insVAReemisionDoc__c:SUM + clb_ins_PolicyRatingDetail__c.clb_insVAAsistenciaMedica__c:SUM + clb_ins_PolicyRatingDetail__c.clb_insVATrasladoMedico__c:SUM");
        FORMULA1.put("formulaType","number");
        FORMULA1.put("label","Valor asegurado");
        FORMULA1.put("reportType","Insurance_Policy_with_Rating_Details__c");

        Map<String, Object> customSummaryFormula = new HashMap<>();
        customSummaryFormula.put("FORMULA6",FORMULA6);
        customSummaryFormula.put("FORMULA1",FORMULA1);


        Map<String, Object> reportMetadata = new HashMap<>();
        reportMetadata.put("name","FilterAcctsReport");
        //reportMetadata.put("id","00O7b000000MJTvEAO");
        reportMetadata.put("id",pCodReporteCore);
        reportMetadata.put("reportFormat","SUMMARY");
        reportMetadata.put("developerName","Produccion_Primas_Nuevas_N_restructurado_P6W");
        reportMetadata.put("reportType", reportType);
        reportMetadata.put("detailColumns", detailColumns);
        reportMetadata.put("reportBooleanFilter", "1 AND 2");
        reportMetadata.put("reportFilters", reportFilters);
        reportMetadata.put("currency", null);
        reportMetadata.put("aggregates", aggregates);
        reportMetadata.put("groupingsDown", groupingsDown);
        reportMetadata.put("groupingsAcross", new ArrayList<>());
        reportMetadata.put("standardDateFilter", standardDateFilter);
        reportMetadata.put("customSummaryFormula", customSummaryFormula);
        reportMetadata.put("showGrandTotal", true);
        reportMetadata.put("showSubtotals", false);

        Map<String, Object> obj = new HashMap<>();
        obj.put("reportMetadata",reportMetadata);


        return obj;


        // falta





    }

    private List<ColumnasReporteDto> obtenerCodigoReporteDeColumnasReporte(List<ColumnasReporteDto> lstColumnasReporte) throws Exception {
        List<ColumnasReporteDto> lst = new ArrayList<>();
        for (ColumnasReporteDto obj : lstColumnasReporte) {
            if(lst.stream().filter(x -> x.getCodReporte().equals(obj.getCodReporte())).collect(Collectors.toList()).isEmpty()){
                lst.add(obj);
            }
        }
         return lst;
    }
}
