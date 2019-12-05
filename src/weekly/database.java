package weekly;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class database {
	

	Connection con;
 /*
	String url="jdbc:postgresql://192.168.174.74:5432/gigadb_v3/";
	String password="gigadb2013";
	String user="gigadb";
	*/
	
	String url="jdbc:postgresql://localhost:5432/gigadb_v3/";
	String password="gigadb2013";
	String user="gigadb";

	Statement stmt;
	PreparedStatement prepforall= null;
	
	public database () {
		
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			con = DriverManager.getConnection(url, user, password);
		//this is important
			con.setAutoCommit(true);
			stmt = con.createStatement();
		
//		int i=1;
		
			} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
		// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
		// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	
	public void getid() throws SQLException {
		
		
			String query= "select * from dataset;" ;
			ResultSet resultSet=stmt.executeQuery(query);
			while(resultSet.next())
			{
				
				System.out.println(resultSet.getInt(1));
		
			}
	}
	
public ArrayList getdataset_manuscript() throws SQLException
	
	{
		
		
		String query= "select identifier from dataset where upload_status='Published' and id NOT IN (select dataset_id from manuscript, dataset where manuscript.dataset_id=dataset.id) order by identifier;";
		ResultSet rs = null;
		String location=null;
		ArrayList<String> aa= new ArrayList<String>();
		rs= stmt.executeQuery(query);
		while(rs.next())
		{
			location= rs.getString(1);
			
			aa.add(location);
			
		}
		
		return aa;
		
	}

	public int getallpublished_doi() throws SQLException
		{
			String query="select count(id) from dataset where upload_status='Published';";
			ResultSet rs=null;
			int number=0;
			rs =stmt.executeQuery(query);
			while(rs.next())
			{
				number= rs.getInt(1);
			}
			
			return number;
	
	}
	
	public String getallunpublished_doi() throws SQLException
	{
		String query="select identifier, title from dataset where upload_status != 'Published' order by identifier;";
		ResultSet rs=null;
		String content="";
		rs =stmt.executeQuery(query);
		while(rs.next())
		{
			String temp="";
			temp += rs.getString(1) +": "+rs.getString(2);
			content+= temp +"\n";
		}
		
		return content;

}
	
	public long getallpublished_size() throws SQLException 
	{	
		long number=0;
		
		String query="select SUM(CAST(dataset_size AS BIGINT)) from dataset where upload_status='Published';";
		ResultSet rs=null;
		rs =stmt.executeQuery(query);
		while(rs.next())
		{
			Object value= rs.getObject(1);
			number= ((Number) value).longValue();
		}
		return number;
	}
	
	public void close () throws SQLException
	{
		con.close();
		
	}
	
	public static void main(String[] args) throws Exception {
		database db = new database();			
//		database.calPhraseProb();
//		System.out.println(database.exist("1.5524/100003"));
	}

}
