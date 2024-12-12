package com.github.guronas.telegram.bot.elements.util;

import com.github.guronas.telegram.bot.elements.exception.KeyNotFoundException;
import com.github.guronas.telegram.bot.elements.model.Element;
import com.github.guronas.telegram.bot.elements.parameter.DynamicParameters;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ElementUtils {
	public static final String KEY_NOT_FOUND_MESSAGE_TEMPLATE = "Key [%s] must be presented for [%s]";

	public static Object getObjectFromMapOrElseThrow(Map<?, ?> map, Object key, String parentName) {
		return getObjectFromMap(map, key)
				.orElseThrow(() -> new KeyNotFoundException(KEY_NOT_FOUND_MESSAGE_TEMPLATE.formatted(key, parentName)));
	}

	public static <T> T getObjectFromMapIfExists(Map<?, ?> map, Object key, Class<T> objectClass) {
		Object value = map.get(key);
		if (objectClass.isInstance(value)) {
			return objectClass.cast(value);
		}
		return null;
	}

	public static <T> T getOrDefault(T object, T defaultObject) {
		return Objects.nonNull(object) ? object : defaultObject;
	}

	public static <T> T buildIfExist(Element<T> element, Map<String,
			String> params, Map<String,
			DynamicParameters> dynamicParameters) {
		return Objects.nonNull(element) ? element.build(params, dynamicParameters) : null;
	}

	private static Optional<Object> getObjectFromMap(Map<?, ?> map, Object key) {
		return Optional.ofNullable(map.get(key));
	}
}
