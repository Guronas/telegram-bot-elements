package com.github.guronas.telegram.bot.elements.exception;

import com.github.guronas.telegram.bot.elements.model.ElementType;

public class ElementConversionException extends TelegramElementsException {

	public static final String ELEMENT_CONVERSION_EXCEPTION_MESSAGE_TEMPLATE = "Failed to convert element with type %s";

	public ElementConversionException(ElementType type) {
		super(ELEMENT_CONVERSION_EXCEPTION_MESSAGE_TEMPLATE, type);
	}

	public ElementConversionException(ElementType type, Throwable cause) {
		super(ELEMENT_CONVERSION_EXCEPTION_MESSAGE_TEMPLATE, cause, type);
	}

	public ElementConversionException(String message, Object... params) {
		super(message, params);
	}
}
