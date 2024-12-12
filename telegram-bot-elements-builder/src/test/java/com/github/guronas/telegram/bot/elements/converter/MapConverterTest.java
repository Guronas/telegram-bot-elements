package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.core.ElementFactory;
import com.github.guronas.telegram.bot.elements.model.Element;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import static com.github.guronas.telegram.bot.elements.TestUtils.TEST_CHAT_ID;
import static com.github.guronas.telegram.bot.elements.TestUtils.TEST_TEXT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapConverterTest {
	private final MapConverterStub mapConverterStub = new MapConverterStub();

	@ParameterizedTest
	@MethodSource("provideMapConverterParams")
	public void canConvert(Object value, boolean expectedResult) {
		boolean result = mapConverterStub.canConvert(value);

		assertEquals(expectedResult, result);
	}

	private static Stream<Arguments> provideMapConverterParams() {
		return Stream.of(
				Arguments.of(Collections.emptyMap(), true),
				Arguments.of(Collections.singletonMap(TEST_TEXT, TEST_CHAT_ID), true),
				Arguments.of(TEST_TEXT, false),
				Arguments.of(null, false)
		);
	}

	private static class MapConverterStub implements MapConverter<Element<?>> {

		@Override
		public Element<?> convert(ElementFactory elementFactory, Map<?, ?> rawContent) {
			return null;
		}
	}
}
