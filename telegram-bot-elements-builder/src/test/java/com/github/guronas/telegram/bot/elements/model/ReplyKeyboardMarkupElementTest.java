package com.github.guronas.telegram.bot.elements.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;
import java.util.List;

import static com.github.guronas.telegram.bot.elements.TestUtils.*;
import static com.github.guronas.telegram.bot.elements.TestUtils.TEST_BUTTON_2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReplyKeyboardMarkupElementTest {

	@Test
	void getType() {
		ReplyKeyboardMarkupElement element = new ReplyKeyboardMarkupElement(Collections.emptyList(), new BooleanElement(true));
		assertEquals(ElementType.REPLY_KEYBOARD_MARKUP, element.type());
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void build(boolean resizeKeyboard) {
		List<KeyboardRowElement> rows = List.of(getTestKeyboardRowElement(), getTestKeyboardRowElement());
		ReplyKeyboardMarkupElement replyKeyboardMarkupElement = new ReplyKeyboardMarkupElement(rows, new BooleanElement(resizeKeyboard));

		ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupElement.build(getTestParams());

		keyboardMarkup.getKeyboard().forEach(this::validateRow);
		assertEquals(resizeKeyboard, keyboardMarkup.getResizeKeyboard());
	}

	@Test
	void buildWithEmptyRows() {
		ReplyKeyboardMarkupElement element = new ReplyKeyboardMarkupElement(Collections.emptyList(), new BooleanElement(true));

		ReplyKeyboardMarkup keyboardMarkup = element.build(getTestParams());

		assertTrue(keyboardMarkup.getKeyboard().isEmpty());
	}

	private void validateRow(KeyboardRow row) {
		KeyboardButton button1 = row.get(0);
		KeyboardButton button2 = row.get(1);

		assertEquals(TEST_BUTTON_1, button1.getText());
		assertEquals(TEST_BUTTON_2, button2.getText());
	}
}
