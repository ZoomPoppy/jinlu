package com.jinlufund.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletRequest;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class ServletRequestUtils {

	/**
	 * 从请求中获取所有名字以某前缀开头的输入参数，去掉前缀后放入一个Map，如果单值value为String，多值value为List<String>，当参数值为空串时忽略该参数
	 * 
	 * @param request
	 *            含有输入参数的请求
	 * @param prefix
	 *            前缀
	 * @return 参数Map
	 */
	public static Map<String, Object> getParamsFromRequestWithPrefix(ServletRequest request, String prefix) {
		Assert.notNull(request, "request must not be null");
		if (prefix == null) {
			prefix = "";
		}

		Map<String, Object> params = new TreeMap<String, Object>(); // 这里选择了有序的Map
		Enumeration<?> paramNames = request.getParameterNames();
		while ((paramNames != null) && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if (paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values != null) {
					if (values.length == 1) {
						if (!StringUtils.isEmpty(values[0])) {
							params.put(unprefixed, values[0]);
						}
					} else {
						List<String> list = new ArrayList<String>();
						for (String value : values) {
							if (!StringUtils.isEmpty(value)) {
								list.add(value);
							}
						}
						params.put(unprefixed, list);
					}
				}
			}
		}
		return params;
	}

	public static String formatParamsWithPrefix(Map<String, Object> params, String prefix) {
		if (params == null) {
			return "";
		}
		if (prefix == null) {
			prefix = "";
		}

		StringBuilder sb = new StringBuilder();
		for (Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof List) {
				@SuppressWarnings("unchecked")
				List<String> multiValue = (List<String>) value;
				for (String v : multiValue) {
					sb.append('&').append(prefix).append(key).append('=').append(v);
				}
			} else if (value instanceof String) {
				sb.append('&').append(prefix).append(key).append('=').append(value);

			} else {
				throw new IllegalArgumentException("parameter value must be string or string list, invalid type: "
						+ value.getClass().getName());
			}
		}
		return sb.toString();
	}

}
