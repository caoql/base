package com.cal.base.common.cache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EhCacheTest {
	
	@Cacheable(key="#param")
    public String getTimestamp(String param) {
		System.out.println(param);
        Long timestamp = System.currentTimeMillis();
        return timestamp.toString();
    }
}
