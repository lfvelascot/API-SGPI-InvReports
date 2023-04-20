package co.edu.usbbog.sgpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.usbbog.sgpi.model.Participaciones;
import co.edu.usbbog.sgpi.model.ParticipacionesPK;

public interface IParticipacionesRepository extends JpaRepository<Participaciones, ParticipacionesPK>{

}
