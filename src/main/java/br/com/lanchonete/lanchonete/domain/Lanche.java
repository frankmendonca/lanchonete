package br.com.lanchonete.lanchonete.domain;

import java.util.Arrays;
import java.util.List;

public class Lanche {

	private final String nome;
	private final List<Ingrediente> ingredientes;

	public Lanche(String nome, List<Ingrediente> ingredientes) {
		this.nome = nome;
		this.ingredientes = ingredientes;
	}

	public Lanche(String nome, Ingrediente ... ingredientes) {
		this(nome, Arrays.asList(ingredientes));
	}

	public String getNome() {
		return nome;
	}

	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}

}
