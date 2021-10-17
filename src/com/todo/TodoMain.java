package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		
		boolean quit = false;
		do {
			Menu.prompt();
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l,sc);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
			case "ls_name":
				System.out.println("��������� �����Ͽ����ϴ�.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("���񿪼����� �����Ͽ����ϴ�.");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("��¥������ �����Ͽ����ϴ�.");
				TodoUtil.listAll(l, "due_date", 1);
				break;
			case "ls_date_desc":
				System.out.println("��¥�������� �����Ͽ����ϴ�.");
				TodoUtil.listAll(l, "due_date", 0);
				break;

			case "exit":
				quit = true;
				break;
				
			case "help":
				Menu.displaymenu();
				break;
			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.findItem(l, keyword);
				break;
			case "find_cate":
				TodoUtil.findCateList(l, sc.next());
				break;
			case "comp":
				TodoUtil.completeItem(l, sc);
				break;
			case "ls_comp":
				TodoUtil.listChecked(l);
				break;
			case "ls_favorite":
				TodoUtil.listFavorite(l);
				break;
			default:
				System.out.println("��Ȯ�� ��ɾ �Է��ϼ���. (���� - help)");
				break;
			}
		} while (!quit);
	}
}
