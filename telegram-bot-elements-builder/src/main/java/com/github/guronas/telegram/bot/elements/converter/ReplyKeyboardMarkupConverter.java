package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.model.*;
import com.github.guronas.telegram.bot.elements.parameter.DynamicParameters;
import com.github.guronas.telegram.bot.elements.core.ElementFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ReplyKeyboardMarkupConverter extends AbstractKeyboardConverter<ReplyKeyboardMarkupElement, KeyboardRowElement> {
	public static final String RESIZE_KEYBOARD_KEY = "resizeKeyboard";

	public ReplyKeyboardMarkupConverter() {
		super(ElementType.REPLY_KEYBOARD_MARKUP, ElementType.KEYBOARD_ROW);
	}

	@Override
	protected ReplyKeyboardMarkupElement convertInternal(ElementFactory elementFactory,
														 List<KeyboardRowElement> rows,
														 String dynamicParameterId,
														 Function<DynamicParameters, List<KeyboardRowElement>> dynamicRowsBuilder,
														 Map<?, ?> rawContent) {
		BooleanElement resizeKeyboardFlag = elementFactory.createElement(ElementType.BOOLEAN, rawContent.get(RESIZE_KEYBOARD_KEY));
		return new ReplyKeyboardMarkupElement(rows, resizeKeyboardFlag, dynamicParameterId, dynamicRowsBuilder);
	}

	@Override
	protected Function<DynamicParameters, List<KeyboardRowElement>> createDynamicRowsBuilder(Integer maxButtonsPerRow, ElementFactory elementFactory) {
		return dynamicParameters -> {
			List<KeyboardButtonElement> buttons = dynamicParameters.getParameters()
					.stream()
					.map(params -> createDynamicButton(elementFactory, params))
					.toList();

			List<KeyboardRowElement> rows = new ArrayList<>();
			int totalSize = buttons.size();
			for (int i = 0; i < totalSize; i += maxButtonsPerRow) {
				List<KeyboardButtonElement> buttonsChunk = buttons.subList(i, Math.min(totalSize, i + maxButtonsPerRow));
				rows.add(new KeyboardRowElement(buttonsChunk));
			}

			return rows;
		};
	}

	private KeyboardButtonElement createDynamicButton(ElementFactory elementFactory, Map<String, String> parameters) {
		return elementFactory.createElement(ElementType.KEYBOARD_BUTTON, parameters);
	}
}
