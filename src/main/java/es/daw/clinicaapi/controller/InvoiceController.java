package es.daw.clinicaapi.controller;

import es.daw.clinicaapi.dto.request.invoice.InvoiceIssueRequest;
import es.daw.clinicaapi.dto.response.invoice.InvoiceResponse;
import es.daw.clinicaapi.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
//@Validated // ????? en observación: no es necesario porque el @Valid del método ya hace la validación, el @Validated se usaría si queremos validar directamente los parámetros del método (ej: @RequestParam) o si queremos usar validaciones a nivel de clase (ej: @Valid en un parámetro de tipo objeto que a su vez tiene validaciones en sus campos)
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping("/{id}/invoice")
    @PreAuthorize("hasAnyRole('ADMIN','BILLING')")
    public ResponseEntity<InvoiceResponse> issueInvoice(
            @PathVariable("id") Long appointmentId,
            @RequestBody @Valid InvoiceIssueRequest request
    ) {
        InvoiceResponse created = invoiceService.issueInvoiceForAppointment(appointmentId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
