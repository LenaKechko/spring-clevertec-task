package ru.clevertec.writer.impl;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import ru.clevertec.util.LoadPropertyFromFile;
import ru.clevertec.writer.IWriter;
import ru.clevertec.writer.util.pdf.CreatePdfDocumentContent;
import ru.clevertec.writer.util.pdf.impl.CreatePdfPageImpl;
import ru.clevertec.writer.util.pdf.impl.CreatePdfPageWithTemplateImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Класс создает pdf документ с заданным шаблоном, если он есть в папке resources
 * В документе имеется заголовок (по центру) и
 *
 * @author Лена Кечко
 */

public class WriterPdf<T> implements IWriter<T> {

    /**
     * Константа, определяющая путь к шаблону
     */
    private final String FILE_TEMPLATE = Objects.requireNonNullElse(
            this.getClass().getClassLoader().getResource("Clevertec_Template.pdf"),
            "").toString();

    /**
     * Метод создания результирующего pdf-файла
     *
     * @param caption заголовок данных в файле
     * @param entity  запись сущности
     * @return имя созданного файла
     */
    @Override
    public String createFile(String caption, T entity) {
        String dest = DEST_PATH + entity.getClass().getSimpleName() + "_"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".pdf";
        File folder = new File(LoadPropertyFromFile.getPath());
        System.out.println(folder);
        if (folder.mkdir()) {
            dest = folder + dest;
        }
        System.out.println(dest);

        PdfDocument destPdf = createPdfWriter(dest);
        if (FILE_TEMPLATE.isEmpty()) {
            new CreatePdfPageImpl(destPdf).createPage();
        } else {
            PdfDocument srcPdf = createPdfReader(FILE_TEMPLATE);
            new CreatePdfPageWithTemplateImpl(destPdf, srcPdf).createPage();
        }

        CreatePdfDocumentContent<T> content = new CreatePdfDocumentContent<>(destPdf);
        content.addContent(caption, entity);
        content.stop();
        return dest;
    }

    /**
     * Метод открывающий шаблон для чтения
     *
     * @param src путь в шаблону
     * @return объект PdfDocument,
     * если шаблона нет в папке resources, то возвращает null
     */
    private PdfDocument createPdfReader(String src) {
        PdfReader reader;
        try {
            reader = new PdfReader(src);
            return new PdfDocument(reader);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Метод создает файл для записи
     *
     * @param dest путь
     * @return объект PdfDocument,
     * если не удалось создать выбрасывает исключение RuntimeException
     */
    private PdfDocument createPdfWriter(String dest) {
        PdfWriter writer;
        try {
            writer = new PdfWriter(dest);
            return new PdfDocument(writer);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
