package ru.clevertec.writer.util.pdf;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.IOException;

/**
 * Класс для создания контента в созданном pdf-файле
 *
 * @author Лена Кечко
 */

public class CreatePdfDocumentContent<T> {

    /**
     * Поле с документом
     */
    private final Document document;

    /**
     * Конструктор создает документ размера A4
     * конфигурирует документ (шрифт, расположение текста)
     */
    public CreatePdfDocumentContent(PdfDocument pdfDocument) {
        document = new Document(pdfDocument, PageSize.A4);
        configDocument();
    }

    /**
     * Метод для добавления контента
     *
     * @param caption заголовок данных документа
     * @param entity  сущность
     */
    public void addContent(String caption, T entity) {
        addParagraph(caption, TextAlignment.CENTER);
        addParagraph(entity.toString());
    }

    /**
     * Метод добавляет контент по ширине
     *
     * @param text добавляемые текст
     */
    private void addParagraph(String text) {
        Paragraph paragraph = new Paragraph(text);
        document.setTextAlignment(TextAlignment.JUSTIFIED);
        document.add(paragraph);
    }

    /**
     * Метод добавляет контент с указанием выравнивания
     *
     * @param text      добавляемые текст
     * @param alignment тип выравнивания
     */
    private void addParagraph(String text, TextAlignment alignment) {
        Paragraph paragraph = new Paragraph(text);
        document.setTextAlignment(alignment);
        document.add(paragraph);
    }

    /**
     * Метод завершающий работу с контентом
     */
    public void stop() {
        document.close();
    }

    /**
     * Метод настраивает расположение текста
     * и применяет настройки для шрифта
     */
    private void configDocument() {
        document.setMargins(200, 70, 50, 70);
        PdfFont font = configFont();
        document.setFont(font);
    }

    /**
     * Метод, в котором задаются настройки шрифта с учетом русского языка
     */
    private PdfFont configFont() {
        final String FONT = "c:/windows/fonts/times.ttf";
        PdfFont font;
        try {
            font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
            return font;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
