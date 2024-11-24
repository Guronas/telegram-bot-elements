package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.model.ElementType;
import com.github.guronas.telegram.bot.elements.model.TextElement;
import com.github.guronas.telegram.bot.elements.parser.ElementFactory;

public class TextConverter implements ElementConverter<TextElement, String> {

	@Override
	public TextElement convert(ElementFactory elementFactory, String rawContent) {
		return new TextElement(rawContent, elementFactory.createElementResolver(ElementType.TEXT));
	}

	@Override
	public boolean canConvert(Object rawContent) {
		return rawContent instanceof String;
	}
}
