package com.github.guronas.telegram.bot.elements;

import com.github.guronas.telegram.bot.elements.model.*;

import java.util.List;
import java.util.Map;

import static com.github.guronas.telegram.bot.elements.model.AnswerCallbackQueryElement.CALLBACK_QUERY_ID_KEY;
import static com.github.guronas.telegram.bot.elements.model.Element.CHAT_ID_KEY;
import static com.github.guronas.telegram.bot.elements.model.MessageElement.MESSAGE_ID_KEY;

public class TestUtils {
	public static final String TEST_TEXT = "testText";
	public static final String TEST_CALLBACK_QUERY_ID = "testCallbackQueryId";
	public static final String TEST_CHAT_ID = "testChatId";
	public static final String TEST_MESSAGE_ID = "12345";
	public static final String TEST_BUTTON_1 = "TestButton1";
	public static final String TEST_BUTTON_2 = "TestButton2";

	public static TextElement getTestTextElement() {
		return new TextElement(TEST_TEXT, null);
	}

	public static TextElement getTestTextElement(String text) {
		return new TextElement(text, null);
	}

	public static Map<String, String> getTestParams() {
		return Map.of(
				CHAT_ID_KEY, TEST_CHAT_ID,
				MESSAGE_ID_KEY, TEST_MESSAGE_ID,
				CALLBACK_QUERY_ID_KEY, TEST_CALLBACK_QUERY_ID,
				"testParamKey", "testParamValue"
		);
	}

	public static InlineKeyboardRowElement getTestInlineKeyboardRowElement() {
		return new InlineKeyboardRowElement(List.of(
				getTestInlineKeyboardButtonElement(TEST_TEXT, TEST_BUTTON_1),
				getTestInlineKeyboardButtonElement(TEST_TEXT, TEST_BUTTON_2)
		));
	}

	public static InlineKeyboardButtonElement getTestInlineKeyboardButtonElement(String text, String callbackData) {
		return new InlineKeyboardButtonElement(getTestTextElement(text), getTestTextElement(callbackData));
	}

	public static KeyboardRowElement getTestKeyboardRowElement() {
		return new KeyboardRowElement(List.of(
				getTestKeyboardButtonElement(TEST_BUTTON_1),
				getTestKeyboardButtonElement(TEST_BUTTON_2)
		));
	}

	public static KeyboardButtonElement getTestKeyboardButtonElement(String text) {
		return new KeyboardButtonElement(getTestTextElement(text));
	}
}
