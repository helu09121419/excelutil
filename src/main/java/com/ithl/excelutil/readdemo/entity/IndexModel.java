package com.ithl.excelutil.readdemo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class IndexModel extends BaseRowModel {
    //服务码
    @ExcelProperty(index = 3)
    private String cl4;
    //场景码
    @ExcelProperty(index = 5)
    private String cl6;
    //渠道名
    @ExcelProperty(index = 7)
    private String cl8;
    //系统名
    @ExcelProperty(index = 8)
    private String cl9;

    public String getCl4() {
        return cl4;
    }

    public void setCl4(String cl4) {
        this.cl4 = cl4;
    }

    public String getCl6() {
        return cl6;
    }

    public void setCl6(String cl6) {
        this.cl6 = cl6;
    }

    public String getCl8() {
        return cl8;
    }

    public void setCl8(String cl8) {
        this.cl8 = cl8;
    }

    public String getCl9() {
        return cl9;
    }

    public void setCl9(String cl9) {
        this.cl9 = cl9;
    }
}
