package com.prueba;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PruebaTest {

	Prueba prueba ;
	
	@Before
	public void inicializando(){
		prueba = new Prueba(); 
	}
	
	
	@Test
	public void test() {
		assertEquals("Hola!", prueba.saluda());
	}

}
