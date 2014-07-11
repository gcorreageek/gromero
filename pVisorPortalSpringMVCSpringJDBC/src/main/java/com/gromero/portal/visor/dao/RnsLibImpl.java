package com.gromero.portal.visor.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class RnsLibImpl implements RnsLib {

	private JdbcTemplate jdbcTemplate;
	 
    @Autowired
	public void setJdbcTemplate1(@Qualifier("jdbcTemplate1") DataSource jdbcTemplate1) {
		this.jdbcTemplate = new JdbcTemplate(jdbcTemplate1);
	}
    
}
