package com.github.guronas.telegram.bot.elements.model;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.Collections;
import java.util.List;

import static com.github.guronas.telegram.bot.elements.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InlineKeyboardElementTest {

	@Test
	void getType() {
		InlineKeyboardElement element = new InlineKeyboardElement(Collections.emptyList());
		assertEquals(ElementType.INLINE_KEYBOARD, element.type());
	}

	@Test
	void build() {
		List<InlineKeyboardRowElement> rows = List.of(getTestInlineKeyboardRowElement(), getTestInlineKeyboardRowElement());
		InlineKeyboardElement element = new InlineKeyboardElement(rows);

		InlineKeyboardMarkup keyboardMarkup = element.build(getTestParams());

		keyboardMarkup.getKeyboard().forEach(this::validateRow);
	}

	@Test
	void buildWithEmptyRows() {
		InlineKeyboardElement element = new InlineKeyboardElement(Collections.emptyList());

		InlineKeyboardMarkup keyboardMarkup = element.build(getTestParams());

		assertTrue(keyboardMarkup.getKeyboard().isEmpty());
	}

	private void validateRow(InlineKeyboardRow row) {
		InlineKeyboardButton button1 = row.get(0);
		InlineKeyboardButton button2 = row.get(1);

		assertEquals(TEST_TEXT, button1.getText());
		assertEquals(TEST_BUTTON_1, button1.getCallbackData());
		assertEquals(TEST_TEXT, button2.getText());
		assertEquals(TEST_BUTTON_2, button2.getCallbackData());
	}
}
