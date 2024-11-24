package com.github.guronas.telegram.bot.elements.group;

import com.github.guronas.telegram.bot.elements.model.Element;

import java.util.Map;

public record ElementGroup(String name, Map<String, Element<?>> elements) {
}
