package com.github.guronas.telegram.bot.elements.model;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.Collections;
import java.util.List;

import static com.github.guronas.telegram.bot.elements.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InlineKeyboardRowElementTest {

	@Test
	void getType() {
		InlineKeyboardRowElement element = new InlineKeyboardRowElement(Collections.emptyList());
		assertEquals(ElementType.INLINE_KEYBOARD_ROW, element.type());
	}

	@Test
	void build() {
		List<InlineKeyboardButtonElement> buttons = List.of(
				getTestInlineKeyboardButtonElement(TEST_TEXT, TEST_BUTTON_1),
				getTestInlineKeyboardButtonElement(TEST_TEXT, TEST_BUTTON_2));
		InlineKeyboardRowElement keyboardRowElement = new InlineKeyboardRowElement(buttons);

		InlineKeyboardRow keyboardRow = keyboardRowElement.build(getTestParams());

		InlineKeyboardButton button1 = keyboardRow.get(0);
		InlineKeyboardButton button2 = keyboardRow.get(1);
		assertEquals(TEST_TEXT, button1.getText());
		assertEquals(TEST_BUTTON_1, button1.getCallbackData());
		assertEquals(TEST_TEXT, button2.getText());
		assertEquals(TEST_BUTTON_2, button2.getCallbackData());
	}

	@Test
	void buildEmptyRow() {
		InlineKeyboardRowElement keyboardRowElement = new InlineKeyboardRowElement(Collections.emptyList());

		InlineKeyboardRow keyboardRow = keyboardRowElement.build(getTestParams());

		assertTrue(keyboardRow.isEmpty());
	}
}
