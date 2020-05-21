package com.ithl.excelutil.process;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.ithl.excelutil.readdemo.entity.DataModel;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * 每解析一行会回调invoke()方法。
 *  整个excel解析结束会执行doAfterAllAnalysed()方法
 */

public class UserInfoDataListener extends AnalysisEventListener<DataModel> {
    //有个很重要的点   不能被spring管理,要每次读取excel都要new。
     //这边就会有一个问题：如果UserInfoDataListener中需要用到Spring中的主键怎么办？
    //每次读取100条数据就进行保存操作
    //private static final int count=100;
    //由于每次读都是新 new UserInfoDataListener的，所以这个list不会存在线程安全问题。
    List<DataModel> req=new ArrayList<>();
    List<DataModel> res=new ArrayList<>();
    private static Boolean isResponse=false;
    private  Map<String, String> reqMap ;
    private  Map<String, String> resMap;
    //这个组件是Spring中的组件，这边推荐两种方法注入这个组件
    //第一种就是提供一个UserInfoDataListener的构造方法，这个方法提供一个参数是UserInfoDataListener类型
    //另外一种方法就是将 UserInfoDataListener 这个类定义成 UserService 实现类的内部类（推荐这种方式）
    //private UserService userService;



    @Override
    public void invoke(DataModel dataModel, AnalysisContext analysisContext) {
     System.out.println("解析到一条数据------------------》"+dataModel.toString());
     System.out.println("行号"+analysisContext.getCurrentRowNum());
        Integer rowNum = analysisContext.getCurrentRowNum();

        if(rowNum>=7&&!dataModel.getRealmName().equals("输出")){
            if(!isResponse){
                req.add(dataModel);
            }else{
                res.add(dataModel);
            }

        }

            if(dataModel!=null&&dataModel.getRealmName()!=null&&dataModel.getRealmName().equals("输出")) {

                isResponse = true;
            }


//        if(rowNum<=5){
//
//        }else {
//            if(dataModel.getRealmName()!=null&&(dataModel.getRealmName()).equals("输出")){
//                isResponse=true;
//                return;
//            }
//            if (!isResponse) {
//                list.add(dataModel);
//            } else {
//                res.add(dataModel);
//            }
//        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //这里也要保存数据，确保最后遗留的数据也存储到数据库中

        System.out.println("请求----");
        for(DataModel t:req){
            System.out.println(t.toString());
        }
        System.out.println("响应--------------------------------");
        for(DataModel t:res){
            System.out.println(t.toString());
        }
         reqMap = req.stream().collect(Collectors.toMap(DataModel::getRealmName, DataModel::getMetadataName));
        resMap = res.stream().collect(Collectors.toMap(DataModel::getRealmName, DataModel::getMetadataName));
        XMLPacketNameUtil util=new XMLPacketNameUtil();
        util.instance("300310001","01","QDZH","CBP");

            //saveData((HashMap) req,(HashMap) resMap,util);


    }

    public  Map<String, String> getReqMap() {
        return reqMap;
    }

    public void setReqMap(Map<String, String> reqMap) {
        this.reqMap = reqMap;
    }

    public Map<String, String> getResMap() {
        return resMap;
    }

    public void setResMap(Map<String, String> resMap) {
        this.resMap = resMap;
    }

    public void saveData(HashMap req, HashMap res, XMLPacketNameUtil util)throws IOException {
        String rootXpath="C:\\Users\\helu\\Desktop\\TestToo\\ExcelUtil\\src\\main\\resources";



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
}
