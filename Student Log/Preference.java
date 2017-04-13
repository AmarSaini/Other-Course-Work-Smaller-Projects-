
public class Preference {

	int quietTime;
	int music;
	int reading;
	int chatting;
	
	public Preference(int quietTime, int music, int reading, int chatting) {
		this.quietTime = quietTime;
		this.music = music;
		this.reading = reading;
		this.chatting = chatting;
	}
	
	public int getQuietTime() {
		return quietTime;
	}
	
	public int getMusic() {
		return music;
	}
	
	public int getReading() {
		return reading;
	}
	
	public int getChatting() {
		return chatting;
	}
	
	public int compare (Preference pref) {
		int quietTimeScore = Math.abs(getQuietTime() - pref.getQuietTime());
		int musicScore = Math.abs(getMusic() - pref.getMusic());
		int readingScore = Math.abs(getReading() - pref.getReading());
		int chattingScore = Math.abs(getChatting() - pref.getChatting());
		
		int totalScore = quietTimeScore + musicScore + readingScore + chattingScore;
		
		return totalScore;
	}
	
}
