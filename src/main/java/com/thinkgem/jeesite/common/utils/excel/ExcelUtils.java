package com.thinkgem.jeesite.common.utils.excel;
import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtils {
    private static Configuration configuration =null;
    private static Map<String, Template> allTemplates =null;

    public ExcelUtils(){
        throw new AssertionError();
    }

    public static File createExcel(Map<?, ?> dataMap, String type, String valueName){
        try {
            configuration = new Configuration();
            configuration.setDefaultEncoding("UTF-8");
            configuration.setDirectoryForTemplateLoading(new File(ExcelUtils.class.getResource("/").getPath()+"toExcel/"));
            allTemplates = new HashMap<String, Template>();
            allTemplates.put(type, configuration.getTemplate(valueName));
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        String name = "temp" + (int) (Math.random() * 100000) + ".xls";
        File file = new File(name+".xls");
        Template template = allTemplates.get(type);
        try {
            Writer w = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            template.process(dataMap, w);
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return file;
    }

//    public static void main(String[] args) {
//        String path=ExcelUtils.class.getResource("/").getPath()+"toExcel/"+"test.ftl";
//        System.out.println(path);
//    }

    /**
     *
     * @param request
     * @param response
     * @param dataMap 数据
     * @param type  类别
     * @param valueName 模板名称
     * @param fileName  导出文件名称
     */
    public static void exportExcel(HttpServletRequest request, HttpServletResponse response,
                                   Map<?, ?> dataMap, String type, String valueName,String fileName){
        File file = null;
        InputStream inputStream = null;
        try {
            file = ExcelUtils.createExcel(dataMap,type,valueName);
            inputStream = new FileInputStream(file);
            if (file != null) {
                String charEncoding = "UTF-8";
                if (request.getHeader("User-Agent").toLowerCase().indexOf("msie") != -1) {
                    charEncoding = "GBK";
                }
                String headStr = "attachment; filename=" + new String((fileName).getBytes(charEncoding), "ISO8859-1");
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition", headStr);
                OutputStream out = response.getOutputStream();
                byte[] buffer = new byte[512]; // 缓冲区
                int bytesToRead = -1;
                // 通过循环将读入的Excel文件的内容输出到浏览器中
                while ((bytesToRead = inputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesToRead);
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
