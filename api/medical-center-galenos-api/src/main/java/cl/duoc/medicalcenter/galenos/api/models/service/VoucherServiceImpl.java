package cl.duoc.medicalcenter.galenos.api.models.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import cl.duoc.medicalcenter.galenos.api.models.entity.Bill;

@Service
public class VoucherServiceImpl implements IVoucherService{

	@Override
	public byte [] billVoucher(Bill bill) throws DocumentException {
		
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfPTable table = new PdfPTable(2);

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        
        PdfPCell hcell;
        
        hcell = new PdfPCell(new Phrase("Centro Médico Galenos", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setColspan(2);
        table.addCell(hcell);
        
        hcell = new PdfPCell(new Phrase("Comprobante", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setColspan(2);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Información", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Valor", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        PdfPCell cell;
        
        cell = new PdfPCell(new Phrase("Especialidad" + "(" + bill.getSpecialistId().getSpecialtyId().getSpecialtyDescription() + ") : "));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("$" + bill.getSpecialistId().getSpecialtyId().getSpecialtyAmount()));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Previsión : "));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("$" + bill.getForecastId().getForecastAmount()));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Medio de Pago : "));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("$" + bill.getPaymentTypeId().getPaymentTypeAmount()));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Descuento Centro Médico : "));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("%50"));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        hcell = new PdfPCell(new Phrase("Total a Pagar : ", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("$" + bill.getBillSalary(), headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);
        
        PdfWriter.getInstance(document, out);
        document.open();
        document.add(table);
        document.close();

            
        byte [] voucher = out.toByteArray();
        
        return voucher;
		
	}

	@Override
	public byte [] billReport(List<Bill> bills, String dateStart, String dateEnd) throws DocumentException{
		
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfPTable table = new PdfPTable(3);

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        
        PdfPCell hcell;
        
        hcell = new PdfPCell(new Phrase("Centro Médico Galenos", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setColspan(3);
        table.addCell(hcell);
        
        hcell = new PdfPCell(new Phrase("Comprobante de " + dateStart + " al " + dateEnd, headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setColspan(3);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Paciente", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Valor", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);
        
        hcell = new PdfPCell(new Phrase("Fecha", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        int totalValue = 0;
        
        for (Bill bill : bills) {
			
        	PdfPCell cell;
        	
            cell = new PdfPCell(new Phrase(bill.getPatientId().getPatientFullName()));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase("$" + (bill.getBillSalary() * 70 ) / 100 ));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(bill.getBillMedicalTime().toString()));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            
            totalValue = totalValue + (bill.getBillSalary() * 70 ) / 100;
        	
		}
        
        hcell = new PdfPCell(new Phrase("Valor Total", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);
        
        hcell = new PdfPCell(new Phrase("$" + String.valueOf(totalValue), headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);
        
        hcell = new PdfPCell(new Phrase(dateStart + " al " + dateEnd, headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);
        
        PdfWriter.getInstance(document, out);
        document.open();
        document.add(table);
        document.close();

            
        byte [] voucher = out.toByteArray();
        
        return voucher;
	}

}
