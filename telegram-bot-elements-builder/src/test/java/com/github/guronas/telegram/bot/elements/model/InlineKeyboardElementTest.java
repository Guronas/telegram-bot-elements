package com.github.guronas.telegram.bot.elements.model;

import com.github.guronas.telegram.bot.elements.parameter.DynamicParameters;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.github.guronas.telegram.bot.elements.TestUtils.*;
import static java.util.Collections.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InlineKeyboardElementTest {

	@Test
	void getType() {
		InlineKeyboardElement element = new InlineKeyboardElement(emptyList(),
				TEST_DYNAMIC_ROWS_ID, dynamicParameters -> emptyList());
		assertEquals(ElementType.INLINE_KEYBOARD, element.type());
	}

	@Test
	void build() {
		DynamicParameters testDynamicParameters = getTestDynamicParameters();
		InlineKeyboardElement element = getInlineKeyboardElement(testDynamicParameters);

		InlineKeyboardMarkup keyboardMarkup = element.build(getTestParams(), singletonMap(TEST_DYNAMIC_ROWS_ID, testDynamicParameters));

		List<InlineKeyboardRow> rows = keyboardMarkup.getKeyboard();
		InlineKeyboardRow row1 = rows.get(0);
		InlineKeyboardRow row2 = rows.get(1);
		InlineKeyboardRow row3 = rows.get(2);
		InlineKeyboardRow row4 = rows.get(3);

		InlineKeyboardButton button1 = row3.get(0);
		InlineKeyboardButton button2 = row3.get(1);
		assertEquals(TEST_BUTTON_1, button1.getText());
		assertEquals(TEST_BUTTON_1, button1.getCallbackData());
		assertEquals(TEST_BUTTON_2, button2.getText());
		assertEquals(TEST_BUTTON_2, button2.getCallbackData());

		button1 = row4.get(0);
		button2 = row4.get(1);
		assertEquals(TEST_BUTTON_1, button1.getText());
		assertEquals(TEST_BUTTON_1, button1.getCallbackData());
		assertEquals(TEST_BUTTON_2, button2.getText());
		assertEquals(TEST_BUTTON_2, button2.getCallbackData());

		InlineKeyboardButton button3 = row1.get(0);
		InlineKeyboardButton button4 = row1.get(1);
		InlineKeyboardButton button5 = row2.get(0);
		assertEquals(TEST_BUTTON_3, button3.getText());
		assertEquals(TEST_BUTTON_3, button3.getCallbackData());
		assertEquals(TEST_BUTTON_4, button4.getText());
		assertEquals(TEST_BUTTON_4, button4.getCallbackData());
		assertEquals(TEST_BUTTON_5, button5.getText());
		assertEquals(TEST_BUTTON_5, button5.getCallbackData());
	}

	@Test
	void buildWithEmptyRows() {
		InlineKeyboardElement element = new InlineKeyboardElement(emptyList(),
				TEST_DYNAMIC_ROWS_ID, dynamicParameters -> emptyList());

		InlineKeyboardMarkup keyboardMarkup = element.build(getTestParams(), emptyMap());

		assertTrue(keyboardMarkup.getKeyboard().isEmpty());
	}

	private static InlineKeyboardElement getInlineKeyboardElement(DynamicParameters testDynamicParameters) {
		List<InlineKeyboardRowElement> rowElements = List.of(getTestInlineKeyboardRowElement(), getTestInlineKeyboardRowElement());
		Function<DynamicParameters, List<InlineKeyboardRowElement>> dynamicRowsBuilder = dynamicParameters -> {
			List<Map<String, String>> parameters = dynamicParameters.getParameters();
			if (parameters.equals(testDynamicParameters.getParameters())) {
				return List.of(
						new InlineKeyboardRowElement(List.of(
								getTestInlineKeyboardButtonElement(TEST_BUTTON_3, TEST_BUTTON_3),
								getTestInlineKeyboardButtonElement(TEST_BUTTON_4, TEST_BUTTON_4)
						)),
						new InlineKeyboardRowElement(Collections.singletonList(getTestInlineKeyboardButtonElement(TEST_BUTTON_5, TEST_BUTTON_5)))
				);
			}
			return emptyList();
		};
		return new InlineKeyboardElement(rowElements, TEST_DYNAMIC_ROWS_ID, dynamicRowsBuilder);
	}
}
