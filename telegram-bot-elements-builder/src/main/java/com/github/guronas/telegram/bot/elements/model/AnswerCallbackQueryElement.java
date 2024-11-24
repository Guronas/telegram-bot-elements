package com.github.guronas.telegram.bot.elements.model;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;

import java.util.Map;

public record AnswerCallbackQueryElement(TextElement text,
										 BooleanElement showAlert) implements ApiMethodElement<AnswerCallbackQuery> {
	public static String CALLBACK_QUERY_ID_KEY = "callbackQueryId";

	@Override
	public ElementType type() {
		return ElementType.ANSWER_CALLBACK_QUERY;
	}

	@Override
	public AnswerCallbackQuery build(Map<String, String> params) {
		return AnswerCallbackQuery.builder()
				.text(Element.buildTextOrGetEmpty(text, params))
				.showAlert(showAlert.build(params))
				.callbackQueryId(Element.getParameterOrThrow(params, CALLBACK_QUERY_ID_KEY))
				.build();
	}
}
