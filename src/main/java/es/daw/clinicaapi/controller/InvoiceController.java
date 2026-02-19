package es.daw.clinicaapi.controller;

import es.daw.clinicaapi.dto.request.invoice.InvoiceIssueRequest;
import es.daw.clinicaapi.dto.response.invoice.InvoiceResponse;
import es.daw.clinicaapi.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping("/{id}/invoice")
    @PreAuthorize("hasAnyRole('ADMIN','BILLING')")
    public ResponseEntity<InvoiceResponse> issueInvoice(
            @PathVariable("id") Long appointmentId,
            @RequestBody InvoiceIssueRequest request
    ) {
        InvoiceResponse created = invoiceService.issueInvoiceForAppointment(appointmentId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
