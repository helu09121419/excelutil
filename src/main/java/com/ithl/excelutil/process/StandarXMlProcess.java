package com.ithl.excelutil.process;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandarXMlProcess {
    private static HashMap<String,String> reqMap=new HashMap<>();
    private static HashMap<String,String> map=new HashMap<>();
    private static HashMap<String,String> resMap=new HashMap<>();
    private static   Document document;

    public static HashMap<String, String> getMap() {
        return map;
    }

    public static void setMap(HashMap<String, String> map) {
        StandarXMlProcess.map = map;
    }

    public static HashMap<String, String> getReqMap() {
        return reqMap;
    }

    public static void setReqMap(HashMap<String, String> reqMap) {
        StandarXMlProcess.reqMap = reqMap;
    }

    public static HashMap<String, String> getResMap() {
        return resMap;
    }

    public static void setResMap(HashMap<String, String> resMap) {
        StandarXMlProcess.resMap = resMap;
    }

    public static Document getDocument(String filePath){
        SAXReader reader=new SAXReader();
        try {
            document = reader.read(new File(filePath));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     *
     * 生成IN端拆包、组包文件
     * @param map
     * @param filepath
     * @param targetName
     * @throws IOException
     */
    public static void getInUnpacketOrPacketXml(HashMap<String, String> map,String filepath,String targetName,Boolean isPacket)throws IOException {

        setMap(map);
        getDocument(filepath);
        Element rootElement = document.getRootElement();
        Element service_body = rootElement.element("Service_Body");
        Element element;
        if(isPacket){
            element = service_body.element("response");
        }else{
            element = service_body.element("request");
        }

      for(Map.Entry<String,String> entry:map.entrySet()){
          String key = entry.getKey();
          String value = entry.getValue();
          Element nodeElement = element.addElement(key);
          nodeElement.addAttribute("metadataid",key);

      }
        OutputFormat format=OutputFormat.createPrettyPrint();
      format.setNewlines(true);
        XMLWriter writer=new XMLWriter(new FileOutputStream(
                new File(targetName)),format);
      writer.write(document);

    }
    public static void getOutUnpacketOrPacketXml(HashMap<String, String> reqMap,HashMap<String, String> resMap,String filepath,String targetName,Boolean isPacket,
                                                 boolean req)throws IOException {

        setReqMap(reqMap);
        setResMap(resMap);
        getDocument(filepath);
        Element element;
        if(isPacket){
            element= (Element)document.selectSingleNode("/Service/Service_Body/request");
        }else{
            if(req){
                Element node = (Element) document.selectSingleNode("/Service/Service_Body");
                Element element1 = node.addElement("request");
                for (Map.Entry<String, String> entry : resMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    Element element2 = element1.addElement(key);
                    element2.addAttribute("metadataid", value + "A");
                }
            }
            element= (Element)document.selectSingleNode("/Service/Service_Body/response");

        }
        for(Map.Entry<String,String> entry:reqMap.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            Element nodeElement = element.addElement(key);
            nodeElement.addAttribute("metadataid",key);

        }
        OutputFormat format=OutputFormat.createPrettyPrint();
        format.setNewlines(true);
        XMLWriter writer=new XMLWriter(new FileOutputStream(
                new File(targetName)),format);
        writer.write(document);

    }

    /**
     *
     * 生成服务定义文件
     * @param flag 判断是否需要请求报文
     * @throws IOException
     */
    public static void getServiceXml(HashMap<String, String> reqMap,HashMap<String, String> resMap,String filepath,String targetName,Boolean flag)throws IOException {

        setReqMap(reqMap);
        setResMap(resMap);
        getDocument(filepath);

        Element element = (Element)document.selectSingleNode("/Service/request/sdoroot/Service_Body");
        for(Map.Entry<String,String> entry:reqMap.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            Element element1 = element.addElement(key);
            element1.addAttribute("metadataid",value);
        }
        //响应 若需要返回请求报文元数据+A
        if(flag){
            Element resElement = (Element)document.selectSingleNode("/Service/response/sdoroot");
            Element resReqEl = resElement.addElement("request");
            for(Map.Entry<String,String> entry:reqMap.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();
                Element element1 = resReqEl.addElement(key);
                element1.addAttribute("metadataid",value+"A");
            }
        }
        Element resElement = (Element)document.selectSingleNode("/Service/response/sdoroot/BODY");
        for(Map.Entry<String,String> entry:resMap.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            Element element1 = resElement.addElement(key);
            element1.addAttribute("metadataid",value);
        }


        OutputFormat format=OutputFormat.createPrettyPrint();
        format.setNewlines(true);
        XMLWriter writer=new XMLWriter(new FileOutputStream(
                new File(targetName)),format);
        writer.write(document);

    }

//    public static void main(String[] args) throws IOException{
//
//        Map<String, String> map = new HashMap<>();
//        map.put("PP0010","PP0010");
//        map.put("PP0020","PP0020");
//        map.put("PP0020","PP0020");
//        String target="C:\\Users\\helu\\Desktop\\TestToo\\ExcelUtil\\src\\main\\resources\\out.xml";
//        String target1="C:\\Users\\helu\\Desktop\\TestToo\\ExcelUtil\\src\\main\\resources\\apcket.xml";
//
//        Document document = StandarXMlProcess.getDocument("C:\\Users\\helu\\Desktop\\TestToo\\ExcelUtil\\src\\main\\resources\\标准报文in拆包模板.xml");
//        StandarXMlProcess.getInUnpacketOrPacketXml((HashMap) map,"C:\\Users\\helu\\Desktop\\TestToo\\ExcelUtil\\src\\main\\resources\\标准报文in拆包模板.xml",
//                target,false);
//        StandarXMlProcess.getInUnpacketOrPacketXml((HashMap) map,"C:\\Users\\helu\\Desktop\\TestToo\\ExcelUtil\\src\\main\\resources\\标准报文in端组包模板.xml",
//                target1,true);
//    }

    public static void main(String[] args) throws IOException{
        Map<String, String> map = new HashMap<>();
        map.put("PP0010","PP0010");
        map.put("PP0020","PP0020");
        map.put("PP0020","PP0020");
        String rootXpath="C:\\Users\\helu\\Desktop\\TestToo\\ExcelUtil\\src\\main\\resources";
        StandarXMlProcess.getServiceXml((HashMap) map,(HashMap) map,rootXpath+"/标准服务定义.xml",rootXpath+"/service.xml",true);
       //out端组包
        StandarXMlProcess.getOutUnpacketOrPacketXml((HashMap) map,null,rootXpath+"/out端组包模板.xml",rootXpath+"/out/packet.xml",true,false);
        //out端拆包
        StandarXMlProcess.getOutUnpacketOrPacketXml((HashMap) map,(HashMap) map,rootXpath+"/out端拆包模板.xml",rootXpath+"/out/unpacket.xml",false,true);
    }
}
