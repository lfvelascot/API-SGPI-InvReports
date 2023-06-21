package co.edu.usbbog.sgpireports.service;

import java.util.List;

import co.edu.usbbog.sgpireports.model.Facultad;
import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.Programa;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.Semillero;
import net.minidev.json.JSONArray;

public interface IListasJSON {
	
	public JSONArray proyectosToJSONArray(List<Proyecto> lista);
	
	public JSONArray gruposToJSONArray(List<GrupoInvestigacion> lista);
	
	public JSONArray semillerosToJSONArray(List<Semillero> lista);
	
	public JSONArray facultadesToJSONArray(List<Facultad> lista);

	public JSONArray programasToJSONArray(List<Programa> lista);

}
