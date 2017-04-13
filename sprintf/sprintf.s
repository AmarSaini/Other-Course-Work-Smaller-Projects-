#sprintf!
#$a0 has the pointer to the buffer to be printed to
#$a1 has the pointer to the format string
#$a2 and $a3 have (possibly) the first two substitutions for the format string
#the rest are on the stack
#return the number of characters (ommitting the trailing '\0') put in the buffer

        .text

sprintf:

	li $s0, -1		# Start counter at -1 because of NULL terminator
	li $s1, 2		# Start argument counter at 2

Loop1:				# Goes through the format string

	lb $t0, 0($a1)		# Loads next letter from format
	
	beq $t0, '%', Procedure1	# Checks if letter is %
	sb $t0, 0($a0)		# Stores letter in buffer
	addi $a1, $a1, 1	# Increments pointer to format
	addi $a0, $a0, 1	# Increments pointer to buffer
	addi $s0, $s0, 1	# Increments character count
	bne $t0, $0, Loop1	# Checks if the format pointer is pointing at NULL
	
	move $v0, $s0		# Moves character count into $v0
	
Loop2:				# Drecrements buffer back to its head

	addi $a0, $a0, -1	# Decrements pointer to buffer
	addi $s0, $s0, -1	# Decrements counter for the decrement of the pointer to buffer
	bne $s0, $0, Loop2
	
	
	jr	$ra		#this sprintf implementation rocks!
	
Procedure1:

	addi $a1, $a1, 1	# Increments pointer to format
	lb $t0, 0($a1)		# Loads next letter from format
	li $s3, 0		# Set the at least value back to 0
	li $s4, 0		# Set the at most value back to 0
	li $s5, 0		# Set the left justify value back to 0
	li $s6, 0		# Set the negative check back to 0
	beq $s1, 2, MoveArg2	# Checks to see if argument 2 is next
	beq $s1, 3, MoveArg3	# Checks to see if argument 3 is next
	beq $s1, 4, MoveArg4	# Checks to see if argument 4 is next
	beq $s1, 5, MoveArg5	# Checks to see if argument 5 is next
	beq $s1, 6, MoveArg6	# Checks to see if argument 6 is next
	beq $s1, 7, MoveArg7	# Checks to see if argument 7 is next
ArgSkip:
	addi $s1, $s1, 1	# Increment argument count
	beq $t0, 's', String	# Checks if letter is s
	beq $t0, 'u', Unsigned	# Checks if letter is u
	beq $t0, 'x', Hex	# Checks if letter is x
	beq $t0, 'o', Octal	# Checks if letter is o
	beq $t0, 'd', Decimal	# Checks if letter is d
	beq $t0, '.', AtMost	# Checks if letter is .
	beq $t0, '+', Positive	# Checks if letter is +
	beq $t0, '-', LeftJustify	# Checks if letter is -
	j AtLeast
		
String:

	lb $t1, 0($s2)		# Loads the first letter of str
	
StringSize:
	addi $t5, $t5, 1	# Increments string size
	addi $s2, $s2, 1	# Increments pointer to str
	lb $t1, 0($s2)		# Loads next letter of str
	bne $t1, $zero, StringSize	# Checks if str pointer is pointing at NULL
	
	move $t6, $t5		# Moves string size into $t6
	
RestoreString:
	addi $s2, $s2, -1	# Decrements pointer to str
	addi $t6, $t6, -1	# Decrements string size
	bne $t6, $zero, RestoreString	# Checks if the pointer to str is restored
	
	bge $t5, $s3, Continue1	# Checks if the string size is at least $s3
	sub $t6, $s3, $t5	# Amount of the spaces needed to meet the at least requirement
	li $t1, ' '		# Loads a space into $t1
	
AtLeastLoop1:
	sb $t1, 0($a0)		# Stores a space into buffer
	addi $a0, $a0, 1	# Increments pointer to buffer
	addi $t6, $t6, -1	# Decrement spaces needed
	addi $s0, $s0, 1	# Increments character count
	bne $t6, $zero, AtLeastLoop1	# Checks if any more spaces are needed
	
