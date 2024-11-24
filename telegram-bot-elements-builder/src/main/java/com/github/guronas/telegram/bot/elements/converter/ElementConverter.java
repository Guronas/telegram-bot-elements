package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.exception.ElementConversionException;
import com.github.guronas.telegram.bot.elements.model.Element;
import com.github.guronas.telegram.bot.elements.parser.ElementFactory;

public interface ElementConverter<E extends Element<?>, C> {

	E convert(ElementFactory elementFactory, C rawContent) throws ElementConversionException;

	boolean canConvert(Object rawContent);
}
