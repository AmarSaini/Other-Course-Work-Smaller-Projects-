import java.util.Scanner;

public class BBall {
	
	public static int REGULATION = 4;
	
	public static int MAX_OVERTIME = 9;
	
	public static final int MAX_ROUNDS = 13;
	
	public static final int SENTINEL = -999;
	
	public static final int VISITORS = 1;
	
	public static final int HOME = 2;
	
	
	
	public static void initialize(int[] visitorScores, int[] homeScores) {
		
		for (int i = 0; i < MAX_ROUNDS; i++) {
			
			visitorScores[i] = SENTINEL;
			homeScores[i] = SENTINEL;
			
		}
		
	}
	
	public static int readScores(Scanner input, int[] visitorScores, int[] homeScores) {
		
		int rounds = 1;
		int visitorScore = 0;
		int homeScore = 0;
		
		while (!gameIsOver(rounds, visitorScore, homeScore)) {
			
			visitorScores[rounds-1] = input.nextInt();
			homeScores[rounds-1] = input.nextInt();
			visitorScore += visitorScores[rounds-1];
			homeScore += homeScores[rounds-1];
			rounds++;
			
		}
		
		return rounds-1;
		
	}
	
	public static boolean gameIsOver(int rounds, int visitorScore, int homeScore) {
		return ((rounds > 4 && visitorScore != homeScore) || (rounds == 14));
	}
	
	
	public static int gameScore(int rounds, int[] teamScores) {
		
		int output = 0;
		
		for (int i = 1; i <= rounds; i++) {
			
			if (teamScores[i-1] != SENTINEL && i <= 4) {
				System.out.print(teamScores[i-1] + "   ");
				output += teamScores[i-1];
			}
			
			else if (teamScores[i-1] != SENTINEL && i > 4) {
				System.out.print(teamScores[i-1] + "     ");
				output += teamScores[i-1];
			}
			
			else
				break;
			
		}
		
		return output;
	}
	
	public static int gameScore(int[] teamScores) {
		
		int output = 0;
		
		for (int i : teamScores) {
			
			if (i != SENTINEL)
				output += i;
			
		}
		
		return output;
	}
	
	
	
	
	public static String result (int rounds, int[] visitorScores, int[] homeScores) {
		
		System.out.print("\t 1   2   3   4");
		
		if (rounds > 4) {
			for (int i = 4 ; i < rounds; i++) {
				System.out.print("   OT" + (i-3));
				}
		}
		
		System.out.println();
		
		System.out.print("Visitors:");
		int visitorScore = gameScore(rounds, visitorScores);
		System.out.println();
		
		
		System.out.print("Home: \t ");
		int homeScore = gameScore(rounds, homeScores);
		System.out.println();
		
		
		if (rounds == 4 && visitorScore > homeScore)
			return "The visiting team won " + visitorScore + " to " + homeScore + " in Regulation";
		
		if (rounds == 4 && homeScore > visitorScore)
			return "The home team won " + homeScore + " to " + visitorScore + " in Regulation";
		
		if (rounds == 5 && visitorScore > homeScore)
			return "The visiting team won " + visitorScore + " to " + homeScore + " in 1 Overtime";
		
		if (rounds == 5 && homeScore > visitorScore)
			return "The home team won " + homeScore + " to " + visitorScore + " in 1 Overtime";
		
		if (rounds > 5 && visitorScore > homeScore)
			return "The visiting team won " + visitorScore + " to " + homeScore + " in " + (rounds-4) + " Overtimes";
		
		if (rounds > 5 && homeScore > visitorScore)
			return "The home team won " + homeScore + " to " + visitorScore + " in " + (rounds-4) + " Overtimes";
		
		if (rounds == 13 && visitorScore == homeScore)
			return "The visiting team tied with the home team " + visitorScore + " to " + homeScore + " in " + (rounds-4) + " Overtimes";
		
		return "";
		
	}
	
	public static String result (int rounds, int visitorScore, int homeScore) {
		
		if (rounds == 4 && visitorScore > homeScore)
			return "The visiting team won " + visitorScore + " to " + homeScore + " in Regulation";
		
		if (rounds == 4 && homeScore > visitorScore)
			return "The home team won " + homeScore + " to " + visitorScore + " in Regulation";
		
		if (rounds == 5 && visitorScore > homeScore)
			return "The visiting team won " + visitorScore + " to " + homeScore + " in 1 Overtime";
		
		if (rounds == 5 && homeScore > visitorScore)
			return "The home team won " + homeScore + " to " + visitorScore + " in 1 Overtime";
		
		if (rounds > 5 && visitorScore > homeScore)
			return "The visiting team won " + visitorScore + " to " + homeScore + " in " + (rounds-4) + " Overtimes";
		
		if (rounds > 5 && homeScore > visitorScore)
			return "The home team won " + homeScore + " to " + visitorScore + " in " + (rounds-4) + " Overtimes";
		
		if (rounds == 13 && visitorScore == homeScore)
			return "The visiting team tied with the home team " + visitorScore + " to " + homeScore + " in " + (rounds-4) + " Overtimes";
		
		return "";
	}
	
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
		int[] visitorScores = new int [MAX_ROUNDS];
		int[] homeScores = new int [MAX_ROUNDS];
		
		initialize(visitorScores, homeScores);
		
		System.out.print("Do you want to see BoxScore? (y or n): ");
		String response = input.next();
		
		System.out.println("Please enter the scores below");
		int rounds = readScores(input, visitorScores, homeScores);
		
		if (response.charAt(0) == 'y' || response.charAt(0) == 'Y') {
			System.out.println(result(rounds, visitorScores, homeScores));
			
		}
		
		else {
			System.out.println(result(rounds, gameScore(visitorScores), gameScore(homeScores)));
		}
		
	}

}
