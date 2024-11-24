package com.github.guronas.telegram.bot.elements.model;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record TextElement(String text,
						  Function<String, Optional<Element<String>>> externalTextResolver) implements Element<String> {
	public static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\$\\{(.+?)}");


	@Override
	public ElementType type() {
		return ElementType.TEXT;
	}

	@Override
	public String build(Map<String, String> params) {
		Matcher matcher = PLACEHOLDER_PATTERN.matcher(text);
		StringBuilder result = new StringBuilder();
		while (matcher.find()) {
			String placeholder = matcher.group(1);
			String replacement = params.getOrDefault(placeholder, resolveExternalTextOrDefault(placeholder, params, matcher.group(0)));
			matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
		}

		matcher.appendTail(result);

		return result.toString();
	}

	private String resolveExternalTextOrDefault(String externalTextElementName, Map<String, String> params, String defaultValue) {
		return externalTextResolver.apply(externalTextElementName)
				.filter(element -> element.type() == ElementType.TEXT)
				.map(element -> element.build(params))
				.orElse(defaultValue);
	}
}
