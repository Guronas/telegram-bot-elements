package com.github.guronas.telegram.bot.elements.parameter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class DynamicParameters {
	private final List<Map<String, String>> parameters = new ArrayList<>();

	public DynamicParameters(List<Map<String, String>> parameters) {
		this.parameters.addAll(parameters);
	}

	public void add(Map<String, String> parameters) {
		this.parameters.add(parameters);
	}

	public void add(List<Parameter> parameters) {
		add(Parameters.buildParameters(parameters));
	}
}
