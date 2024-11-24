package com.github.guronas.telegram.bot.elements.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Objects;
import java.util.stream.Stream;

import static com.github.guronas.telegram.bot.elements.TestUtils.*;
import static com.github.guronas.telegram.bot.elements.model.Element.EMPTY_TEXT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InlineKeyboardButtonElementTest {

	@Test
	void getType() {
		InlineKeyboardButtonElement element = new InlineKeyboardButtonElement(getTestTextElement(), getTestTextElement());
		assertEquals(ElementType.INLINE_KEYBOARD_BUTTON, element.type());
	}

	@ParameterizedTest
	@MethodSource("provideInlineButtonTestParams")
	void buildInlineButton(TextElement text, TextElement callbackData) {
		String expectedText = Objects.isNull(text) ? EMPTY_TEXT : TEST_TEXT;
		String expectedCallBackData = Objects.isNull(callbackData) ? EMPTY_TEXT : TEST_TEXT;

		InlineKeyboardButtonElement element = new InlineKeyboardButtonElement(text, callbackData);
		InlineKeyboardButton keyboardButton = element.build(getTestParams());

		assertEquals(expectedText, keyboardButton.getText());
		assertEquals(expectedCallBackData, keyboardButton.getCallbackData());
	}

	private static Stream<Arguments> provideInlineButtonTestParams() {
		return Stream.of(
				Arguments.of(getTestTextElement(), getTestTextElement()),
				Arguments.of(null, getTestTextElement()),
				Arguments.of(getTestTextElement(), null),
				Arguments.of(null, null)
		);
	}
}
