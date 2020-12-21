package es.uca.gii.csi20.mdj.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import es.uca.gii.csi20.mdj.data.Data;
import es.uca.gii.csi20.mdj.data.Estado;

public class EstadoTest {
	
	@BeforeAll
	public void setUpBeforeClass() throws Exception { Data.LoadDriver(); }
	
	@Test
	public void testConstructor() throws Exception {
		Connection con = null;
		ResultSet rs = null;
		Estado estado;
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT id, Nombre FROM estado;");
			rs.next();
			estado = new Estado(rs.getInt("id"), con);
			
			assertEquals(rs.getInt("Id"), estado.getId());
			assertEquals(rs.getString("Nombre"), estado.getNombre());
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close(); 
		}
	}
	
	@Test
	public void testSelect() throws Exception {
		List<Estado> aEstado = Estado.Select(null);
		
		assertEquals("Abierto", aEstado.get(0).getNombre());
		assertEquals("Cerrado", aEstado.get(1).getNombre());
		assertEquals("Prescrito", aEstado.get(2).getNombre());
	}
	
	@Test
	public void testCreate() throws Exception{		
		Connection con = null;
		ResultSet rs = null;
		Estado estado;
		
		try {
			con = Data.Connection();
			
			estado = Estado.Create("Estado");
			
			rs = con.createStatement().executeQuery("SELECT id, Nombre FROM estado WHERE id = " + estado.getId() + ";");
			
			rs.next();
			
			assertEquals(rs.getInt("Id"), estado.getId());
			assertEquals(rs.getString("Nombre"), estado.getNombre());		
			
			con.createStatement().executeUpdate("DELETE FROM estado WHERE id = " + estado.getId() + ";");
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	@Test
	public void testUpdate() throws Exception{
		Estado estado = Estado.Create("Estado");
		estado.setNombre("UpdateEstado");
		estado.Update();
		Estado eEstadoUpdate = new Estado(estado.getId());
		assertEquals("UpdateEstado", eEstadoUpdate.getNombre());
		estado.Delete();
	}
	
	@Test
	public void testDelete() throws Exception{
		Connection con = null;
		ResultSet rs = null;
		try {
			Estado estado = Estado.Create("NombreDelete");
			con = Data.Connection();
			estado.Delete();
			rs = con.createStatement().executeQuery("SELECT id FROM caso WHERE id = " + estado.getId());
			assertEquals(false, rs.next());
			assertEquals(true, estado.getIsDeleted());	
		} catch (SQLException e) { throw e; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
}
