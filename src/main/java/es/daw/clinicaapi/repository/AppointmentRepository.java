package es.daw.clinicaapi.repository;

import es.daw.clinicaapi.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
