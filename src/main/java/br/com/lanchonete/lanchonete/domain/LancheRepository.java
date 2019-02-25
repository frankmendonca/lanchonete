package br.com.lanchonete.lanchonete.domain;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LancheRepository {
	
	// Simula uma leitura ao banco
	private final List<Lanche> todosOsLanches;

	@Autowired
	public LancheRepository(IngredienteRepository ingredienteRepository) {
		todosOsLanches = carregarLanches(ingredienteRepository);
	}

	public List<Lanche> listarTodosLanches() {
		return todosOsLanches;
	}

	private List<Lanche> carregarLanches(IngredienteRepository ingredienteRepository) {
		Ingrediente bacon = ingredienteRepository.bacon();
		Ingrediente hamburguerDeCarne = ingredienteRepository.hamburguerDeCarne();
		Ingrediente queijo = ingredienteRepository.queijo();
		Ingrediente ovo = ingredienteRepository.ovo();
		
		List<Lanche> lanches = new LinkedList<>();
		lanches.add(new Lanche("X-Bacon", bacon, hamburguerDeCarne, queijo));
		lanches.add(new Lanche("X-Burger", hamburguerDeCarne, queijo));
		lanches.add(new Lanche("X-Egg", ovo, hamburguerDeCarne, queijo));
		lanches.add(new Lanche("X-Egg Bacon", ovo, bacon, hamburguerDeCarne, queijo));
		
		return lanches;
	}
}
