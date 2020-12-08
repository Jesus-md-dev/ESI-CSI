package es.uca.gii.csi20.mdj.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Caso {
	private int _iId;
	private String _sTitulo;
	private String _sDescripcion;
	private int _iImportancia;
	private boolean _bIsDeleted;
	private Estado _eEstado;
	
	public Caso(int iId) throws Exception {
		_iId = iId;
		Connection con = null;
		ResultSet rs = null;
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT Id_Estado, Titulo, Descripcion, Importancia "
					+ "FROM caso WHERE id = " + iId);
			rs.next();
						
			_sTitulo = rs.getString("Titulo");
			_sDescripcion = rs.getString("Descripcion");
			_iImportancia = rs.getInt("Importancia");
			_bIsDeleted = false;
			_eEstado = new Estado(rs.getInt("Id_Estado"));
		} catch ( SQLException e ) { throw e; } 
		finally {
			if(rs != null) rs.close();
			if(con != null) con.close();
		}
	}
	
	public int getId() { return _iId; }
	public String getTitulo() { return _sTitulo; }
	public String getDescripcion() { return _sDescripcion; }
	public int getImportancia() { return _iImportancia; }
	public Estado getEstado() { return _eEstado; }
	public boolean getIsDeleted() { return _bIsDeleted; }
	
	public void setTitulo(String sTitulo) { _sTitulo = sTitulo; }
	public void setDescripcion(String sDescripcion) { _sDescripcion = sDescripcion; }
	public void setImportancia(int iImportancia) { _iImportancia = iImportancia; }
	public void setEstado(Estado eEstado) { _eEstado = eEstado; }

	public String toString() {
		return "Caso" + "@" + _iId + ":" + _sTitulo + ":" + _sDescripcion + ":" + _iImportancia + ":" + _eEstado;
	}
	
	public void Delete() throws Exception {
		Connection con = null;
		
		try {
			if(_bIsDeleted) {
				throw new Exception("Borrada");
			}
			
			con = Data.Connection();
			con.createStatement().executeUpdate("DELETE FROM caso WHERE id = " + _iId + ";");
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
		
		String sQuery = "UPDATE caso SET"
				+ " Titulo = " + Data.String2Sql(_sTitulo, true, false)
				+ ", Descripcion = " + Data.String2Sql(_sDescripcion, true, false)
				+ ", Importancia = " + _iImportancia
				+ ", Id_Estado = " + _eEstado.getId()
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
	
	private static String Where(String sTitulo, String sDescripcion,
			Integer iImportancia, String sEstado) {
		//TODO
		String sWhere = "";
		boolean bAnd = false;
		if(sEstado != null) {
			sWhere += "INNER JOIN estado ON caso.Id_Estado = estado.id";
			
			if(bAnd)
				sWhere += " AND estado.Nombre like " + Data.String2Sql(sEstado, true, true);
			else {
				sWhere += " WHERE estado.Nombre like " + Data.String2Sql(sEstado, true, true);
				bAnd = true;
			}
		}	
		
		if(sTitulo != null) {
			if(bAnd)
				sWhere += " AND Titulo like " + Data.String2Sql(sTitulo, true, true);
			else {
				sWhere += " WHERE Titulo like " + Data.String2Sql(sTitulo, true, true);
				bAnd = true;
			}
		}
		
		if(sDescripcion != null)
			if(bAnd)
				sWhere += " AND Descripcion like " + Data.String2Sql(sDescripcion, true, true);
			else {
				sWhere += " WHERE Descripcion like " + Data.String2Sql(sDescripcion, true, true);
				bAnd = true;
			}
		
		if(iImportancia != null)
			if(bAnd)
				sWhere += " AND Importancia like " + iImportancia;
			else {
				sWhere += " WHERE Importancia like " + iImportancia;
				bAnd = true;
			}
		
		return sWhere;
	}
	
	public static ArrayList<Caso> Select(String sTitulo, String sDescripcion,
			Integer iImportancia, String sEstado) throws Exception {
		Connection con = null;
		ResultSet rs = null;
		
		String sQuery = "Select caso.id, caso.Id_Estado, caso.Titulo, caso.Descripcion, caso.Importancia "
				+ "FROM caso "
				+ Where(sTitulo, sDescripcion, iImportancia, sEstado);
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery(sQuery);
			ArrayList<Caso> alCasoList = new ArrayList<Caso>();
			
			while(rs.next()) {
				alCasoList.add(new Caso(rs.getInt("Id")));
			}
			
			return alCasoList;
			
		} catch (Exception e) { throw e; }
		finally {
			if(rs != null) rs.close();
			if(con != null) con.close();
		}
	}
	
	public static Caso Create(String sTitulo, String sDescripcion, int iImportancia, Estado eEstado) throws Exception {
		Connection con = null;
		
		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO caso "
					+ "VALUES (NULL," + eEstado.getId() 
					+ "," + Data.String2Sql(sTitulo, true, false) + "," 
					+ Data.String2Sql(sDescripcion, true, false) + "," + iImportancia + ")");
			return new Caso(Data.LastId(con));
		}
		catch (SQLException ee) {throw ee;}
		finally {
			if(con != null) con.close();
		}
	}
}
