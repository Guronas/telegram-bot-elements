package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.exception.ElementConversionException;
import com.github.guronas.telegram.bot.elements.model.ElementType;
import com.github.guronas.telegram.bot.elements.model.KeyboardButtonElement;
import com.github.guronas.telegram.bot.elements.model.TextElement;
import com.github.guronas.telegram.bot.elements.core.ElementFactory;
import com.github.guronas.telegram.bot.elements.util.ElementUtils;

import java.util.Map;

public class KeyboardButtonConverter implements MapConverter<KeyboardButtonElement> {

	@Override
	public KeyboardButtonElement convert(ElementFactory elementFactory, Map<?, ?> rawContent) throws ElementConversionException {
		try {
			Object rawText = rawContent.get(ElementType.TEXT.getTypeName());
			TextElement text = elementFactory.createElement(ElementType.TEXT, rawText);

			return new KeyboardButtonElement(text);
		} catch (Exception e) {
			throw new ElementConversionException(ElementType.KEYBOARD_BUTTON, e);
		}
	}
}
