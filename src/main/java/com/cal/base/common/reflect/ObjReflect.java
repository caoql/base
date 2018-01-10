package com.cal.base.common.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 获取一个类的反射值
 * @author andyc
 * 2017-10-25
 */
public class ObjReflect {
	
	private static final Logger logger = Logger.getLogger(ObjReflect.class);
	
	/**
	 * 返回一个对象的字符串值
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj){
		StringBuffer sb = new StringBuffer();
		try {
			sb = getObjectValue(obj);
		} catch (Exception e) {
		    e.printStackTrace();
			logger.error(e);
		} 
		return sb.toString();
	}
	
	private static StringBuffer getObjectValue(Object object) throws Exception{  
        // 拿到该类  
        Class<?> clz = object.getClass();  
        // 获取实体类的所有属性，返回Field数组  
        Field[] fields = clz.getDeclaredFields();  
        StringBuffer sb = new StringBuffer();
        // --for() begin  
        for (Field field : fields) {
            //1.如果类型是String  
            if (field.getGenericType().toString().equals("class java.lang.String")) {  
                // 拿到该属性的get方法
                Method m = (Method) object.getClass().getMethod(  
                        "get" + getMethodName(field.getName()));  
                // 调用getter方法获取属性值  
                String val = (String) m.invoke(object);
                if (val != null) {  
                	sb.append(" "+ field.getName()+":" + val);
                }  
            }
  
            //2.如果类型是Integer  
            if (field.getGenericType().toString().equals("class java.lang.Integer") || 
            		field.getGenericType().toString().equals("int")) {  
                Method m = (Method) object.getClass().getMethod(  
                        "get" + getMethodName(field.getName()));  
                Integer val = (Integer) m.invoke(object);  
                if (val != null) {  
                	sb.append(" "+ field.getName()+":" + val);
                }  
            }  
  
            //3.如果类型是Double  
            if (field.getGenericType().toString().equals("class java.lang.Double") || 
            		field.getGenericType().toString().equals("double")) {  
                Method m = (Method) object.getClass().getMethod(  
                        "get" + getMethodName(field.getName()));  
                Double val = (Double) m.invoke(object);  
                if (val != null) {  
                	sb.append(" "+ field.getName()+":" + val);
                }  
            }  
  
            //4.如果类型是Boolean 是封装类  
            if (field.getGenericType().toString().equals(  
                    "class java.lang.Boolean")) {  
                Method m = (Method) object.getClass().getMethod(  
                        "get" + getMethodName(field.getName()));  
                Boolean val = (Boolean) m.invoke(object);  
                if (val != null) {  
                	sb.append(" "+ field.getName()+":" + val);
                }  
        	}  
  
            // 如果类型是boolean 基本数据类型不一样,支持isXXX和getXXX
            // 反射找不到getter的具体名  
            if (field.getGenericType().toString().equals("boolean")) {
            	Method m;
            	try{
            		m = (Method) object.getClass().getMethod(  
                    		"is" + getMethodName(field.getName())); 
            	} catch(NoSuchMethodException e){
            		m = (Method) object.getClass().getMethod(  
                    		"get" + getMethodName(field.getName())); 
            	}
                Boolean val = (Boolean) m.invoke(object);  
                if (val != null) {  
                	sb.append(" "+ field.getName()+":" + val);
                }  
            }  
            
            //5.如果类型是Date  
            if (field.getGenericType().toString().equals(  
                    "class java.util.Date") || field.getGenericType().toString().equals(  
                            "class java.sql.Date")) {  
                Method m = (Method) object.getClass().getMethod(  
                        "get" + getMethodName(field.getName()));  
                Date val = (Date) m.invoke(object);  
                if (val != null) {  
                	sb.append(" "+ field.getName() + ":" + val);
                }  
            }
            //6.如果类型是Long-要忽视serialVersionUID
            if (field.getGenericType().toString().equals("class java.lang.Long")) {  
                Method m = (Method) object.getClass().getMethod(  
                        "get" + getMethodName(field.getName()));  
                Long val = (Long) m.invoke(object);  
                if (val != null) {  
                	sb.append(" "+ field.getName()+":" + val);
                }  
            }  
  
            //7.如果类型是BigDecimal  
            if (field.getGenericType().toString().equals("class java.math.BigDecimal")) {  
                Method m = (Method) object.getClass().getMethod(  
                        "get" + getMethodName(field.getName()));  
                BigDecimal val = (BigDecimal) m.invoke(object);  
                if (val != null) {  
                	sb.append(" "+ field.getName()+":" + val);
                }  
            }  
            
            
            //如果还需要其他的类型请自己做扩展  
        }
        //for() --end 
        return sb;
    }  
  
    //把一个字符串的第一个字母大写、效率是最高的
    private static String getMethodName(String fildeName) {  
        byte[] items = fildeName.getBytes();  
        items[0] = (byte) ((char) items[0] - 'a' + 'A');  
        return new String(items);  
    }  
}
