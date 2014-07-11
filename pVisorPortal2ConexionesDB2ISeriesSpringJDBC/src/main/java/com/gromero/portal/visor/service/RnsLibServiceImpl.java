package com.gromero.portal.visor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gromero.portal.visor.dao.RnsLibDAO;

@Service
public class RnsLibServiceImpl implements RnsLibService {

	@Autowired
	RnsLibDAO rnsLibDAO;
	
	
	
}
