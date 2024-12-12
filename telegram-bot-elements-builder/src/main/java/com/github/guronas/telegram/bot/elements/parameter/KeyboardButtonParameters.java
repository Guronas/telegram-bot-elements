package com.github.guronas.telegram.bot.elements.parameter;

import com.github.guronas.telegram.bot.elements.model.ElementType;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class KeyboardButtonParameters extends DynamicParameters {

	public KeyboardButtonParameters(List<Map<String, String>> parameters) {
		super(parameters);
	}

	public void add(String text) {
		add(Collections.singletonMap(ElementType.TEXT.getTypeName(), text));
	}
}
