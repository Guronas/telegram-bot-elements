package com.github.guronas.telegram.bot.elements.util;

import com.github.guronas.telegram.bot.elements.exception.KeyNotFoundException;
import com.github.guronas.telegram.bot.elements.model.Element;
import com.github.guronas.telegram.bot.elements.parameter.DynamicParameters;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ElementUtilsTest {
	public static final String TEST_KEY = "testKey";
	public static final String TEST_VALUE1 = "testValue1";
	public static final Integer TEST_VALUE2 = 42;
	public static final String TEST_PARENT_NAME = "testParentName";
	public static final String TEST_DEFAULT_VALUE = "testDefault";

	@Test
	void getObjectFromMap() {
		Object result = ElementUtils.getObjectFromMapOrElseThrow(Map.of(TEST_KEY, TEST_VALUE1), TEST_KEY, TEST_PARENT_NAME);

		assertEquals(TEST_VALUE1, result);
	}

	@Test
	void getObjectFromMapAndThrow() {
		assertThrows(KeyNotFoundException.class, () ->
				ElementUtils.getObjectFromMapOrElseThrow(emptyMap(), TEST_KEY, TEST_PARENT_NAME)
		);
	}

	@Test
	void getObjectFromMapIfExists() {
		Integer result = ElementUtils.getObjectFromMapIfExists(Map.of(TEST_KEY, TEST_VALUE2), TEST_KEY, Integer.class);

		assertEquals(TEST_VALUE2, result);
	}

	@Test
	void getObjectFromMapIfExistsWithInvalidType() {
		String result = ElementUtils.getObjectFromMapIfExists(Map.of(TEST_KEY, TEST_VALUE2), TEST_KEY, String.class);

		assertNull(result);
	}

	@Test
	void getObjectFromMapIfExistsKeyDoesNotExist() {
		Integer result = ElementUtils.getObjectFromMapIfExists(emptyMap(), TEST_KEY, Integer.class);

		assertNull(result);
	}

	@Test
	void getOrDefault() {
		String result = ElementUtils.getOrDefault(TEST_VALUE1, TEST_DEFAULT_VALUE);

		assertEquals(TEST_VALUE1, result);
	}

	@Test
	void getOrDefaultNullValue() {
		String result = ElementUtils.getOrDefault(null, TEST_DEFAULT_VALUE);

		assertEquals(TEST_DEFAULT_VALUE, result);
	}

	@Test
	@SuppressWarnings("unchecked")
	void buildIfExist() {
		Element<String> element = mock(Element.class);
		Map<String, String> params = emptyMap();
		Map<String, DynamicParameters> dynamicParameters = emptyMap();
		when(element.build(params, dynamicParameters)).thenReturn(TEST_VALUE1);

		String result = ElementUtils.buildIfExist(element, params, dynamicParameters);

		assertEquals(TEST_VALUE1, result);
		verify(element).build(params, dynamicParameters);
	}

	@Test
	void buildIfExistElementIsNull() {
		String result = ElementUtils.buildIfExist(null, emptyMap(), emptyMap());

		assertNull(result);
	}
}
