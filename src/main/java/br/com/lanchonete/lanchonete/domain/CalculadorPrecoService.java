package br.com.lanchonete.lanchonete.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculadorPrecoService {

	private static final double DESCONTO_LIGHT = 0.1;
	private static final int QTDE_CARNE_COM_DESCONTO = 3;

	@Autowired
	private IngredienteRepository ingredienteRepository;

	public RelatorioPrecoCalculado calcular(final IngredientesACalcular ingredientesACalcular) {
		final RelatorioPrecoCalculado relatorioPreco = calcularPrecoLanche(ingredientesACalcular);

		calcularValorDescontoMuitaCarne(ingredientesACalcular, relatorioPreco);
		calcularValorDescontoMuitoQueijo(ingredientesACalcular, relatorioPreco);
		calcularPercentualDescontoLight(ingredientesACalcular, relatorioPreco);

		return relatorioPreco;
	}

	private RelatorioPrecoCalculado calcularPrecoLanche(final IngredientesACalcular ingredientesACalcular) {
		final List<String> listaIngredientes = new LinkedList<>();

		MoedaReal precoLanche = MoedaReal.ZERO;

		for (final Entry<String, Integer> ingredienteEntry : ingredientesACalcular.getIngredientes().entrySet()) {
			listaIngredientes.add(ingredienteEntry.getKey());
			final Ingrediente ingrediente = ingredienteRepository.buscarIngrediente(ingredienteEntry.getKey());
			final int qtde = ingredientesACalcular.getIngrediente(ingredienteEntry.getKey());
			precoLanche = precoLanche.somar(ingrediente.getPreco().multiplicar(qtde));
		}

		return new RelatorioPrecoCalculado(listaIngredientes, precoLanche);
	}

	private void calcularValorDescontoMuitaCarne(final IngredientesACalcular ingredientesACalcular,
			final RelatorioPrecoCalculado relatorioPreco) {

		final Ingrediente carne = ingredienteRepository.hamburguerDeCarne();

		final int qtdeCarne = ingredientesACalcular.getIngrediente(carne.getNome());
		final int qtdeDesconto = qtdeCarne / QTDE_CARNE_COM_DESCONTO;

		if (qtdeDesconto > 0) {
			relatorioPreco.aplicarDesconto("Muita carne", carne.getPreco().multiplicar(qtdeDesconto));
		}
	}

	private void calcularValorDescontoMuitoQueijo(final IngredientesACalcular ingredientesACalcular,
			final RelatorioPrecoCalculado relatorioPreco) {

		final Ingrediente queijo = ingredienteRepository.queijo();

		final int qtdeQueijo = ingredientesACalcular.getIngrediente(queijo.getNome());
		final int qtdeDesconto = qtdeQueijo / QTDE_CARNE_COM_DESCONTO;

		if (qtdeDesconto > 0) {
			relatorioPreco.aplicarDesconto("Muito queijo", queijo.getPreco().multiplicar(qtdeDesconto));
		}
	}

	private void calcularPercentualDescontoLight(final IngredientesACalcular ingredientesACalcular, final RelatorioPrecoCalculado relatorioPreco) {
		final Ingrediente alface = ingredienteRepository.alface();
		final int qtdeAlface = ingredientesACalcular.getIngrediente(alface.getNome());

		if (qtdeAlface == 0) {
			return; // não tem alface, não tem desconto
		}

		final Ingrediente bacon = ingredienteRepository.bacon();
		final int qtdeBacon = ingredientesACalcular.getIngrediente(bacon.getNome());

		if (qtdeBacon > 0) {
			return; // tem bacon, não tem desconto
		}

		final MoedaReal precoFinal = relatorioPreco.getPrecoFinal();

		relatorioPreco.aplicarDesconto("Light", precoFinal.multiplicar(DESCONTO_LIGHT));
	}

}
