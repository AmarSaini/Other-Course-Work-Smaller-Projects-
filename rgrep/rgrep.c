#include <stdio.h>
#define MAXSIZE 4096

/**
 * You can use this recommended helper function 
 * Returns true if partial_line matches pattern, starting from
 * the first char of partial_line.
 */
int matches_leading(char *partial_line, char *pattern) {
  // Implement if desire 

  return 0;
}

int charCheck(char *line, char *pattern, int lineSize, int patternSize, int oldLC, int printCheck) {

	int lCount = 0; //Counts increments of char* line
	int pCount = 0; //Counts increments of char* pattern
	int dotCount = 0; //Counts increments of the '.' wildcard
	int k = 1; //Used to initalize the increment of the '.' wildcard
	int q = 1; //Used to modify the pattern for the '?' wildcard
	int qCount = 0; //Counts location of the char before the '?' wildcard
	int trueSize = 0; //Counts the true size of the pattern (Wildcards excluded if they don't count as part of the string)
	int pl = 2; //Increments the pattern to whatever is after a '+' wildcard
	int plusCount = 0; //Counts how many of the same char is after the '.' wildcard

	while (*pattern != '\0') { //Counts true size

		if (*pattern == '\\') {

			pattern++;
			pCount++;
			continue;

		}

		if (*(pattern+1) == '+') {

			pattern++;
			pattern++;
			pCount++;
			pCount++;
			trueSize++;
			continue;

		}

		if (*(pattern+1) == '?') {

			pattern++;
			pattern++;
			pCount++;
			pCount++;
			continue;

		}

		trueSize++;
		pattern++;
		pCount++;

	}

	while(pCount != 0) { //Resets pattern char pointer

		pattern--;
		pCount--;

	}

	if (trueSize > lineSize) { //Checks if the true size of the pattern is greater than the line

		return 0;

	}

	//printf("%d %d %s %s", patternSize, lineSize, pattern, line);

	while (*line != '\0') {

	Check: //Used to get back to the while loop when you have to exit another while loop.

		if (*pattern == '\\') { //'\' wildcard function

			pattern++;
			pCount++;

			goto Backslash;

		}

		if (*(pattern + 1) == '?') { //'?' wildcard function with recursion.

			char tempP1 [patternSize-1];
			char* newP1 = tempP1;

			qCount = pCount;

			while (pCount != 0) {

				pCount--;
				pattern--;

			}

			while (pCount != qCount) {

				*newP1 = *pattern;
				newP1++;
				pattern++;
				pCount++;

			}

			*newP1 = *pattern;
			newP1++;

			while (*(pattern + q) != '\0') {

				*newP1 = *(pattern + q + 1);
				newP1++;
				q++;

			}

			q = 1;

			while (pCount != 0) {

				pCount--;
				pattern--;

			}

			while (lCount != 0) {

				lCount--;
				line--;

			}

			for (int i = 0; i < patternSize; i++) {

				newP1--;

			}

			patternSize--;

			printCheck += charCheck(line, newP1, lineSize, patternSize, oldLC, printCheck);

			char tempP2 [patternSize-1];
			char* newP2 = tempP2;

			while (pCount != qCount) {

				*newP2 = *pattern;
				newP2++;
				pattern++;
				pCount++;

			}

			while (*(pattern + q) != '\0') {

				*newP2 = *(pattern + q + 1);
				newP2++;
				q++;

			}

			q = 1;

			while (pCount != 0) {

				pCount--;
				pattern--;

			}

			while (lCount != 0) {

				lCount--;
				line--;

			}

			for (int i = 0; i < patternSize; i++) {

				newP2--;

			}

			patternSize--;

			printCheck += charCheck(line, newP2, lineSize, patternSize, oldLC, printCheck);

			return printCheck;


		}

		if (*(pattern + 1) == '+') { //'+' wildcard function/loop

			if (*pattern == '.') { //'.' with a '+' wildcard function

				if (*(pattern + 2) == '\0') { //If nothing is after the .+ then print

					goto Print;

				}

				pCount++;
				pCount++;
				pattern++;
				pattern++;
				line++;
				lCount++;

				oldLC = oldLC + lCount;

				printCheck += charCheck(line, pattern, lineSize - lCount, patternSize - pCount, oldLC, printCheck); //Recursion for finding a match after .+

				return printCheck;

				

			}

			if (*pattern == *line) { //Regular '+' wildcard function

				if (*pattern == *(line + 1)) { //Checks if there is a same char after the '+' wildcard

					while (*pattern == *(pattern + pl)) {

						pl++;
						plusCount++;

					}

					pl = 2;

				}

				while (*pattern == *line) { //Checks for multiple chars of the char before the '+' wildcard.

					line++;
					lCount++;

				}

				while (plusCount != 0) {

					line--;
					lCount--;
					plusCount--;

				}

				pCount++; //Skips over + and counts it as a char match.
				pCount++;
				pattern++;
				pattern++;

			}


		}

		while (*pattern == '.') { //'.' wildcard function/loop

			if (*(pattern - 1) == '\0') { //No dotCount needed

				pattern++;
				pCount++;
				line++;
				lCount++;

				if (*pattern == *line) {

					dotCount++;
					goto Match;

				}

			}

			else if (*(pattern - 1) != '\0') { //dotCount needed

				if (dotCount == 0) {

					while (*(pattern - k) != '\0') { //Initializes dotCount for charaters before the first '.' wildcard

						dotCount++;
						k++;

					}

					k = 1;
					pattern++;
					pCount++;
					line++;
					lCount++;
					continue;

				}

				pattern++; //Increments for other '.' wildcards
				pCount++;
				line++;
				lCount++;
				dotCount++;

			}

			goto Check;

		}

		Backslash:

		if ((*pattern != *line) && (pCount != 0) && (pCount != patternSize)) {

			while (pCount != 0) { //Decrements pattern to the correct location.

				pCount--;
				pattern--;

			}

			while (dotCount != 0) { //Decrements dotCount and line to the correct location.

				dotCount--;
				lCount--;
				line--;

			}

			if (*pattern != '\\') {

				continue;

			}

		}

		if (*pattern == *line) { //Increments pattern and line if the chars match

			if (dotCount != 0) { //Increments dotCount if a . wildcard is being used

				dotCount++;

			}

			Match:

			if (*(pattern + 1) == '+') {

				goto Check;

			}

			pattern++;
			pCount++;
			line++;
			lCount++;

			continue;

		}

		if (pCount == patternSize) { //Prints the string if the pattern is found

		Print:

		while (lCount != 0) {

			line--;
			lCount--;

		}

		while (oldLC != 0) {

			line--;
			oldLC--;

		}

			return 1;

		}

		line++;
		lCount++;

	}

	return printCheck;

}

