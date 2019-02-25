package br.com.lanchonete.lanchonete.view;

import java.math.BigDecimal;
import java.util.List;

public class RelatorioPrecoInfo {

	private List<String> ingredientes;
	private BigDecimal precoLanche;
	private BigDecimal precoDesconto;
	private BigDecimal precoFinal;
	private List<String> promocoes;

	public List<String> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(final List<String> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public BigDecimal getPrecoLanche() {
		return precoLanche;
	}

	public void setPrecoLanche(final BigDecimal precoLanche) {
		this.precoLanche = precoLanche;
	}

	public BigDecimal getPrecoDesconto() {
		return precoDesconto;
	}

	public void setPrecoDesconto(final BigDecimal precoDesconto) {
		this.precoDesconto = precoDesconto;
	}

	public BigDecimal getPrecoFinal() {
		return precoFinal;
	}

	public void setPrecoFinal(final BigDecimal precoFinal) {
		this.precoFinal = precoFinal;
	}

	public List<String> getPromocoes() {
		return promocoes;
	}

	public void setPromocoes(final List<String> promocoes) {
		this.promocoes = promocoes;
	}

}
