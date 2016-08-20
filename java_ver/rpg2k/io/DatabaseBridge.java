package rpg2k.io;

import java.sql.*;

public class DatabaseBridge
{
	// fields
	protected Connection CONNECTION;
	// constructor
	public DatabaseBridge() {
		Class.forClass("org.sqlite.JDBC");
		CONNECTION = new DriverManager.getConnection("jdbc:sqlite::memory:");
	}
	// store array to database
	protected void store(Lcf1DArray input, byte raw[][]) {
	}
	protected void store(Lcf2DArray input, byte raw[][][]) {
	}
}
