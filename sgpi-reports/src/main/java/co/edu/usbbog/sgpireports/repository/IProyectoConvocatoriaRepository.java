package co.edu.usbbog.sgpireports.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.usbbog.sgpireports.model.ProyectosConvocatoria;
import co.edu.usbbog.sgpireports.model.ProyectosConvocatoriaPK;

public interface IProyectoConvocatoriaRepository extends JpaRepository<ProyectosConvocatoria, ProyectosConvocatoriaPK> {

}
