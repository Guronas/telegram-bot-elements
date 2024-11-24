package com.github.guronas.telegram.bot.elements.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.github.guronas.telegram.bot.elements.TestUtils.TEST_TEXT;
import static com.github.guronas.telegram.bot.elements.TestUtils.getTestParams;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextElementTest {

	@Test
	void getType() {
		TextElement element = new TextElement(TEST_TEXT, null);
		assertEquals(ElementType.TEXT, element.type());
	}

	@Test
	void buildPlainText() {
		TextElement textElement = new TextElement(TEST_TEXT, null);

		String text = textElement.build(getTestParams());

		assertEquals(TEST_TEXT, text);
	}

	@Test
	void buildTextWithPlaceholders() {
		final String paramReference = "paramReference";
		final String externalReference = "externalReference";
		final String testTextWithPlaceholders = "%s ${%s} ${%s} test".formatted(TEST_TEXT, paramReference, externalReference);
		final String resolvableText1 = "testResolvableText1";
		final String resolvableText2 = "testResolvableText2";
		Function<String, Optional<Element<String>>> externalTextResolver = reference ->
		{
			if (paramReference.equals(reference) || externalReference.equals(reference)) {
				return Optional.of(new TextElement(resolvableText2, null));
			}
			return Optional.empty();
		};

		TextElement textElement = new TextElement(testTextWithPlaceholders, externalTextResolver);

		Map<String, String> testParams = new HashMap<>(getTestParams());
		testParams.put(paramReference, resolvableText1);
		String text = textElement.build(testParams);

		assertEquals("testText testResolvableText1 testResolvableText2 test", text);
	}
}
