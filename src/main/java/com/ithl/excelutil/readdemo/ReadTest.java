package com.ithl.excelutil.readdemo;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.ithl.excelutil.process.UserInfoDataListener;
import com.ithl.excelutil.readdemo.entity.DataModel;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * EasyExcel  常用读方式
 *
 */

public class ReadTest {

    /**
     *
     * 最简单的读
     * 1.创建excel对应的实体对象
     * 2.由于默认一行行的读取excel，所以需要创建excel 一行一行的回调监听器
     * 3.直接读即可
     *
     */
    public static void main(String[] args) {
        //simpleRead();
        try {
            readExcel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void simpleRead(){
        //有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后
        //里面用到spring可以构造方法传进去
        //写法1：
        String filePath="C:\\Users\\helu\\Desktop\\天津银行ESB项目_ESB云上网关（新核心）_字段映射_V0.1.6.XLSX";
        String fileName = "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 参数一：读取的excel文件路径
        // 参数二：读取sheet的一行，将参数封装在DemoData实体类中
        // 参数三：读取每一行的时候会执行DemoDataListener监听器
        //EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
        FileInputStream in=null;
        try {
           in = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        AnalysisEventListener listener=new UserInfoDataListener();

        //List<Object> read = EasyExcelFactory.read(in, new Sheet(8, 0,DataModel.class));
        EasyExcelFactory.readBySax(in,new Sheet(8,0,DataModel.class),new UserInfoDataListener());

//        for ( Object a :read){
//            System.out.println(a);
//        }


    }
    @Test
    public static void readExcel()throws IOException {
        String rootXpath="D:\\SVN\\DOC\\03_规范管理\\服务治理文档\\TJBSD\\V0.1.6\\字段映射";
        String fileName="/天津银行ESB项目_特色业务平台_字段映射_V0.1.6.XLSX";
        FileInputStream in=new FileInputStream(rootXpath+fileName);
        EasyExcelFactory.readBySax(in,new Sheet(7,0,DataModel.class),new UserInfoDataListener());

    }
}
