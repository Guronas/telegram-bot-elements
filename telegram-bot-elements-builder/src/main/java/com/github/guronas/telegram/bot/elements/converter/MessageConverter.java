package com.github.guronas.telegram.bot.elements.converter;

import com.github.guronas.telegram.bot.elements.exception.ElementConversionException;
import com.github.guronas.telegram.bot.elements.model.*;
import com.github.guronas.telegram.bot.elements.parser.ElementFactory;
import com.github.guronas.telegram.bot.elements.util.ElementUtils;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Map;
import java.util.Objects;

public class MessageConverter implements MapConverter<MessageElement> {
	public static final String ACTION_KEY = "action";
	public static final String REPLY_KEYBOARD_KEY = "replyKeyboard";

	@Override
	public MessageElement convert(ElementFactory elementFactory, Map<?, ?> rawContent) throws ElementConversionException {
		try {
			Object rawAction = ElementUtils.getObjectFromMapIfExists(rawContent, ACTION_KEY);
			if (Objects.nonNull(rawAction) && !(rawAction instanceof String)) {
				throw new ElementConversionException("Unexpected type of message action: %s", rawAction.getClass());
			}

			MessageAction action = rawAction == null ? MessageAction.CREATE : MessageAction.valueOf(((String) rawAction).toUpperCase());
			if (action == MessageAction.DELETE) {
				return new MessageElement(MessageAction.DELETE, null, null);
			} else {
				return convertWithChildren(elementFactory, action, rawContent);
			}
		} catch (Exception e) {
			throw new ElementConversionException(ElementType.MESSAGE, e);
		}
	}

	private MessageElement convertWithChildren(ElementFactory elementFactory, MessageAction action, Map<?, ?> rawContent)
			throws ElementConversionException {
		Object rawText = ElementUtils.getObjectFromMapIfExists(rawContent, ElementType.TEXT.getTypeName());
		TextElement text = elementFactory.createElement(ElementType.TEXT, rawText);
		return new MessageElement(action, text, convertReplyKeyBoardIfExist(elementFactory, rawContent));
	}

	private Element<? extends ReplyKeyboard> convertReplyKeyBoardIfExist(ElementFactory elementFactory, Map<?, ?> rawContent)
			throws ElementConversionException {
		Object rawReplyKeyboard = ElementUtils.getObjectFromMapIfExists(rawContent, REPLY_KEYBOARD_KEY);
		if (Objects.isNull(rawReplyKeyboard) || !(rawReplyKeyboard instanceof Map<?, ?> replyKeyboard)) {
			return null;
		}

		return createReplyKeyBoardElement(elementFactory, replyKeyboard);
	}

	private Element<? extends ReplyKeyboard> createReplyKeyBoardElement(ElementFactory elementFactory, Map<?, ?> rawReplyKeyboard)
			throws ElementConversionException {
		int replyKeyboardSize = rawReplyKeyboard.size();
		if (replyKeyboardSize > 1) {
			throw new ElementConversionException("Expected 1 reply keyboard for the message but was %s", replyKeyboardSize);
		}

		Map.Entry<?, ?> replyKeyboard = rawReplyKeyboard.entrySet()
				.iterator()
				.next();

		String keyboardTypeName = (String) replyKeyboard.getKey();
		ElementType keyboardType = ElementType.getType(keyboardTypeName);
		return elementFactory.createElement(keyboardType, replyKeyboard.getValue());
	}
}