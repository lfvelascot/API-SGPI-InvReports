package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Compra;
import co.edu.usbbog.sgpireports.model.Presupuesto;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.datamodels.PresupuestoR;

@Service
public class PresupuestoRService {
	
	private MiselaneaService extras = new MiselaneaService();

	public List<PresupuestoR> getPresupuestosProyecto(Proyecto p) {
		List<PresupuestoR> salida = new ArrayList<>();
		if (!p.getPresupuestos().isEmpty()) {
			for (Presupuesto pr : p.getPresupuestos()) {
				salida.add(cargarTotales(pr, p));
			}
		}
		return ordenarPresupuestos(salida);
	}

	private List<PresupuestoR> ordenarPresupuestos(List<PresupuestoR> presupuestos) {
		Collections.sort(presupuestos, Collections.reverseOrder());
		return presupuestos;
	}

	private PresupuestoR cargarTotales(Presupuesto pr, Proyecto p) {
		PresupuestoR x = new PresupuestoR(pr.getId().toString(), "$ " + String.valueOf(pr.getMonto()),
				extras.getFechaFormateada(pr.getFecha()), pr.getDescripcion());
		x.setTitulo(p.getTitulo());
		if (pr.getCompras().isEmpty()) {
			x.setTotalAceptadas("$0");
			x.setTotalrealizadas("$0");
			x.setTotalRechazadas("$0");
			x.setTotalSolicitadas("$0");
			x.setPresupuestoRestante(x.getMonto());
		} else {
			double totalAceptadas = 0.0, totalRealizadas = 0.0, totalRechazadas = 0.0, totalSolicitadas = 0.0;
			for (Compra c : pr.getCompras()) {
				if(c.getValor() != 0.0) {
					switch (c.getEstado()) {
					case 0:
						totalSolicitadas += c.getValor();
						break;
					case 1:
						totalRealizadas += c.getValor();
						break;
					case 2:
						totalRechazadas += c.getValor();
						break;
					case 3:
						totalAceptadas += c.getValor();
						break;
					default:
						break;
					}
				}
			}
			x.setTotalAceptadas("$ "+String.valueOf(totalAceptadas));
			x.setTotalrealizadas("$ "+String.valueOf(totalRealizadas));
			x.setTotalRechazadas("$ "+String.valueOf(totalRechazadas));
			x.setTotalSolicitadas("$ "+String.valueOf(totalSolicitadas));
			x.setPresupuestoRestante("$ "+String.valueOf(pr.getMonto()-totalRealizadas));
		}
		return x;
	}

	public List<PresupuestoR> getPresupuestosSem(List<Proyecto> proyectos) {
		List<PresupuestoR> salida = new ArrayList<>();
		for (Proyecto p : proyectos) {
			if (!p.getPresupuestos().isEmpty()) {
				salida.addAll(getPresupuestosProyecto(p));
			}
		}
		return ordenarPresupuestos(salida);
	}


	public double getTotalPresupuestoSem(List<Proyecto> proyectos) {
		double salida = 0.0;
		for (Proyecto p : proyectos) {
			if (!p.getPresupuestos().isEmpty()) {
				for (Presupuesto pr : p.getPresupuestos()) {
					salida += pr.getMonto();
				}
			}
		}
		return salida;
	}

	public List<PresupuestoR> getPresupuestosGI(List<Semillero> semilleros) {
		List<PresupuestoR> salida = new ArrayList<>();
		for (Semillero p : semilleros) {
			if (!p.getProyectos().isEmpty()) {
				salida.addAll(getPresupuestosSem(p.getProyectos()));
			}
		}
		return ordenarPresupuestos(salida);
	}


	public double getTotalPresupuestoGI(List<Semillero> semilleros) {
		double salida = 0.0;
		for (Semillero p : semilleros) {
			if (!p.getProyectos().isEmpty()) {
				salida += getTotalPresupuestoSem(p.getProyectos());
			}
		}
		return salida;
	}

}
