package es.uca.gii.csi20.mdj.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.StringJoiner;

public abstract class Entity {
	protected int __iId;
	protected boolean __bIsDeleted = false;
	protected String __sTabla;
	
	public int getId() { return __iId; }
	public boolean getIsDeleted() { return __bIsDeleted; }
	
	/**
	 * @param sQuery
	 * @throws Exception
	 */
	protected void Update(String sQuery) throws Exception {
		Connection con = null;
		
		try {
			if(__bIsDeleted) throw new IllegalStateException(__sTabla + " " + __iId + " ya eliminado.");
			
			con = Data.Connection();
			con.createStatement().executeUpdate(sQuery);
		} catch(Exception e) { throw e; }
		finally { if(con != null) con.close(); }
	}
	
	public void Delete() throws Exception {
		Connection con = null;
		
		try {
			if(__bIsDeleted) throw new IllegalStateException(__sTabla + " " + __iId + " ya eliminado.");
			
			con = Data.Connection();
			con.createStatement().executeUpdate("DELETE FROM " + __sTabla + " WHERE id = " + __iId + ";");
			__bIsDeleted = true;
		} catch(SQLException e){ throw e; }
		finally { if(con != null) con.close(); } 
	}
	
	/**
	 * @param asFields
	 * @param aiTypes
	 * @param aoValues
	 * @return
	 */
	protected static String Where(String[] asFields, int[] aiTypes, Object[] aoValues) {
		StringJoiner stringjoinerAnd = new StringJoiner(" AND ");
  
        for(int i = 0; i < asFields.length; ++i) {
        	if(aoValues[i] != null)
        		switch(aiTypes[i]) {
        			case Types.VARCHAR:
        				stringjoinerAnd.add(asFields[i] + " like " + Data.String2Sql(aoValues[i].toString(), true, true));
        				break;
        			case Types.INTEGER:
        				stringjoinerAnd.add(asFields[i] + " = " + aoValues[i]);
        				break;
        		}
        }
        if(stringjoinerAnd.toString() != "")
        	return "Where " + stringjoinerAnd;
        return stringjoinerAnd.toString();
	}
}
