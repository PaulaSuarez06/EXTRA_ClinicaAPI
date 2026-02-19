package es.daw.clinicaapi.dto.request.invoice;

public record InvoiceLineCreateRequest(
    Long medicalServiceId,
    Integer qty
) {}

