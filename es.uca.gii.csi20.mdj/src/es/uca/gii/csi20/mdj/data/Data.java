package es.uca.gii.csi20.mdj.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

import es.uca.gii.csi20.mdj.util.Config;

public class Data {
	
    public static String getPropertiesUrl() { return "./db.properties"; }
	
    public static Connection Connection() throws Exception {
	
        try {
            Properties properties = Config.Properties(getPropertiesUrl());
            return DriverManager.getConnection(
                properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.username"),
                properties.getProperty("jdbc.password"));
       }
       catch (Exception ee) { throw ee; }
	}
    
    public static void LoadDriver() 
        throws InstantiationException, IllegalAccessException, 
        ClassNotFoundException, IOException {
			
            Class.forName(Config.Properties(Data.getPropertiesUrl()
            ).getProperty("jdbc.driverClassName")).newInstance();
    }
    
    public static String String2Sql( String s, boolean bAddQuotes, boolean bAddWildcards ) {
    	
    	s = s.replace("'","''");
    	
    	if(bAddWildcards)
    		s = new String("%" + s + "%");    	
    	
    	if(bAddQuotes)
    		s = new String("'" + s + "'");
    	
    	return s;
    }
    
    public static int Boolean2Sql(boolean b) {
    	if(b)
    		return 1;
    	else
    		return 0;
    }
    
    public static int LastId(Connection con) throws Exception {
    	ResultSet rs = null;
    	
    	try {
    		Properties properties = Config.Properties(getPropertiesUrl());
    		rs = con.createStatement().executeQuery(properties.getProperty("jdbc.lastIdSentence"));
    		rs.next();
    		
    		return rs.getInt("LAST_INSERT_ID()");
    	}
    	catch (Exception ee) { throw ee; }
    }
    
}