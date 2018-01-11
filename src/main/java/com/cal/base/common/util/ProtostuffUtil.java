package com.cal.base.common.util;

import com.cal.base.system.entity.po.User;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class ProtostuffUtil {
	private Schema<User> schema = RuntimeSchema.createFrom(User.class);
	 
	// 序列化
	public byte[] serialize(final User user) {
		final LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try {
			return serializeInternal(user, schema, buffer);
		} catch (final Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
		
	}
	
	// 反序列化
	public User deserialize(final byte[] bytes) {
		try {
			User user = deserializeInternal(bytes, schema.newMessage(), schema);
			if (user != null) {
				return user;
			}
		} catch (final Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} 
		return null;
	}

	private <T> T deserializeInternal(final byte[] bytes, final T result,
			final Schema<T> schema) {
		ProtostuffIOUtil.mergeFrom(bytes, result, schema);
		return result;
	}

	private <T> byte[] serializeInternal(final T source, final Schema<T> schema,
			final LinkedBuffer buffer) {
		return ProtostuffIOUtil.toByteArray(source, schema, buffer);
	}
}
