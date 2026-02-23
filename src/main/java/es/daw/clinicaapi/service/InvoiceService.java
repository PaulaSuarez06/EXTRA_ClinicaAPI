package es.daw.clinicaapi.service;

import es.daw.clinicaapi.dto.request.invoice.InvoiceIssueRequest;
import es.daw.clinicaapi.dto.request.invoice.InvoiceLineCreateRequest;
import es.daw.clinicaapi.dto.response.invoice.InvoiceResponse;
import es.daw.clinicaapi.entity.Appointment;
import es.daw.clinicaapi.exception.BadRequestException;
import es.daw.clinicaapi.exception.ConflictException;
import es.daw.clinicaapi.exception.NotFoundException;
import es.daw.clinicaapi.repository.AppointmentRepository;
import es.daw.clinicaapi.repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final AppointmentRepository appointmentRepository;
    private final InvoiceRepository invoiceRepository;

    @Transactional
    public InvoiceResponse issueInvoiceForAppointment(Long appointmentId, InvoiceIssueRequest req) {

        // La cita debe existir; si no → NotFoundException (404).
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("Appointment not found with id: "+appointmentId));

        // La cita no puede estar ya facturada; si no → ConflictException (409).
        // solo refleja lo que hay en ese objeto en memoria (y cómo se haya cargado).
        // No es una garantía de unicidad en BD.
        // Si invoice es LAZY, el campo puede no estar cargado. Revisad la entidad Appointment.
        if (appointment.getInvoice() != null || invoiceRepository.existsByAppointmentId(appointmentId)) {
            throw new ConflictException("Appointment already has an invoice");
        }

        // No puede haber servicios duplicados en el json request; si no → BadRequestException (400)
        checkDuplicateServices(req.lines());

        // TODO: La cita no puede estar CANCELLED. si no → BussinessRuleException (422).
        // TODO: La cita debe estar COMPLETED si no → BussinessRuleException (422).
        // TODO: Cada medicalServiceId debe existir y estar active=true; si no: inexistente → 404 / inactivo → 422
        // TODO: Las líneas de factura se emitirán sin aplicar ningún tipo de descuento.
        // TODO: El tipo de IVA aplicado en las líneas de factura será siempre del 21% (VAT_21).
        // TODO: El cálculo de impuestos y totales debe realizarse en el servidor.
        // TODO: Los importes económicos deberán redondearse a dos decimales siguiendo el redondeo estándar.

        return null;

    }

    private void checkDuplicateServices(List<InvoiceLineCreateRequest> lines) {
        Set<Long> ids = new HashSet<>();
        for (InvoiceLineCreateRequest line : lines) {
            if(!ids.add(line.medicalServiceId()))
                throw new BadRequestException("Duplicate medical service id in lines: "+line.medicalServiceId());
        }
    }


}
