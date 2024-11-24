package com.github.guronas.telegram.bot.elements.model;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public record KeyboardRowElement(List<? extends Element<KeyboardButton>> buttons) implements Element<KeyboardRow> {

	@Override
	public ElementType type() {
		return ElementType.KEYBOARD_ROW;
	}

	@Override
	public KeyboardRow build(Map<String, String> params) {
		if (Objects.isNull(buttons) || buttons.isEmpty()) {
			return new KeyboardRow();
		} else {
			return new KeyboardRow(buildButtons(params));
		}
	}

	private List<KeyboardButton> buildButtons(Map<String, String> params) {
		return buttons.stream()
				.map(button -> button.build(params))
				.toList();
	}
}
