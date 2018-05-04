/**
 * Generic methods to connect to ORACLE database and operate with db records
 */
package test_Suite.lib.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import test_Suite.constants.db.IDbConst;

/**
 * @author apankov
 *
 */
public class DBSqlBase {
	private static final Logger log = Logger.getLogger(DBSqlBase.class);
	
    // database related variables
    protected OracleDriver driver = null;
    protected Connection con            = null;
    
    protected CallableStatement[] cst   = null;
    protected Statement[] stmt          = null;
    protected PreparedStatement[] pStmt = null;
    protected ResultSet[] rs            = null;
    
    protected String url        = null;
    protected String user       = null;
    protected String password   = null;
    protected String sql        = null;
    protected int dbMode;
    
	/**
	 * 
	 */
	public DBSqlBase() {
		//create arrays of objects used by the current Db connection
		this.stmt = new Statement[1];
		this.rs   = new ResultSet[1];
	}

    /**
	* Disposes all database resources
	*/
    public void cleanUp() throws SQLException
    {
    	log.debug("Cleaning up");
    	
		//	dispose of result sets, if any
		if(this.rs != null)
		{
			for(int i=0; i<this.rs.length; i++)
			{
				if(this.rs[i] != null)
				{
					this.rs[i].close();
				}
			}
			
			this.rs = null;
		}
	   
		//	dispose of statements, if any
		if(this.stmt != null)
		{
			for(int i=0; i<this.stmt.length; i++)
			{
				if(this.stmt[i]!=null)
				{
					this.stmt[i].close();
				}
			}
			
			this.stmt = null;
		}
		
		//	dispose of prepared statements, if any
		if(this.pStmt != null)
		{
			for(int i=0;i<this.pStmt.length;i++)
			{
				if(this.pStmt[i] != null)
				{
					this.pStmt[i].close();
				}
			}
			
			this.pStmt = null;
		}
		
		//dispose of callable statements, if any
	    if(this.cst != null)
	    {
	    	for(int i=0;i<this.cst.length;i++)
	    	{
	    		if(this.cst[i]!=null)
	    		{
	    			this.cst[i].close();
	    		}
		}
	    	
	    	this.cst = null;
	    }
		
		// dispose of DB connection
	   if (this.con != null)
	   {
		   this.con.close();
		   this.con = null;
	   }
	
		//deregister driver
	   if(this.driver != null)
	   {
		    DriverManager.deregisterDriver(this.driver);
		    this.driver = null;
	   }
    }

    
    /**
     * Sets the user.
     * 
     * @param usr the usr
     */
    public void setUser(String usr)
    {
    	this.user = usr;
    }

    
    public String getUser()
    {
	   return this.user;
    }

    /**
	* Convenience method for setting the DB password
	*
	* @param String pwd  --DB password
	*/
    public void setPassword(String pwd)
    {
	   this.password = pwd;
    }

    /**
	* Gets the DB password
	*
	* @param String password  --DB password
	*/
    public String getPassword()
    {
	   return this.password;
    }

    /**
	* Sets the DB URL
	*
	* @param String u     --The DB URL
	*/
    public void setUrl(String url)
    {
	   this.url = url;
    }

    /**
	* Gets the DB URL
	*
	* @return String url  --DB URL
	*/
    public String getUrl()
    {
	   return this.url;
    }

    /**
	* Sets the database connection mode
	*
	* @param int m     --The DB connection mode
	*/
    public void setDbMode(int mode)
    {
	   this.dbMode = mode;
    }

    /**
	* Gets the database connection mode
	*
	* @return int 
	*/
    public int getDbMode()
    {
	   return this.dbMode;
    }

    /**
	* Sets the DB SQL string
	*
	* @param  String sql  --DB SQL string
	*/
    public void setSqlString(String sql)
    {
	   this.sql = sql;
    }

    /**
	* Gets the DB SQL string
	*
	* @return String sqlString  --DB SQL string
	*/
    public String getSqlString()
    {
	   return this.sql;
    }

    /**
	* Prepares DB connection
	*/
    public Connection getConnection() throws SQLException
    {
	if(this.con == null)
	{
		this.driver = new OracleDriver();
		DriverManager.registerDriver(this.driver);
			this.con = DriverManager.getConnection(this.url, this.user, this.password);
	}
            
	    return this.con;
    }
    
    /**
     * Sets DB connection to the specified one
     * 
     * @param c
     */
    public void setConnection(Connection con)
    {
    	if(con != null)
    	{
			this.con = con;
		}            
    }
    
    /**
	* Prepares DB connection
	*/
    public Connection getConnection(String ur, String us, String pw) throws SQLException
    {
    	//reset
    	if(this.con != null)
    	{
			this.con.close();
			this.con = null;
    	}
    	
    	this.driver = new OracleDriver();
    	log.info("URL " + ur + " User: " + us + " Pass: " + pw);
	    DriverManager.registerDriver(this.driver);
	    log.info("REGISTERIN DRIVER");
		this.con = DriverManager.getConnection(ur, us, pw);
		log.info("RESTORING CONNECTION " + con.toString());
	    return this.con;
    }


    public Connection getDataSourceConnection() throws NamingException, SQLException
    {
    	log.trace("started getConnection()");
    	Context initContext = new InitialContext();
    	log.trace("got initial context");
    	Context envContext  = (Context)initContext.lookup(IDbConst.CONTEXT_LOOKUP_KEY);
    	log.trace("got envContext");    	    	 
    	DataSource ds       = (DataSource)envContext.lookup(IDbConst.DATA_SOURCE_NAME);
    	log.trace("got DataSource");
    	Connection conn     = null;
    	 
    	if(ds == null)
    	{
    		log.debug("Data source is NULL");
    	}
    	else
    	{
    		conn     = ds.getConnection();  		
    	}   	
    	   	
    	 return conn;  
    }
    
    public ResultSet getResultSet(String sqlQuery)
    {
		try
		{
			log.debug("Query: " + sqlQuery);
			this.stmt[0] = this.getConnection().createStatement();
			this.rs[0]   = this.stmt[0].executeQuery(sqlQuery);
			if(this.rs[0] == null)
				throw new Exception("ResultSet is null after execution!");
	    	
		}
		catch (SQLException sqle)
		{
			log.error("SQL EXCEPTION: " + sqle.getMessage());
		}
		catch (Exception ex)
		{
			log.error("EXCEPTION:" + ex.getMessage());
		}
		return this.rs[0];
    }
}
