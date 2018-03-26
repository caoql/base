package com.cal.base.common.util.idgen;

import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

public class UUIDUtil {
	private static Random rd = new Random();
	
	public synchronized static String newDCGuid(String sysCode) {
		if (StringUtils.isEmpty(sysCode)) {
			sysCode = "";
		}
		StringBuilder rtn = new StringBuilder(sysCode);
		rtn.append(System.currentTimeMillis());
		rtn.append(Integer.toHexString(rd.nextInt(16)).toUpperCase());
		rtn.append(Integer.toHexString(rd.nextInt(16)).toUpperCase());
		rtn.append(Integer.toHexString(rd.nextInt(16)).toUpperCase());
		rtn.append(Integer.toHexString(rd.nextInt(16)).toUpperCase());
		rtn.append(Integer.toHexString(rd.nextInt(16)).toUpperCase());
		rtn.append(Integer.toHexString(rd.nextInt(16)).toUpperCase());
		rtn.append(Integer.toHexString(rd.nextInt(16)).toUpperCase());
		rtn.append(Integer.toHexString(rd.nextInt(16)).toUpperCase());
		rtn.append(Integer.toHexString(rd.nextInt(16)).toUpperCase());
		rtn.append(Integer.toHexString(rd.nextInt(16)).toUpperCase());
		return rtn.toString();
	}
	/**
	 * 获得uuid
	 * @return
	 */
	public synchronized static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.replace("-", "");
	}
	/**
	 * 获得一个0到9的随机数
	 * @return
	 */
	public static String getRandomNumber(){
		return ""+((int)(Math.random()*1000));
	}
	/**
	 * 产生定长的随机数
	 * @param length
	 * @return
	 */
	public static String getRandomNumber(int length){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<length;i++){
			sb.append(getRandomNumber());
		}
		return sb.toString();
	}
}
