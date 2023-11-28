package ru.clevertec.writer.util.pdf.impl;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import ru.clevertec.writer.util.ICreatePage;

/**
 * Класс для создания страницы в документе без шаблона
 *
 * @author Лена Кечко
 */

public class CreatePdfPageImpl implements ICreatePage {

    /**
     * Поле с файлом для дальнейшего изменения
     */
    private PdfDocument destPdf = null;

    /**
     * Инициализирующий конструктор
     */
    public CreatePdfPageImpl(PdfDocument destPdf) {
        this.destPdf = destPdf;
    }

    /**
     * Создание страницы
     */
    public void createPage() {
        PdfPage page = destPdf.addNewPage();
    }
}
