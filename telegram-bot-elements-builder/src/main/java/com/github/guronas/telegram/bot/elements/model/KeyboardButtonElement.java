package com.github.guronas.telegram.bot.elements.model;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.Map;

public record KeyboardButtonElement(Element<String> text) implements Element<KeyboardButton> {

	@Override
	public ElementType type() {
		return ElementType.KEYBOARD_BUTTON;
	}

	@Override
	public KeyboardButton build(Map<String, String> params) {
		return KeyboardButton.builder()
				.text(Element.buildTextOrGetEmpty(text, params))
				.build();
	}
}
