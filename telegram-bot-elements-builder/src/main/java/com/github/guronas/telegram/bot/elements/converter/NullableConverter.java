package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.model.Element;

import java.util.Objects;

public interface NullableConverter<E extends Element<?>, C> extends ElementConverter<E, C> {

	@Override
	default boolean canConvert(Object rawContent) {
		return Objects.isNull(rawContent) || internalCanConvert(rawContent);
	}

	boolean internalCanConvert(Object rawContent);
}
