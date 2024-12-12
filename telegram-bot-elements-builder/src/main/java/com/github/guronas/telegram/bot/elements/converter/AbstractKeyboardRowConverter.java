package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.exception.ElementConversionException;
import com.github.guronas.telegram.bot.elements.model.Element;
import com.github.guronas.telegram.bot.elements.model.ElementType;
import com.github.guronas.telegram.bot.elements.core.ElementFactory;
import com.github.guronas.telegram.bot.elements.util.ElementUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public abstract class AbstractKeyboardRowConverter<T extends Element<?>, B extends Element<?>> implements ListConverter<T> {
	private final ElementType type;
	private final ElementType buttonType;

	@Override
	public T convert(ElementFactory elementFactory, List<?> rawContent) throws ElementConversionException {
		try {
			List<B> buttons = rawContent.stream()
					.filter(rootButtonElement -> rootButtonElement instanceof Map<?, ?>)
					.map(rootButtonElement -> ((Map<?, ?>) rootButtonElement))
					.map(rootButtonElement -> rootButtonElement.get(buttonType.getTypeName()))
					.map(button -> elementFactory.<B, Object>createElement(buttonType, button))
					.toList();

			return convertInternal(buttons);
		} catch (Exception e) {
			throw new ElementConversionException(type, e);
		}
	}

	protected abstract T convertInternal(List<B> buttons);
}