Continue1:
	
	lb $t1, 0($s2)		# Loads the first letter of str
	li $t5, 0		# Set $t5 back to 0
	
StringLoop:

	sb $t1, 0($a0)		# Stores letter of str into buffer
	addi $t5, $t5, 1	# Increments counter for how many chars have been stored in the buffer
	addi $s2, $s2, 1	# Increments pointer to str
	addi $a0, $a0, 1	# Increments pointer to buffer
	addi $s0, $s0, 1	# Increments character count
	lb $t1, 0($s2)		# Loads next letter of str
	beq $t5, $s4, Break1	# Breaks the loop when the at most number of chars have been stored
	bne $t1, $zero, StringLoop	# Checks if str pointer is pointing at NULL
	
Break1:
	addi $a1, $a1, 1	# Increments pointer to format
	li $t5, 0		# Set $t5 back to 0
	j Loop1			# Returns to Loop1

Unsigned:

	move $t1, $s2		# Loads the Unsigned argument into $t1
	li $t2, 10		# Loads 10 into $t2
	j Loop3			# Jumps to Loop3

Hex:

	move $t1, $s2		# Loads the Hex argument into $t1
	li $t2, 16		# Loads 16 into $t2
	j Loop3			# Jumps to Loop3

Octal:

	move $t1, $s2		# Loads the Octal argument into $t1
	li $t2, 8		# Loads 8 into $t2
	j Loop3			# Jumps to Loop3

Decimal:

	move $t1, $s2		# Loads the Decimal argument into $t1
	li $t2, 10		# Loads 10 into $t2
	
	bge $t1, $zero, Loop3	# Checks if the Decimal is negative
	
	li $t8, -1		# Loads -1 into $t8
	mult $t1, $t8		# Makes the Decimal argument positive
	mflo $t1		# Moves the positive Decimal argument into $t1
	
	li $s6, 1		# Sets $s6 as 1 to include the - in a negative number
	
	j Loop3			# Jumps to Loop3
	
Loop3:
	div $t1, $t2		# Divides argument by 10
	addi $t3, $t3, 1	# Increments the number count
	addi $sp, $sp, -4	# Decrements pointer to the stack
	mflo $t1		# Sets the argument as the result of the argument divided by 10
	mfhi $t4		# Stores the remainder into $t4
	sw $t4, 0($sp)		# Stores the remainder into the stack
	bne $t1, $zero, Loop3	# Checks if the argument divded by 10 is 0
	
	move $t8, $t3		# Move size of decimal to $t8 for Left-Justify
	beq $s5, 1, Negative	# Left-Justify the decimal
	
	
Left1:
	bge $t8, $s3, Continue2 # Checks if decimal size is at least $s3
	ble $s3, $s4, Continue2 # Checks if the at least value is less than or the same as the at most value
	
	sub $t5, $s3, $t8	# Amount of spaces needed for the at least requirement
	
	li $t4, ' '		# Sets $t4 as a space
	
AtLeastLoop2:
	sb $t4, 0($a0)		# Stores a space in the buffer
	addi $a0, $a0, 1	# Increments pointer to buffer
	addi $s0, $s0, 1	# Increments character count
	addi $t5, $t5, -1	# Decrements spaces needed
	bne $t5, $zero, AtLeastLoop2	# Checks if any more spaces are needed
	
Continue2:

	beq $s5, 1, Break2	# Skip Loop4 since the decimal has been Left-Justified
	
Negative:
	bne $s6, 1, Continue3	# Checks if this is a negative number
	li $t7, '-'		# Loads '-' into $t8
	sb $t7, 0($a0)		# Stores '-' into the buffer
	addi $a0, $a0, 1	# Increments pointer to buffer
	addi $s0, $s0, 1	# Increments character counter

Continue3:

	beq $s4, $zero, Loop4	# Jumps to Loop4 if the at most number is 0
	ble $s4, $t8, Loop4	# Jumpts to Loop4 if the at most number is less than or the same as the decimal size
	sub $t6, $s4, $t8	# Amount of 0's to add for the at most requirement
	
	li $t4, '0'		# Sets $t4 as the 0 char
	
