package com.gromero.portal.visor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gromero.portal.visor.dao.SicexDAO;

@Service
public class SicexServiceImpl implements SicexService {
	@Autowired
	SicexDAO sicexDAO;
	 
	
	
}
