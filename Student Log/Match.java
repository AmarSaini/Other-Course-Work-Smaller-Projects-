import java.util.*;
import java.io.*;

public class Match {

	public static void readFile(Student[] sarr) {
		
		String filename = "";
		Scanner input = new Scanner(System.in);
		
		System.out.print("Type in file location: ");
		filename = input.next();
		
		readFile(filename, sarr);
		
	}
	
	public static void readFile(String filename, Student[] sarr) {
		
		int i = 0;
		String name;
		char gender;
		int month;
		int day;
		int year;
		int quietTime;
		int music;
		int reading;
		int chatting;
		int numStudents;
		int currentScore = 0;
		int bestScore = 0;
		Student bestStudent = null;
		
		try {
			
		Scanner input = new Scanner(new FileReader(filename));
		input.useDelimiter("[\t-]");
		
		while (input.hasNextLine()) {
			Scanner nl = new Scanner(input.nextLine());
			nl.useDelimiter("[\t-]");
			
			name = nl.next();
			gender = nl.next().charAt(0);
			
			month = nl.nextInt();
			day = nl.nextInt();
			year = nl.nextInt();
			
			quietTime = nl.nextInt();
			music = nl.nextInt();
			reading = nl.nextInt();
			chatting = nl.nextInt();
			
			System.out.println(name + " " + gender + " " + month + " " + day + " " + year  + " " + quietTime + " " + music + " " + reading + " " + chatting);
			
			Date dt = new Date(year, month, day);
			Preference pt = new Preference(quietTime, music, reading, chatting);
			sarr[i] = new Student (name, gender, dt, pt);
			i++;
			
		}
		
		input.close();
		}
		
		catch (FileNotFoundException e) {
			System.out.println("e");
		}
		
		catch (NoSuchElementException e) {
			System.out.println("e");
		}
		
		numStudents = i;
		
		for (i = 0; i < numStudents; i++) {
			
			bestScore = 0;
			bestStudent = null;
			
			if (sarr[i].getMatch() != true) {
				
				for (int j = i + 1; j < numStudents; j++) {
					
					if (sarr[j].getMatch() != true) {
						currentScore = sarr[i].compare(sarr[j]);
					
						if (currentScore > bestScore) {
							bestScore = currentScore;
							bestStudent = sarr[j];
						}
					
					}
					
				}
				
			}
			
			if (bestScore > 0) {
				System.out.println(sarr[i].getName() + " matches with " + bestStudent.getName() + " with the score of " + bestScore);
				sarr[i].changeMatch(bestStudent);
			}
			
			if (bestScore == 0 && sarr[i].getMatch() != true) {
				System.out.println(sarr[i].getName() + " has no matches");
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		
		Student[] sarr = new Student[100];
		
		readFile(sarr);

	}

}