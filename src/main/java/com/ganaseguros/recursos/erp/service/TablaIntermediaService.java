package com.ganaseguros.recursos.erp.service;


import com.ganaseguros.recursos.erp.dao.ITablaIntermediaDao;
import com.ganaseguros.recursos.erp.dao.IViewReporteDao;
import com.ganaseguros.recursos.erp.dto.TablaIntermediaDto;
import com.ganaseguros.recursos.erp.dto.ViewReporteDto;
import com.ganaseguros.recursos.erp.entity.TablaIntermediaEntity;
import com.ganaseguros.recursos.utils.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TablaIntermediaService {
    @Autowired
    IViewReporteDao iViewReporteDao;
    @Autowired
    ITablaIntermediaDao iTablaIntermediaDao;

    @Value("${dir.archivo.contables}")
    private String  dirArchivoContables;


    @Transactional
    public ResponseDto generateIntermediateTable (){
        ResponseDto res = new ResponseDto();
        try{

            List<TablaIntermediaDto> lstTablaIntermedia = this.obtieneTablaIntermediaDto();
            iTablaIntermediaDao.deleteAll();
            for ( TablaIntermediaDto obj  :lstTablaIntermedia) {
                TablaIntermediaEntity insert = new TablaIntermediaEntity();
                insert.setCodRamo(obj.getCodRamo());
                insert.setRamo(obj.getRamo());
                insert.setCodReporte(obj.getCodReporte());
                insert.setCampoReporte(obj.getCampoReporte());
                insert.setNombreReporte(obj.getNombreReporte());
                insert.setTipoMoneda(obj.getTipoMoneda());
                insert.setCodigoConcepto(obj.getCodigoConcepto());
                insert.setDescripcionConcepto(obj.getDescripcionConcepto());
                insert.setCodigoCuentaSapPuc(obj.getCodigoCuentaSapPuc());
                insert.setDescripcionCuenta(obj.getDescripcionCuenta());
                insert.setDebeHaber(obj.getDebeHaber());
                insert.setUsuarioId(1000L);
                insert.setEstadoId(1000L);
                iTablaIntermediaDao.save(insert);
            }
            res.setCodigo("1000");
            res.setMensaje("con exito");

        }catch (Exception ex){
            res.setCodigo("1001");
            res.setMensaje(ex.toString());
        }
        return res;
    }
    public ResponseDto getIntermediateTable(){
        ResponseDto res = new ResponseDto();
        List<TablaIntermediaDto> lst  = new ArrayList<>();
        List<TablaIntermediaEntity> lstTablaIntermedia =  iTablaIntermediaDao.findAll();
        TablaIntermediaDto objDto ;
         for ( TablaIntermediaEntity obj:lstTablaIntermedia) {
             objDto = new TablaIntermediaDto();
             objDto.setTablaIntermediaId(obj.getTablaIntermediaId());
             objDto.setCodRamo(obj.getCodRamo());
             objDto.setRamo(obj.getRamo());
             objDto.setCodReporte(obj.getCodReporte());
             objDto.setCampoReporte(obj.getCampoReporte());
             objDto.setNombreReporte(obj.getNombreReporte());
             objDto.setTipoMoneda(obj.getTipoMoneda());
             objDto.setCodigoConcepto(obj.getCodigoConcepto());
             objDto.setDescripcionConcepto(obj.getDescripcionConcepto());
             objDto.setCodigoCuentaSapPuc(obj.getCodigoCuentaSapPuc());
             objDto.setDescripcionCuenta(obj.getDescripcionCuenta());
             objDto.setDebeHaber(obj.getDebeHaber());
             objDto.setUsuarioId(obj.getUsuarioId());
             lst.add(objDto);
         }
        try{
            res.setCodigo("1000");
            res.setMensaje("sdsdsds");
            res.setElementoGenerico(lst);
            return res;
        }catch (Exception ex){
            res.setCodigo("1001");
            res.setMensaje(ex.toString());
            return res;
        }
     }
    public List<TablaIntermediaDto> obtieneTablaIntermediaDto (){
        List<TablaIntermediaDto> lstDatosIntermedio = new ArrayList();
        try
        {
            //String path = dirArchivoContables+"/Observaciones Tabla Intermedia V3.3.xlsx";
            String path = dirArchivoContables+"/Tabla intermedia V 4.2 10.07.23.xlsx";

            FileInputStream file = new FileInputStream(new File(path));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();

            rowIterator.next();
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                row.cellIterator();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                int columna = 0;
                TablaIntermediaDto   objDatosIntermedioDto = new TablaIntermediaDto();
                while (cellIterator.hasNext())
                {
                    columna++;
                    Cell cell = cellIterator.next();

                    try{
                        switch(columna) {
                            case 1:
                                cell.setCellType(CellType.STRING);
                                objDatosIntermedioDto.setCodRamo(Long.parseLong(cell.getStringCellValue()));
                                break;
                            case 2:
                                cell.setCellType(CellType.STRING);
                                objDatosIntermedioDto.setRamo(cell.getStringCellValue()+"");
                                break;
                            case 3:
                                cell.setCellType(CellType.STRING);
                                objDatosIntermedioDto.setCodReporte(Long.parseLong(cell.getStringCellValue()));
                                break;
                            case 4:
                                cell.setCellType(CellType.STRING);
                                objDatosIntermedioDto.setCampoReporte(cell.getStringCellValue()+"");
                                break;
                            case 5:
                                cell.setCellType(CellType.STRING);
                                objDatosIntermedioDto.setNombreReporte(cell.getStringCellValue()+"");
                                break;
                            case 6:
                                cell.setCellType(CellType.STRING);
                                objDatosIntermedioDto.setTipoMoneda(cell.getStringCellValue()+"");
                                break;
                            case 7:
                                cell.setCellType(CellType.STRING);
                                objDatosIntermedioDto.setCodigoConcepto(cell.getStringCellValue()+"");
                                break;
                            case 8:
                                cell.setCellType(CellType.STRING);
                                objDatosIntermedioDto.setDescripcionConcepto(cell.getStringCellValue()+"");
                                break;
                            case 9:
                                cell.setCellType(CellType.STRING);
                                objDatosIntermedioDto.setCodigoCuentaSapPuc(cell.getStringCellValue()+"");
                                break;
                            case 10:
                                cell.setCellType(CellType.STRING);
                                objDatosIntermedioDto.setDescripcionCuenta(cell.getStringCellValue()+"");
                                break;
                            case 11:
                                cell.setCellType(CellType.STRING);
                                objDatosIntermedioDto.setDebeHaber(cell.getStringCellValue()+"");
                                break;

                        }
                    }catch (Exception ex){
                        System.out.println(ex);
                    }

                }
                lstDatosIntermedio.add(objDatosIntermedioDto);
            }

            file.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return lstDatosIntermedio;
    }

    public ResponseDto actualizarTablaIntermedia(Long pTablaIntermediaId, TablaIntermediaDto pTablaIntermediaDto){
        ResponseDto res = new ResponseDto();
        try{
            TablaIntermediaEntity update = iTablaIntermediaDao.findById(pTablaIntermediaId).get();
            update.setCodRamo(pTablaIntermediaDto.getCodRamo());
            update.setRamo(pTablaIntermediaDto.getRamo());
            update.setCodReporte(pTablaIntermediaDto.getCodReporte());
            update.setCampoReporte(pTablaIntermediaDto.getCampoReporte());
            update.setNombreReporte(pTablaIntermediaDto.getNombreReporte());
            update.setTipoMoneda(pTablaIntermediaDto.getTipoMoneda());
            update.setCodigoConcepto(pTablaIntermediaDto.getCodigoConcepto());
            update.setDescripcionConcepto(pTablaIntermediaDto.getDescripcionConcepto());
            update.setCodigoCuentaSapPuc(pTablaIntermediaDto.getCodigoCuentaSapPuc());
            update.setDescripcionCuenta(pTablaIntermediaDto.getDescripcionCuenta());
            update.setDebeHaber(pTablaIntermediaDto.getDebeHaber());
            update.setUsuarioId(pTablaIntermediaDto.getUsuarioId());
            iTablaIntermediaDao.save(update);
            res.setCodigo("1000");
            res.setMensaje("Actualización exitosa");
            return res;


        }catch (Exception ex){
            res.setCodigo("1001");
            res.setMensaje("Error el actualziar");
            return res;
        }
    }
    public ResponseDto obtenerReporteTodos(){
        ResponseDto res = new ResponseDto();
        try{
            List<ViewReporteDto> lst =  iViewReporteDao.obtenerReportesTodos();
            res.setCodigo("1000");
            res.setMensaje("Reportes encontrados con exito");
            res.setElementoGenerico(lst);
            return res;

        }catch (Exception ex){
            res.setCodigo("1001");
            res.setMensaje(ex.toString());
            return res;
        }
    }
}
