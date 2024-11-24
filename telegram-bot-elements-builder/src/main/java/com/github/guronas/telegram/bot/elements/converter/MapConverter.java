package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.model.Element;

import java.util.Map;

public interface MapConverter<E extends Element<?>> extends ElementConverter<E, Map<?, ?>> {

	@Override
	default boolean canConvert(Object rawContent) {
		return rawContent instanceof Map<?, ?>;
	}
}
