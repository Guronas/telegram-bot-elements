package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.core.ElementFactory;
import com.github.guronas.telegram.bot.elements.exception.ElementConversionException;
import com.github.guronas.telegram.bot.elements.model.Element;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.stream.Stream;

import static com.github.guronas.telegram.bot.elements.TestUtils.TEST_TEXT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NullableConverterTest {
	private final NullableConverterStub nullableConverterStub = new NullableConverterStub();

	@ParameterizedTest
	@MethodSource("provideNullableConverterParams")
	public void canConvert(Object value, boolean expectedResult) {
		boolean result = nullableConverterStub.canConvert(value);

		assertEquals(expectedResult, result);
	}

	private static Stream<Arguments> provideNullableConverterParams() {
		return Stream.of(
				Arguments.of(Collections.emptyList(), true),
				Arguments.of(TEST_TEXT, false),
				Arguments.of(null, true)
		);
	}

	private static class NullableConverterStub implements NullableConverter<Element<?>, Object> {

		@Override
		public boolean internalCanConvert(Object rawContent) {
			return !(rawContent instanceof String);
		}

		@Override
		public Element<?> convert(ElementFactory elementFactory, Object rawContent) throws ElementConversionException {
			return null;
		}
	}
}
