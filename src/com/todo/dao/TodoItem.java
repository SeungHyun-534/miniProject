package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private int id;
    private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
    private int is_checked;
    private int rate;
    private int is_favorite;


    public TodoItem(String title, String desc,String category, String due_date){
        this.title=title;
        this.desc=desc;
        this.category = category;
        this.due_date = due_date;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date= f.format(new Date());
    }
    
    public TodoItem(String title, String desc, String current_date, String category, String due_date){
        this.title=title;
        this.desc=desc;
        this.current_date= current_date;
        this.category = category;
        this.due_date = due_date;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
    public String toSaveString() {
    	return category + "##"+ title + "##" + desc + "##" + due_date + "##" + current_date + "\n";
    }

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String toString() {
		String check = is_checked == 1?"√":Integer.toString(getRate())+"%";
		String star = is_favorite == 1 ?"★":"☆";
		return "[" + star +"]"+id+". [" + getCategory() +"] [" + check  +"]"+getTitle()+" - "+ getDesc() + " - "+ getDue_date()+" - "+getCurrent_date();
	}

	public int getIs_checked() {
		return is_checked;
	}

	public void setIs_checked(int is_checked) {
		this.is_checked = is_checked;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getIs_favorite() {
		return is_favorite;
	}

	public void setIs_favorite(int is_favorite) {
		this.is_favorite = is_favorite;
	}
}
