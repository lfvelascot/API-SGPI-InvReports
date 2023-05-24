package co.edu.usbbog.sgpireports.service.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Compra;
import co.edu.usbbog.sgpireports.model.Presupuesto;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.datamodels.CompraR;
import co.edu.usbbog.sgpireports.model.datamodels.PresupuestoR;

@Service
public class PresupuestoRService {

	private List<String> estadoCompra = Arrays.asList("Solicitada", "Realizada", "Rechazada","Aceptada");

	public List<PresupuestoR> getPresupuestosProyecto(Proyecto p) {
		List<PresupuestoR> salida = new ArrayList<>();
		if (!p.getPresupuestos().isEmpty()) {
			for (Presupuesto pr : p.getPresupuestos()) {
				salida.add(new PresupuestoR(pr.getId().toString(), "$ " + String.valueOf(pr.getMonto()),
						getFechaFormateada(pr.getFecha()), pr.getDescripcion()));
			}
		}
		return salida;
	}

	public List<CompraR> getComprasProyecto(Proyecto p) {
		List<CompraR> salida = new ArrayList<>();
		if(!p.getPresupuestos().isEmpty()) {
			for(Presupuesto pr : p.getPresupuestos()) {
				if(!pr.getCompras().isEmpty()) {
					for(Compra c : pr.getCompras()) {
						var valor = 0.0;
						if(c.getValor() != null) {
							valor = c.getValor();
						}
						salida.add(new CompraR(getFechaFormateada(c.getFechaSolicitud()),
								c.getNombre(),
								c.getTipo(),
								c.getCodigoCompra(),
								valor,
								getFechaFormateada(c.getFechaCompra()),
								estadoCompra.get(c.getEstado()),
								c.getLink(),
								c.getDescripcion(),
								pr.getId().toString()));
					}
				}
			}
		}
		return salida;
	}

	private String getFechaFormateada(LocalDate fecha) {
		if (fecha != null) {
			return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} else {
			return "";
		}
	}

}
