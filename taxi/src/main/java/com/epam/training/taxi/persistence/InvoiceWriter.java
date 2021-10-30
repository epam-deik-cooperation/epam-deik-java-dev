package com.epam.training.taxi.persistence;

import com.epam.training.taxi.invoice.Invoice;

import java.io.IOException;

public interface InvoiceWriter {

    void write(Invoice invoice) throws IOException;

}
