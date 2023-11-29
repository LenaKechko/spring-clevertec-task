package ru.clevertec.writer.util.pdf.impl;

import com.itextpdf.kernel.pdf.PdfDocument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreatePdfPageImplTest {

    @Mock
    private PdfDocument destPdf;

    @InjectMocks
    private CreatePdfPageImpl createPdfPage;

    @Test
    void createPageShouldCallAddNewPage() {
        // given

        // when
        createPdfPage.createPage();

        // then
        verify(destPdf).addNewPage();
    }
}