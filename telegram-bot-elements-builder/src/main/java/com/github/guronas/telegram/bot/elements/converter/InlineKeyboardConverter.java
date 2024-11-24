package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.model.*;
import com.github.guronas.telegram.bot.elements.parser.ElementFactory;

import java.util.List;
import java.util.Map;

public class InlineKeyboardConverter extends AbstractKeyboardConverter<InlineKeyboardElement, InlineKeyboardRowElement> {

	public InlineKeyboardConverter() {
		super(ElementType.INLINE_KEYBOARD, ElementType.INLINE_KEYBOARD_ROW);
	}

	@Override
	protected InlineKeyboardElement convertInternal(ElementFactory elementFactory, List<InlineKeyboardRowElement> rows, Map<?, ?> rawContent) {
		return new InlineKeyboardElement(rows);
	}
}
