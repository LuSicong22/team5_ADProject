package nus.edu.iss.simulated.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import nus.edu.iss.simulated.model.DailyAttractionDetail;
import nus.edu.iss.simulated.repository.DailyAttractionDetailRepo;

@Service
@Transactional
public class  DailyAttractionDetailServiceImpl implements DailyAttractionDetailService{
	

	@Autowired
	private DailyAttractionDetailRepo dapRepo;


	@Override
	public DailyAttractionDetail findAttractionDetailById(long id) {
		// TODO Auto-generated method stub
		return dapRepo.findById(id).get();
	}

	@Override
	public List<DailyAttractionDetail> findAttractionDetailByMonthAndAttractionName
	(int monthNum) {
		// TODO Auto-generated method stub
		return dapRepo.findByMonthandAttractionName(monthNum);
	}


	@Override
	public DailyAttractionDetail findAttractionDetailByDateAndAttractionName
	(LocalDate date,String attractionName) {
		// TODO Auto-generated method stub
		return dapRepo.findbyDateAndAttractionName(date,attractionName);
	}
//	
//	
//	@Override
//	public List<DailyAttractionDetail> findAttractionDetailByName(AttractionName attractionName) {
//		// TODO Auto-generated method stub
//		return dapRepo.findDailyAttractionDetailByName(attractionName);
//	}



}