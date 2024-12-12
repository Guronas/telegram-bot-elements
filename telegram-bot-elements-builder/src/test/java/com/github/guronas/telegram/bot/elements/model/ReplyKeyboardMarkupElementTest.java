package com.github.guronas.telegram.bot.elements.model;

import com.github.guronas.telegram.bot.elements.parameter.DynamicParameters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.github.guronas.telegram.bot.elements.TestUtils.*;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReplyKeyboardMarkupElementTest {

	@Test
	void getType() {
		ReplyKeyboardMarkupElement element = new ReplyKeyboardMarkupElement(Collections.emptyList(), new BooleanElement(true),
				TEST_DYNAMIC_ROWS_ID, dynamicParameters -> Collections.emptyList());
		assertEquals(ElementType.REPLY_KEYBOARD_MARKUP, element.type());
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void build(boolean resizeKeyboard) {
		DynamicParameters testDynamicParameters = getTestDynamicParameters();
		ReplyKeyboardMarkupElement replyKeyboardMarkupElement = getKeyboardElement(resizeKeyboard, testDynamicParameters);

		ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupElement.build(getTestParams(), Map.of(TEST_DYNAMIC_ROWS_ID, testDynamicParameters));

		assertEquals(resizeKeyboard, keyboardMarkup.getResizeKeyboard());

		List<KeyboardRow> rows = keyboardMarkup.getKeyboard();
		KeyboardRow row1 = rows.get(0);
		KeyboardRow row2 = rows.get(1);
		KeyboardRow row3 = rows.get(2);
		KeyboardRow row4 = rows.get(3);

		KeyboardButton button1 = row3.get(0);
		KeyboardButton button2 = row3.get(1);
		assertEquals(TEST_BUTTON_1, button1.getText());
		assertEquals(TEST_BUTTON_2, button2.getText());

		button1 = row4.get(0);
		button2 = row4.get(1);
		assertEquals(TEST_BUTTON_1, button1.getText());
		assertEquals(TEST_BUTTON_2, button2.getText());

		KeyboardButton button3 = row1.get(0);
		KeyboardButton button4 = row1.get(1);
		KeyboardButton button5 = row2.get(0);
		assertEquals(TEST_BUTTON_3, button3.getText());
		assertEquals(TEST_BUTTON_4, button4.getText());
		assertEquals(TEST_BUTTON_5, button5.getText());
	}

	@Test
	void buildWithEmptyRows() {
		ReplyKeyboardMarkupElement element = new ReplyKeyboardMarkupElement(Collections.emptyList(), new BooleanElement(true),
				TEST_DYNAMIC_ROWS_ID, dynamicParameters -> Collections.emptyList());

		ReplyKeyboardMarkup keyboardMarkup = element.build(getTestParams(), Map.of(TEST_DYNAMIC_ROWS_ID, getTestDynamicParameters()));

		assertTrue(keyboardMarkup.getKeyboard().isEmpty());
	}

	private static ReplyKeyboardMarkupElement getKeyboardElement(boolean resizeKeyboard, DynamicParameters testDynamicParameters) {
		List<KeyboardRowElement> rowElements = List.of(getTestKeyboardRowElement(), getTestKeyboardRowElement());
		Function<DynamicParameters, List<KeyboardRowElement>> dynamicRowsBuilder = dynamicParameters -> {
			List<Map<String, String>> parameters = dynamicParameters.getParameters();
			if (parameters.equals(testDynamicParameters.getParameters())) {
				return List.of(
						new KeyboardRowElement(List.of(
								getTestKeyboardButtonElement(TEST_BUTTON_3),
								getTestKeyboardButtonElement(TEST_BUTTON_4)
						)),
						new KeyboardRowElement(Collections.singletonList(getTestKeyboardButtonElement(TEST_BUTTON_5)))
				);
			}
			return emptyList();
		};
		return new ReplyKeyboardMarkupElement(rowElements, new BooleanElement(resizeKeyboard), TEST_DYNAMIC_ROWS_ID, dynamicRowsBuilder);
	}
}
