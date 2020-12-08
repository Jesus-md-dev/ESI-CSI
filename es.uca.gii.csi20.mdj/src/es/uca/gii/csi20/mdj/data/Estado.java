package es.uca.gii.csi20.mdj.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Estado {
	private int _iId;
	private String _sNombre;
	private boolean _bIsDeleted;
	
	public Estado(int iId) throws Exception {
		_iId = iId;
		Connection con = null;
		ResultSet rs = null;
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT nombre "
					+ "FROM estado Where id = " + iId);
			rs.next();
			_sNombre = rs.getString("Nombre"); 
		} catch(Exception e) { throw e; }
		finally {
			if(rs != null) rs = null;
			if(con != null) con = null;
		}
	}
	
	public int getId() { return _iId; }
	public String getNombre() { return _sNombre; }
	public boolean getIsDeleted() { return _bIsDeleted; }
	
	public void setNombre(String sNombre) { _sNombre = sNombre; }
	
	private static String Where(String sNombre) {
		String sWhere = "";
		if(sNombre != null)
				sWhere += " WHERE estado.Nombre like " + Data.String2Sql(sNombre, true, true);
		return sWhere;
	}
	
	public static List<Estado> Select(String sNombre) throws Exception {
		Connection con = null;
		ResultSet rs = null;
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT id FROM estado" + Where(sNombre));
			ArrayList<Estado> alEstadoList = new ArrayList<Estado>();
			
			while(rs.next()) {
				alEstadoList.add(new Estado(rs.getInt("id")));
			}
			
			return alEstadoList;
		} catch(Exception e) { throw e; }
		finally {
			if(rs != null) rs = null;
			if(con != null) con = null;
		}
	}
	
	public String toString() { return _sNombre; }
	
	public static Estado Create(String sNombre) throws Exception {
		Connection con = null;
		
		String sQuery = "INSERT INTO estado "
				+ "VALUES (NULL," +  Data.String2Sql(sNombre, true, false) + ");";
		
		
		
		try {
			con = Data.Connection();
			con.createStatement().executeUpdate(sQuery);
			
			return new Estado(Data.LastId(con));
		}
		catch (SQLException ee) {throw ee;}
		finally {
			if(con != null) con.close();
		}
	}
	
	public void Delete() throws Exception {
		Connection con = null;
		
		try {
			if(_bIsDeleted) {
				throw new Exception("Borrada");
			}
			
			con = Data.Connection();
			con.createStatement().executeUpdate("DELETE FROM estado WHERE id = " + _iId + ";");
			_bIsDeleted = true;
		} catch(Exception e){
			throw e;
		}
		finally {
			if(con != null) con.close();
		}
	}
	
	public void Update() throws Exception {
		Connection con = null;
		
		String sQuery = "UPDATE estado SET"
				+ " Nombre = " + Data.String2Sql(_sNombre, true, false)
				+ " WHERE id = " + _iId;
		try {
			if(_bIsDeleted)
				throw new Exception("Borrada");
			
			con = Data.Connection();
			con.createStatement().executeUpdate(sQuery);
		} catch(Exception e) {
			throw e;
		}
		finally {
			if(con != null) con.close();
		}
	}	
}