package com.ithl.excelutil.process;

import com.ithl.excelutil.readdemo.entity.IndexModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 生成所有拆组包所需名
 */
public class XMLPacketNameUtil {
    //组包名称
    private  String InPacketName;
    //IN拆包名称
    private  String InUnapcketName;
    //OUT组包名称
    private  String OutPacketName;
    //OUT拆包名称
    private  String OutUnapcketName;
    //服务定义名称
    private  String serviceName;
    //渠道名
    private  String channelName;
    //系统名
    private  String systemName;
    //服务码
    private  String SvcCd;
    //场景码
    private  String SvcScn;
    private List<XMLPacketNameUtil> xmlList=new ArrayList<>();


    public   void instance(String SvcCd,String SvcScn,String channelName, String systemName){
            this.InPacketName="service_"+SvcCd+SvcScn+"_system_"+channelName+".xml";
            this.InUnapcketName="channel_"+channelName+"_service_"+SvcCd+SvcScn+".xml";
            this.OutPacketName="service_"+SvcCd+SvcScn+"_system_"+systemName+".xml";
            this.OutUnapcketName="channel_"+systemName+"_service_"+SvcCd+SvcScn+".xml";
            this.serviceName="service_"+SvcCd+SvcScn+".xml";

    }
    public   List<XMLPacketNameUtil> instanceOfList(List<IndexModel> li){
        for(IndexModel model:li){
            XMLPacketNameUtil util = new XMLPacketNameUtil();
                String SvcCd=model.getCl4();
                String SvcScn=model.getCl6();
                String channelName= model.getCl8();
                String systemName = model.getCl9();
            util.InPacketName="service_"+SvcCd+SvcScn+"_system_"+channelName+".xml";
            util.InUnapcketName="channel_"+channelName+"_service_"+SvcCd+SvcScn+".xml";
            util.OutPacketName="service_"+SvcCd+SvcScn+"_system_"+systemName+".xml";
            util.OutUnapcketName="channel_"+systemName+"_service_"+SvcCd+SvcScn+".xml";
            util.serviceName="service_"+SvcCd+SvcScn+".xml";
            if(util!=null){
                xmlList.add(util);
            }

        }
        return xmlList;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getInPacketName() {
        return InPacketName;
    }

    public void setInPacketName(String inPacketName) {
        InPacketName = inPacketName;
    }

    public String getInUnapcketName() {
        return InUnapcketName;
    }

    public void setInUnapcketName(String inUnapcketName) {
        InUnapcketName = inUnapcketName;
    }

    public String getOutPacketName() {
        return OutPacketName;
    }

    public void setOutPacketName(String outPacketName) {
        OutPacketName = outPacketName;
    }

    public String getOutUnapcketName() {
        return OutUnapcketName;
    }

    public void setOutUnapcketName(String outUnapcketName) {
        OutUnapcketName = outUnapcketName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSvcCd() {
        return SvcCd;
    }

    public void setSvcCd(String svcCd) {
        SvcCd = svcCd;
    }

    public String getSvcScn() {
        return SvcScn;
    }

    public void setSvcScn(String svcScn) {
        SvcScn = svcScn;
    }
}
