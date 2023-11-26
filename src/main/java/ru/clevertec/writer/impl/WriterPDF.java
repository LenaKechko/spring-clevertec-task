package ru.clevertec.writer.impl;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import ru.clevertec.writer.IWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import static com.itextpdf.layout.properties.Property.FONT;

public class WriterPDF<T> implements IWriter<T> {

    /**
     * Константа, определяющая путь к шаблону
     */
    private final String SRC_TEMPLATE = Objects.requireNonNull(this.getClass().getClassLoader().getResource("Clevertec_Template.pdf")).toString();

    @Override
    public void createFile(String text, T entity) {
        String dest = "print" + entity.getClass().getSimpleName() + ".pdf";
        PdfDocument srcPdf = createPdfReader(SRC_TEMPLATE);
        PdfDocument destPdf = createPdfWriter(dest);
        createDocumentWithTemplate(destPdf, srcPdf);
        addInfoToDocument(destPdf, text, entity);
    }

    private void createDocumentWithTemplate(PdfDocument destPdf, PdfDocument srcPdf) {
        // Opening a page from the existing PDF
        PdfPage origPage = srcPdf.getPage(1);

        PdfPage page = destPdf.addNewPage();
        PdfCanvas canvas = new PdfCanvas(page);
        // Add the object to the canvas
        PdfFormXObject pageCopy = null;
        try {
            pageCopy = origPage.copyAsFormXObject(destPdf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        canvas.addXObjectAt(pageCopy, 0, 0);
    }

    private PdfDocument createPdfReader(String src) {
        // Creating a PdfReader
        PdfReader reader = null;
        try {
            reader = new PdfReader(src);
            return new PdfDocument(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Creating a PdfDocument objects
    }

    private PdfDocument createPdfWriter(String dest) {
        // Creating a PdfWriter object
        PdfWriter writer = null;
        try {
            writer = new PdfWriter(dest);
            return new PdfDocument(writer);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addInfoToDocument(PdfDocument pdfDocument, String text, T entity) {
        // Creating a Document object
        final String FONT = "c:/windows/fonts/times.ttf";
        PdfFont font = null;
        try {
            font = PdfFontFactory.createFont(FONT, "Cp1251", PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Document doc = new Document(pdfDocument, PageSize.A4);
        doc.setMargins(200, 70, 50, 70);
        doc.setFont(font);
        // Creating an Area Break
        // Adding area break to the PDF
        doc.setTextAlignment(TextAlignment.CENTER);
        doc.add(new Paragraph(text));
        doc.setTextAlignment(TextAlignment.JUSTIFIED);
        doc.add(new Paragraph(entity.toString()));
        // Closing the document
        doc.close();
    }

}
