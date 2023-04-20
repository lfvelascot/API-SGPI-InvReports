package co.edu.usbbog.sgpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.usbbog.sgpi.model.ProyectosConvocatoria;
import co.edu.usbbog.sgpi.model.ProyectosConvocatoriaPK;

public interface IProyectoConvocatoriaRepository extends JpaRepository<ProyectosConvocatoria, ProyectosConvocatoriaPK> {

}
