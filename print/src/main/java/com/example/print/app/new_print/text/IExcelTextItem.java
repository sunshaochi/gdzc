package com.example.print.app.new_print.text;

import java.util.List;

public interface IExcelTextItem {

    void setExcelData(List<String> excelData);

    List<String> getExcelData();

    boolean hasExcelData();

}
