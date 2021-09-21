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
		
		System.out.print("내용 >");
		sc.nextLine();
		desc = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String title = sc.next();
		
		System.out.print("[항목 삭제]\n 삭제할 항목의 제목을 입력하시오 > ");
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 수정]\n수정할 항목의 제목을 입력하시오 > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("그런 제목을 가진 문서를 찾을 수 없습니다.");
			return;
		}

		System.out.print("새 제목 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("이미 존재하는 제목입니다.");
			return;
		}
		
		System.out.print("새 내용 > ");
		sc.nextLine();
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("수정되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록]");
		for (TodoItem item : l.getList()) {
			System.out.println("[" + item.getTitle() +"] "+ item.getDesc() + " - "+ item.getCurrent_date());
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
				String title = st.nextToken();
				String desc = st.nextToken();
				String current_date = st.nextToken();
				l.addItem(new TodoItem(title,desc,current_date));
			}
			reader.close();
		}catch(FileNotFoundException e) {
			System.out.println("읽어올 파일을 찾을 수 없습니다.");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
