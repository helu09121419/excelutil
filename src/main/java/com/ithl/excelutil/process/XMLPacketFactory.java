package com.ithl.excelutil.process;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.ithl.excelutil.readdemo.entity.DataModel;
import com.ithl.excelutil.readdemo.entity.IndexModel;
import org.apache.poi.ss.formula.functions.T;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLPacketFactory {
    private static Map<String,Object> list=new HashMap<>();
    public static void instance(){

    }

    public static Map readExcelOfOneSheet(String filename, int sheetNo, Class<? extends BaseRowModel> clazz,UserInfoDataListener  an)throws IOException{
        String rootXpath="D:\\SVN\\DOC\\03_规范管理\\服务治理文档\\TJBSD\\V0.1.6\\字段映射";
        String fileName="/天津银行ESB项目_特色业务平台_字段映射_V0.1.6.XLSX";
        //int sheetNo, int headLineMun, Class<? extends BaseRowModel> clazz, String sheetName, List<List<String>> head)

        FileInputStream in=new FileInputStream(filename);EasyExcelFactory.readBySax(in,new Sheet(6,0,clazz),an);
        Map<String, String> reqMap = an.getReqMap();
        Map<String, String> resMap = an.getResMap();
        list.put("REQ_MESSAGE",reqMap);
        list.put("RES_MESSAGE",resMap);
        return list;

    }

    public static List<IndexModel> readExcelIndexInfo(String filename, int sheetNo, Class<? extends BaseRowModel> clazz,IndexInfoDataListener index)throws IOException{
        String rootXpath="D:\\SVN\\DOC\\03_规范管理\\服务治理文档\\TJBSD\\V0.1.6\\字段映射";
        String fileName="/天津银行ESB项目_特色业务平台_字段映射_V0.1.6.XLSX";
        FileInputStream in=new FileInputStream(filename);
        EasyExcelFactory.readBySax(in,new Sheet(sheetNo,0,clazz),index);


        List<IndexModel> li = index.getLi();
        return li;
    }


    public static void generateXMl(String rootXpath,HashMap req, HashMap res, XMLPacketNameUtil util)throws IOException {
        rootXpath="C:\\Users\\helu\\Desktop\\TestToo\\ExcelUtil\\src\\main\\resources";


        StandarXMlProcess.getInUnpacketOrPacketXml(req,rootXpath+"/标准报文in拆包模板.xml",
                rootXpath+"/in/"+util.getInUnapcketName(),false);
        StandarXMlProcess.getInUnpacketOrPacketXml(res,rootXpath+"/标准报文in端组包模板.xml",
                rootXpath+"/in/"+util.getInPacketName(),true);
        StandarXMlProcess.getServiceXml(req,res,rootXpath+"/标准服务定义.xml",
                rootXpath+"/in/"+util.getServiceName(),true);
        StandarXMlProcess.getServiceXml(req,res,rootXpath+"/标准服务定义.xml",
                rootXpath+"/out/"+util.getServiceName(),true);
        //out端组包
        StandarXMlProcess.getOutUnpacketOrPacketXml(req,null,rootXpath+"/out端组包模板.xml",
                rootXpath+"/out/"+util.getOutPacketName(),true,false);
        //out端拆包
        StandarXMlProcess.getOutUnpacketOrPacketXml(req,res,rootXpath+"/out端拆包模板.xml",
                rootXpath+"/out/"+util.getOutUnapcketName(),false,true);
    }

    public static void main(String[] args)throws IOException {
        String rootXpath="D:\\SVN\\DOC\\03_规范管理\\服务治理文档\\TJBSD\\V0.1.6\\字段映射";
        String fileName="/天津银行ESB项目_特色业务平台_字段映射_V0.1.6.XLSX";
        FileInputStream in=new FileInputStream(rootXpath+fileName);
        //InputStream in, ExcelTypeEnum excelTypeEnum, Object customContent, AnalysisEventListener eventListener
        ExcelReader reader1 = new ExcelReader(in, null, null, new AnalysisEventListener() {
            @Override
            public void invoke(Object o, AnalysisContext analysisContext) {

            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

            }
        });


        List<Sheet> sheets = reader1.getSheets();


//        System.out.println(sheets.size());
//        for(Sheet s:sheets){
//            String sheetName = s.getSheetName();
//            if(sheetName.equals("Sheet3")){
//                int sheetNo = s.getSheetNo();
//                System.out.println("-------------------------------->"+sheetNo);
//            }
//
//        }
    }
}
