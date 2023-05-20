package co.edu.usbbog.sgpireports.service.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.ProyectosConvocatoria;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.datamodels.ProyectosConvocatoriaR;
import co.edu.usbbog.sgpireports.repository.IProyectoRepository;

@Service
public class ProyectoConvocatoriaRService {

	@Autowired
	private IProyectoRepository proyecto;

	public List<ProyectosConvocatoriaR> getProyectosConvAbiertas(int cc) {
		List<Proyecto> aux = proyecto.findBySemillero(cc);
		List<ProyectosConvocatoriaR> salida = new ArrayList<>();
		for (Proyecto p : aux) {
			List<ProyectosConvocatoria> aux2 = p.getProyectosConvocatoria();
				for (ProyectosConvocatoria pc : aux2) {
					if (pc.getConvocatoria().getEstado().equals("ABIERTA")) {
						salida.add(new ProyectosConvocatoriaR(p.getTitulo(),
								pc.getConvocatoria().getNombreConvocatoria(),
								getFechaFormateada(pc.getConvocatoria().getFechaInicio()),
								getFechaFormateada(pc.getConvocatoria().getFechaFinal()),
								pc.getConvocatoria().getEntidad(), p.getSemillero().getNombre(),
								pc.getIdProyecto()));
					}
				}
			}
		return salida;
	}

	private String getFechaFormateada(LocalDate fecha) {
		return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // 17-02-2022
	}

	public List<ProyectosConvocatoriaR> getProyectosConvAbiertasGI(List<Semillero> semilleros) {
		List<ProyectosConvocatoriaR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProyectosConvAbiertas(s.getId()));
		}
		return salida;
	}

	public List<ProyectosConvocatoriaR> getProyectosConvCerradasGI(List<Semillero> semilleros) {
		List<ProyectosConvocatoriaR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProyectosConvCerradas(s.getId()));
		}
		return salida;
	}

	public List<ProyectosConvocatoriaR> getProyectosConvCerradas(int cc) {
		List<Proyecto> aux = proyecto.findBySemillero(cc);
		List<ProyectosConvocatoriaR> salida = new ArrayList<>();
		for (Proyecto p : aux) {
			List<ProyectosConvocatoria> aux2 = p.getProyectosConvocatoria();
			if (!aux2.isEmpty()) {
				for (ProyectosConvocatoria pc : aux2) {
					if (!pc.getConvocatoria().getEstado().equals("ABIERTA")) {
						salida.add(new ProyectosConvocatoriaR(p.getTitulo(),
								pc.getConvocatoria().getNombreConvocatoria(),
								getFechaFormateada(pc.getConvocatoria().getFechaInicio()),
								getFechaFormateada(pc.getConvocatoria().getFechaFinal()),
								pc.getConvocatoria().getEntidad(), p.getSemillero().getNombre(), pc.getIdProyecto()));
					}
				}
			}
		}
		return salida;
	}

	public List<ProyectosConvocatoriaR> getParticipacionesProyecto(List<ProyectosConvocatoria> proyectosConvocatoria) {
		List<ProyectosConvocatoriaR> salida = new ArrayList<>();
		for (ProyectosConvocatoria pc : proyectosConvocatoria) {
				salida.add(new ProyectosConvocatoriaR(pc.getConvocatoria().getEstado(), pc.getConvocatoria().getNombreConvocatoria(),
						getFechaFormateada(pc.getConvocatoria().getFechaInicio()),
						getFechaFormateada(pc.getConvocatoria().getFechaFinal()), pc.getConvocatoria().getEntidad(), "",
						pc.getIdProyecto()));
		}
		return salida;
	}

}
