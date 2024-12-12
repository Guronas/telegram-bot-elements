package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.exception.ElementConversionException;
import com.github.guronas.telegram.bot.elements.model.BooleanElement;
import com.github.guronas.telegram.bot.elements.core.ElementFactory;

import java.util.Objects;

public class BooleanConverter implements NullableConverter<BooleanElement, Boolean> {

	@Override
	public BooleanElement convert(ElementFactory elementFactory, Boolean rawContent) throws ElementConversionException {
		return new BooleanElement(!Objects.isNull(rawContent) && rawContent);
	}

	@Override
	public boolean internalCanConvert(Object rawContent) {
		return rawContent instanceof Boolean;
	}
}
