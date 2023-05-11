package com.ganaseguros.recursos.core.rest_template;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ganaseguros.recursos.core.dto.ReporteDto;
import com.ganaseguros.recursos.utils.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportesRestTemplate {

    ObjectMapper oMapper = new ObjectMapper();

    @Value("${api.base.core}")
    private String apiBaseCore;

    @Value("${api.base.core.grant_type}")
    private String apiBaseCoreGrantType;

    @Value("${api.base.core.client_id}")
    private String apiBaseCoreClienteId;

    @Value("${api.base.core.client_secret}")
    private String apiBaseCoreClientSecret;

    @Value("${api.base.core.username}")
    private String apiBaseCoreUserName;

    @Value("${api.base.core.password}")
    private String apiBaseCorePassword;
    private final RestTemplate restTemplate;

    public ResponseDto obtenerToken() {
        ResponseDto res = new ResponseDto();

        try{
            ObjectMapper oMapper = new ObjectMapper();

            String url = apiBaseCore + "/oauth2/token?grant_type="+apiBaseCoreGrantType+ "&client_id="+apiBaseCoreClienteId+"&client_secret="+apiBaseCoreClientSecret+ "&username="+apiBaseCoreUserName+"&password="+apiBaseCorePassword;
            ResponseEntity<Map> resultMap = restTemplate.postForEntity(new URI(url),  new HashMap<>(), Map.class);
            if (resultMap != null && resultMap.getStatusCode().value() == 200) {
                String token = oMapper.convertValue(resultMap.getBody().get("access_token"), String.class);
                res.setCodigo("1000");
                res.setMensaje("Token generado");
                res.setElementoGenerico(token);
                return  res;
            } else {
                res.setCodigo("1001");
                res.setMensaje("No se pudo generar Token");
                return  res;
            }
        }catch (Exception ex){
            res.setCodigo("1001");
            res.setMensaje("No se pudo generar generar token para acceder a ERP");
            return  res;
        }
    }


    public ResponseDto obtieneReporteCore(String token, Map<String, Object> request) {
        ResponseDto res = new ResponseDto();
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Map> body = new HttpEntity<>(request, headers);
            ResponseEntity<Map> resultMap = restTemplate.postForEntity(apiBaseCore + "/data/v57.0/analytics/reports/00O7b000000MJTvEAO", body, Map.class);

            if (resultMap != null && resultMap.getStatusCode().value() == 200) {
                ObjectMapper oMapper = new ObjectMapper();

                Map factMap = oMapper.convertValue(resultMap.getBody().get("factMap"), Map.class);
                Map tt = oMapper.convertValue(factMap.get("T!T"), Map.class);
                List<Map> aggregatesValor = oMapper.convertValue(tt.get("aggregates"), ArrayList.class);

                Map reportMetadata = oMapper.convertValue(resultMap.getBody().get("reportMetadata"), Map.class);
                List<String> aggregatesCampo = oMapper.convertValue(reportMetadata.get("aggregates"), ArrayList.class);

                List<ReporteDto> lstReporte = new ArrayList<>();
                int contador = 0;
                for (String campo :aggregatesCampo) {
                    ReporteDto obj = new ReporteDto();
                    obj.setCampo(campo);
                    obj.setValor(aggregatesValor.get(contador).get("value")+"");
                    lstReporte.add(obj);
                    contador++;
                }

                res.setCodigo("1000");
                res.setMensaje("cosas");
                res.setElementoGenerico(lstReporte);
                return res;

            } else {
                res.setCodigo("1001");
                res.setMensaje("Error");
                return res;
            }
        } catch (Exception ex) {
            res.setCodigo("1001");
            res.setMensaje(ex.toString());
            return res;
        }
    }

}
