package com.ganaseguros.recursos.erp.rest_template;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ganaseguros.recursos.utils.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class EnvioAsientoRestTemplate {


    @Value("${api.base.erp}")
    private String apiBaseErp;
    private final RestTemplate restTemplate;

    public ResponseDto obtenerToken() {
        ResponseDto res = new ResponseDto();
        try{
            ObjectMapper oMapper = new ObjectMapper();
            Map<String, Object> data = new HashMap<>();
            data.put("username", "GanasegurosQA");
            data.put("password", "GanasegurosQA123.,");
            ResponseEntity<Map> resultMap = restTemplate.postForEntity(apiBaseErp + "/Auth/ObtenerToken", data, Map.class);
            if (resultMap != null && resultMap.getStatusCode().value() == 200) {
                Map<String, Object> dataToken = oMapper.convertValue(resultMap.getBody().get("data"), Map.class);
                String token = dataToken.get("accessToken").toString();
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

    public ResponseDto enviarErpAsientoContable(String token, Map<String, Object> request) {
        ResponseDto res = new ResponseDto();
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Map> body = new HttpEntity<>(request, headers);
            ResponseEntity<Map> resultMap = restTemplate.postForEntity(apiBaseErp + "/JournalEntry/GuardarAsiento", body, Map.class);
            /*res.setCodigo(ConstDiccionarioMensaje.COD1000);
            res.setMensaje(ConstDiccionarioMensaje.COD1000_MENSAJE);*/

            if (resultMap != null && resultMap.getStatusCode().value() == 200) {
                ObjectMapper oMapper = new ObjectMapper();
                String code = oMapper.convertValue(resultMap.getBody().get("code"), String.class);
                String message = oMapper.convertValue(resultMap.getBody().get("message"), String.class);
                //String data = oMapper.convertValue(resultMap.getBody().get("data"), String.class);
                res.setCodigo(code);
                res.setMensaje(message);
                res.setElementoGenerico(resultMap);
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
