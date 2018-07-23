package com.crawler.vw.cache;

import com.alibaba.fastjson.JSON;
import com.crawler.vw.entity.Blog;
import com.crawler.vw.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DataCache {

	@Autowired
	private StringRedisTemplate redisTemplate;

	public void save(Blog blog) {
		redisTemplate.opsForValue().set(blog.getUrlMd5(), JSON.toJSONString(blog));
	}

	public Blog get(String url) {
		String md5Url = Md5Util.getMD5(url.getBytes());
		String blogStr = redisTemplate.opsForValue().get(md5Url);
		if (StringUtils.isEmpty(blogStr)) {
			return new Blog();
		}
		return JSON.parseObject(blogStr, Blog.class);
	}

}
