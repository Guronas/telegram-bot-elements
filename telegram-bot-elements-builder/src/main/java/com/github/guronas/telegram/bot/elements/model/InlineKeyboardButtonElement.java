package com.github.guronas.telegram.bot.elements.model;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Map;

public record InlineKeyboardButtonElement(
		Element<String> text,
		Element<String> callbackData
) implements ApiObjectElement<InlineKeyboardButton> {

	@Override
	public ElementType type() {
		return ElementType.INLINE_KEYBOARD_BUTTON;
	}

	@Override
	public InlineKeyboardButton build(Map<String, String> params) {
		return InlineKeyboardButton.builder()
				.text(Element.buildTextOrGetEmpty(text, params))
				.callbackData(Element.buildTextOrGetEmpty(callbackData, params))
				.build();
	}
}
