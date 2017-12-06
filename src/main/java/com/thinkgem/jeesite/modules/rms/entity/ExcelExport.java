package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by wjp on 2016/05/20.
 */
public class ExcelExport {
    private HttpServletRequest request;
    private HttpServletResponse response;

    private String title;
    private String[] rowsName;
    private String fileName;
    private List<Object[]> dataList ;

    public void exp(){
        ExcelUtil ex = new ExcelUtil(title, rowsName, dataList);
        try {
            HSSFWorkbook workbook = ex.export();
            if(workbook !=null){
                String charEncoding = "UTF-8";
                if (request.getHeader("User-Agent").toLowerCase().indexOf("msie") != -1) {
                    charEncoding = "GBK";
                }
                String headStr = "attachment; filename=" + new String((fileName+".xls").getBytes(charEncoding),"ISO8859-1");
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition", headStr);
                OutputStream out = response.getOutputStream();
                workbook.write(out);

            }
        }catch (IOException e1) {
            e1.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRowsName(String[] rowsName) {
        this.rowsName = rowsName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDataList(List<Object[]> dataList) {
        this.dataList = dataList;
    }
}
