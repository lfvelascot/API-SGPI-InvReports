package co.edu.usbbog.sgpireports.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.usbbog.sgpireports.model.Participaciones;
import co.edu.usbbog.sgpireports.model.ParticipacionesPK;

public interface IParticipacionesRepository extends JpaRepository<Participaciones, ParticipacionesPK>{

}
