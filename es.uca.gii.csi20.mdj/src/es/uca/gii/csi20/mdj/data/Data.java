package es.uca.gii.csi20.mdj.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
    	String r = "";
    	int i = 0;
    	char c = new String("'").charAt(0);
        
        while(i < s.length()){
            if(s.charAt(i) == c )
                r+="''";
            else
                r+=s.charAt(i);
            ++i;
        }    
    	
    	if(bAddWildcards)
    		r = new String("%" + r + "%");
    	
    	if(bAddQuotes)
    		r = new String("'" + r + "'");
    	
    	return r;
    }
    
    public static int Boolean2Sql(boolean b) {
    	if(b)
    		return 1;
    	else
    		return 0;
    }
        
}