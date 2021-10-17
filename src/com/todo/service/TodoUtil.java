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
		
		System.out.print("완료 (0 or 1)> ");
		int is_checked = sc.nextInt();
		
		System.out.print("즐겨찾기 (0 or 1) > ");
		int is_favorite = sc.nextInt();
		
		int rate;
		if(is_checked == 0) {
			System.out.print("완수율 (0~100) > ");
			rate = sc.nextInt();
		}
		else rate = 100;
		
		System.out.print("마감일자 > ");
		String due_date = sc.next().trim();
		TodoItem i = new TodoItem(title,desc,category,due_date);
		i.setIs_checked(is_checked);
		i.setIs_favorite(is_favorite);
		i.setRate(rate);
		if(list.addItem(i)>0)
			System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l,Scanner sc) {
		
		System.out.print("[항목 삭제]\n 삭제할 항목의 번호를 입력하시오 > ");
		while(sc.hasNextInt()) {
			int no = sc.nextInt();
			
			if(l.deleteItem(no)>0) {
				System.out.println(no + "번 항목 삭제되었습니다. \n");
			}
		}
		sc.nextLine();
	}

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 수정]\n수정할 항목의 번호를 입력하시오 > ");
		
		int index = sc.nextInt();
		
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
		
		System.out.print("완료 (0 or 1)> ");
		int is_checked = sc.nextInt();
		
		System.out.print("즐겨찾기 (0 or 1) > ");
		int is_favorite = sc.nextInt();
		
		int rate;
		if(is_checked == 0) {
			System.out.print("완수율 (0~100) > ");
			rate = sc.nextInt();
		}
		else rate = 100;
		
		TodoItem i = new TodoItem(new_title,new_description,new_category,new_due_date);
		i.setIs_checked(is_checked);
		i.setIs_favorite(is_favorite);
		i.setRate(rate);
		i.setId(index);
		if(l.updateItem(i)>0)
			System.out.println("수정되었습니다.");

	}
	
	public static void listAll(TodoList l) {
		System.out.println("[전체 목록, 총 "+l.getCount()+"개]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}

	public static void listAll(TodoList l,String orderby, int ordering) {
		System.out.println("[전체 목록, 총 "+l.getCount()+"개]");
		for (TodoItem item : l.getOrderedList(orderby,ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void completeItem(TodoList l, Scanner sc) {
		int index;
		while(sc.hasNextInt()) {
			index = sc.nextInt();
			l.completeItem(index);
			System.out.println(index + "번 항목 완료 체크하였습니다.\n");
		}
		sc.nextLine();
	}
	
	public static void listChecked(TodoList l) {
		int count = 0;
		for(TodoItem item : l.getListChecked()) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 "+ count + "개의 항목이 완료되었습니다.");
	}
	
	public static void listFavorite(TodoList l) {
		int count = 0;
		for(TodoItem item : l.getListFavorite()) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 "+ count + "개의 항목이 완료되었습니다.");
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
		int count = 0;
		for(TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 "+ count + "개의 항목을 찾았습니다.");
	}
	
	public static void findCateList(TodoList l,String keyword) {
		int count = 0;
		for(TodoItem item: l.getListCategory(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 "+ count + "개의 항목을 찾았습니다.");
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		for(String item : l.getCategories()) {
			System.out.print(item+" ");
			count++;
		}
		System.out.println("\n총 "+count+"개의 카테고리가 등록되어 있습니다.");
	}
	
	
}
