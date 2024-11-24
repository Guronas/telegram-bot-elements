package com.github.guronas.telegram.bot.elements.model;

import com.github.guronas.telegram.bot.elements.exception.MandatoryParameterException;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;

import java.util.Collections;
import java.util.Map;

import static com.github.guronas.telegram.bot.elements.TestUtils.*;
import static com.github.guronas.telegram.bot.elements.model.AnswerCallbackQueryElement.CALLBACK_QUERY_ID_KEY;
import static org.junit.jupiter.api.Assertions.*;

public class AnswerCallbackQueryElementTest {

	@Test
	void getType() {
		TextElement textElement = getTestTextElement();
		AnswerCallbackQueryElement element = new AnswerCallbackQueryElement(textElement, null);
		assertEquals(ElementType.ANSWER_CALLBACK_QUERY, element.type());
	}

	@Test
	void buildWithValidParameters() {
		TextElement textElement = getTestTextElement();
		BooleanElement showAlertElement = new BooleanElement(true);
		Map<String, String> params = getTestParams();

		AnswerCallbackQueryElement element = new AnswerCallbackQueryElement(textElement, showAlertElement);
		AnswerCallbackQuery result = element.build(params);

		assertNotNull(result);
		assertEquals(TEST_TEXT, result.getText());
		assertEquals(TEST_CALLBACK_QUERY_ID, result.getCallbackQueryId());
		assertTrue(result.getShowAlert());
	}

	@Test
	void buildWithMissingCallbackQueryId() {
		TextElement textElement = getTestTextElement();
		Map<String, String> params = Collections.emptyMap();

		AnswerCallbackQueryElement element = new AnswerCallbackQueryElement(textElement, new BooleanElement(true));

		Exception exception = assertThrows(MandatoryParameterException.class, () -> element.build(params));
		assertTrue(exception.getMessage().contains(CALLBACK_QUERY_ID_KEY));
	}
}
