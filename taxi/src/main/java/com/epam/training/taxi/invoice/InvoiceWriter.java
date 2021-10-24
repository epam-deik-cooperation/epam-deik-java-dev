package com.epam.training.taxi.invoice;

import java.io.IOException;

public interface InvoiceWriter {

    void write(Invoice invoice) throws IOException;

}
