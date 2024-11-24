package com.github.guronas.telegram.bot.elements.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BooleanElementTest {

	@Test
	void getType() {
		BooleanElement element = new BooleanElement(true);
		assertEquals(ElementType.BOOLEAN, element.type());
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void build(boolean value) {
		BooleanElement element = new BooleanElement(value);
		assertEquals(value, element.build(Collections.emptyMap()));
	}
}
