package com.github.guronas.telegram.bot.elements.parameter;

import com.github.guronas.telegram.bot.elements.model.ElementType;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class InlineKeyboardButtonParameters extends DynamicParameters {

	public InlineKeyboardButtonParameters(List<Map<String, String>> parameters) {
		super(parameters);
	}

	public void add(String text, String callBackData) {
		add(Map.of(
				ElementType.TEXT.getTypeName(), text,
				ElementType.CALLBACK_DATA.getTypeName(), callBackData
		));
	}
}
