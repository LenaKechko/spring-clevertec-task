package ru.clevertec.writer.util.pdf.impl;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import ru.clevertec.writer.util.ICreatePage;

import java.io.IOException;

/**
 * Класс для создания страницы в документе с шаблоном
 *
 * @author Лена Кечко
 */
public class CreatePdfPageWithTemplateImpl implements ICreatePage {

    /**
     * Поле с файлом для дальнейшего изменения
     */
    private final PdfDocument destPdf;
    /**
     * Поле с шаблоном
     */
    private final PdfDocument srcPdf;

    /**
     * Инициализирующий конструктор
     */
    public CreatePdfPageWithTemplateImpl(PdfDocument destPdf, PdfDocument srcPdf) {
        this.destPdf = destPdf;
        this.srcPdf = srcPdf;
    }

    /**
     * Создания страницы с шаблоном
     */
    public void createPage() {
        PdfPage origPage = srcPdf.getPage(1);
        PdfPage page = destPdf.addNewPage();
        PdfCanvas canvas = new PdfCanvas(page);
        PdfFormXObject pageCopy;
        try {
            pageCopy = origPage.copyAsFormXObject(destPdf);
            canvas.addXObjectAt(pageCopy, 0, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
