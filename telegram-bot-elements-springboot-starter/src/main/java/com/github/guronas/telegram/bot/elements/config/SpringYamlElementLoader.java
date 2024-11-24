package com.github.guronas.telegram.bot.elements.config;

import com.github.guronas.telegram.bot.elements.TelegramElementRegistry;
import com.github.guronas.telegram.bot.elements.parser.ElementParser;
import com.github.guronas.telegram.bot.elements.ElementLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.YamlMapFactoryBean;

import java.util.Map;

@RequiredArgsConstructor
public class SpringYamlElementLoader implements ElementLoader {
	private final YamlMapFactoryBean telegramElementsYamlFactoryBean;
	private final ElementParser elementParser = new ElementParser();


	@Override
	public void loadElements(TelegramElementRegistry elementRegistry) {
		Map<String, Object> rawElementGroups = telegramElementsYamlFactoryBean.getObject();
		elementParser.parse(elementRegistry, rawElementGroups);
	}
}
