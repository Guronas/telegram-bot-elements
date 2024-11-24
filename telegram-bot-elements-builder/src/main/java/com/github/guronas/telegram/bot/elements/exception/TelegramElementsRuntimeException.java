package com.github.guronas.telegram.bot.elements.exception;

public class TelegramElementsRuntimeException extends RuntimeException {
	public TelegramElementsRuntimeException() {
	}

	public TelegramElementsRuntimeException(String message, Object... params) {
		super(message.formatted(params));
	}

	public TelegramElementsRuntimeException(String message, Throwable cause, Object... params) {
		super(message.formatted(params), cause);
	}

	public TelegramElementsRuntimeException(Throwable cause) {
		super(cause);
	}
}
