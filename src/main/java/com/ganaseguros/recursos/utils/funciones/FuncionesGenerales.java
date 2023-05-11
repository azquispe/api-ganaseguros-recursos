package com.ganaseguros.recursos.utils.funciones;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FuncionesGenerales {
  public static List<Long> eliminarDuplicadoList(List<Long> lst){
      Set<Long> list = new HashSet<>(lst);
      lst.clear();
      lst.addAll(list);
      return lst;
  }
    public static String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }
}
