package com.github.guronas.telegram.bot.elements.model;

import com.github.guronas.telegram.bot.elements.exception.TelegramElementsRuntimeException;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.github.guronas.telegram.bot.elements.TestUtils.*;
import static com.github.guronas.telegram.bot.elements.model.Element.EMPTY_TEXT;
import static org.junit.jupiter.api.Assertions.*;

public class MessageElementTest {

	@Test
	void getType() {
		MessageElement element = new MessageElement(MessageAction.DELETE, null, null);
		assertEquals(ElementType.MESSAGE, element.type());
	}

	@Test
	void buildDeleteMessage() {
		MessageElement element = new MessageElement(
				MessageAction.DELETE,
				null,
				null
		);

		BotApiMethod<?> message = element.build(getTestParams(), Map.of(TEST_DYNAMIC_ROWS_ID, getTestDynamicParameters()));

		assertInstanceOf(DeleteMessage.class, message);
		DeleteMessage deleteMessage = (DeleteMessage) message;
		assertEquals(TEST_CHAT_ID, deleteMessage.getChatId());
		assertEquals(Integer.valueOf(TEST_MESSAGE_ID), deleteMessage.getMessageId());
	}

	@Test
	void buildEditMessageText() {
		TextElement textElement = getTestTextElement();
		InlineKeyboardElement replyKeyboardElement = new InlineKeyboardElement(List.of(getTestInlineKeyboardRowElement()), TEST_DYNAMIC_ROWS_ID,
				dynamicParameters -> Collections.emptyList());
		MessageElement messageElement = new MessageElement(
				MessageAction.EDIT,
				textElement,
				replyKeyboardElement
		);

		BotApiMethod<?> message = messageElement.build(getTestParams(), Map.of(TEST_DYNAMIC_ROWS_ID, getTestDynamicParameters()));

		assertInstanceOf(EditMessageText.class, message);
		EditMessageText editMessageText = (EditMessageText) message;
		assertEquals(TEST_CHAT_ID, editMessageText.getChatId());
		assertEquals(Integer.valueOf(TEST_MESSAGE_ID), editMessageText.getMessageId());
		assertEquals(TEST_TEXT, editMessageText.getText());
		assertEquals(replyKeyboardElement.build(getTestParams(), Map.of(TEST_DYNAMIC_ROWS_ID, getTestDynamicParameters())), editMessageText.getReplyMarkup());
	}

	@Test
	void buildEditMessageWithEmptyText() {
		InlineKeyboardElement replyKeyboardElement = new InlineKeyboardElement(List.of(getTestInlineKeyboardRowElement()), TEST_DYNAMIC_ROWS_ID,
				dynamicParameters -> Collections.emptyList());
		MessageElement messageElement = new MessageElement(
				MessageAction.EDIT,
				null,
				replyKeyboardElement
		);

		BotApiMethod<?> message = messageElement.build(getTestParams(), Map.of(TEST_DYNAMIC_ROWS_ID, getTestDynamicParameters()));

		assertInstanceOf(EditMessageText.class, message);
		EditMessageText editMessageText = (EditMessageText) message;
		assertEquals(EMPTY_TEXT, editMessageText.getText());
	}

	@Test
	void buildSendMessage() {
		TextElement textElement = getTestTextElement();
		InlineKeyboardElement replyKeyboardElement = new InlineKeyboardElement(List.of(getTestInlineKeyboardRowElement()), TEST_DYNAMIC_ROWS_ID,
				dynamicParameters -> Collections.emptyList());
		MessageElement messageElement = new MessageElement(
				MessageAction.CREATE,
				textElement,
				replyKeyboardElement
		);

		BotApiMethod<?> message = messageElement.build(getTestParams(), Map.of(TEST_DYNAMIC_ROWS_ID, getTestDynamicParameters()));

		assertInstanceOf(SendMessage.class, message);
		SendMessage sendMessage = (SendMessage) message;
		assertEquals(TEST_CHAT_ID, sendMessage.getChatId());
		assertEquals(TEST_TEXT, sendMessage.getText());
		assertEquals(replyKeyboardElement.build(getTestParams(), Map.of(TEST_DYNAMIC_ROWS_ID, getTestDynamicParameters())), sendMessage.getReplyMarkup());
	}

	@Test
	void buildSendMessageWithEmptyText() {
		InlineKeyboardElement replyKeyboardElement = new InlineKeyboardElement(List.of(getTestInlineKeyboardRowElement()), TEST_DYNAMIC_ROWS_ID,
				dynamicParameters -> Collections.emptyList());
		MessageElement messageElement = new MessageElement(
				MessageAction.CREATE,
				null,
				replyKeyboardElement
		);

		BotApiMethod<?> message = messageElement.build(getTestParams(), Map.of(TEST_DYNAMIC_ROWS_ID, getTestDynamicParameters()));

		assertInstanceOf(SendMessage.class, message);
		SendMessage sendMessage = (SendMessage) message;
		assertEquals(EMPTY_TEXT, sendMessage.getText());
	}

	@Test
	void buildEditedInlineKeyboardWithInvalidType() {
		ReplyKeyboardMarkupElement replyKeyboardElement = new ReplyKeyboardMarkupElement(List.of(getTestKeyboardRowElement()), new BooleanElement(true),
				TEST_DYNAMIC_ROWS_ID, dynamicParameters -> Collections.emptyList());

		MessageElement element = new MessageElement(
				MessageAction.EDIT,
				getTestTextElement(),
				replyKeyboardElement
		);

		assertThrows(TelegramElementsRuntimeException.class, () -> element.build(getTestParams(), Map.of(TEST_DYNAMIC_ROWS_ID, getTestDynamicParameters())));
	}
}
