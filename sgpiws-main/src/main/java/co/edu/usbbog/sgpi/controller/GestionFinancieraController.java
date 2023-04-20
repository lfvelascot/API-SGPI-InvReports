package co.edu.usbbog.sgpi.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbbog.sgpi.model.Compra;
import co.edu.usbbog.sgpi.model.Presupuesto;
import co.edu.usbbog.sgpi.model.Semillero;
import co.edu.usbbog.sgpi.model.Usuario;
import co.edu.usbbog.sgpi.service.IGestionFinancieraService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@RestController
@CrossOrigin
@RequestMapping("/gestionfinanciera")
public class GestionFinancieraController {
	@Autowired
	private IGestionFinancieraService gestionFinancieraService;

	
	@GetMapping(value = "/listarpresupuestoporproyecto/{id}")
	public JSONArray listarPresupuestoPorProyecto(@PathVariable int id) {
		
		JSONArray salida = new JSONArray(); 
		List<Presupuesto> pre = gestionFinancieraService.PresupuestoPorProyecto(id);
		for (Presupuesto presupuesto : pre) {
			salida.add(presupuesto.toJson()) ;
		}
		return salida;		
	}
	
	@GetMapping(value = "/eliminarpresupuesto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String eliminarSemillero(@PathVariable int id) {	
		if(gestionFinancieraService.eliminarPresupuesto(id)) {
			return "Eliminado";
		}
		
		return "No se puede eliminar";
	}
	
	@PostMapping(value = "/crearpresupuesto")
	public JSONObject crearSemilleros(@RequestBody Presupuesto presupuesto) {
		JSONObject salida = new JSONObject();
		if(gestionFinancieraService.crearPresupuesto(presupuesto)) {
			salida.put("respuesta", "se creo");			
		}else {
			salida.put("respuesta", "NO se creo");
		}
		
		return salida;
	}
	
	
	@PostMapping("/modificarpresupuesto")
	public JSONObject modificarPresupuesto(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		if(!gestionFinancieraService.modificarPresupuesto( Integer.parseInt(
				entrada.getAsString("id")) 
				, entrada.getAsString("monto")
				, entrada.getAsString("fecha")
				, entrada.getAsString("descripcion"))) {
			salida.put("respuesta", "se actualizo el presupuesto");
		}else {
			salida.put("respuesta", "no se pudo actualizar el presupuesto");
		}
		return salida;
	}
	
	@GetMapping(value = "/presupuestoid/{id}")
	public JSONObject presupuestolistarPorId(@PathVariable int id) {

		JSONObject x= new JSONObject();	
		if(gestionFinancieraService.presupuestoporid(id) !=null) {
			Presupuesto presupuesto = gestionFinancieraService.presupuestoporid(id);
			return presupuesto.toJson();
		}
		else {
			return x;
		}	

	}
	
	@PostMapping("/modificarcompra")
	public JSONObject modificarCompra(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		if(!gestionFinancieraService.modificarCompra(Integer.parseInt(
				entrada.getAsString("id")), 
				entrada.getAsString("nombre"), 
				entrada.getAsString("tipo"), 
				entrada.getAsString("link"),
				entrada.getAsString("descripcion"))) {
			salida.put("respuesta", "se actualizo la compra");
		}else {
			salida.put("respuesta", "no se pudo actualizar la compra");
		}
		return salida;
	}
	
	@GetMapping(value = "/compraid/{id}")
	public JSONObject compralistarPorId(@PathVariable int id) {

		JSONObject x= new JSONObject();	
		if(gestionFinancieraService.compraporid(id) !=null) {
			Compra compra = gestionFinancieraService.compraporid(id);
			return compra.toJson();
		}
		else {
			return x;
		}	

	}
	
	@GetMapping(value = "/listarcomprasdelpresupuesto/{id}")
	public JSONArray listarComprasDelPresupuesto(@PathVariable int id) {
		
		JSONArray salida = new JSONArray(); 
		List<Compra> com = gestionFinancieraService.CompraPorPresupuesto(id);
		for (Compra compra : com) {
			salida.add(compra.toJson()) ;
		}
		return salida;		
	}
	
	@GetMapping(value = "/eliminarcompra/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String eliminarCompra(@PathVariable int id) {	
		boolean x = gestionFinancieraService.eliminarCompra(id);
		if(x) {
			return "Eliminado";
		}
		else {
			return "No se puede eliminar";
			
		}
		
	}
	
