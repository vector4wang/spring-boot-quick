package com.active2.config;

import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author wangxc
 * @date: 2019/3/6 下午10:11
 *
 */
public class DynamicPropertySource extends MapPropertySource {


	public DynamicPropertySource(String name, Map<String, Object> source) {
		super(name, source);
	}

	public static Builder builder(){
		return new Builder();
	}

	public static class Builder{
		private String sourceName;
		private Map<String, Object> sourceMap = new HashMap<>();

		public Builder setSourceName(String sourceName) {
			this.sourceName = sourceName;
			return this;
		}

		public Builder setProperty(String key, String value) {
			this.sourceMap.put(key, value);
			return this;
		}

		public Builder setProperty(Map<String, Object> sourceMap) {
			this.sourceMap.putAll(sourceMap);
			return this;

		}

		public DynamicPropertySource build() {
			return new DynamicPropertySource(this.sourceName,this.sourceMap);
		}

	}

}
