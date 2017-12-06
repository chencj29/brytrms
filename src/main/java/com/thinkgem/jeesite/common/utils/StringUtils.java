/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.modules.ams.entity.LongPlanStop;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 *
 * @author ThinkGem
 * @version 2013-05-22
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 转换为字节数组
     *
     * @param str
     * @return
     */
    public static byte[] getBytes(String str) {
        if (str != null) {
            try {
                return str.getBytes(CHARSET_NAME);
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 转换为字节数组
     *
     * @param bytes
     * @return
     */
    public static String toString(byte[] bytes) {
        try {
            return new String(bytes, CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            return EMPTY;
        }
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inString(String str, String... strs) {
        if (str != null) {
            for (String s : strs) {
                if (str.equals(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean conString(String str, List<String> strs) {
        if (str != null) {
            for (String s : strs) {
                if (str.equals(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * 替换为手机识别的HTML，去掉样式及属性，保留回车。
     *
     * @param html
     * @return
     */
    public static String replaceMobileHtml(String html) {
        if (html == null) {
            return "";
        }
        return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
    }

    /**
     * 替换为手机识别的HTML，去掉样式及属性，保留回车。
     *
     * @param txt
     * @return
     */
    public static String toHtml(String txt) {
        if (txt == null) {
            return "";
        }
        return replace(replace(Encodes.escapeHtml(txt), "\n", "<br/>"), "\t", "&nbsp; &nbsp; ");
    }

    /**
     * 缩略字符串（不区分中英文字符）
     *
     * @param str    目标字符串
     * @param length 截取长度
     * @return
     */
    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
                currentLength += String.valueOf(c).getBytes("GBK").length;
                if (currentLength <= length - 3) {
                    sb.append(c);
                } else {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String abbr2(String param, int length) {
        if (param == null) {
            return "";
        }
        StringBuffer result = new StringBuffer();
        int n = 0;
        char temp;
        boolean isCode = false; // 是不是HTML代码
        boolean isHTML = false; // 是不是HTML特殊字符,如&nbsp;
        for (int i = 0; i < param.length(); i++) {
            temp = param.charAt(i);
            if (temp == '<') {
                isCode = true;
            } else if (temp == '&') {
                isHTML = true;
            } else if (temp == '>' && isCode) {
                n = n - 1;
                isCode = false;
            } else if (temp == ';' && isHTML) {
                isHTML = false;
            }
            try {
                if (!isCode && !isHTML) {
                    n += String.valueOf(temp).getBytes("GBK").length;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (n <= length - 3) {
                result.append(temp);
            } else {
                result.append("...");
                break;
            }
        }
        // 取出截取字符串中的HTML标记
        String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)",
                "$1$2");
        // 去掉不需要结素标记的HTML标记
        temp_result = temp_result
                .replaceAll(
                        "</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
                        "");
        // 去掉成对的HTML标记
        temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>",
                "$2");
        // 用正则表达式取出标记
        Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
        Matcher m = p.matcher(temp_result);
        List<String> endHTML = Lists.newArrayList();
        while (m.find()) {
            endHTML.add(m.group(1));
        }
        // 补全不成对的HTML标记
        for (int i = endHTML.size() - 1; i >= 0; i--) {
            result.append("</");
            result.append(endHTML.get(i));
            result.append(">");
        }
        return result.toString();
    }

    /**
     * 转换为Double类型
     */
    public static Double toDouble(Object val) {
        if (val == null) {
            return 0D;
        }
        try {
            return Double.valueOf(trim(val.toString()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }

    /**
     * 获得i18n字符串
     */
    public static String getMessage(String code, Object[] args) {
        LocaleResolver localLocaleResolver = (LocaleResolver) SpringContextHolder.getBean(LocaleResolver.class);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Locale localLocale = localLocaleResolver.resolveLocale(request);
        return SpringContextHolder.getApplicationContext().getMessage(code, args, localLocale);
    }

    /**
     * 获得用户远程地址
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        } else if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase("hello_world") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase("hello_world") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase("hello_world") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 如果不为空，则设置值
     *
     * @param target
     * @param source
     */
    public static void setValueIfNotBlank(String target, String source) {
        if (isNotBlank(source)) {
            target = source;
        }
    }

    /**
     * 转换为JS获取对象值，生成三目运算返回结果
     *
     * @param objectString 对象串
     *                     例如：row.user.id
     *                     返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
     */
    public static String jsGetVal(String objectString) {
        StringBuilder result = new StringBuilder();
        StringBuilder val = new StringBuilder();
        String[] vals = split(objectString, ".");
        for (int i = 0; i < vals.length; i++) {
            val.append("." + vals[i]);
            result.append("!" + (val.substring(1)) + "?'':");
        }
        result.append(val.substring(1));
        return result.toString();
    }

    /**
     * @param @param  code
     * @param @return
     * @return String
     * @throws
     * @Title: code2YesOrNo
     * @Description: 通用方法，将字符的0、1转换成否、是来保存，0：否，1：是
     * @author: chencheng
     * @date： 2015年12月23日 上午11:23:54
     */
    public static String code2YesOrNo(String code) {
        String result = null;
        if (org.apache.commons.lang3.StringUtils.isEmpty(code)) {
            result = null;
        } else if (code.equals("1")) {
            result = "是";
        } else if (code.equals("0")) {
            result = "否";
        }
        return result;
    }

    /**
     * @param @param  code
     * @param @return
     * @return String
     * @throws
     * @Title: code2InOutType
     * @Description: 进出港类型
     * @author: chencheng
     * @date： 2016年1月3日 下午4:12:49
     */
    public static String code2InOutType(String code) {
        String result = null;
        if (org.apache.commons.lang3.StringUtils.isEmpty(code)) {
            result = null;
        } else if (code.equals("J")) {
            result = "进港";
        } else if (code.equals("C")) {
            result = "出港";
        }
        return result;
    }

    /**
     * 获取随机字母数字组合
     *
     * @param length 字符串长度
     * @return
     */
    public static String getRandomCharAndNumr(Integer length) {
        String str = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            boolean b = random.nextBoolean();
            if (b) { // 字符串
                // int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母
                str += (char) (65 + random.nextInt(26));// 取得大写字母
            } else { // 数字
                str += String.valueOf(random.nextInt(10));
            }
        }
        return str;
    }

    /**
     * @param @return
     * @return String
     * @throws
     * @Title: list2LongPlanStopStr
     * @Description: LongPlanStop Object List to LongPlanStopStr for JSP view
     * @author: chencheng
     * @date： 2016年1月5日 下午5:20:55
     */
    public static String list2LongPlanStopStr(List<LongPlanStop> list, String legal) {
        StringBuffer sb = new StringBuffer();
        if (legal.equals("1")) {
            sb.append("<span>");
        } else {
            sb.append("<span style=\"color: red;\" title=\"计划拆分不合法\">");
        }
        if (list != null && !list.isEmpty()) {
            for (LongPlanStop longPlanStop : list) {
                String stime = "";
                String etime = "";
                if (StringUtils.isNotBlank(longPlanStop.getStopPointSTime())) {
                    stime = longPlanStop.getStopPointSTime() + "&nbsp;离港&nbsp;";
                }
                if (StringUtils.isNotBlank(longPlanStop.getStopPointETime())) {
                    etime = longPlanStop.getStopPointETime() + "&nbsp;到港";
                }
                sb.append(longPlanStop.getStopPointName()).append("&nbsp;&nbsp;&nbsp;").append(stime);
                sb.append(etime).append("<br>");
            }
        }
        sb.append("</span>");
        return sb.toString();
    }

    /**
     * @param @param  flightNumStr
     * @param @return
     * @return List<String>
     * @throws
     * @Title: divideFlightNum
     * @Description: 拆分长期计划里的航班号，根据/分隔，如：HU111/2，拆分为HU111;HU112
     * @author: chencheng
     * @date： 2016年1月12日 下午6:14:32
     */
    public static List<String> divideFlightNum(String flightNumStr) throws Exception {
        List<String> flightNumList = new ArrayList<String>();
        if (flightNumStr.indexOf("/") > 0) {

            String[] flightNumArray = flightNumStr.split("/");
            String baseFlightNum = flightNumStr.substring(0, flightNumStr.indexOf("/"));
            flightNumList.add(baseFlightNum);

            for (int i = 1; i < flightNumArray.length; i++) {
                String postfix = flightNumArray[i];
                String prefix = baseFlightNum.substring(0, baseFlightNum.length() - postfix.length());
                flightNumList.add(prefix + postfix);
            }
        } else {
            flightNumList.add(flightNumStr);
        }
        return flightNumList;
    }

    /**
     * 根据changeTypeCode返回变更类型
     * 1：修改；2：新增；3：删除
     *
     * @param s
     */
    public static String getChangeTypeByCode(String s) {
        String result = "";
        switch (s) {
            case "1":
                result = "修改";
                break;
            case "2":
                result = "新增";
                break;
            case "3":
                result = "删除";
                break;
        }
        return result;
    }

    public static String[] listField2Array(List<?> list, String propertyName) {
        ArrayList<String> result = new ArrayList();
        if (list == null || list.size() == 0)
            return result.toArray(new String[]{});
        //
        String value = "";
        for (Object c : list) {
            Field field = ReflectionUtils.findField(c.getClass(), propertyName);
            if (field != null) {
                field.setAccessible(true);
                value = (String) ReflectionUtils.getField(field, c);
                if (StringUtils.isNotBlank(value) && !result.contains(value)) {
                    result.add(value);
                }
            }
        }
        return result.toArray(new String[]{});
    }

    public static List<String> listField2Collection(List<?> list, String propertyName) {
        String[] result = listField2Array(list, propertyName);
        if (result != null) {
            return Arrays.asList(result).stream().distinct().collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 自定义模板 wjp
     *
     * @param msg 带大括号的字模板
     * @param o   要替换的参数
     * @return
     */
    public static String tpl(String msg, Object... o) {
        try {
            String result = msg;
            if (msg != null) {
                String rep = "\\{\\}";//定义模板字符串
                if ((result.length() - result.replaceAll(rep, "").length()) / 2 == o.length) {
                    for (int i = 0; i < o.length; i++) {
                        result = result.replaceFirst(rep, String.valueOf(o[i]));
                    }
                    return result;
                } else {
//                throw new RuntimeException("参数数量不符!");
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static String tplMap(String msg, Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String regex = "\\{\\{" + entry.getKey() + "\\}\\}";
            msg = msg.replaceAll(regex, entry.getValue().toString());
        }
        return msg;
    }

    //转为easyUI datagrid 绑定的数据
    public static String mapToEasyJson(Map<String, Integer> map) {
        //{"total":1,"rows":[{"id":"1","user":"name"},{"id":"2","user":"name2"}]}
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            list.add("{\"gateNum\":\"" + entry.getKey() + "\",\"num\":\"" + entry.getValue() + "\"}");
        }
        return "{\"total\":" + list.size() + ",\"rows\":[" + StringUtils.join(list, ",") + "]}";
    }

    //转为字符串 (用于统计查询)
    public static String toStr(Object field, Object... ext) {
        if (ext != null && ext.length == 1) {
            if (field == null && ext[0] == null) return "";
            else if (field == null) {
                if (ext[0] instanceof Date) {
                    return DateUtils.formatDate((Date) ext[0], "yyyy-MM-dd HH:ss");
                } else return ext[0].toString();
            } else if (ext[0] == null) {
                if (field instanceof Date) {
                    return DateUtils.formatDate((Date) field, "yyyy-MM-dd HH:ss");
                } else return field.toString();
            } else {
                return field.toString() + ext[0].toString();
            }
        } else {
            if (field == null) return "";
            if (field instanceof Date) {
                return DateUtils.formatDate((Date) field, "yyyy-MM-dd HH:ss");
            }
            return field.toString();
        }
    }
}
