package com.github.guronas.telegram.bot.elements.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.stream.Stream;

import static com.github.guronas.telegram.bot.elements.TestUtils.*;
import static com.github.guronas.telegram.bot.elements.model.Element.EMPTY_TEXT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeyboardButtonElementTest {

	@Test
	void getType() {
		KeyboardButtonElement element = new KeyboardButtonElement(getTestTextElement());
		assertEquals(ElementType.KEYBOARD_BUTTON, element.type());
	}

	@ParameterizedTest
	@MethodSource("provideKeyboardButtonTestParams")
	void build(TextElement textElement, String expectedText) {
		KeyboardButtonElement keyboardButtonElement = new KeyboardButtonElement(textElement);

		KeyboardButton keyboardButton = keyboardButtonElement.build(getTestParams());

		assertEquals(expectedText, keyboardButton.getText());
	}

	private static Stream<Arguments> provideKeyboardButtonTestParams() {
		return Stream.of(
				Arguments.of(getTestTextElement(), TEST_TEXT),
				Arguments.of(null, EMPTY_TEXT)
		);
	}
}
