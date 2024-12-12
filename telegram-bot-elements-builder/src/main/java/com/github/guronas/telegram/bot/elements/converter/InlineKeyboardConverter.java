package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.parameter.DynamicParameters;
import com.github.guronas.telegram.bot.elements.model.*;
import com.github.guronas.telegram.bot.elements.core.ElementFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class InlineKeyboardConverter extends AbstractKeyboardConverter<InlineKeyboardElement, InlineKeyboardRowElement> {

	public InlineKeyboardConverter() {
		super(ElementType.INLINE_KEYBOARD, ElementType.INLINE_KEYBOARD_ROW);
	}

	@Override
	protected InlineKeyboardElement convertInternal(ElementFactory elementFactory,
													List<InlineKeyboardRowElement> rows,
													String dynamicParameterId,
													Function<DynamicParameters, List<InlineKeyboardRowElement>> dynamicRowsBuilder,
													Map<?, ?> rawContent) {
		return new InlineKeyboardElement(rows, dynamicParameterId, dynamicRowsBuilder);
	}

	@Override
	protected Function<DynamicParameters, List<InlineKeyboardRowElement>> createDynamicRowsBuilder(Integer maxButtonsPerRow, ElementFactory elementFactory) {
		return dynamicParameters -> {
			List<InlineKeyboardButtonElement> buttons = dynamicParameters.getParameters()
					.stream()
					.map(params -> createDynamicButton(elementFactory, params))
					.toList();

			List<InlineKeyboardRowElement> rows = new ArrayList<>();
			int totalSize = buttons.size();
			for (int i = 0; i < totalSize; i += maxButtonsPerRow) {
				List<InlineKeyboardButtonElement> buttonsChunk = buttons.subList(i, Math.min(totalSize, i + maxButtonsPerRow));
				rows.add(new InlineKeyboardRowElement(buttonsChunk));
			}

			return rows;
		};
	}

	private InlineKeyboardButtonElement createDynamicButton(ElementFactory elementFactory, Map<String, String> parameters) {
		return elementFactory.createElement(ElementType.INLINE_KEYBOARD_BUTTON, parameters);
	}
}
