package itu.s6.tpseo.framework.pdfutils;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.VerticalAlignment;
import be.quodlibet.boxable.line.LineStyle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.List;
import java.util.*;

public class PDFTableBuilder {

    protected Row<PDPage> header;
    List<CellData> metadata;
    BaseTable baseTable;
    float headerHeight = 30, rowHeight;
    float headerFontSize = 12, rowFontSize = 12;
    List<?> data;
    PDFont fontPlain = PDType1Font.HELVETICA;
    PDFont fontBold = PDType1Font.HELVETICA_BOLD;
    private Class<?> clazz;

    public PDFTableBuilder(Class<?> clazz, List<?> data, float yPos, float yStartNewPage, float bottomMargin, float width, float margin, PDDocument document, PDPage page) throws Exception {
        this.clazz = clazz;
        buildMetadata();
        this.data = data;
        this.baseTable = new BaseTable(yPos, yStartNewPage, bottomMargin, width, margin, document, page, true, true);
        this.buildHeader();
        this.buildRows();
    }

    public void draw () throws Exception {
        this.baseTable.draw();
    }

    private static final Map<Class<?>, List<CellData>> list = new HashMap<>();

    private void buildMetadata() {
        List<CellData> cellData = list.get(this.clazz);
        if (cellData == null) {
             cellData = new ArrayList<>();
             list.put(clazz, cellData);

            for (Method method: this.clazz.getMethods()) {
                PDFColumn col = method.getAnnotation(PDFColumn.class);
                if (col == null) continue;
                cellData.add(new CellData( col.width(), col.value(), method, col.order()));
            }

            Collections.sort(cellData);
        }
        this.metadata = cellData;
    }


    private void buildRows() throws Exception {
        for (Object obj: data) {
            Row<PDPage> row = this.baseTable.createRow(getRowHeight());
            this.buildCells(row, obj);
        }
    }

    private void buildCells (Row<PDPage> row, Object obj) throws Exception {
        for (int i = 0; i < metadata.size(); i++) {
            Cell<PDPage> cell = row.createCell(metadata.get(i).getWidth(), metadata.get(i).getMethod().invoke(obj).toString());
            this.buildCell(cell);
        }
    }

    private Cell<PDPage> buildCell(Cell<PDPage> cell) {
        cell.setFont(fontPlain);
        cell.setFontSize(getRowFontSize());
        cell.setLineSpacing(1.5f);
        return cell;
    }

    protected void buildHeader () {
        header = this.baseTable.createRow(getHeaderHeight());
        for (CellData data: metadata) {
            this.buildHeaderCell(data);
        }
    }

    private Cell<PDPage> buildHeaderCell(CellData data) {
        Cell<PDPage> cell = this.header.createCell(data.getWidth(), data.getTitle());
        cell.setFont(fontBold);
        cell.setFontSize(getHeaderFontSize());
        cell.setTopBorderStyle(new LineStyle(Color.BLACK, 3));
        cell.setValign(VerticalAlignment.MIDDLE);
        return cell;
    }


    public float getHeaderHeight() {
        return headerHeight;
    }

    public float getHeaderFontSize() {
        return headerFontSize;
    }

    public float getRowHeight() {
        return rowHeight;
    }

    public float getRowFontSize() {
        return rowFontSize;
    }
}
