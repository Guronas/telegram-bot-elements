package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.model.BooleanElement;
import com.github.guronas.telegram.bot.elements.model.ElementType;
import com.github.guronas.telegram.bot.elements.model.KeyboardRowElement;
import com.github.guronas.telegram.bot.elements.model.ReplyKeyboardMarkupElement;
import com.github.guronas.telegram.bot.elements.parser.ElementFactory;
import com.github.guronas.telegram.bot.elements.util.ElementUtils;

import java.util.List;
import java.util.Map;

public class ReplyKeyboardMarkupConverter extends AbstractKeyboardConverter<ReplyKeyboardMarkupElement, KeyboardRowElement> {
	public static final String RESIZE_KEYBOARD_KEY = "resizeKeyboard";

	public ReplyKeyboardMarkupConverter() {
		super(ElementType.REPLY_KEYBOARD_MARKUP, ElementType.KEYBOARD_ROW);
	}

	@Override
	protected ReplyKeyboardMarkupElement convertInternal(ElementFactory elementFactory, List<KeyboardRowElement> rows, Map<?, ?> rawContent) {
		Object rawResizeKeyboardFlag = ElementUtils.getObjectFromMapIfExists(rawContent, RESIZE_KEYBOARD_KEY);
		BooleanElement resizeKeyboardFlag = elementFactory.createElement(ElementType.BOOLEAN, rawResizeKeyboardFlag);
		return new ReplyKeyboardMarkupElement(rows, resizeKeyboardFlag);
	}
}
