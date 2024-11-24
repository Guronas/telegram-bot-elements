package com.github.guronas.telegram.bot.elements.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum ElementType {
	MESSAGE("message"),
	INLINE_KEYBOARD("inlineKeyboard"),
	INLINE_KEYBOARD_ROW("inlineKeyboardRow"),
	INLINE_KEYBOARD_BUTTON("inlineKeyboardButton"),
	TEXT("text"),
	CALLBACK_DATA("callbackData"),
	REPLY_KEYBOARD_MARKUP("replyKeyboardMarkup"),
	KEYBOARD_ROW("keyboardRow"),
	KEYBOARD_BUTTON("keyboardButton"),
	ANSWER_CALLBACK_QUERY("answerCallbackQuery"),
	BOOLEAN("boolean");

	private static final Map<String, ElementType> typeByNames = Arrays.stream(ElementType.values())
			.collect(Collectors.toMap(ElementType::getTypeName, Function.identity()));
	private final String typeName;

	public static ElementType getType(String name) {
		return Optional.ofNullable(typeByNames.get(name))
				.orElseThrow(() -> new IllegalArgumentException("Unknown type: %s".formatted(name)));
	}
}
