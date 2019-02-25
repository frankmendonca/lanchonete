package br.com.lanchonete.lanchonete.domain;

public class Ingrediente {

	private final String nome;
	private final MoedaReal preco;

	public Ingrediente(String nome, double preco) {
		this.nome = nome;
		this.preco = MoedaReal.valueOf(preco);
	}

	public String getNome() {
		return nome;
	}

	public MoedaReal getPreco() {
		return preco;
	}

	@Override
	public int hashCode() {
		return nome.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Ingrediente other = (Ingrediente) obj;
		return nome.equals(other.nome);
	}

	@Override
	public String toString() {
		return nome;
	}

}
