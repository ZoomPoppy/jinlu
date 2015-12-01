package com.jinlufund.utils;

import java.util.Iterator;

public class StringUtils {

	public static boolean hasText(String str) {
		return org.springframework.util.StringUtils.hasText(str);
	}

	public static boolean isEmpty(String str) {
		return org.springframework.util.StringUtils.isEmpty(str);
	}

	public static String join(Iterable<?> iterable, String separator) {
		Iterator<?> iterator = iterable.iterator();
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return "";
		}
		final Object first = iterator.next();
		if (!iterator.hasNext()) {
			if (first != null) {
				return first.toString();
			}
			return "";
		}

		final StringBuilder buf = new StringBuilder(256);
		if (first != null) {
			buf.append(first);
		}
		while (iterator.hasNext()) {
			if (separator != null) {
				buf.append(separator);
			}
			final Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
		}
		return buf.toString();
	}

}
