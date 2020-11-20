package es.uca.gii.csi20.mdj.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Caso {
	int iId_;
	String sTitulo_;
	String sDescripcion_;
	int iImportancia_;
	boolean _bIsDeleted;
	
	public Caso(int iId) throws Exception {
		iId_ = iId;
		Connection con = null;
		ResultSet rs = null;
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT Titulo, Descripcion, Importancia "
					+ "FROM caso WHERE id = " + iId);
			rs.next();
						
			sTitulo_ = rs.getString("Titulo");
			sDescripcion_ = rs.getString("Descripcion");
			iImportancia_ = rs.getInt("Importancia");
			_bIsDeleted = false;
		} catch ( SQLException e ) { throw e; } 
		finally {
			if(rs != null) rs.close();
			if(con != null) con.close();
		}
	}
	
	public int getId() { return iId_; }
	public String getTitulo() { return sTitulo_; }
	public String getDescripcion() { return sDescripcion_; }
	public int getImportancia() { return iImportancia_; }
	public boolean getIsDeleted() { return _bIsDeleted; }
	
	public void setTitulo(String sTitulo) { sTitulo_ = sTitulo; }
	public void setDescripcion(String sDescripcion) { sDescripcion_ = sDescripcion; }
	public void setImportancia(int iImportancia) { iImportancia_ = iImportancia; }

	public String toString() {
		return "Caso" + "@" + iId_ + ":" + sTitulo_ + ":" + sDescripcion_ + ":" + iImportancia_;
	}
	
	public void Delete() throws Exception {
		Connection con = null;
		
		try {
			if(_bIsDeleted) {
				throw new Exception("Borrada");
			}
			
			con = Data.Connection();
			con.createStatement().executeUpdate("DELETE FROM caso WHERE id = " + iId_ + ";");
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
				+ " Titulo = " + Data.String2Sql(sTitulo_, true, false)
				+ ", Descripcion = " + Data.String2Sql(sDescripcion_, true, false)
				+ ", Importancia = " + iImportancia_
				+ " WHERE id = " + iId_;
		
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
	
	private static String Where(String sTitulo, String sDescripcion, Integer iImportancia) {
		String sWhere = "";
		boolean bAnd = false;
		
		if(sTitulo != null) {
			sWhere = "WHERE Titulo like " + Data.String2Sql(sTitulo, true, true);
			bAnd = true;
		}
		if(sDescripcion != null)
			if(bAnd)
				sWhere += " AND Descripcion like " + Data.String2Sql(sDescripcion, true, true);
			else {
				sWhere = "WHERE Descripcion like " + Data.String2Sql(sDescripcion, true, true);
				bAnd = true;
			}
		if(iImportancia != null)
			if(bAnd)
				sWhere += " AND Importancia like " + iImportancia;
			else {
				sWhere = "WHERE Importancia like " + iImportancia;
				bAnd = true;
			}
		
		return sWhere;
	}
	
	public static ArrayList<Caso> Select(String sTitulo, String sDescripcion, Integer iImportancia) 
			throws Exception {
		Connection con = null;
		ResultSet rs = null;
		
		String sQuery = "Select id, Titulo, Descripcion, Importancia "
				+ "FROM caso "
				+ Where(sTitulo, sDescripcion, iImportancia);
		
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
	
	public static Caso Create(String sTitulo, String sDescripcion, int iImportancia) throws Exception {
		Connection con = null;
		
		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO caso "
					+ "VALUES (NULL," + Data.String2Sql(sTitulo, true, false) + "," 
					+ Data.String2Sql(sDescripcion, true, false) + "," + iImportancia + ")");
			
			return new Caso(Data.LastId(con));
		}
		catch (SQLException ee) {throw ee;}
		finally {
			if(con != null) con.close();
		}
	}
}
