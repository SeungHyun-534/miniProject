package com.todo.service;

import java.io.*;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 추가]\n제목 > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("이미 존재하는 제목입니다.");
			return;
		}
		
		System.out.print("카테고리 > ");
		String category = sc.next().trim();

		
		System.out.print("내용 > ");
		sc.nextLine();
		desc = sc.nextLine().trim();
		
		System.out.print("마감일자 > ");
		String due_date = sc.next().trim();
		
		list.addItem(new TodoItem(title,desc,category,due_date));
		System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		System.out.print("[항목 삭제]\n 삭제할 항목의 번호를 입력하시오 > ");
		Scanner sc = new Scanner(System.in);
		int no = sc.nextInt();
		TodoItem item = l.getList().get(no-1);
		
		System.out.println(no+". [" + item.getCategory() +"] "+item.getTitle()+" - "+ item.getDesc() + " - "+ item.getDue_date()+" - "+item.getCurrent_date());
		System.out.print("위 항목을 삭제하시겠습니까? (y/n)");
		String ans = sc.next();
		
		if(ans.equals("y")) {
			l.deleteItem(item);
			System.out.println("삭제되었습니다.");
		}
		
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 수정]\n수정할 항목의 번호를 입력하시오 > ");
		int no = sc.nextInt();
		TodoItem item = l.getList().get(no-1);
		System.out.println(no+". [" + item.getCategory() +"] "+item.getTitle()+" - "+ item.getDesc() + " - "+ item.getDue_date()+" - "+item.getCurrent_date());

		System.out.print("새 제목 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("이미 존재하는 제목입니다.");
			return;
		}
		
		System.out.print("새 카테고리 > ");
		String new_category = sc.next().trim();

		
		System.out.print("새 내용 > ");
		sc.nextLine();
		String new_description = sc.nextLine().trim();
		
		System.out.print("새 마감일자 > ");
		String new_due_date = sc.next().trim();
		
		l.deleteItem(item);
		l.addItem(new TodoItem(new_title,new_description,new_category,new_due_date));
		System.out.println("수정되었습니다.");

	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록, 총 "+l.getList().size()+"개]");
		int no = 1;
		for (TodoItem item : l.getList()) {
			System.out.println(no+". [" + item.getCategory() +"] "+item.getTitle()+" - "+ item.getDesc() + " - "+ item.getDue_date()+" - "+item.getCurrent_date());
			++no;
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter(filename);
			for(TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String oneline;
			while((oneline = reader.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(oneline,"##");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				l.addItem(new TodoItem(title,desc,current_date,category,due_date));
			}
			reader.close();
		}catch(FileNotFoundException e) {
			System.out.println("읽어올 파일을 찾을 수 없습니다.");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void findItem(TodoList l,String keyword) {
		int no = 1, count = 0;
		for(TodoItem item : l.getList()) {
			if(item.getTitle().indexOf(keyword) != -1 || item.getDesc().indexOf(keyword) != -1) {
				System.out.println(no+". [" + item.getCategory() +"] "+item.getTitle()+" - "+ item.getDesc() + " - "+ item.getDue_date()+" - "+item.getCurrent_date());
				++count;
			}
			++no;
		}
		System.out.println("총 "+ count + "개의 항목을 찾았습니다.");
	}
	
	public static void findByCategory(TodoList l,String keyword) {
		int no = 1, count = 0;
		for(TodoItem item : l.getList()) {
			if(item.getCategory().indexOf(keyword) != -1) {
				System.out.println(no+". [" + item.getCategory() +"] "+item.getTitle()+" - "+ item.getDesc() + " - "+ item.getDue_date()+" - "+item.getCurrent_date());
				++count;
			}
			++no;
		}
		System.out.println("총 "+ count + "개의 항목을 찾았습니다.");
	}
	
	public static void ls_cate(TodoList l) {
		HashSet<String> cate = new HashSet<String>();
		for(TodoItem item : l.getList()) {
			cate.add(item.getCategory());
		}
		int count = 0;
		for(String category : cate) {
			if(count == cate.size()-1) {
				System.out.println(category);
			}
			else {
				System.out.print(category+" / ");
			}
			++count;
		}
		System.out.println("총 "+count+"개의 카테고리가 등록되어 있습니다.");
	}
	
	
}
