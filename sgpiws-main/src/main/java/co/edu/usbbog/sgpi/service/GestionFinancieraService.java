package co.edu.usbbog.sgpi.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.usbbog.sgpi.model.Compra;
import co.edu.usbbog.sgpi.model.Presupuesto;
import co.edu.usbbog.sgpi.model.Proyecto;
import co.edu.usbbog.sgpi.repository.ICompraRepository;
import co.edu.usbbog.sgpi.repository.IPresupuestoRepository;
import co.edu.usbbog.sgpi.repository.IProyectoRepository;
import net.minidev.json.JSONObject;

@Service
public class GestionFinancieraService implements IGestionFinancieraService {

	@Autowired
	private IPresupuestoRepository presupuestoRepo;

	@Autowired
	private IProyectoRepository proyectoRepo;

	@Autowired
	private ICompraRepository compraRepo;

	// metodo que traera el presupuesto de un metodo en especifico
	@Override
	public List<Presupuesto> PresupuestoPorProyecto(int proyecto) {
		Proyecto pro = proyectoRepo.getById(proyecto);
		List<Presupuesto> x = new ArrayList<>();
		if (pro != null) {
			List<Presupuesto> presupuesto = presupuestoRepo.findByProyecto(proyecto);
			return presupuesto;
		} else {
			return x;
		}
	}

	// metodo que eliminara el presupuesto de un proyecto
	@Override
	public boolean eliminarPresupuesto(int id) {
		List<Compra> compras = compraRepo.findByPresupuesto(id);
		boolean presupuesto = presupuestoRepo.existsById(id);

		if (compras.isEmpty() && presupuesto == true) {
			presupuestoRepo.deleteById(id);
			return !presupuestoRepo.existsById(id);

		}
		return false;
	}

	// metodo que asigna un presupuesto a un proyecto
	@Override
	public boolean crearPresupuesto(Presupuesto presupuesto) {
		Proyecto pro = proyectoRepo.getById(presupuesto.getProyecto().getId());
		List<Presupuesto> b = pro.getPresupuestos();
		if (b.isEmpty()) {
			Presupuesto pre = presupuestoRepo.save(presupuesto);
			return presupuestoRepo.existsById(pre.getId());
		} else {
			return false;
		}
	}

	// metodo para actualizar un presupuesto
	@Override
	public boolean modificarPresupuesto(int id, String monto, String fecha, String descripcion) {
		Presupuesto presupuesto = presupuestoRepo.getById(id);
		if (monto != "") {
			presupuesto.setMonto(Double.parseDouble(monto));
		}
		if (fecha != "") {
			presupuesto.setFecha(LocalDate.parse(fecha));
		}
		if (descripcion != "") {
			presupuesto.setDescripcion(descripcion);
		}
		presupuestoRepo.save(presupuesto);
		return !presupuestoRepo.existsById(presupuesto.getId());
	}

	// metodo que trae toda la informacion del presupuesto escogido
	@Override
	public Presupuesto presupuestoporid(int id) {
		Presupuesto presupuesto = presupuestoRepo.getById(id);
		return presupuesto;
	}

	// metodo que traera las compras de un presupuesto
	@Override
	public List<Compra> CompraPorPresupuesto(int presupuesto) {
		Presupuesto pre = presupuestoRepo.getById(presupuesto);
		List<Compra> x = new ArrayList<>();
		if (pre != null) {
			List<Compra> compras = compraRepo.findByPresupuesto(presupuesto);
			return compras;
		}
		return x;
	}

	// metodo que eliminara la compra
	@Override
	public boolean eliminarCompra(int id) {
		boolean compras = compraRepo.existsById(id);

		if (compras == true) {
			compraRepo.deleteById(id);
			return !compraRepo.existsById(id);
		}
		return false;
	}

	// metodo que creara la compra
	@Override
	public boolean crearCompra(Compra compra, int presupuesto) {

		Presupuesto presu = presupuestoRepo.getById(presupuesto);
		if (presu == null) {
			return false;
		} else {

			compra.setPresupuesto(presu);
			compraRepo.save(compra);
		}

		return compraRepo.existsById(compra.getId());

	}

	// metodo que modificara la compra
	public boolean modificarCompra(int id, String nombre, String tipo, String link, String descripcion) {
		Compra compra = compraRepo.getById(id);
		if (nombre != "") {
			compra.setNombre(nombre);
		}
		if (tipo != "") {
			compra.setTipo(tipo);
		}

		if (link != "") {
			compra.setLink(link);
		}
		if (descripcion != "") {
			compra.setDescripcion(descripcion);
		}
		compraRepo.save(compra);
		return !compraRepo.existsById(compra.getId());
	}

	// metodo que traera la informacion de la compra escogida
	@Override
	public Compra compraporid(int id) {
		Compra compra = compraRepo.getById(id);
		return compra;
	}

	@Override
	public boolean autorizarCompra(int Estado) {
		// TODO Auto-generated method stub
		return false;
	}

	// metodo para realizar la compra teniendo en cuenta las compras totales y
	// teniendo en cuenta que no se exceda el presupuesto
	@Override
	public String realziarCompra(int compra, String codigo, LocalDate fechaCompra, Double valor, int estado) {
		Compra comp = compraRepo.getById(compra);

		JSONObject presupuestototal = presupuestoTotal(comp.getPresupuesto().getId());
		JSONObject comprastotales = new JSONObject();
		if (comprasTotales(comp.getPresupuesto().getId()) == null) {
			comprastotales.put("SUM(valor)", "0");
		} else {
			comprastotales = comprasTotales(comp.getPresupuesto().getId());
		}
		String pre = presupuestototal.getAsString("monto");
		String com = comprastotales.getAsString("SUM(valor)");

		double presupuesto = Double.parseDouble(pre);
		double compras = Double.parseDouble(com);

		if (presupuesto < (compras + valor)) {
			return "no se puede realizar la compra, el presupuesto se a excedido";
		}

		if (fechaCompra.isBefore(comp.getFechaSolicitud())) {
			return "la fecha debe ser mayor a la fecha de solicitud";
		}
		comp.setCodigoCompra(codigo);
		comp.setFechaCompra(fechaCompra);
		comp.setEstado(estado);
		comp.setValor(valor);
		compraRepo.save(comp);

		return "compra creada";
	}

	// metodo para actualizar el estado de la compra
	@Override
	public boolean actualizarestadoCompra(int compra, int estado) {
		Compra comp = compraRepo.getById(compra);
		if (!compraRepo.existsById(compra)) {
			return false;
		}
		comp.setEstado(estado);
		compraRepo.save(comp);

		return compraRepo.existsById(comp.getId());
	}

	// metodoq ue obtiene todas las compras de un presupuesto
	@Override
	public JSONObject comprasTotales(int presupuesto) {
		JSONObject comp = compraRepo.ComprasTotalesDelPresupuesto(presupuesto);
		return comp;
	}

	// metodo quie obtiene el presupuesto
	@Override
	public JSONObject presupuestoTotal(int presupuesto) {
		JSONObject presu = compraRepo.Presupuestototal(presupuesto);
		return presu;
	}

	// metodo que obtiene las compras teniendo en cuenta un estado en especifico
	@Override
	public List<JSONObject> comprasPorEstado(int estado, int presupuesto) {
		List<JSONObject> com = compraRepo.ComprasPorEstado(estado, presupuesto);
		return com;
	}

}