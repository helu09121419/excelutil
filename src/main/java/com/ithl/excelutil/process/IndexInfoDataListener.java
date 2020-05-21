package com.ithl.excelutil.process;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.ithl.excelutil.readdemo.entity.DataModel;
import com.ithl.excelutil.readdemo.entity.IndexModel;

import java.util.ArrayList;
import java.util.List;

public class IndexInfoDataListener extends AnalysisEventListener<IndexModel> {
    private List<IndexModel> li=new ArrayList<>();
    @Override
    public void invoke(IndexModel indexModel, AnalysisContext analysisContext) {
        Integer num = analysisContext.getCurrentRowNum();
        if(num>1){
            li.add(indexModel);
            System.out.println("---------------------"+indexModel.getCl4());
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {


    }

    public List<IndexModel> getLi() {
        return li;
    }


}
