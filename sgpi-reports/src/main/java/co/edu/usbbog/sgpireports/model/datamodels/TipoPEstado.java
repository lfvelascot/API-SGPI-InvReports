package co.edu.usbbog.sgpireports.model.datamodels;

public class TipoPEstado {

	private String tipo;
	private int numero;
	private int numero2;
	private int numero3;

	public TipoPEstado(String tipo, int numero) {
		this.tipo = tipo;
		this.numero = numero;
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

}
