package br.com.lanchonete.lanchonete.domain;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class IngredienteRepository {

	private final List<Ingrediente> todosOsIngredientes = new LinkedList<>();

	// Simula acesso ao banco ou api
	private final Ingrediente alface = new Ingrediente("Alface", 0.40);
	private final Ingrediente bacon = new Ingrediente("Bacon", 2.00);
	private final Ingrediente hamburguerDeCarne = new Ingrediente("Hambúrguer de carne", 3.00);
	private final Ingrediente ovo = new Ingrediente("Ovo", 0.80);
	private final Ingrediente queijo = new Ingrediente("Queijo", 1.50);

	public IngredienteRepository() {
		todosOsIngredientes.add(alface);
		todosOsIngredientes.add(bacon);
		todosOsIngredientes.add(hamburguerDeCarne);
		todosOsIngredientes.add(ovo);
		todosOsIngredientes.add(queijo);
	}

	public Ingrediente alface() {
		return alface;
	}

	public Ingrediente bacon() {
		return bacon;
	}

	public Ingrediente hamburguerDeCarne() {
		return hamburguerDeCarne;
	}

	public Ingrediente ovo() {
		return ovo;
	}

	public Ingrediente queijo() {
		return queijo;
	}

	public List<Ingrediente> listarTodosIngredientes() {
		return todosOsIngredientes;
	}

	public Ingrediente buscarIngrediente(final String nomeDoIngrediente) {
		for (final Ingrediente ingrediente : todosOsIngredientes) {
			if (nomeDoIngrediente.equals(ingrediente.getNome())) {
				return ingrediente;
			}
		}
		throw new IllegalStateException("Ingrediente '" + nomeDoIngrediente + "' não encontrado");
	}

}
