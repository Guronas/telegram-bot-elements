package com.github.guronas.telegram.bot.elements.model;

import com.github.guronas.telegram.bot.elements.parameter.DynamicParameters;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public record ReplyKeyboardMarkupElement(
		List<KeyboardRowElement> rows,
		BooleanElement resizeKeyboard,
		String dynamicRowsId,
		Function<DynamicParameters, List<KeyboardRowElement>> dynamicRowsBuilder)
		implements ReplyKeyboardElement<KeyboardRowElement, KeyboardRow, ReplyKeyboardMarkup> {

	@Override
	public ElementType type() {
		return ElementType.REPLY_KEYBOARD_MARKUP;
	}

	@Override
	public ReplyKeyboardMarkup build(Map<String, String> params, Map<String, DynamicParameters> dynamicParameters) {
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(buildRows(params, dynamicParameters));
		replyKeyboardMarkup.setResizeKeyboard(resizeKeyboard.build(params));
		return replyKeyboardMarkup;
	}
}
