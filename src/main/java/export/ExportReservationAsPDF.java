package export;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import main.LoggedUserData;
import models.BarModel;
import models.cards.ReservationCardModel;
import services.ServiceProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static main.ApplicationResourceStrings.*;

public class ExportReservationAsPDF {
    Document document = new Document();
    PdfWriter writer;
    ReservationCardModel reservationCardModel;

    public ExportReservationAsPDF(File file, ReservationCardModel reservationCardModel) {

        try {
            this.reservationCardModel = reservationCardModel;
            if (reservationCardModel != null) {
                Font headerFont = FontFactory.getFont(FontFactory.COURIER, 14, Font.ITALIC, new CMYKColor(58, 0, 5, 29));
                Font titleFont = FontFactory.getFont(FontFactory.COURIER, 20, Font.BOLD, new CMYKColor(100, 0, 9, 41));

                writer = PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();

                Paragraph header = new Paragraph(APPLICATION_NAME, headerFont);
                header.setAlignment(Paragraph.ALIGN_RIGHT);
                document.add(header);

                document.add(new Paragraph("   "));

                Paragraph title = new Paragraph(RESERVATION_TEXT, titleFont);
                title.setAlignment(Paragraph.ALIGN_CENTER);
                document.add(title);

                document.add(new Paragraph("   "));
                document.add(new Paragraph("   "));

                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10f);
                table.setSpacingAfter(10f);

                float[] columnWidths = {1f, 1f};
                table.setWidths(columnWidths);

                PdfPCell[] cells = new PdfPCell[16];
                for (int i = 0; i < cells.length; i++) {
                    cells[i] = new PdfPCell();
                    cells[i].setPaddingLeft(10);
                    cells[i].setPaddingBottom(10);
                    cells[i].setHorizontalAlignment(Element.ALIGN_CENTER);
                    cells[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cells[i].setUseBorderPadding(true);
                }

                cells[0].addElement(new Paragraph(EVENT_NAME_TEXT));
                cells[2].addElement(new Paragraph(BAR_NAME_TEXT));
                cells[4].addElement(new Paragraph(BAR_ADRESS_TEXT));
                cells[6].addElement(new Paragraph(ARTIST_NAME_TEXT));
                cells[8].addElement(new Paragraph(DATE_TEXT));
                cells[10].addElement(new Paragraph(HOUR_TEXT));
                cells[12].addElement(new Paragraph(SEAT_NUMBER_TEXT));
                cells[14].addElement(new Paragraph(RESERVATION_MADE_BY_TEXT));

                BarModel barModel = ServiceProvider.getBarService().getBar(reservationCardModel.getEventModel().getBar_manager_id());

                cells[1].addElement(new Paragraph(reservationCardModel.getEventModel().getName()));
                cells[3].addElement(new Paragraph(reservationCardModel.getBarName()));
                cells[5].addElement(new Paragraph(barModel.getAddress()));
                cells[7].addElement(new Paragraph(reservationCardModel.getArtistName()));
                cells[9].addElement(new Paragraph(reservationCardModel.getEventModel().getDate()));
                cells[11].addElement(new Paragraph(reservationCardModel.getEventModel().getStart_hour() + ""));
                cells[13].addElement(new Paragraph(reservationCardModel.getReservationModel().getReserved_seats() + ""));
                cells[15].addElement(new Paragraph(LoggedUserData.getInstance().getUserModel().getName()));

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
