package com.github.guronas.telegram.bot.elements.model;

import org.junit.jupiter.api.Test;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;
import java.util.List;

import static com.github.guronas.telegram.bot.elements.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KeyboardRowElementTest {

	@Test
	void getType() {
		KeyboardRowElement element = new KeyboardRowElement(Collections.emptyList());
		assertEquals(ElementType.KEYBOARD_ROW, element.type());
	}

	@Test
	void build() {
		List<KeyboardButtonElement> buttons = List.of(
				getTestKeyboardButtonElement(TEST_BUTTON_1),
				getTestKeyboardButtonElement(TEST_BUTTON_2));
		KeyboardRowElement keyboardRowElement = new KeyboardRowElement(buttons);

		KeyboardRow keyboardRow = keyboardRowElement.build(getTestParams());

		KeyboardButton button1 = keyboardRow.get(0);
		KeyboardButton button2 = keyboardRow.get(1);
		assertEquals(TEST_BUTTON_1, button1.getText());
		assertEquals(TEST_BUTTON_2, button2.getText());
	}

	@Test
	void buildEmptyRow() {
		KeyboardRowElement keyboardRowElement = new KeyboardRowElement(Collections.emptyList());

		KeyboardRow keyboardRow = keyboardRowElement.build(getTestParams());

		assertTrue(keyboardRow.isEmpty());
	}
}
