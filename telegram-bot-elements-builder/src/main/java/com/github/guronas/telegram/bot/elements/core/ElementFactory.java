package com.github.guronas.telegram.bot.elements.core;

import com.github.guronas.telegram.bot.elements.TelegramElementRegistry;
import com.github.guronas.telegram.bot.elements.converter.ElementConverter;
import com.github.guronas.telegram.bot.elements.converter.ResolvableElementConverter;
import com.github.guronas.telegram.bot.elements.exception.ElementConversionException;
import com.github.guronas.telegram.bot.elements.exception.TelegramElementsRuntimeException;
import com.github.guronas.telegram.bot.elements.model.Element;
import com.github.guronas.telegram.bot.elements.model.ElementType;
import lombok.RequiredArgsConstructor;

import java.util.EnumMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class ElementFactory {
	public static final Pattern REFERENCE_PLACEHOLDER_PATTERN = Pattern.compile("^\\$\\{(.+?)}$");

	private final String groupName;
	private final TelegramElementRegistry elementRegistry;
	private final EnumMap<ElementType, ElementConverter<?, ?>> elementConverters;

	@SuppressWarnings("unchecked")
	public <T extends Element<?>, C> T createElement(ElementType type, C rawData) {
		try {
			ElementConverter<T, C> converter = (ElementConverter<T, C>) elementConverters.get(type);
			if (!converter.canConvert(rawData)) {
				if (rawData instanceof String rawReference) {
					return (T) new ResolvableElementConverter<>(type)
							.convert(this, getReference(rawReference));
				}
				throw new ElementConversionException("Unable to convert object [%s] with converter [%s]", rawData, converter);
			}
			return converter.convert(this, rawData);
		} catch (ElementConversionException e) {
			throw new TelegramElementsRuntimeException("Failed to convert element with type [%s]: %s", e, type, rawData);
		}
	}

	@SuppressWarnings("unchecked")
	public <E> Function<String, Optional<Element<E>>> createElementResolver(ElementType type) {
		return elementReference -> elementRegistry.getElement(groupName, elementReference)
				.filter(element -> element.type() == type)
				.map(element -> (Element<E>) element);
	}

	private String getReference(String rawData) {
		Matcher matcher = REFERENCE_PLACEHOLDER_PATTERN.matcher(rawData);
		if (!matcher.matches()) {
			throw new TelegramElementsRuntimeException("Failed to resolve element reference from raw data: %s", rawData);
		}

		return matcher.group(1);
	}
}
