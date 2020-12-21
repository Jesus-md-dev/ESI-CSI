package es.uca.gii.csi20.mdj.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.StringJoiner;

public abstract class Entity {
	private int _iId;
	private boolean _bIsDeleted = false;
	private String _sTable;
	
	public int getId() { return _iId; }
	public void setId(int iId) { _iId = iId; } 
	public boolean getIsDeleted() { return _bIsDeleted; }
	public void setIsDeleted(boolean bIsdeleted) { _bIsDeleted = bIsdeleted; }
	public String getTable() { return _sTable; }
	public void setTable(String sTabla) { _sTable = sTabla; }
	 
	/**
	 * @param sQuery
	 * @throws Exception
	 */
	protected void Update(String sQuery) throws Exception {
		Connection con = null;
		
		try {
			if(_bIsDeleted) throw new IllegalStateException(_sTable + " " + _iId + " ya eliminado.");
			
			con = Data.Connection();
			con.createStatement().executeUpdate(sQuery);
		} catch(Exception e) { throw e; }
		finally { if(con != null) con.close(); }
	}
	
	public void Delete() throws Exception {
		Connection con = null;
		
		try {
			if(_bIsDeleted) throw new IllegalStateException(_sTable + " " + _iId + " ya eliminado.");
			
			con = Data.Connection();
			con.createStatement().executeUpdate("DELETE FROM " + _sTable + " WHERE id = " + _iId + ";");
			_bIsDeleted = true;
		} catch(SQLException e){ throw e; }
		finally { if(con != null) con.close(); } 
	}
	
	/**
	 * @param asField
	 * @param aiType
	 * @param aoValue
	 * @return
	 */
	protected static String Where(String[] asField, int[] aiType, Object[] aoValue) {
		StringJoiner stringJoiner = new StringJoiner(" AND ");
  
        for(int i = 0; i < asField.length; ++i) {
        	if(aoValue[i] != null)
        		switch(aiType[i]) {
        			case Types.VARCHAR:
        				stringJoiner.add(asField[i] + " like " + Data.String2Sql(aoValue[i].toString(), true, true));
        				break;
        			case Types.INTEGER:
        				stringJoiner.add(asField[i] + " = " + aoValue[i]);
        				break;
        		}
        }
        if(stringJoiner.toString() != "")
        	return "Where " + stringJoiner;
        return stringJoiner.toString();
	}
}
