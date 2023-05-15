package co.edu.usbbog.sgpireports.service;

public class SemillerosR {
	
	private String nombre, fechaFun, liderSemillero, lineaInv;
	private int totalMiembros;
	private int totalEventos;
	private int totalConvocatorias;
	private int proyectosActivos;
	private int proyectosFinalizados;
	private int totalProyectos; 
	
	
	public SemillerosR(String nombre, String fechaFun, String liderSemillero, String lineaInv, int totalMiembros) {
		this.nombre = nombre;
		this.fechaFun = fechaFun;
		this.liderSemillero = liderSemillero;
		this.lineaInv = lineaInv;
		this.totalMiembros = totalMiembros;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getFechaFun() {
		return fechaFun;
	}
	public void setFechaFun(String fechaFun) {
		this.fechaFun = fechaFun;
	}
	public String getLiderSemillero() {
		return liderSemillero;
	}
	public void setLiderSemillero(String liderSemillero) {
		this.liderSemillero = liderSemillero;
	}
	public String getLineaInv() {
		return lineaInv;
	}
	public void setLineaInv(String lineaInv) {
		this.lineaInv = lineaInv;
	}
	public int getTotalMiembros() {
		return totalMiembros;
	}
	public void setTotalMiembros(int totalMiembros) {
		this.totalMiembros = totalMiembros;
	}
	public int getTotalEventos() {
		return totalEventos;
	}
	public void setTotalEventos(int totalEventos) {
		this.totalEventos = totalEventos;
	}
	public int getTotalConvocatorias() {
		return totalConvocatorias;
	}
	public void setTotalConvocatorias(int totalConvocatorias) {
		this.totalConvocatorias = totalConvocatorias;
	}
	public int getProyectosActivos() {
		return proyectosActivos;
	}
	public void setProyectosActivos(int proyectosActivos) {
		this.proyectosActivos = proyectosActivos;
	}
	public int getProyectosFinalizados() {
		return proyectosFinalizados;
	}
	public void setProyectosFinalizados(int proyectosFinalizados) {
		this.proyectosFinalizados = proyectosFinalizados;
	}
	public int getTotalProyectos() {
		return totalProyectos;
	}
	public void setTotalProyectos(int totalProyectos) {
		this.totalProyectos = totalProyectos;
	}
	
	
}
