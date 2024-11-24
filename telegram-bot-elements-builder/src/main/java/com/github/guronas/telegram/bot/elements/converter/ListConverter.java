package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.model.Element;

import java.util.List;

public interface ListConverter<E extends Element<?>> extends ElementConverter<E, List<?>> {

	@Override
	default boolean canConvert(Object rawContent) {
		return rawContent instanceof List<?>;
	}
}
