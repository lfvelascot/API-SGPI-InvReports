package co.edu.usbbog.sgpireports.model.datamodels;

public class PresupuestoR { 
	
	private String id, monto, fecha, descripcion;

	public PresupuestoR(String id,String monto, String fecha, String descripcion) {
		this.id = id;
		this.monto = monto;
		this.fecha = fecha;
		if(descripcion != null) {
			this.descripcion = descripcion;
		} else {
			this.descripcion = "";
		}
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
