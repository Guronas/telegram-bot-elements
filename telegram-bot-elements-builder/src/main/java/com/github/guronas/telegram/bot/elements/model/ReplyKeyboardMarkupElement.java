package com.github.guronas.telegram.bot.elements.model;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
import java.util.Map;

public record ReplyKeyboardMarkupElement(
		List<? extends Element<KeyboardRow>> rows,
		BooleanElement resizeKeyboard) implements ReplyKeyboardElement<ReplyKeyboardMarkup> {

	@Override
	public ElementType type() {
		return ElementType.REPLY_KEYBOARD_MARKUP;
	}

	@Override
	public ReplyKeyboardMarkup build(Map<String, String> params) {
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(buildRows(params));
		replyKeyboardMarkup.setResizeKeyboard(resizeKeyboard.build(params));
		return replyKeyboardMarkup;
	}

	private List<KeyboardRow> buildRows(Map<String, String> params) {
		return rows.stream()
				.map(element -> element.build(params))
				.toList();
	}
}