/**
 * You may assume that all strings are properly null terminated 
 * and will not overrun the buffer set by MAXSIZE 
 *
 * Implementation of the rgrep matcher function
 */
int rgrep_matches(char *line, char *pattern) {

	int lCount = 0; //Counts increments of char* line
	int pCount = 0; //Counts increments of char* pattern
	int lineSize = 0; //Line string size
	int patternSize = 0; //Pattern string size
	int oldLC = 0;
	int printCheck = 0;

	while (*line != '\n') {

		lineSize++;
		line++;
		lCount++;

	}

	for (int i = 0; i < lCount; i++) {

		line--;

	}

	lCount = 0;

	while (*pattern != '\0') {

		patternSize++;
		pattern++;
		pCount++;

	}

	for (int i = 0; i < pCount; i++) {

		pattern--;

	}

	pCount = 0;

	printCheck = charCheck(line, pattern, lineSize, patternSize, oldLC, printCheck);

	if (printCheck > 0) {

		return 1;

	}

    return 0;
}

int main(int argc, char **argv) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s <PATTERN>\n", argv[0]);
        return 2;
    }

    /* we're not going to worry about long lines */
    char buf[MAXSIZE];

    while (!feof(stdin) && !ferror(stdin)) {
        if (!fgets(buf, sizeof(buf), stdin)) {
            break;
        }
        if (rgrep_matches(buf, argv[1])) {
            fputs(buf, stdout);
            fflush(stdout);
        }
    }

    if (ferror(stdin)) {
        perror(argv[0]);
        return 1;
    }

    return 0;
}
