package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.ProyectosConvocatoria;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.datamodels.ProyectosConvocatoriaR;

@Service
public class ProyectoConvocatoriaRService {

	private MiselaneaService extras = new MiselaneaService();

	public List<ProyectosConvocatoriaR> getProyectosConvAbiertas(List<Proyecto> lista) {
		List<ProyectosConvocatoriaR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			List<ProyectosConvocatoria> aux2 = p.getProyectosConvocatoria();
			for (ProyectosConvocatoria pc : aux2) {
				if (pc.getConvocatoria().getEstado().equals("ABIERTA")) {
					salida.add(createObject(p,pc));
				}
			}
		}
		return ordenarSalida(salida);
	}

	public List<ProyectosConvocatoriaR> getProyectosConvAbiertasGI(List<Semillero> semilleros) {
		List<ProyectosConvocatoriaR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProyectosConvAbiertas(s.getProyectos()));
		}
		return ordenarSalida(salida);
	}

	public List<ProyectosConvocatoriaR> getProyectosConvCerradasGI(List<Semillero> semilleros) {
		List<ProyectosConvocatoriaR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProyectosConvCerradas(s.getProyectos()));
		}
		return ordenarSalida(salida);
	}

	public List<ProyectosConvocatoriaR> getProyectosConvCerradas(List<Proyecto> lista) {
		List<ProyectosConvocatoriaR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			List<ProyectosConvocatoria> aux2 = p.getProyectosConvocatoria();
			if (!aux2.isEmpty()) {
				for (ProyectosConvocatoria pc : aux2) {
					if (!pc.getConvocatoria().getEstado().equals("ABIERTA")) {
						salida.add(createObject(p,pc));
					}
				}
			}
		}
		return ordenarSalida(salida);
	}

	private ProyectosConvocatoriaR createObject(Proyecto p, ProyectosConvocatoria pc) {
		return new ProyectosConvocatoriaR(
				p.getTitulo(),
				pc.getConvocatoria().getNombreConvocatoria(),
				extras.getFechaFormateada(pc.getConvocatoria().getFechaInicio()),
				extras.getFechaFormateada(pc.getConvocatoria().getFechaFinal()),
				pc.getConvocatoria().getEntidad(),
				p.getSemillero().getNombre(),
				pc.getIdProyecto());
	}

	public List<ProyectosConvocatoriaR> getParticipacionesProyecto(List<ProyectosConvocatoria> proyectosConvocatoria) {
		List<ProyectosConvocatoriaR> salida = new ArrayList<>();
		for (ProyectosConvocatoria pc : proyectosConvocatoria) {
			salida.add(new ProyectosConvocatoriaR(pc.getConvocatoria().getEstado(),
					pc.getConvocatoria().getNombreConvocatoria(),
					extras.getFechaFormateada(pc.getConvocatoria().getFechaInicio()),
					extras.getFechaFormateada(pc.getConvocatoria().getFechaFinal()), pc.getConvocatoria().getEntidad(),
					"", pc.getIdProyecto()));
		}
		return ordenarSalida(salida);
	}

	public List<ProyectosConvocatoriaR> getParticipacionesConv(List<Proyecto> aux) {
		List<ProyectosConvocatoriaR> salida = new ArrayList<>();
		for (Proyecto p : aux) {
			List<ProyectosConvocatoria> aux2 = p.getProyectosConvocatoria();
			if (!aux2.isEmpty()) {
				for (ProyectosConvocatoria pc : aux2) {
					salida.add(createObject(p,pc));
				}
			}
		}
		return ordenarSalida(salida);
	}
	
	public int countConvocatorias(List<ProyectosConvocatoriaR> aux3) {
		List<String> salida = new ArrayList<>();
		for (ProyectosConvocatoriaR i : aux3) {
			if (!salida.contains(i.getConvocatoria())) {
				salida.add(i.getConvocatoria());
			}
		}
		return salida.size();
	}

	private List<ProyectosConvocatoriaR> ordenarSalida(List<ProyectosConvocatoriaR> salida) {
		Collections.sort(salida, Collections.reverseOrder());
		return salida;
	}

}
