package edu.neu.csye7374.designpattern.facade;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.itextpdf.text.pdf.draw.LineSeparator;
import edu.neu.csye7374.model.Invoice;
import edu.neu.csye7374.model.ProductPO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class createPDF {

    private Invoice invoice;
    private static final BaseColor NEU_RED = new BaseColor(204, 0, 0);
    private static final BaseColor NEU_BLACK = new BaseColor(0, 0, 0);
    private static final BaseColor LIGHT_GRAY = new BaseColor(240, 240, 240);

    public String generatePDF(Invoice invoice) {
        this.invoice = invoice;
        Document document = new Document(PageSize.A4, 50, 50, 100, 60);
        String filename = null;

        try {
            filename = "Invoice_" + invoice.getId() + "_" + invoice.getPurchaseOrder().getBuyer().getCompanyName() + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));

            // Add header and footer
            HeaderFooter event = new HeaderFooter();
            writer.setPageEvent(event);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }

        document.open();

        try {
            // Add logo/university name section
            addHeader(document);

            // Add invoice details section
            addInvoiceDetails(document);

            // Add billing information
            addBillingInfo(document);

            // Add product table
            addProductTable(document);

            // Add payment summary
            addPaymentSummary(document);

            // Add terms and conditions
            addTermsAndConditions(document);

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.close();
        return filename;
    }

    private void addHeader(Document document) throws DocumentException {
        // University Name and Logo Section
        Font universityFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, NEU_RED);
        Font taglineFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY);

        Paragraph university = new Paragraph("NORTHEASTERN UNIVERSITY", universityFont);
        university.setAlignment(Element.ALIGN_CENTER);
        document.add(university);

        Paragraph tagline = new Paragraph("Excellence in Education Since 1898", taglineFont);
        tagline.setAlignment(Element.ALIGN_CENTER);
        document.add(tagline);

        document.add(Chunk.NEWLINE);

        // Invoice Title
        Font invoiceFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
        Paragraph invoiceTitle = new Paragraph("INVOICE", invoiceFont);
        invoiceTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(invoiceTitle);

        // Add a line separator
        LineSeparator line = new LineSeparator();
        line.setLineColor(NEU_RED);
        line.setLineWidth(2);
        document.add(new Chunk(line));
        document.add(Chunk.NEWLINE);
    }

    private void addInvoiceDetails(Document document) throws DocumentException {
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.BLACK);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);

        PdfPTable detailsTable = new PdfPTable(2);
        detailsTable.setWidthPercentage(100);
        detailsTable.setWidths(new float[]{1, 1});

        // Left side - Invoice details
        PdfPCell leftCell = new PdfPCell();
        leftCell.setBorder(Rectangle.NO_BORDER);
        leftCell.addElement(new Phrase("Invoice Number: ", labelFont));
        leftCell.addElement(new Phrase("INV-" + String.format("%06d", invoice.getId()), valueFont));
        leftCell.addElement(Chunk.NEWLINE);
        leftCell.addElement(new Phrase("Invoice Date: ", labelFont));
        leftCell.addElement(new Phrase(new SimpleDateFormat("MMMM dd, yyyy").format(new Date()), valueFont));
        leftCell.addElement(Chunk.NEWLINE);
        leftCell.addElement(new Phrase("Payment Due: ", labelFont));
        leftCell.addElement(new Phrase(invoice.getPaymentDate(), valueFont));

        // Right side - PO details
        PdfPCell rightCell = new PdfPCell();
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        rightCell.addElement(new Phrase("Purchase Order #: ", labelFont));
        rightCell.addElement(new Phrase("PO-" + String.format("%06d", invoice.getPurchaseOrder().getId()), valueFont));
        rightCell.addElement(Chunk.NEWLINE);
        rightCell.addElement(new Phrase("Payment Status: ", labelFont));

        detailsTable.addCell(leftCell);
        detailsTable.addCell(rightCell);

        document.add(detailsTable);
        document.add(Chunk.NEWLINE);
    }

    private void addBillingInfo(Document document) throws DocumentException {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, NEU_RED);
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

        PdfPTable billingTable = new PdfPTable(2);
        billingTable.setWidthPercentage(100);
        billingTable.setWidths(new float[]{1, 1});

        // Bill From
        PdfPCell fromCell = new PdfPCell();
        fromCell.setBorder(Rectangle.NO_BORDER);
        fromCell.setBackgroundColor(LIGHT_GRAY);
        fromCell.setPadding(10);

        Paragraph fromHeader = new Paragraph("BILL FROM:", headerFont);
        fromCell.addElement(fromHeader);
        fromCell.addElement(new Phrase("Northeastern University\n", valueFont));
        fromCell.addElement(new Phrase("360 Huntington Ave\n", valueFont));
        fromCell.addElement(new Phrase("Boston, MA 02115\n", valueFont));
        fromCell.addElement(new Phrase("Phone: (617) 373-2000\n", valueFont));
        fromCell.addElement(new Phrase("Tax ID: 04-2103594", valueFont));

        // Bill To
        PdfPCell toCell = new PdfPCell();
        toCell.setBorder(Rectangle.NO_BORDER);
        toCell.setBackgroundColor(LIGHT_GRAY);
        toCell.setPadding(10);

        Paragraph toHeader = new Paragraph("BILL TO:", headerFont);
        toCell.addElement(toHeader);
        toCell.addElement(new Phrase(invoice.getPurchaseOrder().getBuyer().getOwnerName() + "\n", valueFont));
        toCell.addElement(new Phrase(invoice.getPurchaseOrder().getBuyer().getCompanyName() + "\n", valueFont));

        billingTable.addCell(fromCell);
        billingTable.addCell(toCell);
        billingTable.setSpacingAfter(20);

        document.add(billingTable);
    }

    private void addProductTable(Document document) throws DocumentException {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.WHITE);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{0.8f, 3f, 1f, 1.2f, 1.2f});

        // Table headers
        String[] headers = {"#", "Product Description", "Quantity", "Unit Price", "Total"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
            cell.setBackgroundColor(NEU_RED);
            cell.setPadding(8);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColor(BaseColor.WHITE);
            table.addCell(cell);
        }

        // Table rows
        List<ProductPO> productPOs = invoice.getPurchaseOrder().getProducts();

        if (productPOs != null && !productPOs.isEmpty()) {
            List<ProductPO> distinctProductList = productPOs.stream().distinct().collect(Collectors.toList());
            int index = 1;

            for (ProductPO productPO : distinctProductList) {
                if (productPO != null && productPO.getProduct() != null) {
                    // Row number
                    PdfPCell indexCell = new PdfPCell(new Phrase(String.valueOf(index++), cellFont));
                    indexCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    indexCell.setPadding(6);
                    indexCell.setBorderColor(BaseColor.LIGHT_GRAY);
                    table.addCell(indexCell);

                    // Product name
                    PdfPCell nameCell = new PdfPCell(new Phrase(productPO.getProduct().getProductName(), cellFont));
                    nameCell.setPadding(6);
                    nameCell.setBorderColor(BaseColor.LIGHT_GRAY);
                    table.addCell(nameCell);

                    // Quantity
                    PdfPCell qtyCell = new PdfPCell(new Phrase(String.valueOf(productPO.getQuantity()), cellFont));
                    qtyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    qtyCell.setPadding(6);
                    qtyCell.setBorderColor(BaseColor.LIGHT_GRAY);
                    table.addCell(qtyCell);

                    // Unit price
                    PdfPCell priceCell = new PdfPCell(new Phrase("$" + String.format("%.2f", productPO.getProduct().getPrice()), cellFont));
                    priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    priceCell.setPadding(6);
                    priceCell.setBorderColor(BaseColor.LIGHT_GRAY);
                    table.addCell(priceCell);

                    // Total
                    double total = productPO.getProduct().getPrice() * productPO.getQuantity();
                    PdfPCell totalCell = new PdfPCell(new Phrase("$" + String.format("%.2f", total), cellFont));
                    totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    totalCell.setPadding(6);
                    totalCell.setBorderColor(BaseColor.LIGHT_GRAY);
                    table.addCell(totalCell);
                }
            }
        }

        document.add(table);
    }

    private void addPaymentSummary(Document document) throws DocumentException {
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.BLACK);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);
        Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, NEU_RED);

        PdfPTable summaryTable = new PdfPTable(2);
        summaryTable.setWidthPercentage(40);
        summaryTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        summaryTable.setSpacingBefore(20);

        double subtotal = invoice.getPurchaseOrder().getTotalAmount();
        double tax = subtotal * 0.0625; // 6.25% MA tax
        double total = subtotal + tax;

        // Subtotal
        PdfPCell subtotalLabel = new PdfPCell(new Phrase("Subtotal:", labelFont));
        subtotalLabel.setBorder(Rectangle.NO_BORDER);
        subtotalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        subtotalLabel.setPadding(5);

        PdfPCell subtotalValue = new PdfPCell(new Phrase("$" + String.format("%.2f", subtotal), valueFont));
        subtotalValue.setBorder(Rectangle.NO_BORDER);
        subtotalValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
        subtotalValue.setPadding(5);

        // Tax
        PdfPCell taxLabel = new PdfPCell(new Phrase("Tax (6.25%):", labelFont));
        taxLabel.setBorder(Rectangle.NO_BORDER);
        taxLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        taxLabel.setPadding(5);

        PdfPCell taxValue = new PdfPCell(new Phrase("$" + String.format("%.2f", tax), valueFont));
        taxValue.setBorder(Rectangle.NO_BORDER);
        taxValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
        taxValue.setPadding(5);

        // Total
        PdfPCell totalLabel = new PdfPCell(new Phrase("TOTAL:", totalFont));
        totalLabel.setBorder(Rectangle.TOP);
        totalLabel.setBorderWidth(2);
        totalLabel.setBorderColor(NEU_RED);
        totalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalLabel.setPadding(5);

        PdfPCell totalValue = new PdfPCell(new Phrase("$" + String.format("%.2f", total), totalFont));
        totalValue.setBorder(Rectangle.TOP);
        totalValue.setBorderWidth(2);
        totalValue.setBorderColor(NEU_RED);
        totalValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalValue.setPadding(5);

        summaryTable.addCell(subtotalLabel);
        summaryTable.addCell(subtotalValue);
        summaryTable.addCell(taxLabel);
        summaryTable.addCell(taxValue);
        summaryTable.addCell(totalLabel);
        summaryTable.addCell(totalValue);

        document.add(summaryTable);
    }

    private void addTermsAndConditions(Document document) throws DocumentException {
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK);
        Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.GRAY);

        Paragraph termsHeader = new Paragraph("Terms and Conditions:", headerFont);
        document.add(termsHeader);

        Paragraph terms = new Paragraph(
                "1. Payment is due within 30 days of invoice date.\n" +
                        "2. Late payments may incur a 1.5% monthly service charge.\n" +
                        "3. Please include invoice number with payment.\n" +
                        "4. Make checks payable to Northeastern University.",
                textFont
        );
        document.add(terms);

        document.add(Chunk.NEWLINE);

        Paragraph thankYou = new Paragraph("Thank you for your business!",
                FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, NEU_RED));
        thankYou.setAlignment(Element.ALIGN_CENTER);
        document.add(thankYou);
    }

    // Inner class for header and footer
    class HeaderFooter extends PdfPageEventHelper {
        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.GRAY);

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();

            // Footer
            Phrase footer = new Phrase("Northeastern University | 360 Huntington Ave, Boston, MA 02115 | www.northeastern.edu", footerFont);
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    footer,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, 0);

            // Page number
            Phrase pageNum = new Phrase("Page " + writer.getPageNumber(), footerFont);
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
                    pageNum,
                    document.right(),
                    document.bottom() - 10, 0);
        }
    }
}