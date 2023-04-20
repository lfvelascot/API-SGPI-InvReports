package co.edu.usbbog.sgpireports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.usbbog.sgpireports.model.Actividad;


interface IActividadRepository extends JpaRepository<Actividad, String> {

}
