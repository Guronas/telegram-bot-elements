package com.github.guronas.telegram.bot.elements.parameter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Parameters {

	public static Map<String, String> buildParameters(Parameter... parameters) {
		return buildParameters((Arrays.stream(parameters)));
	}

	public static Map<String, String> buildParameters(List<Parameter> parameters) {
		return buildParameters(parameters.stream());
	}

	private static Map<String, String> buildParameters(Stream<Parameter> parameters) {
		return parameters.collect(Collectors.toMap(
				Parameter::key,
				parameter -> parameter.value().toString()));
	}
}
