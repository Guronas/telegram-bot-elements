package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.model.ElementType;
import com.github.guronas.telegram.bot.elements.model.InlineKeyboardButtonElement;
import com.github.guronas.telegram.bot.elements.model.InlineKeyboardRowElement;

import java.util.List;

public class InlineKeyboardRowConverter extends AbstractKeyboardRowConverter<InlineKeyboardRowElement, InlineKeyboardButtonElement> {

	public InlineKeyboardRowConverter() {
		super(ElementType.INLINE_KEYBOARD_ROW, ElementType.INLINE_KEYBOARD_BUTTON);
	}

	@Override
	protected InlineKeyboardRowElement convertInternal(List<InlineKeyboardButtonElement> buttons) {
		return new InlineKeyboardRowElement(buttons);
	}
}
