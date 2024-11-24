package com.github.guronas.telegram.bot.elements.config;

import com.github.guronas.telegram.bot.elements.ElementLoader;
import com.github.guronas.telegram.bot.elements.TelegramElementRegistry;
import com.github.guronas.telegram.bot.elements.exception.ElementNotFoundException;
import com.github.guronas.telegram.bot.elements.group.ElementGroup;
import com.github.guronas.telegram.bot.elements.model.Element;
import com.github.guronas.telegram.bot.elements.parser.DefaultElementRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class SpringElementRegistry implements TelegramElementRegistry {
	private final TelegramElementRegistry elementRegistryDelegate = new DefaultElementRegistry();
	private final ElementLoader elementLoader;

	@PostConstruct
	void init() {
		elementLoader.loadElements(elementRegistryDelegate);
	}

	@Override
	public Optional<Element<?>> getElement(String groupName, String elementName) {
		return elementRegistryDelegate.getElement(groupName, elementName);
	}

	@Override
	public BotApiMethod<?> buildBotApiMethod(String groupName, String elementName, Map<String, String> params) throws ElementNotFoundException {
		return elementRegistryDelegate.buildBotApiMethod(groupName, elementName, params);
	}

	@Override
	public BotApiObject buildBotApiObject(String groupName, String elementName, Map<String, String> params) throws ElementNotFoundException {
		return elementRegistryDelegate.buildBotApiObject(groupName, elementName, params);
	}

	@Override
	public <T> T buildElement(String groupName, String elementName, Map<String, String> params, Class<T> elementType) throws ElementNotFoundException {
		return elementRegistryDelegate.buildElement(groupName, elementName, params, elementType);
	}

	@Override
	public void setElementGroups(Map<String, ElementGroup> elementGroups) {
		elementRegistryDelegate.setElementGroups(elementGroups);
	}
}
