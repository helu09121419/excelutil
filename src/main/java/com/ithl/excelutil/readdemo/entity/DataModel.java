package com.ithl.excelutil.readdemo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 *
 * 接口数据模型
 */
@Data
public class DataModel extends BaseRowModel {
    @ExcelProperty(index = 0)
    private String realmName;//域名
    @ExcelProperty(index = 1)
    private String cName;//中文名
    @ExcelProperty(index = 7)
    private String metadataName;


    public String getRealmName() {
        return realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getMetadataName() {
        return metadataName;
    }

    public void setMetadataName(String metadataName) {
        this.metadataName = metadataName;
    }
}
