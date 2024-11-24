package com.github.guronas.telegram.bot.elements.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ElementTypeTest {

	@ParameterizedTest
	@EnumSource(value = ElementType.class)
	public void getTypeByName(ElementType expectedType) {
		String typeName = expectedType.getTypeName();
		ElementType type = ElementType.getType(typeName);

		assertEquals(expectedType, type);
	}

	@Test
	public void getNotExistingType() {
		assertThrows(IllegalArgumentException.class, () -> ElementType.getType("notExistingType"));
	}
}
