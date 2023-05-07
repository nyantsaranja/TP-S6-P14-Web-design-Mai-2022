package itu.s6.tpseo.framework.pdfutils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MappedSuperclass;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
public class PdfGenerator {
    @JsonIgnore
    static PDType1Font font = PDType1Font.HELVETICA;
    @JsonIgnore
    static PDType1Font fontBold = PDType1Font.HELVETICA_BOLD;
    @JsonIgnore
    static float fontSize = 12;
    @JsonIgnore
    static float leading = 1.5f * fontSize;
    @JsonIgnore
    static float margin = 50;

    static String appName = "PDF Utils";

    public static byte[] createPDF(List<?> rows, String filename,String title,Class<?> objClass) throws Exception {
        String outputFileName = filename;
        File file = new File(outputFileName);
        System.out.println(file.getAbsolutePath());

        try (PDDocument document = new PDDocument()) {

            // create page
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            float tx = 50;
            float ty = page.getMediaBox().getHeight() - 150;


            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(fontBold, leading);
                // set color to light blue, such as in
                // set text to bold
                contentStream.newLineAtOffset(tx, ty);
                contentStream.showText(title);
                contentStream.endText();
            }

            // starting y position is whole page height subtracted by top and bottom margin
            float yStartNewPage = page.getMediaBox().getHeight() - (1 * margin);

            // we want table across whole page width (subtracted by left and right margin ofcourse)
            float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

            boolean drawContent = true;
            float bottomMargin = 70;

            // y position is your coordinate of top left corner of the table
            float yPosition = page.getMediaBox().getHeight() - (2 *margin);

            // write app name on last page at bottom right
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.beginText();
                contentStream.setFont(font, fontSize);
                contentStream.newLineAtOffset(page.getMediaBox().getWidth() - 200, 50);
                contentStream.showText("Generated by " + appName);
                contentStream.endText();
            }

            // add a date to top right of page
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.beginText();
                contentStream.setFont(font, fontSize);
                contentStream.newLineAtOffset(page.getMediaBox().getWidth() - 200, page.getMediaBox().getHeight() - 50);
                contentStream.showText("Date: " + LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                contentStream.endText();
            }
            // write "App" in the top left corner in artistically way and in multicolor
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ITALIC, leading*1.6f);
                contentStream.setNonStrokingColor(0, 100, 180);
                contentStream.newLineAtOffset(50, page.getMediaBox().getHeight() - 50);
                contentStream.showText("Vehicles Management");
                // draw an empty rectangle around the text
                contentStream.endText();
            }

            PDFTableBuilder builder = new PDFTableBuilder(objClass, rows, yPosition - 100, yStartNewPage, bottomMargin, tableWidth, margin, document, page);
            builder.draw();

            document.save(file);
        }
        return Files.readAllBytes(file.toPath());
    }
}