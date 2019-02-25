package br.com.lanchonete.lanchonete.domain;

import java.util.HashMap;
import java.util.Map;

public class IngredientesACalcular {

	private Map<String, Integer> ingredientes = new HashMap<>();

	public Map<String, Integer> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(final Map<String, Integer> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public int getIngrediente(final String ingrediente) {
		return ingredientes.getOrDefault(ingrediente, 0);
	}

}
