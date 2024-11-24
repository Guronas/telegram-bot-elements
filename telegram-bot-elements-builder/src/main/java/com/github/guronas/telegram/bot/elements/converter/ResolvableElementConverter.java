package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.model.Element;
import com.github.guronas.telegram.bot.elements.model.ElementType;
import com.github.guronas.telegram.bot.elements.model.ResolvableElement;
import com.github.guronas.telegram.bot.elements.parser.ElementFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResolvableElementConverter<E> implements ElementConverter<Element<E>, String> {
	private final ElementType type;

	@Override
	public Element<E> convert(ElementFactory elementFactory, String rawContent) {
		return new ResolvableElement<>(type, rawContent, elementFactory.createElementResolver(type));
	}

	@Override
	public boolean canConvert(Object rawContent) {
		return rawContent instanceof String;
	}
}
