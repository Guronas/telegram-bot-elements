package com.github.guronas.telegram.bot.elements.core;

import com.github.guronas.telegram.bot.elements.TelegramElementRegistry;
import com.github.guronas.telegram.bot.elements.exception.ElementNotFoundException;
import com.github.guronas.telegram.bot.elements.exception.TelegramElementsRuntimeException;
import com.github.guronas.telegram.bot.elements.group.ElementGroup;
import com.github.guronas.telegram.bot.elements.model.Element;
import com.github.guronas.telegram.bot.elements.parameter.DynamicParameters;
import com.github.guronas.telegram.bot.elements.parameter.Parameter;
import com.github.guronas.telegram.bot.elements.parameter.Parameters;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;

import java.util.*;

@Setter
@RequiredArgsConstructor
public class DefaultElementRegistry implements TelegramElementRegistry {
	private Map<String, ElementGroup> elementGroups;

	@Override
	public Optional<Element<?>> getElement(String groupName, String elementName) {
		return Optional.ofNullable(elementGroups.get(groupName))
				.map(ElementGroup::elements)
				.map(elements -> elements.get(elementName));
	}

	@Override
	public BotApiMethod<?> buildBotApiMethod(String groupName, String elementName, List<Parameter> params) throws ElementNotFoundException {
		return buildElement(groupName, elementName, params, BotApiMethod.class);
	}

	@Override
	public BotApiObject buildBotApiObject(String groupName, String elementName, List<Parameter> params) throws ElementNotFoundException {
		return buildElement(groupName, elementName, params, BotApiObject.class);
	}

	@Override
	public <T> T buildElement(String groupName,
							  String elementName,
							  List<Parameter> params,
							  Class<T> elementType) throws ElementNotFoundException {
		return buildElement(groupName, elementName, Parameters.buildParameters(params), elementType);
	}

	@Override
	public BotApiMethod<?> buildBotApiMethod(String groupName, String elementName, Map<String, String> params) throws ElementNotFoundException {
		return buildElement(groupName, elementName, params, BotApiMethod.class);
	}

	@Override
	public BotApiObject buildBotApiObject(String groupName, String elementName, Map<String, String> params) throws ElementNotFoundException {
		return buildElement(groupName, elementName, params, BotApiObject.class);
	}

	@Override
	public BotApiMethod<?> buildBotApiMethod(String groupName,
											 String elementName,
											 Map<String, String> params,
											 Map<String, DynamicParameters> dynamicParameters) throws ElementNotFoundException {
		return buildElement(groupName, elementName, params, dynamicParameters, BotApiMethod.class);
	}

	@Override
	public BotApiObject buildBotApiObject(String groupName,
										  String elementName,
										  Map<String, String> params,
										  Map<String, DynamicParameters> dynamicParameters) throws ElementNotFoundException {
		return buildElement(groupName, elementName, params, dynamicParameters, BotApiObject.class);
	}

	@Override
	public <T> T buildElement(String groupName,
							  String elementName,
							  Map<String, String> params,
							  Class<T> elementType) throws ElementNotFoundException {
		return buildElement(groupName, elementName, params, Collections.emptyMap(), elementType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T buildElement(String groupName,
							  String elementName,
							  Map<String, String> params,
							  Map<String, DynamicParameters> dynamicParameters,
							  Class<T> elementType) throws ElementNotFoundException {
		Object builtObject = Optional.ofNullable(elementGroups.get(groupName))
				.map(ElementGroup::elements)
				.map(elements -> elements.get(elementName))
				.map(element -> element.build(params, dynamicParameters))
				.orElseThrow(() -> new ElementNotFoundException(elementName, groupName));

		if (!elementType.isInstance(builtObject)) {
			throw new TelegramElementsRuntimeException("Element %s with type [%s] is not an instance of [%s]", elementName, builtObject.getClass(), elementType);
		}

		return ((T) builtObject);
	}
}
