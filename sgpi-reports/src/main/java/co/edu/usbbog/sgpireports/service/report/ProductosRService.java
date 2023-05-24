package co.edu.usbbog.sgpireports.service.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Producto;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.ProyectosConvocatoria;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.datamodels.ProductoR;

@Service
public class ProductosRService {




	public List<ProductoR> getProductosGI(List<Semillero> semilleros) {
		List<ProductoR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProductosSemillero(s.getProyectos()));
		}
		return salida;
	}

	public List<ProductoR> getProductosSemillero(List<Proyecto> lista) {
		List<ProductoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			List<Producto> aux = p.getProductos();
			if (!aux.isEmpty()) {
				salida.addAll(getProductosProyecto(aux));
			}
		}
		return salida;
	}

	private String getFechaFormateada(LocalDate fecha) {
		return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // 17-02-2022
	}

	public List<ProductoR> getProductosProyectosActSemillero(List<Proyecto> lista) {
		List<ProductoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (p.getFechaFin() == null) {
				List<Producto> aux = p.getProductos();
				if (!aux.isEmpty()) {
					for (Producto pp : aux) {
						salida.add(new ProductoR(p.getTitulo(), pp.getTituloProducto(), pp.getTipoProducto(),
								pp.getUrlRepo(), getFechaFormateada(pp.getFecha())));
					}
				}
			}
		}
		return salida;
	}

	public List<ProductoR> getProductosProyectosFin(List<Proyecto> lista) {
		List<ProductoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (p.getFechaFin() != null) {
				if (!p.getProductos().isEmpty()) {
					for (Producto pp : p.getProductos()) {
						salida.add(new ProductoR(p.getTitulo(), pp.getTituloProducto(), pp.getTipoProducto(),
								pp.getUrlRepo(), getFechaFormateada(pp.getFecha())));
					}
				}
			}
		}
		return salida;
	}

	public List<ProductoR> getProductosProyectosActGI(List<Semillero> semilleros) {
		List<ProductoR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProductosProyectosActSemillero(s.getProyectos()));
		}
		return salida;
	}

	public List<ProductoR> getProductosProyectosFinGI(List<Semillero> semilleros) {
		List<ProductoR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProductosProyectosFin(s.getProyectos()));
		}
		return salida;
	}

	public List<ProductoR> getProductosProyecto(List<Producto> productos) {
		List<ProductoR> salida = new ArrayList<>();
		for (Producto p : productos) {
			salida.add(new ProductoR(p.getProyecto().getTitulo(), p.getTituloProducto(), p.getTipoProducto(), p.getUrlRepo(),
					getFechaFormateada(p.getFecha())));
		}
		return salida;
	}

	public List<ProductoR> getProductosProyectosSemConv(List<Proyecto> lista) {
		List<ProductoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (!p.getProductos().isEmpty() && !p.getProyectosConvocatoria().isEmpty()) {
				for (Producto pc : p.getProductos()) {
					salida.add(new ProductoR(p.getTitulo(), pc.getTituloProducto(), pc.getTipoProducto(),
							pc.getUrlRepo(), getFechaFormateada(pc.getFecha())));
				}
			}
		}
		return salida;
	}
	
	public List<ProductoR> getProductosProyectosSemConvA(List<Proyecto> lista) {
		List<ProductoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (!p.getProductos().isEmpty() && !p.getProyectosConvocatoria().isEmpty()) {
				for(ProyectosConvocatoria pc : p.getProyectosConvocatoria()) {
					if(pc.getConvocatoria().getEstado().equals("ABIERTA")) {
						salida.addAll(getProductosProyecto(p.getProductos()));
						break;
					}
				}
			}
		}
		return salida;
	}

	public List<ProductoR> getProductosProyectosGIConv(List<Semillero> semilleros) {
		List<ProductoR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProductosProyectosSemConv(s.getProyectos()));
		}
		return salida;
	}
	
	public List<ProductoR> getProductosProyectosGIConvA(List<Semillero> semilleros) {
		List<ProductoR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProductosProyectosSemConvA(s.getProyectos()));
		}
		return salida;
	}

	public List<ProductoR> getProductosProyectosESem(List<Proyecto> proyectos) {
		List<ProductoR> salida = new ArrayList<>();
		for (Proyecto p : proyectos) {
			if (!p.getParticipaciones().isEmpty() && !p.getProductos().isEmpty()) {
				salida.addAll(getProductosProyecto(p.getProductos()));
			}
		}
		return salida;
	}

	public List<ProductoR> getProductosProyectosEGI(List<Semillero> semilleros) {
		List<ProductoR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProductosProyectosESem(s.getProyectos()));
		}
		return salida;
	}

}
