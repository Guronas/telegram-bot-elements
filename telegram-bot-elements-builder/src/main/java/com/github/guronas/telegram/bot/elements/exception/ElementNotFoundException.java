package com.github.guronas.telegram.bot.elements.exception;

public class ElementNotFoundException extends TelegramElementsException {

	public ElementNotFoundException(String elementName, String groupName) {
		super("Element with name %s was not found in the element group %s", elementName, groupName);
	}
}
