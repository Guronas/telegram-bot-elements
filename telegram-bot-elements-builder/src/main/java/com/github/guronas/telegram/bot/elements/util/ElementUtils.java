package com.github.guronas.telegram.bot.elements.util;

import com.github.guronas.telegram.bot.elements.exception.KeyNotFoundException;
import com.github.guronas.telegram.bot.elements.model.Element;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ElementUtils {
	public static final String KEY_NOT_FOUND_MESSAGE_TEMPLATE = "Key [%s] must be presented for [%s]";

	public static Object getObjectFromMapOrElseThrow(Map<?, ?> map, Object key, String parentName) {
		return getObjectFromMap(map, key)
				.orElseThrow(() -> new KeyNotFoundException(KEY_NOT_FOUND_MESSAGE_TEMPLATE.formatted(key, parentName)));
	}

	public static Object getObjectFromMapIfExists(Map<?, ?> map, Object key) {
		return getObjectFromMap(map, key).orElse(null);
	}

	public static <T> T getOrDefault(T object, T defaultObject) {
		return Objects.nonNull(object) ? object : defaultObject;
	}

	public static <T> T buildIfExist(Element<T> element, Map<String, String> params) {
		return Objects.nonNull(element) ? element.build(params) : null;
	}

	private static Optional<Object> getObjectFromMap(Map<?, ?> map, Object key) {
		return Optional.ofNullable(map.get(key));
	}
}
