package com.github.guronas.telegram.bot.elements.model;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.List;
import java.util.Map;

public record InlineKeyboardElement(
		List<? extends Element<InlineKeyboardRow>> rows) implements ReplyKeyboardElement<InlineKeyboardMarkup> {

	@Override
	public ElementType type() {
		return ElementType.INLINE_KEYBOARD;
	}

	@Override
	public InlineKeyboardMarkup build(Map<String, String> params) {
		return new InlineKeyboardMarkup(buildRows(params));
	}

	private List<InlineKeyboardRow> buildRows(Map<String, String> params) {
		return rows.stream()
				.map(element -> element.build(params))
				.toList();
	}
}
