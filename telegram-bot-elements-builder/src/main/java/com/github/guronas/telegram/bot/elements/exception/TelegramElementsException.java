package com.github.guronas.telegram.bot.elements.exception;

public class TelegramElementsException extends Exception {

	public TelegramElementsException() {
	}

	public TelegramElementsException(String message, Object... params) {
		super(message.formatted(params));
	}

	public TelegramElementsException(String message, Throwable cause, Object... params) {
		super(message.formatted(params), cause);
	}

	public TelegramElementsException(Throwable cause) {
		super(cause);
	}
}
