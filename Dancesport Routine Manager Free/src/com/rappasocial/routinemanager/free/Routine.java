package com.rappasocial.routinemanager.free;

public class Routine {
	
	int id;
	public String name;
	public String yt_id;
	long created_on;
	long modified_on;
	
	
	Routine(int _id, String _name, String _yt_id, long _created_on, long _modified_on) {
		
		id = _id;
		name = _name;
		yt_id = _yt_id;
		created_on = _created_on;
		modified_on = _modified_on;
		
	}

}
