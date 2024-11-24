package com.github.guronas.telegram.bot.elements.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "elements")
public class TelegramElementsProperties {
	private String workingDirectory;
}
