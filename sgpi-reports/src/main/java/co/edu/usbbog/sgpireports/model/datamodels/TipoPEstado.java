package co.edu.usbbog.sgpireports.model.datamodels;

import javassist.expr.NewArray;

public class TipoPEstado implements  Comparable<TipoPEstado> {

	private String tipo;
	private int numero;
	private int numero2;
	private int numero3;

	public TipoPEstado(String tipo, int numero) {
		this.tipo = tipo;
		this.numero = numero;
	}

	public TipoPEstado(String tipo) {
		this.tipo = tipo;
	}

	public int getNumero3() {
		return numero3;
	}

	public void setNumero3(int numero3) {
		this.numero3 = numero3;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getNumero2() {
		return numero2;
	}

	public void setNumero2(int numero2) {
		this.numero2 = numero2;
	}

	@Override
	public int compareTo(TipoPEstado o) {
		if(o.getTipo().chars().allMatch(Character::isDigit)) {
			return Integer.valueOf(Integer.parseInt(getTipo())).compareTo(Integer.valueOf(Integer.parseInt(o.getTipo())));
		} else {
			return getTipo().compareTo(o.getTipo());
		}
	}

}
