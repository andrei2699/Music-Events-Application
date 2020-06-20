package export;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import static main.ApplicationResourceStrings.APPLICATION_NAME;

public class ExportAsPDF implements IExport {
    Document document = new Document();
    PdfWriter writer;

    @Override
    public void export(File file, List<ExportRow> rows, String pageTitle) {

        try {
            if (rows != null) {
                Font headerFont = FontFactory.getFont(FontFactory.COURIER, 15, Font.ITALIC, new CMYKColor(100, 0, 8, 67));
                Font titleFont = FontFactory.getFont(FontFactory.COURIER, 20, Font.BOLD, new CMYKColor(100, 0, 8, 45));

                writer = PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();

                Paragraph header = new Paragraph(APPLICATION_NAME, headerFont);
                header.setAlignment(Paragraph.ALIGN_RIGHT);
                document.add(header);

                document.add(new Paragraph("   "));

                Paragraph title = new Paragraph(pageTitle, titleFont);
                title.setAlignment(Paragraph.ALIGN_CENTER);
                document.add(title);

                document.add(new Paragraph("   "));
                document.add(new Paragraph("   "));

                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(90);
                table.setSpacingBefore(10f);
                table.setSpacingAfter(10f);

                float[] columnWidths = {0.6f, 1f};
                table.setWidths(columnWidths);

                PdfPCell[] cells = new PdfPCell[2 * rows.size()];
                for (int i = 0; i < cells.length; i++) {
                    cells[i] = new PdfPCell();
                    cells[i].setPaddingLeft(10);
                    cells[i].setPaddingBottom(10);
                    cells[i].setHorizontalAlignment(Element.ALIGN_CENTER);
                    cells[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cells[i].setUseBorderPadding(true);
                }

                for (int i = 0; i < cells.length; i = i + 2) {
                    cells[i].addElement(new Paragraph(rows.get(i / 2).getAttributeName()));
                    cells[i + 1].addElement(new Paragraph(rows.get(i / 2).getAttributeValue()));
                }

                for (PdfPCell cell : cells) {
                    table.addCell(cell);
                }

                document.add(table);

                document.close();
                writer.close();
            }
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
