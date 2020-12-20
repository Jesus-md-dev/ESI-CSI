package es.uca.gii.csi20.mdj.data;

import java.sql.Connection;
import java.sql.Types;
import java.util.StringJoiner;

public abstract class Entity {
	protected int _iId;
	protected boolean _bIsDeleted = false;
	protected String _sTabla;
	
	public int getId() { return _iId; }
	public boolean getIsDeleted() { return _bIsDeleted; }
	
	/**
	 * @param sQuery
	 * @throws Exception
	 */
	protected void Update(String sQuery) throws Exception {
		Connection con = null;
		
		try {
			if(_bIsDeleted)
				throw new Exception("El Caso ya ha sido eliminado");
			
			con = Data.Connection();
			con.createStatement().executeUpdate(sQuery);
		} catch(Exception e) {
			throw e;
		}
		finally {
			if(con != null) con.close();
		}
	}
	
	/**
	 * @throws Exception
	 */
	public void Delete() throws Exception {
		Connection con = null;
		
		try {
			if(_bIsDeleted)
				throw new Exception("El estado ya ha sido eliminado.");
			
			con = Data.Connection();
			con.createStatement().executeUpdate("DELETE FROM " + _sTabla + " WHERE id = " 
			+ _iId + ";");
			_bIsDeleted = true;
		} catch(Exception e){
			throw e;
		}
		finally {
			if(con != null) con.close();
		}
	}
	
	/**
	 * @param sFields
	 * @param iTypes
	 * @param oValues
	 * @return
	 */
	protected static String Where(String[] sFields, int[] iTypes, Object[] oValues) {
		StringJoiner stringjoinerAnd = new StringJoiner(" AND ");
  
        for(int i = 0; i < sFields.length; ++i) {
        	if(oValues[i] != null)
        		switch(iTypes[i]) {
        			case Types.VARCHAR:
        				stringjoinerAnd.add(sFields[i] + " like " + Data.String2Sql(oValues[i].toString(),
        						true, true));
        				break;
        			case Types.INTEGER:
        				stringjoinerAnd.add(sFields[i] + " = " + oValues[i]);
        				break;
        		}
        		
        }
        
        if(stringjoinerAnd.toString() != "")
        	return "Where " + stringjoinerAnd;
        return stringjoinerAnd.toString();
	}
}
