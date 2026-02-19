package es.daw.clinicaapi.service;

import es.daw.clinicaapi.dto.request.invoice.InvoiceIssueRequest;
import es.daw.clinicaapi.dto.request.invoice.InvoiceLineCreateRequest;
import es.daw.clinicaapi.dto.response.invoice.InvoiceResponse;
import es.daw.clinicaapi.exception.BadRequestException;
import es.daw.clinicaapi.mapper.InvoiceMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class InvoiceService {


    @Transactional
    public InvoiceResponse issueInvoiceForAppointment(Long appointmentId, InvoiceIssueRequest req) {


//        No puede haber servicios duplicados en el json request; si no → BadRequestException (400)
        checkDuplicateServices(req.lines());

//        La cita debe existir; si no → NotFoundException (404).
//                La cita no puede estar ya facturada; si no → ConflictException (409).
//                La cita no puede estar CANCELLED. si no → BussinessRuleException (422).
//                La cita debe estar COMPLETED si no → BussinessRuleException (422).
//                Cada medicalServiceId debe existir y estar active=true; si no: inexistente → 404 / inactivo → 422 (el primero que falle)
//        Las líneas de factura se emitirán sin aplicar ningún tipo de descuento.
//        El tipo de IVA aplicado en las líneas de factura será siempre del 21% (VAT_21). El cálculo de impuestos y totales debe realizarse en el servidor.
//        Los importes económicos deberán redondearse a dos decimales siguiendo el redondeo estándar.

        //return InvoiceMapper.toResponse(null);
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
