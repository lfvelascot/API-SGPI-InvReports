package co.edu.usbbog.sgpireports.service.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import co.edu.usbbog.sgpireports.model.datamodels.PresupuestoR;

@Service
public class PresupuestoRService {

	private List<String> estadoCompra = Arrays.asList("Solicitada", "Realizada", "Rechazada", "Aceptada");

	public List<PresupuestoR> getPresupuestosProyecto(Proyecto p) {
		List<PresupuestoR> salida = new ArrayList<>();
		if (!p.getPresupuestos().isEmpty()) {
			for (Presupuesto pr : p.getPresupuestos()) {
				salida.add(cargarTotalesCompras(pr, p));
			}
		}
		return ordenarPresupuestos(salida);
	}

	private List<PresupuestoR> ordenarPresupuestos(List<PresupuestoR> presupuestos) {
		Collections.sort(presupuestos, Collections.reverseOrder());
		return presupuestos;
	}

	private PresupuestoR cargarTotalesCompras(Presupuesto pr, Proyecto p) {
		PresupuestoR x = new PresupuestoR(pr.getId().toString(), "$ " + String.valueOf(pr.getMonto()),
				getFechaFormateada(pr.getFecha()), pr.getDescripcion());
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

	public List<CompraR> getComprasProyecto(Proyecto p) {
		List<CompraR> salida = new ArrayList<>();
		if (!p.getPresupuestos().isEmpty()) {
			for (Presupuesto pr : p.getPresupuestos()) {
				if (!pr.getCompras().isEmpty()) {
					for (Compra c : pr.getCompras()) {
						double valor = 0.0;
						if (c.getValor() != null) {
							valor = c.getValor();
						}
						salida.add(new CompraR(getFechaFormateada(c.getFechaSolicitud()), c.getNombre(), c.getTipo(),
								c.getCodigoCompra(), valor, getFechaFormateada(c.getFechaCompra()),
								estadoCompra.get(c.getEstado()), c.getLink(), c.getDescripcion(),
								pr.getId().toString()));
					}
				}
			}
		}
		return ordernarCompras(salida);
	}

	private List<CompraR> ordernarCompras(List<CompraR> compras) {
		Collections.sort(compras, Collections.reverseOrder());
		return compras;
	}

	private String getFechaFormateada(LocalDate fecha) {
		if (fecha != null) {
			return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} else {
			return "";
		}
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

	public List<CompraR> getComprasSem(List<Proyecto> proyectos) {
		List<CompraR> salida = new ArrayList<>();
		for (Proyecto p : proyectos) {
			if (!p.getPresupuestos().isEmpty()) {
				salida.addAll(getComprasProyecto(p));
			}
		}
		return ordernarCompras(salida);
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

	public double getTotalComprasSem(List<Proyecto> proyectos) {
		double salida = 0.0;
		for (Proyecto p : proyectos) {
			if (!p.getPresupuestos().isEmpty()) {
				for (Presupuesto pr : p.getPresupuestos()) {
					if (!pr.getCompras().isEmpty()) {
						for (Compra c : pr.getCompras()) {
							if (c.getValor() != null && c.getEstado() == 1) {
								salida += c.getValor();
							}
						}
					}
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

	public List<CompraR> getComprasGI(List<Semillero> semilleros) {
		List<CompraR> salida = new ArrayList<>();
		for (Semillero p : semilleros) {
			if (!p.getProyectos().isEmpty()) {
				salida.addAll(getComprasSem(p.getProyectos()));
			}
		}
		return ordernarCompras(salida);
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

	public double getTotalComprasGI(List<Semillero> semilleros) {
		double salida = 0.0;
		for (Semillero p : semilleros) {
			if (!p.getProyectos().isEmpty()) {
				salida += getTotalComprasSem(p.getProyectos());
			}
		}
		return salida;
	}

}