	@PostMapping(value = "/crearcompra")
	public JSONObject crearCompra(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
			
			Compra compra = new Compra(

					LocalDate.parse(entrada.getAsString("fecha_solicitud")), 
					entrada.getAsString("nombre"),
					entrada.getAsString("tipo"),
					Integer.parseInt(entrada.getAsString("estado")),
					entrada.getAsString("descripcion"),
					entrada.getAsString("link"));
			
			
			
			if(gestionFinancieraService.crearCompra(
					compra,
					Integer.parseInt(entrada.getAsString("presupuesto")))) {
				salida.put("respuesta", "compra creada");
			}else{
				salida.put("respuesta", "no se pudo");
			}
					

		return salida;
		
	}
	
	
	
	
	@PostMapping(value = "/realizarcompra")
	public JSONObject realziarCompra(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		if(gestionFinancieraService.realziarCompra(
				Integer.parseInt(entrada.getAsString("id")),
				entrada.getAsString("codigo_compra"),
				LocalDate.parse(entrada.getAsString("fecha_compra")),
				 Double.parseDouble(entrada.getAsString("valor")),
						 Integer.parseInt(entrada.getAsString("estado"))
			)=="compra creada") {
			salida.put("respuesta", "se creo");
		}
		else if(gestionFinancieraService.realziarCompra(
				Integer.parseInt(entrada.getAsString("id")),
				entrada.getAsString("codigo_compra"),
				LocalDate.parse(entrada.getAsString("fecha_compra")),
				 Double.parseDouble(entrada.getAsString("valor")),
						 Integer.parseInt(entrada.getAsString("estado"))
			)=="la fecha debe ser mayor a la fecha de solicitud") {
			salida.put("respuesta", "la fecha debe ser mayor a la fecha de solicitud");
		}else if(gestionFinancieraService.realziarCompra(
				Integer.parseInt(entrada.getAsString("id")),
				entrada.getAsString("codigo_compra"),
				LocalDate.parse(entrada.getAsString("fecha_compra")),
				 Double.parseDouble(entrada.getAsString("valor")),
						 Integer.parseInt(entrada.getAsString("estado"))
			)=="no se puede realizar la compra, el presupuesto se a excedido") {
			salida.put("respuesta", "no se puede realizar la compra, el presupuesto se a excedido");
		}else {
			salida.put("respuesta", "NO se creo");
		}
		return salida;
	}
	
	@PostMapping(value = "/actualizarestadocompra")
	public JSONObject actualizarEstadoCompra(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		if(gestionFinancieraService.actualizarestadoCompra(
				Integer.parseInt(entrada.getAsString("id_compra")),
				Integer.parseInt(entrada.getAsString("estado"))
			)) {
			salida.put("respuesta", "se creo");
		}else {
			salida.put("respuesta", "NO se creo");
		}
		return salida;
	}
	
	@GetMapping(value = "/comprastotalesporpresupuesto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject comprasTotales(@PathVariable int id) {
		JSONObject salida = new JSONObject();
		JSONObject comprastotales = gestionFinancieraService.comprasTotales(id);
		
		if(comprastotales != null) {
			salida.put("salida","$"+ comprastotales.getAsString("SUM(valor)"));
			
		}
		else {
			salida.put("salida", "no tiene compras realizadas");
			
		}
		return salida;
		
		}
	
	
	@GetMapping(value = "/comprassolicitadas/{presupuesto}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<JSONObject> compraCreadas(@PathVariable int presupuesto) {
		List<JSONObject> comprasestado = gestionFinancieraService.comprasPorEstado(1,presupuesto);
		return comprasestado;
		
		}
	
	@GetMapping(value = "/comprasaceptadas/{presupuesto}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<JSONObject> comprasAceptadas(@PathVariable int presupuesto) {
		List<JSONObject> comprasestado = gestionFinancieraService.comprasPorEstado(2,presupuesto);
		return comprasestado;
		
		}
	@GetMapping(value = "/comprasrealizadas/{presupuesto}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<JSONObject> comprasRealizadas(@PathVariable int presupuesto) {
		List<JSONObject> comprasestado = gestionFinancieraService.comprasPorEstado(3,presupuesto);
		return comprasestado;
		
		}
	@GetMapping(value = "/comprasrechazadas/{presupuesto}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<JSONObject> comprasRechadas(@PathVariable int presupuesto) {
		List<JSONObject> comprasestado = gestionFinancieraService.comprasPorEstado(4,presupuesto);
		return comprasestado;
		
		}
	
	
	
	
	
			
	}

