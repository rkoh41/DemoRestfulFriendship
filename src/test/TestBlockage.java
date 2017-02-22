package test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.pixelcised.demorestfulfriendship.Model.Entity.Blockage;

public class TestBlockage {

	@Test
	public void testBlockageDefaultConstructor() {
		Blockage blockage = new Blockage();
		assertNull(blockage.getRequestor());
		assertNull(blockage.getTarget());
	}
	
	@Test
	public void testBlockageParamConstructor() {
		Blockage blockage = new Blockage("awesome@example.com", "terrific@example.com");
		assertEquals("awesome@example.com", blockage.getRequestor());
		assertEquals("terrific@example.com", blockage.getTarget());
	}

}
