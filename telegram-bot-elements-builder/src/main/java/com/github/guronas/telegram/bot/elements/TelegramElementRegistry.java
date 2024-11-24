package com.github.guronas.telegram.bot.elements;

import com.github.guronas.telegram.bot.elements.exception.ElementNotFoundException;
import com.github.guronas.telegram.bot.elements.group.ElementGroup;
import com.github.guronas.telegram.bot.elements.model.Element;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;

import java.util.Map;
import java.util.Optional;

public interface TelegramElementRegistry {

	Optional<Element<?>> getElement(String groupName, String elementName);

	BotApiMethod<?> buildBotApiMethod(String groupName,
									  String elementName,
									  Map<String, String> params) throws ElementNotFoundException;

	BotApiObject buildBotApiObject(String groupName,
								   String elementName,
								   Map<String, String> params) throws ElementNotFoundException;

	<T> T buildElement(String groupName,
					   String elementName,
					   Map<String, String> params,
					   Class<T> elementType) throws ElementNotFoundException;

	//TODO refactor setting elements logic
	void setElementGroups(Map<String, ElementGroup> elementGroups);
}