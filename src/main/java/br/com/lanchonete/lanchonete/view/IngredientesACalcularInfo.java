package br.com.lanchonete.lanchonete.view;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.com.lanchonete.lanchonete.domain.IngredientesACalcular;

public class IngredientesACalcularInfo {

	private List<IngredienteInfo> ingredientes = new LinkedList<>();

	public void setIngredientes(final List<IngredienteInfo> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public List<IngredienteInfo> getIngredientes() {
		return ingredientes;
	}

	public static class IngredienteInfo {
		private String nome;
		private Integer quantidade;

		public String getNome() {
			return nome;
		}

		public void setNome(final String nome) {
			this.nome = nome;
		}

		public Integer getQuantidade() {
			return quantidade;
		}

		public void setQuantidade(final Integer quantidade) {
			this.quantidade = quantidade;
		}

	}

	public IngredientesACalcular toObjeto() {
		final Map<String, Integer> map = new HashMap<>();

		for (final IngredienteInfo ingrediente : ingredientes) {
			map.put(ingrediente.nome, ingrediente.quantidade);
		}

		final IngredientesACalcular obj = new IngredientesACalcular();
		obj.setIngredientes(map);
		return obj;
	}

}
