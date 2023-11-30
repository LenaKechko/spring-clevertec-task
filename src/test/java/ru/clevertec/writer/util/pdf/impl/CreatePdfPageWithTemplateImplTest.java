package ru.clevertec.writer.util.pdf.impl;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static ru.clevertec.writer.IWriter.DEST_PATH;

@ExtendWith(MockitoExtension.class)
class CreatePdfPageWithTemplateImplTest {

    private final String FILE_TEMPLATE = Objects.requireNonNullElse(
            this.getClass().getClassLoader().getResource("Clevertec_Template.pdf"),
            "").toString();
    private static final String dest = DEST_PATH + "Test" + ".pdf";
    @Mock
    private PdfDocument destPdf;
    @Mock
    private PdfDocument srcPdf;
    @InjectMocks
    private CreatePdfPageWithTemplateImpl createPdfPage;

    @BeforeEach
    void setUp() throws IOException {

        srcPdf = mock(PdfDocument.class, withSettings()
                .useConstructor(new PdfReader(FILE_TEMPLATE))
                .defaultAnswer(CALLS_REAL_METHODS));

        destPdf = mock(PdfDocument.class, withSettings()
                .useConstructor(new PdfWriter(dest))
                .defaultAnswer(CALLS_REAL_METHODS));

        createPdfPage = mock(CreatePdfPageWithTemplateImpl.class, withSettings()
                .useConstructor(destPdf, srcPdf)
                .defaultAnswer(CALLS_REAL_METHODS));
    }

    @Test
    void createPageShouldCallAddNewPage() {
        // given

        // when
        createPdfPage.createPage();

        // then
        verify(destPdf).addNewPage();
    }

    @Test
    void createPageShouldUseTemplate() {
        // given

        // when
        createPdfPage.createPage();

        // then
        verify(srcPdf).getPage(1);
    }

    @AfterEach
    void tearDown() throws IOException {
        destPdf.close();
        Path path = Paths.get(dest);
        Files.deleteIfExists(path);
    }
}