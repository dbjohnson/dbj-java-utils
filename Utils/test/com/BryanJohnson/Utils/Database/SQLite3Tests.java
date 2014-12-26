package com.BryanJohnson.Utils.Database;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;

import org.junit.Test;

import com.BryanJohnson.Utils.Database.SQLite3;

public class SQLite3Tests extends TestCase {

	final String kDatabasePath = "temp.db";
	
	////////////////////
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	    new File(kDatabasePath).delete();
	}
	
	////////////////////

	@Test
	public void testDriverSupport() {
		org.junit.Assert.assertTrue(SQLite3.driverSupported);
	}

	////////////////////

	@Test
	public void testConnection() {
		Connection conn = SQLite3.connect(kDatabasePath);
		org.junit.Assert.assertNotNull(conn);
	}
	
	////////////////////

	@Test
	public void testRetrieve() {
		Connection conn = SQLite3.connect(kDatabasePath);
		
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate("create table person (id integer, name string)");
			statement.executeUpdate("insert into person values (1, 'Bryan')");
			statement.executeUpdate("insert into person values (2, 'Chris')");
			ResultSet rs = statement.executeQuery("select * from person where name='Chris'");
			int cnt = 0;
			while(rs.next()) {
				++cnt; // can also do select count(*) as count...
//				int id = rs.getInt("id");
//				String name = rs.getString("name");
//				System.out.printf("Person id: %03d name: %s\n", id, name);
			}

			org.junit.Assert.assertEquals(cnt, 1);
		}  catch (SQLException e) {
			e.printStackTrace();
			org.junit.Assert.fail("SQLError encountered!\n");
		}
		
	}

	////////////////////

	@Test 
	public void testCrossReference() {
		Connection connection = SQLite3.connect(kDatabasePath);
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("create table book (id integer PRIMARY KEY, name string)");
			statement.executeUpdate("create table author (id integer PRIMARY KEY, name string)");
			statement.executeUpdate("create table bookauthor (bookID integer, authorID integer)");
			
			statement.executeUpdate("insert into book values(1, '2001: A Space Oddyssey')");
			statement.executeUpdate("insert into author values (1, 'Arthur C. Clarke')");			
			statement.executeUpdate("insert into author values (2, 'Getty Lee')");
			
			statement.executeUpdate("insert into bookauthor (bookID, authorID) values(1,1)");
			statement.executeUpdate("insert into bookauthor (bookID, authorID) values(1,2)");
			
			ResultSet rs = statement.executeQuery("select book.name as bookname, author.name as authorname from book" +
					" inner join bookauthor on book.id = bookauthor.bookID" +
					" inner join author on author.id = bookauthor.authorID");
			
			while (rs.next()) {
				System.out.printf("Book: %s, Author: %s\n", rs.getString("bookname"), rs.getString("authorname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			org.junit.Assert.fail("SQLError encountered!\n");
		}
		
		
		
	}

}
