package com.todo.service;

import java.io.*;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� �߰�]\n���� > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("�̹� �����ϴ� �����Դϴ�.");
			return;
		}
		
		System.out.print("ī�װ� > ");
		String category = sc.next().trim();

		
		System.out.print("���� > ");
		sc.nextLine();
		desc = sc.nextLine().trim();
		
		System.out.print("�Ϸ� (0 or 1)> ");
		int is_checked = sc.nextInt();
		
		System.out.print("���ã�� (0 or 1) > ");
		int is_favorite = sc.nextInt();
		
		int rate;
		if(is_checked == 0) {
			System.out.print("�ϼ��� (0~100) > ");
			rate = sc.nextInt();
		}
		else rate = 100;
		
		System.out.print("�������� > ");
		String due_date = sc.next().trim();
		TodoItem i = new TodoItem(title,desc,category,due_date);
		i.setIs_checked(is_checked);
		i.setIs_favorite(is_favorite);
		i.setRate(rate);
		if(list.addItem(i)>0)
			System.out.println("�߰��Ǿ����ϴ�.");
	}

	public static void deleteItem(TodoList l,Scanner sc) {
		
		System.out.print("[�׸� ����]\n ������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		while(sc.hasNextInt()) {
			int no = sc.nextInt();
			
			if(l.deleteItem(no)>0) {
				System.out.println(no + "�� �׸� �����Ǿ����ϴ�. \n");
			}
		}
		sc.nextLine();
	}

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[�׸� ����]\n������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		
		int index = sc.nextInt();
		
		System.out.print("�� ���� > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("�̹� �����ϴ� �����Դϴ�.");
			return;
		}
		
		System.out.print("�� ī�װ� > ");
		String new_category = sc.next().trim();

		
		System.out.print("�� ���� > ");
		sc.nextLine();
		String new_description = sc.nextLine().trim();
		
		System.out.print("�� �������� > ");
		String new_due_date = sc.next().trim();
		
		System.out.print("�Ϸ� (0 or 1)> ");
		int is_checked = sc.nextInt();
		
		System.out.print("���ã�� (0 or 1) > ");
		int is_favorite = sc.nextInt();
		
		int rate;
		if(is_checked == 0) {
			System.out.print("�ϼ��� (0~100) > ");
			rate = sc.nextInt();
		}
		else rate = 100;
		
		TodoItem i = new TodoItem(new_title,new_description,new_category,new_due_date);
		i.setIs_checked(is_checked);
		i.setIs_favorite(is_favorite);
		i.setRate(rate);
		i.setId(index);
		if(l.updateItem(i)>0)
			System.out.println("�����Ǿ����ϴ�.");

	}
	
	public static void listAll(TodoList l) {
		System.out.println("[��ü ���, �� "+l.getCount()+"��]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}

	public static void listAll(TodoList l,String orderby, int ordering) {
		System.out.println("[��ü ���, �� "+l.getCount()+"��]");
		for (TodoItem item : l.getOrderedList(orderby,ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void completeItem(TodoList l, Scanner sc) {
		int index;
		while(sc.hasNextInt()) {
			index = sc.nextInt();
			l.completeItem(index);
			System.out.println(index + "�� �׸� �Ϸ� üũ�Ͽ����ϴ�.\n");
		}
		sc.nextLine();
	}
	
	public static void listChecked(TodoList l) {
		int count = 0;
		for(TodoItem item : l.getListChecked()) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("�� "+ count + "���� �׸��� �Ϸ�Ǿ����ϴ�.");
	}
	
	public static void listFavorite(TodoList l) {
		int count = 0;
		for(TodoItem item : l.getListFavorite()) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("�� "+ count + "���� �׸��� �Ϸ�Ǿ����ϴ�.");
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
			System.out.println("�о�� ������ ã�� �� �����ϴ�.");
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
		System.out.println("�� "+ count + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void findCateList(TodoList l,String keyword) {
		int count = 0;
		for(TodoItem item: l.getListCategory(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("�� "+ count + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		for(String item : l.getCategories()) {
			System.out.print(item+" ");
			count++;
		}
		System.out.println("\n�� "+count+"���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.");
	}
	
	
}
