package com.github.guronas.telegram.bot.elements.model;

import com.github.guronas.telegram.bot.elements.parameter.DynamicParameters;
import com.github.guronas.telegram.bot.elements.exception.TelegramElementsRuntimeException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public interface ReplyKeyboardElement<B extends Element<R>, R, T extends ReplyKeyboard> extends ApiObjectElement<T> {

	List<? extends Element<R>> rows();

	String dynamicRowsId();

	Function<DynamicParameters, List<B>> dynamicRowsBuilder();

	default List<R> buildRows(Map<String, String> params, Map<String, DynamicParameters> dynamicParameters) {
		return Stream.concat(buildDynamicRows(dynamicParameters), rows().stream())
				.map(element -> element.build(params))
				.toList();
	}

	private Stream<? extends Element<R>> buildDynamicRows(Map<String, DynamicParameters> dynamicParameters) {
		return Optional.ofNullable(dynamicRowsId())
				.filter(id -> !id.isBlank())
				.map(dynamicParameters::get)
				.map(dynamicParams -> dynamicRowsBuilder().apply(dynamicParams))
				.stream()
				.flatMap(Collection::stream);
	}
}
