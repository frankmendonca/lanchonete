package br.com.lanchonete.lanchonete.domain;

import java.util.LinkedList;
import java.util.List;

public class RelatorioPrecoCalculado {

	private final List<String> ingredientes;
	private final MoedaReal precoLanche;
	private MoedaReal precoDesconto = MoedaReal.ZERO;
	private MoedaReal precoFinal = MoedaReal.ZERO;
	private final List<String> promocoes = new LinkedList<>();

	public RelatorioPrecoCalculado(final List<String> ingredientes, final MoedaReal precoLanche) {
		this.ingredientes = ingredientes;
		this.precoLanche = precoLanche;
		this.precoFinal = precoLanche;
	}

	public List<String> getIngredientes() {
		return ingredientes;
	}

	public MoedaReal getPrecoLanche() {
		return precoLanche;
	}

	public MoedaReal getPrecoDesconto() {
		return precoDesconto;
	}

	public MoedaReal getPrecoFinal() {
		return precoFinal;
	}

	public List<String> getPromocoes() {
		return promocoes;
	}

	public void aplicarDesconto(final String promocao, final MoedaReal precoDesconto) {
		this.precoDesconto = this.precoDesconto.somar(precoDesconto);
		this.precoFinal = this.precoLanche.subtrair(this.precoDesconto);
		this.promocoes.add(promocao);
	}

}
