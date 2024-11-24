package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.model.ElementType;
import com.github.guronas.telegram.bot.elements.model.KeyboardButtonElement;
import com.github.guronas.telegram.bot.elements.model.KeyboardRowElement;

import java.util.List;

public class KeyboardRowConverter extends AbstractKeyboardRowConverter<KeyboardRowElement, KeyboardButtonElement> {

	public KeyboardRowConverter() {
		super(ElementType.KEYBOARD_ROW, ElementType.KEYBOARD_BUTTON);
	}

	@Override
	protected KeyboardRowElement convertInternal(List<KeyboardButtonElement> buttons) {
		return new KeyboardRowElement(buttons);
	}
}
