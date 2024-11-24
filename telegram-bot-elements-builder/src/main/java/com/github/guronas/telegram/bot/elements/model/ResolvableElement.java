package com.github.guronas.telegram.bot.elements.model;

import com.github.guronas.telegram.bot.elements.exception.TelegramElementsRuntimeException;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;


public record ResolvableElement<T>(ElementType type,
								   String elementReference,
								   Function<String, Optional<Element<T>>> elementResolver) implements Element<T> {

	@Override
	public T build(Map<String, String> params) {
		return elementResolver.apply(elementReference)
				.orElseThrow(() -> new TelegramElementsRuntimeException("Unable to resolve element with element reference %s and type %s", elementReference, type))
				.build(params);
	}
}
