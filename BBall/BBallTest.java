import static org.junit.Assert.*;

import org.junit.Test;

public class BBallTest {
	
	@Test
	public void testGameScore ( ) {
		int [ ] teamScore;
		teamScore = new int [BBall.MAX_ROUNDS];
		for (int k = 0; k < teamScore.length; k++) {
			teamScore[k] = BBall.SENTINEL;
		}
		assertEquals (0, BBall.gameScore(teamScore));
		teamScore[0] = 5;
		assertEquals (5, BBall.gameScore(teamScore));
		teamScore[1] = 9;
		assertEquals (14, BBall.gameScore(teamScore));
		teamScore[2] = 2;
		assertEquals (16, BBall.gameScore(teamScore));
		teamScore[3] = 3;
		assertEquals (19, BBall.gameScore(teamScore));
		teamScore[4] = 4;
		assertEquals (23, BBall.gameScore(teamScore));
		
		int [] visitors1 = {1, 1, 1, 1, BBall.SENTINEL};
		int [] visitors2 = {1, 3, 4, 1, 0, 2, BBall.SENTINEL};
		int [] home1 = {2, 2, 2, 2, BBall.SENTINEL};
		int [] home2 = {2, 3, 2, 3, 2, BBall.SENTINEL};
		
		assertEquals ("1   1   1   1   ", 4, BBall.gameScore(4, visitors1));
		assertEquals ("2   2   2   2   ", 8, BBall.gameScore(4, home1));
		assertEquals ("1   3   4   1   0   2   ", 11, BBall.gameScore(6, visitors2));
		assertEquals ("2   1   2   3   2   0   ", 12, BBall.gameScore(6, home2));
	}
	
	@Test
	public void testResult ( ) { //Both methods of Results
		int rounds6 = 13;
		int rounds5 = 6;
		int rounds4 = 5;
		int rounds3 = 7;
		int rounds2 = 4;
		int rounds1 = 4;
		int [ ] visitors6 = {2, 0, 1, 0, 0, 0, 2, 3, 2, 1, 2, 1, 4, BBall.SENTINEL};
		int [ ] visitors5 = {2, 0, 1, 0, 0, 0, BBall.SENTINEL};
		int [ ] visitors4 = {3, 0, 0, 0, 1, BBall.SENTINEL};
		int [ ] visitors3 = {3, 0, 0, 4, 0, 0, 3, BBall.SENTINEL};
		int [ ] visitors2 = {1, 1, 0, 0, BBall.SENTINEL};
		int [ ] visitors1 = {1, 0, 0, 0, BBall.SENTINEL};
		int [ ] home6 = {2, 0, 1, 0, 0, 0, 2, 3, 2, 1, 2, 1, 4, BBall.SENTINEL};
		int [ ] home5 = {4, 0, 2, 0, 0, 1, BBall.SENTINEL};
		int [ ] home4 = {3, 0, 0, 0, 0, BBall.SENTINEL};
		int [ ] home3 = {3, 0, 0, 1, 0, 0, 2, BBall.SENTINEL};
		int [ ] home2 = {5, 0, 0, 0, BBall.SENTINEL};
		int [ ] home1 = {4, 0, 0, 0, BBall.SENTINEL};
		
		assertEquals ("The home team won 4 to 1 in Regulation", BBall.result(rounds1, visitors1, home1));
		assertEquals ("The visiting team won 10 to 6 in 3 Overtimes", BBall.result(rounds3, visitors3, home3));
		assertEquals ("The home team won 7 to 3 in 2 Overtimes", BBall.result(rounds5, visitors5, home5));
		
		assertEquals ("The home team won 5 to 2 in Regulation", BBall.result(rounds2, BBall.gameScore(visitors2), BBall.gameScore(home2)));
		assertEquals ("The visiting team won 4 to 3 in 1 Overtime", BBall.result(rounds4, BBall.gameScore(visitors4), BBall.gameScore(home4)));
		assertEquals ("The visiting team tied with the home team 18 to 18 in 9 Overtimes", BBall.result(rounds6, BBall.gameScore(visitors6), BBall.gameScore(home6)));
		
	}
	
	@Test
	public void testGameIsOver ( ) {

		int visitors6 = 3;
		int visitors5 = 4;
		int visitors4 = 11;
		int visitors3 = 14;
		int visitors2 = 8;
		int visitors1 = 5;
		int home6 = 18;
		int home5 = 4;
		int home4 = 11;
		int home3 = 3;
		int home2 = 8;
		int home1 = 5;
		
		assertEquals (false, BBall.gameIsOver (5, visitors1, home1));
		assertEquals (false, BBall.gameIsOver (7, visitors2, home2));
		assertEquals (false, BBall.gameIsOver (4, visitors3, home3));
		assertEquals (false, BBall.gameIsOver (13, visitors4, home4));
		assertEquals (false, BBall.gameIsOver (10, visitors5, home5));
		assertEquals (false, BBall.gameIsOver (3, visitors6, home6));
	}
	
	
}
