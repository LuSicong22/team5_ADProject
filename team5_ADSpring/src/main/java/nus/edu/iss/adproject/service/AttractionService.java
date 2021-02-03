package nus.edu.iss.adproject.service;

import java.util.List;

import nus.edu.iss.adproject.model.Attraction;

public interface AttractionService extends IService<Attraction> {
	public List<Attraction> findByUserId(Long userId);

}
