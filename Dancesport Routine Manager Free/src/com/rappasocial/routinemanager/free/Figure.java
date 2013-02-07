package com.rappasocial.routinemanager.free;

public class Figure {
	
	int id;
	public String name;
	public String description;
	public int dance_id;
	
	Figure(int _id, String _name) {
		
		id = _id;
		name = _name;
		
	}
	
    Figure(int _id, String _name, int _dance_id , String _description) {
		
		id = _id;
		name = _name;
		dance_id = _dance_id;
		description = _description;
		
	}
	
    Figure(int _id, String _name, int _dance_id) {
		
		id = _id;
		name = _name;
		dance_id = _dance_id;
		
	}
	

}
