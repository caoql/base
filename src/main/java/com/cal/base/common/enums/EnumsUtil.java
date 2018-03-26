package com.cal.base.common.enums;

import java.util.Random;

/**
 * 随机抽取的工具类
 * @author andyc
 * 2018-3-22
 */
public final class EnumsUtil {
	private static Random random = new Random(47);

	public static <T extends Enum<T>> T random(Class<T> ec) {
		return random(ec.getEnumConstants());
	}

	public static <T> T random(T[] values) {
		return values[random.nextInt(values.length)];
	}
}
