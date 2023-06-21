package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.datamodels.GrupoInvR;

@Service
public class GrupoInvRService {
	
	@Autowired
	private SemillerosRService semilleros;
	private MiselaneaService extras = new MiselaneaService();
	
	public List<GrupoInvR> getGruposInv(List<GrupoInvestigacion> aux) {
		List<GrupoInvR> salida = new ArrayList<>();
		for(GrupoInvestigacion g : aux) {
			GrupoInvR x = new GrupoInvR(g.getNombre(),
					extras.getFechaFormateada(g.getFechaFun()),
					g.getCategoria(),
					extras.getFechaFormateada(g.getFechaCat()));
			x.setNumSemilleros(String.valueOf(g.getSemilleros().size()));
			salida.add(x);
		}
		return salida;
	}
	

	public Map<String, Object> getTotalesActividad(Map<String, Object> map, List<Proyecto> aux, int anioInicio, int anioFin) {
		Integer[] lista = semilleros.getTotales(aux, anioInicio, anioFin);
		map.put("totalParticipacionesEventos",  String.valueOf(lista[0])+ " participacion(es) en eventos");
		map.put("totalPartcipacionesConv", String.valueOf(lista[1]) + " participacion(es) en convocatorias");
		map.put("totalProductos", String.valueOf(lista[2]) + " producto(s) existente(s)");
		map.put("totalProyectosFinalizados", String.valueOf(lista[3]) + " proyecto(s) finalizado(s)");
		map.put("TotalProyectosActivos", String.valueOf(lista[4]) + " proyecto(s) activo(s)");
		return map;
	}
	

}
