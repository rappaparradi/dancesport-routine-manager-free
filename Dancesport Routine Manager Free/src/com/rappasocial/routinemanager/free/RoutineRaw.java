package com.rappasocial.routinemanager.free;

public class RoutineRaw {
	
	public int id;
	public int routine_id;
	public int figure_id;
	public String timing;
	public String comment;
	public int weihgt;
	public int gender;
	
	RoutineRaw(int _id, int _routine_id, int _figure_id, String _timing, String _comment, int _weihgt, int _gender) {
		
		id = _id;
		routine_id = _routine_id;
		figure_id = _figure_id;
		timing = _timing;
		comment = _comment;
		weihgt = _weihgt;
		gender = _gender;
		
	}
	
    RoutineRaw(RoutineRaw obj) {
		
		id = obj.id;
		routine_id = obj.routine_id;
		figure_id = obj.figure_id;
		timing = obj.timing;
		comment = obj.comment;
		weihgt = obj.weihgt;
		gender = obj.gender;
		
	}
	
    RoutineRaw(int _figure_id) {
		

		figure_id = _figure_id;
		timing = "";
		comment = "";
		
	}

}
