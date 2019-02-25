package br.com.lanchonete.lanchonete.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MoedaReal implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final MoedaReal ZERO = new MoedaReal(BigDecimal.ZERO);

	public static MoedaReal valueOf(final double valor) {
		if (valor == 0) {
			return ZERO;
		}
		return new MoedaReal(BigDecimal.valueOf(valor));
	}

	private static MoedaReal valueOf(final BigDecimal valor) {
		if (valor.signum() == 0) {
			return ZERO;
		}
		return new MoedaReal(valor);
	}

	private final BigDecimal valor;

	private MoedaReal(final BigDecimal valor) {
		this.valor = valor.setScale(2);
	}

	public BigDecimal asBigDecimal() {
		return valor;
	}

	public MoedaReal somar(final MoedaReal valor) {
		return MoedaReal.valueOf(this.valor.add(valor.valor));
	}

	public MoedaReal subtrair(final MoedaReal valor) {
		return MoedaReal.valueOf(this.valor.subtract(valor.valor));
	}

	public MoedaReal multiplicar(final double valor) {
		return multiplicar(MoedaReal.valueOf(valor));
	}

	public MoedaReal multiplicar(final MoedaReal valor) {
		return MoedaReal.valueOf(this.valor.multiply(valor.valor).setScale(2, RoundingMode.HALF_DOWN));
	}

	@Override
	public int hashCode() {
		return valor.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		final MoedaReal other = (MoedaReal) obj;
		return valor.equals(other.valor);
	}

	@Override
	public String toString() {
		return this.valor.toString();
	}

}
