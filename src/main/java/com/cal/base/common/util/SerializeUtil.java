package com.cal.base.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeUtil {

    private static Logger logger = LoggerFactory.getLogger(SerializeUtil.class);

    public static byte[] serialize(Object obj) {
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream bo = null;
        ObjectOutputStream oo = null;
        try {
        	if(obj==null){
        		return null;
        	}
            // object to bytearray
            bo = new ByteArrayOutputStream();
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            try {
                if (oo != null) {
                    oo.close();
                }
                if (bo != null) {
                    bo.close();
                }
            } catch (Exception e) {
                logger.error("ByteToObjectUtils.ObjectToByte close stream encounted a error ", e);
            }
        }
        return (bytes);
    }

    public static Object unserialize(byte[] bytes) {
        Object obj = new java.lang.Object();
        ByteArrayInputStream bi = null;
        ObjectInputStream oi = null;
        try {
        	if(bytes==null){
        		return null;
        	}
            // bytearray to object
            bi = new ByteArrayInputStream(bytes);
            oi = new ObjectInputStream(bi);
            obj = oi.readObject();
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
            return null;
        } finally {
            try {
                if (oi != null) {
                    oi.close();
                }
                if (bi != null) {
                    bi.close();
                }
            } catch (Exception e) {
                logger.error("ByteToObjectUtils.ByteToObject close stream encounted an error ", e);
            }
        }
        return obj;
    }
}
