package com.ghb.mydemo.observer;

import java.util.Observable;

public class User extends Observable {

	private String name;
	private int headId;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getHeadId() {
		return headId;
	}
	
	public void setHeadId(int headId) {
		this.headId = headId;
	}
	
	@Override
	public boolean hasChanged() {
		return true;
	}
	
}
