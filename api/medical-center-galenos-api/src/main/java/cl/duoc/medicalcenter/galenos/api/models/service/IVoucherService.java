package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import com.itextpdf.text.DocumentException;

import cl.duoc.medicalcenter.galenos.api.models.entity.Bill;

public interface IVoucherService {

	public byte [] billVoucher(Bill bill) throws DocumentException;
	
	public byte [] billReport(List<Bill> bill, String dateStart, String dateEnd) throws DocumentException;
}
