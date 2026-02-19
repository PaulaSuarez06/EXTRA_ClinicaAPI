package es.daw.clinicaapi.dto.request.invoice;


import java.util.List;

public record InvoiceIssueRequest(
    List<InvoiceLineCreateRequest> lines
) {}

