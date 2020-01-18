package ventanas;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MenuLoginTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCorreo() {
		assertEquals(MenuLogin.comprobarCorreo("MiguelJuan@gmail.com"), true);
		assertTrue(MenuLogin.comprobarCorreo("aitortilla@gmail.com"));
		assertFalse(MenuLogin.comprobarCorreo("rumpelstiltskin@gmail.com"));
		
	}
	
	public void testContrasenya() {
		assertEquals(MenuLogin.comprobarContrasenya("Txuti011"), true);
		assertTrue(MenuLogin.comprobarContrasenya("ElPenas_09"));
		assertFalse(MenuLogin.comprobarContrasenya("Ermikelats"));
	}

}
