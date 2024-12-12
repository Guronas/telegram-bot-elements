package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.exception.ElementConversionException;
import com.github.guronas.telegram.bot.elements.model.ElementType;
import com.github.guronas.telegram.bot.elements.model.InlineKeyboardButtonElement;
import com.github.guronas.telegram.bot.elements.model.TextElement;
import com.github.guronas.telegram.bot.elements.core.ElementFactory;
import com.github.guronas.telegram.bot.elements.util.ElementUtils;

import java.util.Map;

public class InlineKeyboardButtonConverter implements MapConverter<InlineKeyboardButtonElement> {

	@Override
	public InlineKeyboardButtonElement convert(ElementFactory elementFactory, Map<?, ?> rawContent) throws ElementConversionException {
		try {
			Object rawText = rawContent.get(ElementType.TEXT.getTypeName());
			TextElement text = elementFactory.createElement(ElementType.TEXT, rawText);
			Object rawCallbackData = rawContent.get(ElementType.CALLBACK_DATA.getTypeName());
			TextElement callbackData = elementFactory.createElement(ElementType.CALLBACK_DATA, rawCallbackData);

			return new InlineKeyboardButtonElement(text, callbackData);
		} catch (Exception e) {
			throw new ElementConversionException(ElementType.INLINE_KEYBOARD_BUTTON, e);
		}
	}
}
