package com.github.guronas.telegram.bot.elements.parameter;

public record Parameter(String key, Object value) {

	public static Parameter of(ParameterKey keyHolder, Object value) {
		return new Parameter(keyHolder.getKey(), value);
	}

	public static Parameter of(String key, Object value) {
		return new Parameter(key, value);
	}
}
