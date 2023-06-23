package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Compra;
import co.edu.usbbog.sgpireports.model.Presupuesto;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.datamodels.CompraR;
@Service
public class CompraRService {
	
	private List<String> estadoCompra = Arrays.asList("Solicitada", "Realizada", "Rechazada", "Aceptada");
	private MiselaneaService extras = new MiselaneaService();
	
	public List<CompraR> getComprasProyecto(Proyecto p) {
		List<CompraR> salida = new ArrayList<>();
		if (!p.getPresupuestos().isEmpty()) {
			for (Presupuesto pr : p.getPresupuestos()) {
				if (!pr.getCompras().isEmpty()) {
					for (Compra c : pr.getCompras()) {
						double valor = (c.getValor() != null) ? valor = c.getValor() : 0.0;
						salida.add(new CompraR(extras.getFechaFormateada(c.getFechaSolicitud()), c.getNombre(), c.getTipo(),
								c.getCodigoCompra(), valor, extras.getFechaFormateada(c.getFechaCompra()),
								estadoCompra.get(c.getEstado()), c.getLink(), c.getDescripcion(),
								pr.getId().toString()));
					}
				}
			}
		}
		return ordernarCompras(salida);
	}
	
	public List<CompraR> getComprasSem(List<Proyecto> proyectos) {
		List<CompraR> salida = new ArrayList<>();
		for (Proyecto p : proyectos) {
			if (!p.getPresupuestos().isEmpty()) {
				salida.addAll(getComprasProyecto(p));
			}
		}
		return ordernarCompras(salida);
	}
	
	public List<CompraR> getComprasGI(List<Semillero> semilleros) {
		List<CompraR> salida = new ArrayList<>();
		for (Semillero p : semilleros) {
			if (!p.getProyectos().isEmpty()) {
				salida.addAll(getComprasSem(p.getProyectos()));
			}
		}
		return ordernarCompras(salida);
	}
	
	public double getTotalComprasGI(List<Semillero> semilleros) {
		double salida = 0.0;
		for (Semillero p : semilleros) {
			if (!p.getProyectos().isEmpty()) {
				salida += getTotalComprasSem(p.getProyectos());
			}
		}
		return salida;
	}
	public double getTotalComprasSem(List<Proyecto> proyectos) {
		double salida = 0.0;
		for (Proyecto p : proyectos) {
			if (!p.getPresupuestos().isEmpty()) {
				for (Presupuesto pr : p.getPresupuestos()) {
					if (!pr.getCompras().isEmpty()) {
						for (Compra c : pr.getCompras()) {
							salida = (c.getValor() != null && c.getEstado() == 1) ? salida += c.getValor() : salida;
						}
					}
				}
			}
		}
		return salida;
	}

	private List<CompraR> ordernarCompras(List<CompraR> compras) {
		Collections.sort(compras, Collections.reverseOrder());
		return compras;
	}
	
}
