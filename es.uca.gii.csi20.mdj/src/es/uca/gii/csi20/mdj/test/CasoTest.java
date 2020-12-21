package es.uca.gii.csi20.mdj.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import es.uca.gii.csi20.mdj.data.Caso;
import es.uca.gii.csi20.mdj.data.Data;
import es.uca.gii.csi20.mdj.data.Estado;

public class CasoTest {
	
	@BeforeAll
	public void setUpBeforeClass() throws Exception { Data.LoadDriver(); }
	
	@Test
	public void testConstructor() throws Exception {
		Connection con = null;
		ResultSet rs = null;
		Caso caso = new Caso(1);
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT caso.id, caso.Titulo, caso.Descripcion,"
					+ " caso.Importancia FROM caso;");
			rs.next();			
			
			assertEquals(rs.getInt("Id"), caso.getId());
			assertEquals(rs.getString("Titulo"), caso.getTitulo());
			assertEquals(rs.getString("Descripcion"), caso.getDescripcion());
			assertEquals(rs.getInt("Importancia"), caso.getImportancia());	
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	@Test
	public void testCreate() throws Exception{		
		Connection con = null;
		ResultSet rs = null;
		Estado estado = Estado.Create("Estado");
		Caso caso  = Caso.Create("Titulo del caso", "Descipcion del caso", 2, estado);
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT caso.id, caso.Titulo, caso.Descripcion,"
					+ " caso.Importancia FROM caso WHERE id = " + caso.getId() + ";");
			rs.next();
			
			assertEquals(rs.getInt("Id"), caso.getId());
			assertEquals(rs.getString("Titulo"), caso.getTitulo());
			assertEquals(rs.getString("Descripcion"), caso.getDescripcion());
			assertEquals(rs.getInt("Importancia"), caso.getImportancia());			
			
			caso.Delete(); estado.Delete();
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	@Test
	public void testSelect() throws Exception{
		Estado estado = Estado.Create("Estado");
		Caso.Create("PruebaTitulo", "PruebaDescripcion", 3, estado);
		
		ArrayList<Caso> aCaso = Caso.Select("Prueba", null, null, null);
		Caso caso = aCaso.get(0);
		assertEquals("PruebaTitulo", caso.getTitulo());
		assertEquals("PruebaDescripcion", caso.getDescripcion());
		assertEquals(3, caso.getImportancia());
		
		aCaso = Caso.Select("rue", null, null, null);
		caso = aCaso.get(0);
		assertEquals("PruebaTitulo", caso.getTitulo());
		assertEquals("PruebaDescripcion", caso.getDescripcion());
		assertEquals(3, caso.getImportancia());
		
		aCaso = Caso.Select(null, "bades", null, null);
		caso = aCaso.get(0);
		assertEquals("PruebaTitulo", caso.getTitulo());
		assertEquals("PruebaDescripcion", caso.getDescripcion());
		assertEquals(3, caso.getImportancia());
		
		aCaso = Caso.Select(null, null, 3, null);
		caso = aCaso.get(0);
		assertEquals("PruebaTitulo", caso.getTitulo());
		assertEquals("PruebaDescripcion", caso.getDescripcion());
		assertEquals(3, caso.getImportancia());
		
		aCaso = Caso.Select(null, null, null, "sta");
		caso = aCaso.get(0);
		assertEquals("PruebaTitulo", caso.getTitulo());
		assertEquals("PruebaDescripcion", caso.getDescripcion());
		assertEquals(3, caso.getImportancia());
		
		aCaso = Caso.Select("rue", "bades", 3, "sta");
		caso = aCaso.get(0);
		assertEquals("PruebaTitulo", caso.getTitulo());
		assertEquals("PruebaDescripcion", caso.getDescripcion());
		assertEquals(3, caso.getImportancia());
		
		caso.Delete(); estado.Delete();
	}
	
	@Test
	public void testUpdate() throws Exception{
		Estado estado = Estado.Create("Estado");
		Caso caso = Caso.Create("PruebaTitulo", "PruebaDescripcion", 1, estado);
		
		caso.setTitulo("UpdateTitulo");
		caso.setDescripcion("UpdateDescripcion");
		caso.setImportancia(2);
		caso.Update();
		
		caso = new Caso(caso.getId());
		
		assertEquals("UpdateTitulo", caso.getTitulo());
		assertEquals("UpdateDescripcion", caso.getDescripcion());
		assertEquals(2, caso.getImportancia());
		
		caso.Delete(); estado.Delete();
	}
	
	@Test
	public void testDelete() throws Exception{
		Connection con = null;
		ResultSet rs = null;
		Estado estado = Estado.Create("Estado");
		try {
			Caso caso = Caso.Create("TituloDelete", "DescripcionDelete", 1, estado);
			con = Data.Connection();
			caso.Delete();
			estado.Delete();
			rs = con.createStatement().executeQuery("SELECT id FROM caso WHERE id = " + caso.getId());
			assertEquals(false, rs.next());
			assertEquals(true, caso.getIsDeleted());	
		} catch (SQLException e) { throw e; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
}