AtMostLoop1:
	sb $t4, 0($a0)		# Stores the char into the buffer
	addi $a0, $a0, 1	# Incrments pointer to buffer
	addi $s0, $s0, 1	# Increments character count
	addi $t6, $t6, -1	# Decrements amount of 0's to add
	bne $t6, $zero, AtMostLoop1	# Checks if there is any more 0's to add
	
Loop4:	
	lw $t4, 0($sp)		# Loads the number from the stack
	bge $t4, 10, HexValue	# Checks if the number is greater than or equal to 10
	addi $t4, $t4, '0'	# Converts number into char
HexSkip:			# Skips the number to char conversion for Hex chars
	sb $t4, 0($a0)		# Stores the char into the buffer
	addi $sp, $sp, 4	# Increments pointer to the stack
	addi $a0, $a0, 1	# Incrments pointer to buffer
	addi $s0, $s0, 1	# Increments character count
	addi $t3, $t3, -1	# Decrements number count
	bne $t3, $zero, Loop4	# Checks if the last char has been stored into the buffer
	
	beq $s5, 1, Left1	# If Left-Justified, add remaining spaces
	
Break2:
	li $t5, 0		# Set $t5 back to 0
	li $t6, 0		# Set $t6 back to 0
	li $t7, 0		# Set $t7 back to 0
	li $t8, 0		# Set $t7 back to 0
	addi $a1, $a1, 1	# Increments pointer to format
	j Loop1			# Returns to Loop1
	
HexValue:
	addi $t4, $t4, 87	# Adds 87 to get a lowercase char for Hex digits 10-15
	j HexSkip		# Jumps to HexSkip

MoveArg2:
	move $s2, $a2		# Moves arg 2 into $s2
	j ArgSkip		# Jumps to ArgSkip

MoveArg3:
	move $s2, $a3		# Moves arg 3 into $s2
	j ArgSkip		# Jumps to ArgSkip
	
MoveArg4:
	lw $s2, 16($sp)		# Moves arg 4 into $s2
	j ArgSkip		# Jumps to ArgSkip
	
MoveArg5:
	lw $s2, 20($sp)		# Moves arg 5 into $s2
	j ArgSkip		# Jumps to ArgSkip
	
MoveArg6:
	lw $s2, 24($sp)		# Moves arg 6 into $s2
	j ArgSkip		# Jumps to ArgSkip
	
MoveArg7:
	lw $s2, 28($sp)		# Moves arg 7 into $s2
	j ArgSkip		# Jumps to ArgSkip
	
AtLeast:
	addi $s3, $t0, -48	# Converts the at least number from a char to a number, and stores it in $s3
	addi $a1, $a1, 1	# Increment pointer to format
	lb $t0, 0($a1)		# Loads next letter from format
	
	beq $t0, 's', String	# Checks if letter is s
	beq $t0, 'd', Decimal	# Checks if letter is d
	beq $t0, '.', AtMost	# Checks if letter is .
	
AtMost:
	addi $a1, $a1, 1	# Increment pointer to format
	lb $t0, 0($a1)		# Loads next letter from format
	addi $s4, $t0, -48	# Converts the at least number from a char to a number, and stores it in $s4
	
	addi $a1, $a1, 1	# Increment pointer to format
	lb $t0, 0($a1)		# Loads next letter from format
	
	beq $t0, 's', String	# Checks if letter is s
	beq $t0, 'd', Decimal	# Checks if letter is d
	
Positive:

	addi $a1, $a1, 1	# Increment pointer to format
	blt $s2, $zero, Decimal	# Checks if the number is positive
	li $t0, '+'
	sb $t0, 0($a0)		# Stores + into the buffer
	addi $a0, $a0, 1	# Increments pointer to buffer
	addi $s0, $s0, 1	# Increments character count
	
	j Decimal
	
LeftJustify:
	addi $a1, $a1, 1	# Increment pointer to format
	lb $t0, 0($a1)		# Loads next letter from format
	li $s5, 1		# Sets $s5 as 1 to left justify later
	
	j AtLeast		# Jumpts to AtLeast
	


