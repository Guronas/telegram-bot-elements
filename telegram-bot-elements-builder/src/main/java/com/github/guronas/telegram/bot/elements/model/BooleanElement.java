package com.github.guronas.telegram.bot.elements.model;

import java.util.Map;

public record BooleanElement(boolean flag) implements Element<Boolean> {
	@Override
	public ElementType type() {
		return ElementType.BOOLEAN;
	}

	@Override
	public Boolean build(Map<String, String> params) {
		return flag;
	}
}
