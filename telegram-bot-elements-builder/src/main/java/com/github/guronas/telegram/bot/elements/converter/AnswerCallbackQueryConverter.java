package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.exception.ElementConversionException;
import com.github.guronas.telegram.bot.elements.model.AnswerCallbackQueryElement;
import com.github.guronas.telegram.bot.elements.model.BooleanElement;
import com.github.guronas.telegram.bot.elements.model.ElementType;
import com.github.guronas.telegram.bot.elements.model.TextElement;
import com.github.guronas.telegram.bot.elements.parser.ElementFactory;
import com.github.guronas.telegram.bot.elements.util.ElementUtils;

import java.util.Map;

public class AnswerCallbackQueryConverter implements MapConverter<AnswerCallbackQueryElement> {
	public static final String SHOW_ALERT_KEY = "showAlert";

	@Override
	public AnswerCallbackQueryElement convert(ElementFactory elementFactory, Map<?, ?> rawContent) throws ElementConversionException {
		Object rawText = ElementUtils.getObjectFromMapIfExists(rawContent, ElementType.TEXT.getTypeName());
		TextElement text = elementFactory.createElement(ElementType.TEXT, rawText);
		Object rawShowAlertFlag = ElementUtils.getObjectFromMapIfExists(rawContent, SHOW_ALERT_KEY);
		BooleanElement showAlertFlag = elementFactory.createElement(ElementType.BOOLEAN, rawShowAlertFlag);

		return new AnswerCallbackQueryElement(text, showAlertFlag);
	}
}
