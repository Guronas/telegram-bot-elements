package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.core.ElementFactory;
import com.github.guronas.telegram.bot.elements.model.Element;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.github.guronas.telegram.bot.elements.TestUtils.TEST_TEXT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListConverterTest {
	private final ListConverterStub listConverterStub = new ListConverterStub();

	@ParameterizedTest
	@MethodSource("provideListConverterParams")
	public void canConvert(Object value, boolean expectedResult) {
		boolean result = listConverterStub.canConvert(value);

		assertEquals(expectedResult, result);
	}

	private static Stream<Arguments> provideListConverterParams() {
		return Stream.of(
				Arguments.of(Collections.emptyList(), true),
				Arguments.of(Collections.singletonList(TEST_TEXT), true),
				Arguments.of(TEST_TEXT, false),
				Arguments.of(null, false)
		);
	}

	private static class ListConverterStub implements ListConverter<Element<?>> {

		@Override
		public Element<?> convert(ElementFactory elementFactory, List<?> rawContent) {
			return null;
		}
	}
}
