package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.parameter.DynamicParameters;
import com.github.guronas.telegram.bot.elements.exception.ElementConversionException;
import com.github.guronas.telegram.bot.elements.model.Element;
import com.github.guronas.telegram.bot.elements.model.ElementType;
import com.github.guronas.telegram.bot.elements.core.ElementFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.*;
import java.util.function.Function;

import static com.github.guronas.telegram.bot.elements.util.ElementUtils.getObjectFromMapIfExists;

@RequiredArgsConstructor
public abstract class AbstractKeyboardConverter<T extends Element<? extends ReplyKeyboard>, R extends Element<?>> implements MapConverter<T> {
	public static final String ROWS_KEY = "rows";
	public static final String DYNAMIC_ROWS_KEY = "dynamicRows";
	public static final String DYNAMIC_ROWS_ID_KEY = "id";
	public static final String MAX_BUTTONS_PER_ROW_KEY = "maxButtonsPerRow";

	private final ElementType type;
	private final ElementType rowType;

	@Override
	public T convert(ElementFactory elementFactory, Map<?, ?> rawContent) throws ElementConversionException {
		try {
			List<R> rows = Optional.ofNullable(getObjectFromMapIfExists(rawContent, ROWS_KEY, List.class))
					.map(r -> ((List<?>) r))
					.orElse(Collections.emptyList())
					.stream()
					.filter(rootRowElement -> rootRowElement instanceof Map<?, ?>)
					.map(rootRowElement -> ((Map<?, ?>) rootRowElement))
					.map(rootRowElement ->
							getObjectFromMapIfExists(rootRowElement, rowType.getTypeName(), Object.class))
					.map(row -> elementFactory.<R, Object>createElement(rowType, row))
					.toList();

			String dynamicParameterId = null;
			Function<DynamicParameters, List<R>> dynamicRowsBuilder = null;
			Map<?, ?> rawDynamicRows = getObjectFromMapIfExists(rawContent, DYNAMIC_ROWS_KEY, Map.class);
			if (Objects.nonNull(rawDynamicRows) && !rawDynamicRows.isEmpty()) {
				dynamicParameterId = getObjectFromMapIfExists(rawDynamicRows, DYNAMIC_ROWS_ID_KEY, String.class);
				if (!StringUtils.isBlank(dynamicParameterId)) {
					Integer maxButtonsPerRow = Optional.ofNullable(getObjectFromMapIfExists(rawDynamicRows, MAX_BUTTONS_PER_ROW_KEY, Integer.class))
							.filter(num -> num > 0)
							.orElse(1);
					dynamicRowsBuilder = createDynamicRowsBuilder(maxButtonsPerRow, elementFactory);
				}
			}

			return convertInternal(elementFactory, rows, dynamicParameterId, dynamicRowsBuilder, rawContent);
		} catch (Exception e) {
			throw new ElementConversionException(type, e);
		}
	}

	protected abstract T convertInternal(ElementFactory elementFactory,
										 List<R> rows,
										 String dynamicParameterId,
										 Function<DynamicParameters, List<R>> dynamicRowsBuilder,
										 Map<?, ?> rawContent);

	protected abstract Function<DynamicParameters, List<R>> createDynamicRowsBuilder(Integer maxButtonsPerRow, ElementFactory elementFactory);
}
