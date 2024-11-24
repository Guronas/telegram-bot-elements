package com.github.guronas.telegram.bot.elements.model;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public record InlineKeyboardRowElement(
		List<? extends Element<InlineKeyboardButton>> buttons) implements Element<InlineKeyboardRow> {

	@Override
	public ElementType type() {
		return ElementType.INLINE_KEYBOARD_ROW;
	}

	@Override
	public InlineKeyboardRow build(Map<String, String> params) {
		if (Objects.isNull(buttons) || buttons.isEmpty()) {
			return new InlineKeyboardRow();
		} else {
			return new InlineKeyboardRow(buildButtons(params));
		}
	}

	private List<InlineKeyboardButton> buildButtons(Map<String, String> params) {
		return buttons.stream()
				.map(button -> button.build(params))
				.toList();
	}
}
