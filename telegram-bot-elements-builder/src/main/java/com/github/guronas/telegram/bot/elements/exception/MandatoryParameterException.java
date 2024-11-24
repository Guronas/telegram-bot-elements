package com.github.guronas.telegram.bot.elements.exception;

public class MandatoryParameterException extends TelegramElementsRuntimeException {

	public MandatoryParameterException(String parameterName) {
		super("Unable to find mandatory parameter to build element: %s", parameterName);
	}
}
