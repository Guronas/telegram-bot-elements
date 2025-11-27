package com.github.guronas.telegram.bot.elements.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(TelegramElementsConfiguration.class)
public class TelegramElementsAutoConfiguration {
}
