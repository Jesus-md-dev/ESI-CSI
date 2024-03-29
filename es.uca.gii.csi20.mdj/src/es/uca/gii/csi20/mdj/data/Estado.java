package es.uca.gii.csi20.mdj.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class Estado extends Entity{
	private String _sNombre;
	
	public String getNombre() { return _sNombre; }
	public void setNombre(String sNombre) { _sNombre = sNombre; }
	
	/**
	 * Create an object Estado
	 * @param iId
	 * @throws Exception
	 */
	public Estado(int iId) throws Exception {
		Connection con = null;
		try {
			con = Data.Connection();
			Initialize(iId, con);
		} catch(SQLException e) { throw e; }
		finally { if(con != null) con = null; }
	}
	
	/**
	 * Create an object Estado with a initialized Connection
	 * @param iId
	 * @param con
	 * @throws Exception
	 */
	public Estado(int iId, Connection con) throws Exception { Initialize(iId, con); }
	
	/**
	 * Initialize the object with data of the database
	 * @param iId
	 * @param con
	 * @throws Exception
	 */
	private void Initialize(int iId, Connection con) throws Exception {
		ResultSet rs = null;
		try {
			rs = con.createStatement().executeQuery("SELECT nombre "
					+ "FROM estado Where id = " + iId);
			rs.next();
			_sNombre = rs.getString("Nombre");
			setId(iId);
			setTable("estado");
		} catch(SQLException e) { throw e; }
		finally { if(rs != null) rs = null; }
	}
	
	public String toString() { return _sNombre; }
	
	/**
	 * @param sNombre
	 * @return List of objects Estado equivalents to the parameter
	 * @throws Exception
	 */
	public static List<Estado> Select(String sNombre) throws Exception {
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Estado> aEstado = new ArrayList<Estado>();
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT Id FROM estado" 
			+ Where(new String[] { "estado.Nombre" },
					new int[] { Types.VARCHAR	}, 
					new Object[] { sNombre }));
			while(rs.next()) aEstado.add(new Estado(rs.getInt("Id"), con));
			return aEstado;
		} catch(SQLException e) { throw e; }
		finally {
			if(rs != null) rs = null;
			if(con != null) con = null;
		}
	}
	
	/**
	 * @param sNombre
	 * @return an object Estado with the parameter
	 * @throws Exception
	 */
	public static Estado Create(String sNombre) throws Exception {
		Connection con = null;
		
		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO estado (Nombre)"
					+ "VALUES (" +  Data.String2Sql(sNombre, true, false) + ");");
			return new Estado(Data.LastId(con), con);
		} 
		catch (SQLException ee) {throw ee;}
		finally { if(con != null) con.close(); }
	}
	
	/**
	 * Update the object in the database
	 * @throws Exception
	 */
	public void Update() throws Exception {
		super.Update("UPDATE estado SET Nombre = " + Data.String2Sql(_sNombre, true, false)
					+ " WHERE id = " + getId());
	}
}
