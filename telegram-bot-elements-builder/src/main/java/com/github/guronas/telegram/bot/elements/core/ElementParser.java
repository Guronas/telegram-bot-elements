package com.github.guronas.telegram.bot.elements.core;

import com.github.guronas.telegram.bot.elements.TelegramElementRegistry;
import com.github.guronas.telegram.bot.elements.converter.*;
import com.github.guronas.telegram.bot.elements.exception.TelegramElementsRuntimeException;
import com.github.guronas.telegram.bot.elements.model.Element;
import com.github.guronas.telegram.bot.elements.model.ElementType;
import com.github.guronas.telegram.bot.elements.group.ElementGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.github.guronas.telegram.bot.elements.model.ElementType.*;
import static com.github.guronas.telegram.bot.elements.util.ElementUtils.*;

@Slf4j
public class ElementParser {
	public static final String ELEMENT_ID_KEY = "id";
	private static final String ROOT_ELEMENT = "rootElement";

	private final EnumMap<ElementType, ElementConverter<?, ?>> elementConverters = new EnumMap<>(ElementType.class);

	//TODO refactor
	{
		TextConverter textConverter = new TextConverter();
		InlineKeyboardConverter inlineKeyboardConverter = new InlineKeyboardConverter();
		ReplyKeyboardMarkupConverter replyKeyboardMarkupConverter = new ReplyKeyboardMarkupConverter();
		elementConverters.put(MESSAGE, new MessageConverter());
		elementConverters.put(INLINE_KEYBOARD, inlineKeyboardConverter);
		elementConverters.put(INLINE_KEYBOARD_ROW, new InlineKeyboardRowConverter());
		elementConverters.put(INLINE_KEYBOARD_BUTTON, new InlineKeyboardButtonConverter());
		elementConverters.put(TEXT, textConverter);
		elementConverters.put(CALLBACK_DATA, textConverter);
		elementConverters.put(REPLY_KEYBOARD_MARKUP, replyKeyboardMarkupConverter);
		elementConverters.put(KEYBOARD_ROW, new KeyboardRowConverter());
		elementConverters.put(KEYBOARD_BUTTON, new KeyboardButtonConverter());
		elementConverters.put(BOOLEAN, new BooleanConverter());
		elementConverters.put(ANSWER_CALLBACK_QUERY, new AnswerCallbackQueryConverter());
	}

	public void parse(TelegramElementRegistry elementRegistry, Map<String, Object> rawElementGroup) {
		try {

			Map<String, ElementGroup> elementGroups = rawElementGroup.entrySet()
					.stream()
					.filter(group -> group.getValue() instanceof List<?>)
					.map(group -> createElementGroup(new ElementFactory(group.getKey(), elementRegistry, elementConverters),
							group.getKey(), (List<?>) group.getValue()))
					.filter(Objects::nonNull)
					.collect(Collectors.toMap(ElementGroup::name, Function.identity()));

			elementRegistry.setElementGroups(elementGroups);
		} catch (Exception e) {
			throw new TelegramElementsRuntimeException("Failed to parse element groups", e);
		}
	}

	private ElementGroup createElementGroup(ElementFactory elementFactory, String name, List<?> rawElements) {
		try {
			Map<String, Element<?>> elements = rawElements.stream()
					.filter(element -> element instanceof Map)
					.map(element -> ((Map<?, ?>) element))
					.collect(Collectors.toMap(this::getElementId, element -> createElement(elementFactory, element)));
			return new ElementGroup(name, elements);
		} catch (Exception e) {
			log.warn("Failed to parse element group {}, it will be ignored", name, e);
			return null;
		}
	}

	private String getElementId(Map<?, ?> rawElement) {
		Object rawElementId = getObjectFromMapOrElseThrow(rawElement, ELEMENT_ID_KEY, ROOT_ELEMENT);
		if (!(rawElementId instanceof String elementId)) {
			throw new TelegramElementsRuntimeException("Unexpected type of element id: %s", rawElementId.getClass());
		}
		return elementId;
	}

	private Element<?> createElement(ElementFactory elementFactory, Map<?, ?> rawElement) {
		try {
			rawElement.remove(ELEMENT_ID_KEY);
			if (rawElement.size() != 1) {
				throw new TelegramElementsRuntimeException("More then one root element is presented: %s", rawElement.keySet());
			}

			Map.Entry<?, ?> rawRootElement = rawElement.entrySet()
					.iterator()
					.next();
			if (!(rawRootElement.getKey() instanceof String typeName)) {
				throw new TelegramElementsRuntimeException("Invalid element type [%s] for root element: %s", rawRootElement.getKey().getClass(), rawElement);
			}

			return elementFactory.createElement(ElementType.getType(typeName), rawRootElement.getValue());
		} catch (Exception e) {
			throw new TelegramElementsRuntimeException("Failed to parse element, it will be ignored: %s", e, rawElement);
		}
	}
}
