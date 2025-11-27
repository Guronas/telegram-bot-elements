package com.github.guronas.telegram.bot.elements.config;

import com.github.guronas.telegram.bot.elements.ElementLoader;
import com.github.guronas.telegram.bot.elements.TelegramElementRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Configuration
@EnableConfigurationProperties(TelegramElementsProperties.class)
@ComponentScan(basePackages = "com.github.guronas.telegram.bot.elements")
@ConditionalOnProperty(prefix = "telegram.elements", name = "enabled", havingValue = "true", matchIfMissing = true)
public class TelegramElementsConfiguration {
	private static final String ALL_PATH_URLS_POSTFIX = "/**";

	@Bean
	@ConditionalOnMissingBean
	public TelegramElementRegistry telegramElementRegistry(ElementLoader elementLoader) {
		return new SpringElementRegistry(elementLoader);
	}

	@Bean
	@ConditionalOnMissingBean
	public ElementLoader elementLoader(TelegramElementsProperties properties) throws IOException {
		return new SpringYamlElementLoader(yamlMapFactoryBean(properties));
	}

	@Bean("telegramElementsYamlFactoryBean")
	public YamlMapFactoryBean yamlMapFactoryBean(TelegramElementsProperties properties) throws IOException {
		String workingDirectory = properties.getWorkingDirectory();
		if (!StringUtils.hasText(workingDirectory)) {
			throw new BeanCreationException("Failed to create YamlMapFactoryBean: working directory for telegram elements cannot be empty and should be specified");
		}

		ResourcePatternResolver resourcePatResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resourcePatResolver.getResources(workingDirectory + ALL_PATH_URLS_POSTFIX);
		resources = Arrays.stream(resources)
				.filter(Resource::isReadable)
				.toArray(Resource[]::new);

		log.trace("Resources: {}", Arrays.toString(resources));
		YamlMapFactoryBean yamlMapFactoryBean = new YamlMapFactoryBean();
		yamlMapFactoryBean.setResources(resources);
		return yamlMapFactoryBean;
	}
}
