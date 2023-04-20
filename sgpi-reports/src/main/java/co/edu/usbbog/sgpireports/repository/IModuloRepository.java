package co.edu.usbbog.sgpireports.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import co.edu.usbbog.sgpireports.model.Actividad;
import co.edu.usbbog.sgpireports.model.Actividades;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import co.edu.usbbog.sgpireports.model.Modulo;
import net.minidev.json.JSONObject;


public interface IModuloRepository extends JpaRepository<Modulo, String>{

}
