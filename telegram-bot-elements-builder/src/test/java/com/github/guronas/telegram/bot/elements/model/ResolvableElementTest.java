package com.github.guronas.telegram.bot.elements.model;

import com.github.guronas.telegram.bot.elements.exception.TelegramElementsRuntimeException;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Optional;
import java.util.function.Function;

import static com.github.guronas.telegram.bot.elements.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResolvableElementTest {

	@Test
	void build() {
		ElementType type = ElementType.INLINE_KEYBOARD_BUTTON;
		String elementReference = "${testButton1}";
		Function<String, Optional<Element<InlineKeyboardButton>>> elementResolver = reference ->
				Optional.of(getTestInlineKeyboardButtonElement(TEST_TEXT, TEST_BUTTON_1));

		ResolvableElement<InlineKeyboardButton> resolvableElement = new ResolvableElement<>(type, elementReference, elementResolver);

		InlineKeyboardButton button = resolvableElement.build(getTestParams());
		assertEquals(TEST_TEXT, button.getText());
		assertEquals(TEST_BUTTON_1, button.getCallbackData());
	}

	@Test
	void testBuildWithUnresolvableElement() {
		ElementType type = ElementType.INLINE_KEYBOARD_BUTTON;
		String elementReference = "${unknownButton}";
		Function<String, Optional<Element<InlineKeyboardButton>>> elementResolver = reference -> Optional.empty();

		ResolvableElement<InlineKeyboardButton> resolvableElement = new ResolvableElement<>(type, elementReference, elementResolver);

		assertThrows(TelegramElementsRuntimeException.class, () -> resolvableElement.build(getTestParams()));
	}
}
