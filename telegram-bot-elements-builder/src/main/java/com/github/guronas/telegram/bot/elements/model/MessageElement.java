package com.github.guronas.telegram.bot.elements.model;

import com.github.guronas.telegram.bot.elements.exception.TelegramElementsRuntimeException;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Map;
import java.util.Objects;

import static com.github.guronas.telegram.bot.elements.util.ElementUtils.buildIfExist;

public record MessageElement(
		MessageAction action,
		TextElement text,
		Element<? extends ReplyKeyboard> replyKeyboardElement
) implements ApiMethodElement<BotApiMethod<?>> {
	public static final String MESSAGE_ID_KEY = "messageId";

	@Override
	public ElementType type() {
		return ElementType.MESSAGE;
	}

	@Override
	public BotApiMethod<?> build(Map<String, String> params) {
		return switch (action) {
			case DELETE -> new DeleteMessage(params.get(CHAT_ID_KEY), Integer.valueOf(params.get(MESSAGE_ID_KEY)));
			case EDIT -> EditMessageText.builder()
					.chatId(Element.getParameterOrThrow(params, CHAT_ID_KEY))
					.messageId(Integer.valueOf(Element.getParameterOrThrow(params, MESSAGE_ID_KEY)))
					.text(Element.buildTextOrGetEmpty(text, params))
					.replyMarkup(buildEditedInlineKeyboard(replyKeyboardElement, params))
					.build();
			case CREATE -> SendMessage.builder()
					.chatId(Element.getParameterOrThrow(params, CHAT_ID_KEY))
					.text(Element.buildTextOrGetEmpty(text, params))
					.replyMarkup(buildIfExist(replyKeyboardElement, params))
					.build();
		};
	}

	private InlineKeyboardMarkup buildEditedInlineKeyboard(Element<? extends ReplyKeyboard> replyKeyboardElement, Map<String, String> params) {
		if (Objects.isNull(replyKeyboardElement)) {
			return null;
		}
		ReplyKeyboard replyKeyboard = replyKeyboardElement.build(params);
		if (!(replyKeyboard instanceof InlineKeyboardMarkup inlineKeyboardMarkup)) {
			throw new TelegramElementsRuntimeException("Invalid reply markup type [%s] for EditMessageText bot API method", replyKeyboard.getClass());
		}
		return inlineKeyboardMarkup;
	}
}
