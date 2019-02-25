package br.com.lanchonete.lanchonete;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lanchonete.lanchonete.domain.CalculadorPrecoService;
import br.com.lanchonete.lanchonete.domain.Ingrediente;
import br.com.lanchonete.lanchonete.domain.IngredienteRepository;
import br.com.lanchonete.lanchonete.domain.IngredientesACalcular;
import br.com.lanchonete.lanchonete.domain.MoedaReal;
import br.com.lanchonete.lanchonete.domain.RelatorioPrecoCalculado;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculadorPrecoServiceTest {

	@Autowired
	private IngredienteRepository ingredienteRepository;

	@Autowired
	private CalculadorPrecoService calculadorPrecoService;

	@Test
	public void calculaPrecoSemCondicao() {
		final Ingrediente bacon = ingredienteRepository.bacon();
		final Ingrediente hamburguerDeCarne = ingredienteRepository.hamburguerDeCarne();
		final Ingrediente queijo = ingredienteRepository.queijo();
		final MoedaReal precoLanche = bacon.getPreco().somar(hamburguerDeCarne.getPreco()).somar(queijo.getPreco());

		final IngredientesACalcular ingredientes = gerarIngredientesACalcular(bacon, hamburguerDeCarne, queijo);
		final RelatorioPrecoCalculado relatorio = calculadorPrecoService.calcular(ingredientes);

		assertEquals(precoLanche, relatorio.getPrecoLanche());
		assertEquals(MoedaReal.ZERO, relatorio.getPrecoDesconto());
		assertEquals(precoLanche, relatorio.getPrecoFinal());
		assertEquals(0, relatorio.getPromocoes().size());
	}

	@Test
	public void calculaLancheLight() {
		final Ingrediente alface = ingredienteRepository.alface();
		final Ingrediente hamburguerDeCarne = ingredienteRepository.hamburguerDeCarne();
		final Ingrediente queijo = ingredienteRepository.queijo();

		final MoedaReal precoLanche = alface.getPreco().somar(hamburguerDeCarne.getPreco()).somar(queijo.getPreco());
		final MoedaReal precoDesconto = precoLanche.multiplicar(0.1);
		final MoedaReal precoFinal = precoLanche.multiplicar(0.9);

		final IngredientesACalcular ingredientes = gerarIngredientesACalcular(alface, hamburguerDeCarne, queijo);
		final RelatorioPrecoCalculado relatorio = calculadorPrecoService.calcular(ingredientes);

		assertEquals(precoLanche, relatorio.getPrecoLanche());
		assertEquals(precoDesconto, relatorio.getPrecoDesconto());
		assertEquals(precoFinal, relatorio.getPrecoFinal());
		assertEquals(1, relatorio.getPromocoes().size());
		assertEquals("Light", relatorio.getPromocoes().get(0));
	}

	@Test
	public void calculaLancheMuitaCarne() {
		final Ingrediente bacon = ingredienteRepository.bacon();
		final Ingrediente hamburguerDeCarne = ingredienteRepository.hamburguerDeCarne();
		final Ingrediente queijo = ingredienteRepository.queijo();

		final MoedaReal precoLanche = bacon.getPreco()
				.somar(hamburguerDeCarne.getPreco().multiplicar(6))
				.somar(queijo.getPreco());
		final MoedaReal precoDesconto = hamburguerDeCarne.getPreco().multiplicar(2);
		final MoedaReal precoFinal = precoLanche.subtrair(precoDesconto);

		final IngredientesACalcular ingredientes = gerarIngredientesACalcular(bacon, queijo,
				hamburguerDeCarne, hamburguerDeCarne, hamburguerDeCarne,
				hamburguerDeCarne, hamburguerDeCarne, hamburguerDeCarne);
		final RelatorioPrecoCalculado relatorio = calculadorPrecoService.calcular(ingredientes);

		assertEquals(precoLanche, relatorio.getPrecoLanche());
		assertEquals(precoDesconto, relatorio.getPrecoDesconto());
		assertEquals(precoFinal, relatorio.getPrecoFinal());
		assertEquals(1, relatorio.getPromocoes().size());
		assertEquals("Muita carne", relatorio.getPromocoes().get(0));
	}

	@Test
	public void calculaLancheLightMasComMuitaCarne() {
		final Ingrediente alface = ingredienteRepository.alface();
		final Ingrediente hamburguerDeCarne = ingredienteRepository.hamburguerDeCarne();
		final Ingrediente queijo = ingredienteRepository.queijo();

		final MoedaReal precoLanche = alface.getPreco()
				.somar(hamburguerDeCarne.getPreco().multiplicar(4))
				.somar(queijo.getPreco());
		final MoedaReal precoFinal = precoLanche.subtrair(hamburguerDeCarne.getPreco()).multiplicar(0.9);
		final MoedaReal precoDesconto = precoLanche.subtrair(precoFinal);

		final IngredientesACalcular ingredientes = gerarIngredientesACalcular(alface, queijo,
				hamburguerDeCarne, hamburguerDeCarne, hamburguerDeCarne, hamburguerDeCarne);
		final RelatorioPrecoCalculado relatorio = calculadorPrecoService.calcular(ingredientes);

		assertEquals(precoLanche, relatorio.getPrecoLanche());
		assertEquals(precoDesconto, relatorio.getPrecoDesconto());
		assertEquals(precoFinal, relatorio.getPrecoFinal());
		assertEquals(2, relatorio.getPromocoes().size());
		assertEquals("Muita carne", relatorio.getPromocoes().get(0));
		assertEquals("Light", relatorio.getPromocoes().get(1));
	}

	@Test
	public void calculaLancheMuitoQueijo() {
		final Ingrediente bacon = ingredienteRepository.bacon();
		final Ingrediente queijo = ingredienteRepository.queijo();

		final MoedaReal precoLanche = bacon.getPreco().somar(queijo.getPreco().multiplicar(7));
		final MoedaReal precoDesconto = queijo.getPreco().multiplicar(2);
		final MoedaReal precoFinal = precoLanche.subtrair(precoDesconto);

		final IngredientesACalcular ingredientes = gerarIngredientesACalcular(bacon,
				queijo, queijo, queijo, queijo, queijo, queijo, queijo);
		final RelatorioPrecoCalculado relatorio = calculadorPrecoService.calcular(ingredientes);

		assertEquals(precoLanche, relatorio.getPrecoLanche());
		assertEquals(precoDesconto, relatorio.getPrecoDesconto());
		assertEquals(precoFinal, relatorio.getPrecoFinal());
		assertEquals(1, relatorio.getPromocoes().size());
		assertEquals("Muito queijo", relatorio.getPromocoes().get(0));
	}

	@Test
	public void calculaLancheLightMasComMuitaCarneMuitoQueijo() {
		final Ingrediente alface = ingredienteRepository.alface();
		final Ingrediente hamburguerDeCarne = ingredienteRepository.hamburguerDeCarne();
		final Ingrediente queijo = ingredienteRepository.queijo();

		final MoedaReal precoLanche = alface.getPreco().multiplicar(4)
				.somar(hamburguerDeCarne.getPreco().multiplicar(4))
				.somar(queijo.getPreco().multiplicar(4));
		final MoedaReal precoFinal = precoLanche.subtrair(hamburguerDeCarne.getPreco()).subtrair(queijo.getPreco()).multiplicar(0.9);
		final MoedaReal precoDesconto = precoLanche.subtrair(precoFinal);

		final IngredientesACalcular ingredientes = gerarIngredientesACalcular(
				alface, alface, alface, alface,
				queijo, queijo, queijo, queijo,
				hamburguerDeCarne, hamburguerDeCarne, hamburguerDeCarne, hamburguerDeCarne);
		final RelatorioPrecoCalculado relatorio = calculadorPrecoService.calcular(ingredientes);

		assertEquals(precoLanche, relatorio.getPrecoLanche());
		assertEquals(precoDesconto, relatorio.getPrecoDesconto());
		assertEquals(precoFinal, relatorio.getPrecoFinal());
		assertEquals(3, relatorio.getPromocoes().size());
		assertEquals("Muita carne", relatorio.getPromocoes().get(0));
		assertEquals("Muito queijo", relatorio.getPromocoes().get(1));
		assertEquals("Light", relatorio.getPromocoes().get(2));
	}

	// -- Utils ---

	private IngredientesACalcular gerarIngredientesACalcular(final Ingrediente... ingredientes) {
		final IngredientesACalcular ingredientesACalcular = new IngredientesACalcular();
		for (final Ingrediente ingrediente : ingredientes) {
			ingredientesACalcular.getIngredientes().compute(ingrediente.getNome(), (key, oldValue) -> {
				if (oldValue == null) {
					return Integer.valueOf(1);
				} else {
					return oldValue + 1;
				}
			});
		}
		return ingredientesACalcular;
	}

}
