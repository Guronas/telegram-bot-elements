package com.github.guronas.telegram.bot.elements.model;

import com.github.guronas.telegram.bot.elements.parameter.DynamicParameters;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.*;
import java.util.function.Function;

public record InlineKeyboardElement(
		List<InlineKeyboardRowElement> rows,
		String dynamicRowsId,
		Function<DynamicParameters, List<InlineKeyboardRowElement>> dynamicRowsBuilder)
		implements ReplyKeyboardElement<InlineKeyboardRowElement, InlineKeyboardRow, InlineKeyboardMarkup> {

	@Override
	public ElementType type() {
		return ElementType.INLINE_KEYBOARD;
	}

	@Override
	public InlineKeyboardMarkup build(Map<String, String> params, Map<String, DynamicParameters> dynamicParameters) {
		return new InlineKeyboardMarkup(buildRows(params, dynamicParameters));
	}
}
