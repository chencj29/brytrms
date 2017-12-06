package com.bstek.urule;

import com.bstek.urule.model.Label;
import com.bstek.urule.model.library.Datatype;
import com.bstek.urule.model.library.variable.Act;
import com.bstek.urule.model.library.variable.Variable;
import com.thinkgem.jeesite.common.utils.PropertiesLoader;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

public class ClassUtils {

    static PropertiesLoader loader = new PropertiesLoader("classpath:/jeesite.properties");
    static String ignoreFields = loader.getProperty("ignoreFields", "createBy, updateBy, class, currentUser, dbName, createDate, updateDate, isNewRecord, mainDataId, mainTableId, random, searchText, slave, sqlMap, tableId, global, page, delFlag");

    public ClassUtils() {
    }

    public static void classToXml(Class<?> cls, File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException var12) {
                throw new RuntimeException(var12);
            }
        }

        FileOutputStream out = null;

        try {
            out = new FileOutputStream(file);
            List ex = classToVariables(cls);
            StringBuffer sb = new StringBuffer();
            sb.append("<variables clazz=\"" + cls.getName() + "\">");
            Iterator format = ex.iterator();

            while (format.hasNext()) {
                Variable doc = (Variable) format.next();
                sb.append("<variable ");
                sb.append("name=\"" + doc.getName() + "\" ");
                if (doc.getLabel() != null) {
                    sb.append("label=\"" + doc.getLabel() + "\" ");
                }

                if (doc.getDefaultValue() != null) {
                    sb.append("defaultValue=\"" + doc.getDefaultValue() + "\" ");
                }

                if (doc.getType() != null) {
                    sb.append("type=\"" + doc.getType() + "\" ");
                }

                if (doc.getAct() != null) {
                    sb.append("act=\"" + doc.getAct() + "\" ");
                }

                sb.append(">");
                sb.append("</variable>");
            }

            sb.append("</variables>");
            Document doc1 = DocumentHelper.parseText(sb.toString());
            OutputFormat format1 = OutputFormat.createPrettyPrint();
            format1.setEncoding("utf-8");
            XMLWriter writer = new XMLWriter(out, format1);
            writer.write(doc1);
            writer.close();
            out.close();
        } catch (Exception var13) {
            throw new RuntimeException(var13);
        } finally {
            IOUtils.closeQuietly(out);
        }

    }

    public static List<Variable> classToVariables(Class<?> cls) {
        try {
            List e = paserClass("", cls, new ArrayList());
            return e;
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    private static List<Variable> paserClass(String path, Class<?> cls, Collection<Class<?>> parsed) throws Exception {
        ArrayList variables = new ArrayList();
        BeanInfo beanInfo = Introspector.getBeanInfo(cls, Object.class);
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        if (pds != null && !parsed.contains(cls)) {
            parsed.add(cls);
            PropertyDescriptor[] var9 = pds;
            int var8 = pds.length;

            for (int var7 = 0; var7 < var8; ++var7) {
                PropertyDescriptor pd = var9[var7];
                if (ignoreFields.contains(pd.getName())) continue;
                if (!isLabelAnnotationPresent(cls, pd.getName())) continue;
                Variable variable = new Variable();
                Class type = pd.getPropertyType();
                Datatype dataType = getDateType(type);
                String propertyName = pd.getName();
                String label = getPropertyAnnotationLabel(cls, propertyName);
                String name = path + pd.getName();
                variable.setName(name);
                variable.setLabel(label == null ? name : label);
                variable.setType(dataType);
                variable.setAct(Act.InOut);
                if (Datatype.Object.equals(dataType)) {
                    variables.addAll(paserClass(path + pd.getName() + ".", type, parsed));
                } else {
                    variables.add(variable);
                }
            }
        }

        return variables;
    }

    private static boolean isLabelAnnotationPresent(Class<?> clazz, String fieldName) throws Exception {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = clazz.getField(fieldName);
        }
        return field.isAnnotationPresent(Label.class);
    }

    private static String getPropertyAnnotationLabel(Class<?> cls, String fieldName) throws Exception {
//        Field field = cls.getDeclaredField(fieldName);

        Field field = null;
        try {
            field = cls.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = cls.getField(fieldName);
        }

        Label pd = (Label) field.getAnnotation(Label.class);
        return pd != null ? pd.value() : null;
    }

    private static Datatype getDateType(Class<?> type) {
        return String.class.isAssignableFrom(type) ? Datatype.String : (!Boolean.class.isAssignableFrom(type) && !Boolean.TYPE.isAssignableFrom(type) ? (!Integer.class.isAssignableFrom(type) && !Integer.TYPE.isAssignableFrom(type) ? (!Float.class.isAssignableFrom(type) && !Float.TYPE.isAssignableFrom(type) ? (!Long.class.isAssignableFrom(type) && !Long.TYPE.isAssignableFrom(type) ? (BigDecimal.class.isAssignableFrom(type) ? Datatype.BigDecimal : (!Double.class.isAssignableFrom(type) && !Double.TYPE.isAssignableFrom(type) ? (Date.class.isAssignableFrom(type) ? Datatype.Date : (Date.class.isAssignableFrom(type) ? Datatype.Date : (List.class.isAssignableFrom(type) ? Datatype.List : (Map.class.isAssignableFrom(type) ? Datatype.Map : (Set.class.isAssignableFrom(type) ? Datatype.Set : (Enum.class.isAssignableFrom(type) ? Datatype.Enum : (!Character.class.isAssignableFrom(type) && !Character.TYPE.isAssignableFrom(type) ? Datatype.Object : Datatype.Char))))))) : Datatype.Double)) : Datatype.Long) : Datatype.Float) : Datatype.Integer) : Datatype.Boolean);
    }
}
