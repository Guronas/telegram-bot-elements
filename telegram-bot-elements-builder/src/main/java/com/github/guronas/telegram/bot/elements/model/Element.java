package com.github.guronas.telegram.bot.elements.model;

import com.github.guronas.telegram.bot.elements.exception.MandatoryParameterException;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public interface Element<T> {
	String CHAT_ID_KEY = "chatId";
	String EMPTY_TEXT = "";

	ElementType type();

	default T build(Map<String, String> params) {
		throw new UnsupportedOperationException();
	}

	static String getParameterOrThrow(Map<String, String> params, String parameterName) {
		return Optional.ofNullable(params.get(parameterName))
				.orElseThrow(() -> new MandatoryParameterException(parameterName));
	}

	static String buildTextOrGetEmpty(Element<String> text, Map<String, String> params) {
		return Objects.isNull(text) ? EMPTY_TEXT : text.build(params);
	}
}
