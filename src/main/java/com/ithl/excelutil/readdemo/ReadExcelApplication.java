package com.ithl.excelutil.readdemo;

import com.alibaba.excel.metadata.BaseRowModel;
import com.ithl.excelutil.process.IndexInfoDataListener;
import com.ithl.excelutil.process.UserInfoDataListener;
import com.ithl.excelutil.process.XMLPacketFactory;
import com.ithl.excelutil.process.XMLPacketNameUtil;
import com.ithl.excelutil.readdemo.entity.DataModel;
import com.ithl.excelutil.readdemo.entity.IndexModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadExcelApplication {
    static List<IndexModel> indexModels;
    static Map map;
    public static void main(String[] args) {
        String rootPath="D:\\SVN\\DOC\\03_规范管理\\服务治理文档\\TJBSD\\V0.1.6\\字段映射";
        String excelName="/天津银行ESB项目_特色业务平台_字段映射_V0.1.6.XLSX";
        int indexSheetN=3;

        //获取对应行index信息
        try {
             indexModels = XMLPacketFactory.readExcelIndexInfo(rootPath + excelName, indexSheetN, IndexModel.class, new IndexInfoDataListener());

        } catch (IOException e) {
            e.printStackTrace();
        }
        XMLPacketNameUtil util = new XMLPacketNameUtil();
        List<XMLPacketNameUtil> u = util.instanceOfList(indexModels);
        for(int i=0;i<u.size();i++){

        }

        //根据要读的index行获取对应的接口信息
            try {
                map = XMLPacketFactory.readExcelOfOneSheet(rootPath + excelName, 7, DataModel.class, new UserInfoDataListener());
            } catch (IOException e) {
                e.printStackTrace();
            }


        //模板路径
        String forMarkNameRoot="";
        try {
            XMLPacketFactory.generateXMl(forMarkNameRoot,(HashMap) map.get("REQ_MESSAGE"),(HashMap)map.get("RES_MESSAGE"),u.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
