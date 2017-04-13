
public class Student {

	String name = "";
	char gender;
	Date birthDay;
	Preference pref;
	boolean match = false;
	
	public Student (String name, char gender, Date birthDay, Preference pref) {
		this.name = name;
		this.gender = gender;
		this.birthDay = birthDay;
		this.pref = pref;
	}

	public String getName() {
		return name;
	}
	
	public char getGender() {
		return gender;
	}
	
	public Date getBirthDay() {
		return birthDay;
	}
	
	public Preference getPref() {
		return pref;
	}
	
	public boolean getMatch() {
		return match;
	}
	
	public void changeMatch(Student st) {
		match = true;
		st.changeMatch();
	}
	
	public void changeMatch() {
		match = true;
	}
	
	public int compare(Student st) {
		
		int score = 0;
		int prefScore = pref.compare(st.pref);
		int birthDayScore = birthDay.compare(st.birthDay);
		
		if (getGender() != st.getGender())
			return 0;
		
		score = (40-prefScore) + (60 - birthDayScore);
		
		return score;
		
	}
}
