package com.todo.dao;

import java.util.*;
import java.io.*;
import java.sql.*;

import com.todo.service.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;
	
	Connection conn;

	public TodoList() {
		this.conn = DbConnect.getConnection();
		this.list = new ArrayList<TodoItem>();
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, current_date,due_date,is_checked,is_favorite,rate)"
				+"values(?,?,?,?,?,?,?);";
		String sql2 = "insert into cate (category) values(?);";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCurrent_date());
			pstmt.setString(4, t.getDue_date());
			pstmt.setInt(5, t.getIs_checked());
			pstmt.setInt(6, t.getIs_favorite());
			pstmt.setInt(7, t.getRate());
			count = pstmt.executeUpdate();
			pstmt.close();
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, t.getCategory());
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, current_date=?, due_date=? , is_checked=?, is_favorite=?, rate = ?"
				+"where id = ?;" ;
		String sql2 = "update cate set category=? where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCurrent_date());
			pstmt.setString(4, t.getDue_date());
			pstmt.setInt(5, t.getIs_checked());
			pstmt.setInt(6, t.getIs_favorite());
			pstmt.setInt(7, t.getRate());
			pstmt.setInt(8, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, t.getCategory());
			pstmt.setInt(2, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int getCount() {
		Statement stmt;
		int count=0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id=?;";
		String sql2 =  "delete from cate where id=?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list inner join cate on list.id = cate.id";
			ResultSet  rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_checked = rs.getInt("is_checked");
				int is_favorite  = rs.getInt("is_favorite");
				int rate = rs.getInt("rate");
				TodoItem t = new TodoItem(title,description,category,due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_checked(is_checked);
				t.setIs_favorite(is_favorite);
				t.setRate(rate);
				list.add(t);
			}
			rs.close();
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public ArrayList<TodoItem> getList(String keyword){
		ArrayList<TodoItem> list =  new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%"+keyword+"%";
		try {
			String sql = "select * from list inner join cate on list.id = cate.id where title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_checked = rs.getInt("is_checked");
				int is_favorite  = rs.getInt("is_favorite");
				int rate = rs.getInt("rate");
				TodoItem t = new TodoItem(title,description,category,due_date);
				t.setId(id);
				t.setIs_checked(is_checked);
				t.setIs_favorite(is_favorite);
				t.setRate(rate);
				t.setCurrent_date(current_date);
				list.add(t);
				
			}
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby,int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select * from list order inner join cate on list.id = cate.id by "+orderby;
			if(ordering == 0) {
				sql += "  desc";
			}
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_checked = rs.getInt("is_checked");
				int is_favorite  = rs.getInt("is_favorite");
				int rate = rs.getInt("rate");
				TodoItem t = new TodoItem(title,description,category,due_date);
				t.setId(id);
				t.setIs_checked(is_checked);
				t.setIs_favorite(is_favorite);
				t.setRate(rate);
				t.setCurrent_date(current_date);
				list.add(t);
				
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<String> getCategories(){
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select distinct category from cate";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(rs.getString("category"));
			}
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list =  new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "select * from list inner join cate on list.id = cate.id where category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_checked = rs.getInt("is_checked");
				int is_favorite  = rs.getInt("is_favorite");
				int rate = rs.getInt("rate");
				TodoItem t = new TodoItem(title,description,category,due_date);
				t.setId(id);
				t.setIs_checked(is_checked);
				t.setIs_favorite(is_favorite);
				t.setRate(rate);
				t.setCurrent_date(current_date);
				list.add(t);
				
			}
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListChecked(){
		ArrayList<TodoItem> list =  new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "select * from list inner join cate on list.id = cate.id where list.is_checked = 1";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_checked = rs.getInt("is_checked");
				int is_favorite  = rs.getInt("is_favorite");
				int rate = rs.getInt("rate");
				TodoItem t = new TodoItem(title,description,category,due_date);
				t.setId(id);
				t.setIs_checked(is_checked);
				t.setIs_favorite(is_favorite);
				t.setRate(rate);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListFavorite(){
		ArrayList<TodoItem> list =  new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "select * from list inner join cate on list.id = cate.id where list.is_favorite = 1";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_checked = rs.getInt("is_checked");
				int is_favorite  = rs.getInt("is_favorite");
				int rate = rs.getInt("rate");
				TodoItem t = new TodoItem(title,description,category,due_date);
				t.setId(id);
				t.setIs_checked(is_checked);
				t.setIs_favorite(is_favorite);
				t.setRate(rate);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		System.out.println("\n"
				+ "inside list_All method\n");
		for (TodoItem myitem : list) {
			System.out.println(myitem.getTitle() + myitem.getDesc());
		}
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}
	
	public void reverseByDate() {
		Collections.sort(list, new TodoSortByDate());
		Collections.reverse(list);
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
	
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, category, current_date,due_date)"
					+"values(?,?,?,?,?);";
			int records = 0;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line,"##");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due = st.nextToken();
				String current = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, desc);
				pstmt.setString(3, category);
				pstmt.setString(4, current);
				pstmt.setString(5, due);
				int count = pstmt.executeUpdate();
				if(count > 0) records++;
				pstmt.close();
			}
			System.out.println(records+" records read!!");
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void completeItem(int index) {
		String sql = "update list set is_checked = ? ,rate = ?"
				+"where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, 100);
			pstmt.setInt(3, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
