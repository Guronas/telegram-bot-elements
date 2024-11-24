package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.exception.ElementConversionException;
import com.github.guronas.telegram.bot.elements.model.Element;
import com.github.guronas.telegram.bot.elements.model.ElementType;
import com.github.guronas.telegram.bot.elements.parser.ElementFactory;
import com.github.guronas.telegram.bot.elements.util.ElementUtils;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractKeyboardConverter<T extends Element<? extends ReplyKeyboard>, R extends Element<?>> implements MapConverter<T> {
	public static final String ROWS_KEY = "rows";

	private final ElementType type;
	private final ElementType rowType;

	@Override
	public T convert(ElementFactory elementFactory, Map<?, ?> rawContent) throws ElementConversionException {
		try {
			Object rawRows = ElementUtils.getObjectFromMapIfExists(rawContent, ROWS_KEY);
			List<R> rows = Optional.ofNullable(rawRows)
					.filter(r -> r instanceof List<?>)
					.map(r -> ((List<?>) r))
					.orElse(Collections.emptyList())
					.stream()
					.filter(rootRowElement -> rootRowElement instanceof Map<?, ?>)
					.map(rootRowElement -> ((Map<?, ?>) rootRowElement))
					.map(rootRowElement -> ElementUtils.getObjectFromMapIfExists(rootRowElement, rowType.getTypeName()))
					.map(row -> elementFactory.<R, Object>createElement(rowType, row))
					.toList();
			if (rows.isEmpty()) {
				throw new ElementConversionException("Rows for keyboards cannot be empty");
			}

			return convertInternal(elementFactory, rows, rawContent);
		} catch (Exception e) {
			throw new ElementConversionException(type, e);
		}
	}

	protected abstract T convertInternal(ElementFactory elementFactory, List<R> rows, Map<?, ?> rawContent);
}
