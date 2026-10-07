# Telegram Bot Elements

A Java library for building Telegram bot interfaces declaratively. Define reusable messages and keyboards in YAML, then build TelegramBots API objects with runtime parameters. This keeps UI definitions separate from bot logic.

## Features

- Inline and reply keyboards, including dynamically generated rows.
- Message creation, text editing, deletion, and callback query responses.
- `${parameter}` placeholders and references to reusable elements.
- Named element groups and a registry for building API methods and objects.
- A Spring Boot starter that loads YAML definitions and configures the registry.

## Modules and stack

- `telegram-bot-elements-builder`: element models, parsing, conversion, and the registry.
- `telegram-bot-elements-springboot-starter`: YAML loading and Spring Boot integration.

The project targets Java 17 and uses TelegramBots Meta 7.9.1. The starter declares Spring Boot 3.3.5.

## Quick start with Spring Boot

Build and install both modules from the repository root:

```bash
./mvnw clean install
```

Add the starter to your application:

```xml
<dependency>
    <groupId>com.github.guronas</groupId>
    <artifactId>telegram-bot-elements-springboot-starter</artifactId>
    <version>0.0.5</version>
</dependency>
```

Set the definition directory in `application.yml`:

```yaml
telegram:
  elements:
    working-directory: classpath:/telegram-elements
```

Create `src/main/resources/telegram-elements/menu.yml`:

```yaml
main:
  - id: welcome
    message:
      text: "Hello, ${name}!"
      replyKeyboard:
        inlineKeyboard:
          rows:
            - inlineKeyboardRow:
                - inlineKeyboardButton:
                    text: "View profile"
                    callbackData: "profile"
```

Inject `TelegramElementRegistry` into your Spring component and build the message:

```java
import com.github.guronas.telegram.bot.elements.TelegramElementRegistry;
import com.github.guronas.telegram.bot.elements.exception.ElementNotFoundException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.Map;

public SendMessage welcome(TelegramElementRegistry registry, String chatId)
        throws ElementNotFoundException {
    return registry.buildElement(
        "main",
        "welcome",
        Map.of("chatId", chatId, "name", "Alex"),
        SendMessage.class
    );
}
```

Send the resulting `SendMessage` through your Telegram client. The library builds API objects; your bot handles delivery and incoming updates, including the `profile` callback.

## License

[MIT](LICENSE).
