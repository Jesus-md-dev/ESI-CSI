package es.uca.gii.csi20.mdj.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class Caso extends Entity{
	
	private String _sTitulo;
	private String _sDescripcion;
	private int _iImportancia;
	private Estado _estado;
	
	public String getTitulo() { return _sTitulo; }
	public void setTitulo(String sTitulo) { _sTitulo = sTitulo; }
	public String getDescripcion() { return _sDescripcion; }
	public void setDescripcion(String sDescripcion) { _sDescripcion = sDescripcion; }
	public int getImportancia() { return _iImportancia; }
	public void setImportancia(int iImportancia) { _iImportancia = iImportancia; }
	public Estado getEstado() { return _estado; }
	public void setEstado(Estado estado) { _estado = estado; }
	
	/**
	 * Create an object Caso
	 * @param iId
	 * @throws Exception
	 */
	public Caso(int iId) throws Exception {
		Connection con = null;
		ResultSet rs = null;
		try {
			con = Data.Connection();
			Initialize(iId, con);
		} catch ( SQLException e ) { throw e; } 
		finally {
			if(rs != null) rs.close();
			if(con != null) con.close();
		}
	}
	
	/**
	 * Create an object Caso with a initialized Connection
	 * @param iId
	 * @param con
	 * @throws Exception
	 */
	public Caso(int iId, Connection con) throws Exception { Initialize(iId, con); }
	
	/**
	 * Initialize the object with data of the database
	 * @param iId
	 * @param con
	 * @throws Exception
	 */
	private void Initialize(int iId, Connection con) throws Exception {
		ResultSet rs = null;
		try {
			rs = con.createStatement().executeQuery("SELECT caso.Id, caso.Id_Estado, caso.Titulo,"
					+ " caso.Descripcion, caso.Importancia FROM caso Where caso.Id = " + iId);
			rs.next();
			_sTitulo = rs.getString("Titulo");
			_sDescripcion = rs.getString("Descripcion");
			_iImportancia = rs.getInt("Importancia");
			_estado = new Estado(rs.getInt("Id_Estado"));
			setIsDeleted(false);
			setTable("caso");
			setId(iId);
		} catch(Exception e) { throw e; }
		finally { if(rs != null) rs = null; }
	}

	public String toString() {
		return "Caso" + "@" + getId() + ":" + _sTitulo + ":" + _sDescripcion 
				+ ":" + _iImportancia + ":" + _estado;
	}
	
	/**
	 * @param sTitulo
	 * @param sDescripcion
	 * @param iImportancia
	 * @param sEstado
	 * @return List of objects Caso equivalents to the parameters
	 * @throws Exception
	 */
	public static ArrayList<Caso> Select(String sTitulo, String sDescripcion,
			Integer iImportancia, String sEstado) throws Exception {
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Caso> aCaso = new ArrayList<Caso>();
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("Select caso.Id, caso.Id_Estado, caso.Titulo,"
					+ " caso.Descripcion, caso.Importancia"
					+ " FROM caso INNER JOIN estado ON caso.Id_Estado = estado.Id "
					+ Where(
							new String[] { "caso.Titulo", "caso.Descripcion", "caso.Importancia", "estado.Nombre" },
							new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR }, 
							new Object[] { sTitulo, sDescripcion,  iImportancia, sEstado }));
			while(rs.next())
				aCaso.add(new Caso(rs.getInt("Id"), con));
			return aCaso;
		} catch (Exception e) { throw e; }
		finally {
			if(rs != null) rs.close();
			if(con != null) con.close();
		}
	}

	/**
	 * @param sTitulo
	 * @param sDescripcion
	 * @param iImportancia
	 * @param estado
	 * @return an object Caso with the parameters
	 * @throws Exception
	 */
	public static Caso Create(String sTitulo, String sDescripcion, int iImportancia,
			Estado estado) throws Exception {
		Connection con = null;
		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO caso (Id_Estado, Titulo, Descripcion, Importancia)"
					+ "VALUES (" + estado.getId() + "," + Data.String2Sql(sTitulo, true, false) + "," 
					+ Data.String2Sql(sDescripcion, true, false) + "," + iImportancia + ")");
			return new Caso(Data.LastId(con));
		}
		catch (SQLException ee) { throw ee; }
		finally { if(con != null) con.close(); }
	}
	
	/**
	 * Update the object in the database
	 * @throws Exception
	 */
	public void Update() throws Exception {
		super.Update("UPDATE caso SET"
				+ " Titulo = " + Data.String2Sql(_sTitulo, true, false)
				+ ", Descripcion = " + Data.String2Sql(_sDescripcion, true, false)
				+ ", Importancia = " + _iImportancia
				+ ", Id_Estado = " + _estado.getId()
				+ " WHERE id = " + getId());
	}
}
