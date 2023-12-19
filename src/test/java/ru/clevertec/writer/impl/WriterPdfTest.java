package ru.clevertec.writer.impl;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import org.junit.jupiter.api.Test;
import ru.clevertec.dto.AnimalDto;
import ru.clevertec.util.AnimalTestData;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WriterPdfTest {

    @Test
    void createFileShouldCheckContent() throws IOException {
        // given
        String testCaption = "My caption";
        AnimalDto testAnimalDto = AnimalTestData.builder().build().buildAnimalDto();
        String expected = (testCaption + "\n" + testAnimalDto.toString())
                .replaceAll(" ", "")
                .replaceAll("\n", "");
        WriterPdf<AnimalDto> writerPdf = new WriterPdf<>();

        // when
        String nameNewFile = writerPdf.createFile(testCaption, testAnimalDto);

        // then
        PdfReader reader = new PdfReader(nameNewFile);
        TextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
        String actual = PdfTextExtractor.getTextFromPage(reader, 1, strategy)
                .trim()
                .replaceAll(" ", "")
                .replaceAll("\n", "");
        reader.close();

        assertEquals(expected, actual);
    }

}