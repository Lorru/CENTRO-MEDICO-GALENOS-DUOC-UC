package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.IBranchOfficeDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.BranchOffice;


@Service
public class BranchOfficeServiceImpl implements IBranchOfficeService {

	@Autowired
	private IBranchOfficeDao iBranchOfficeDao;
	
	@Override
	@Transactional
	public List<BranchOffice> findAllBranchOfficeByStatusActive() {

		return iBranchOfficeDao.findAllBranchOfficeByStatusActive();
	}

}
