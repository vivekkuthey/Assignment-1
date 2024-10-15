package com.pdf.services;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.pdf.model.InputData;
import com.pdf.model.Item;

@Service
public class PdfService {
	private Logger logger = LoggerFactory.getLogger(PdfService.class);
	
	public FileSystemResource createPdf(InputData inputData) throws DocumentException, FileNotFoundException {

		logger.info("create PDF Started : ");

		List<Item> items = inputData.getItems();

		// Use iText to generate the PDF file
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));
		document.open();

		// Create a new table with 2 columns for seller and buyer details
		PdfPTable detailsTable = new PdfPTable(2);
		detailsTable.setWidthPercentage(100);
		detailsTable.setPadding(10);

		// Add the seller details to the first column of the table
		addDetailsToTable(detailsTable, "Seller Details:", "Name: " + inputData.getSeller(), "GSTIN: " + inputData.getSellerGstin(), "Address: " + inputData.getSellerAddress());

		// Add the buyer details to the second column of the table
		addDetailsToTable(detailsTable, "Buyer Details:", "Name: " + inputData.getBuyer(), "GSTIN: " + inputData.getBuyerGstin(), "Address: " + inputData.getBuyerAddress());

		// Add the details table to the document
		document.add(detailsTable);

		// Add item details
		PdfPTable itemTable = new PdfPTable(4);
		itemTable.setWidthPercentage(100);

		// Add table headers
		itemTable.addCell("Item Name");
		itemTable.addCell("Quantity");
		itemTable.addCell("Rate");
		itemTable.addCell("Amount");

		DecimalFormat decimalFormat = new DecimalFormat("0.00"); // Create a decimal formatter with two decimal places

		for (Item item : items) {
			// Add item details to the table
			itemTable.addCell(item.getName());
			itemTable.addCell(item.getQuantity());
			itemTable.addCell(decimalFormat.format(item.getRate())); // Format the rate to two decimal places
			itemTable.addCell(decimalFormat.format(item.getAmount())); // Format the amount to two decimal places
		}

		// Add the item table to the document
		document.add(itemTable);

		// Close the document
		document.close();

		return new FileSystemResource("output.pdf");
	}

	private void addDetailsToTable(PdfPTable table, String title, String... details) {
		PdfPCell cell = new PdfPCell();
		cell.addElement(new Paragraph(title));
		for (String detail : details) {
			cell.addElement(new Paragraph(detail));
		}
		table.addCell(cell);
	}
	
}














//String title = "welcome to Aman channel";
//String content = "learn code with aman ";
//
//ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//Document document = new Document();
//
//PdfWriter.getInstance(document, out);
//
//document.open();
//
//Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD,20);
//Paragraph titlePara =  new Paragraph(title,titleFont);
//titlePara.setAlignment(Element.ALIGN_CENTER);
//document.add(titlePara);
//
//Font paraFont = FontFactory.getFont(FontFactory.HELVETICA,18);
//Paragraph paragraph = new Paragraph(content);
//paragraph.add(new Chunk("lund mein maza hgai "));
//document.add(paragraph);
//
//document.close();
//
//
//return new ByteArrayInputStream(out.toByteArray());


