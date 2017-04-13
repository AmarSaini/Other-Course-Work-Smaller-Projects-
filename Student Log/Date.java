
public class Date {
	
	int year;
	int month;
	int day;
	
	public Date (int year, int month, int day) {
		
		if (year < 1900 || year > 3000)
			System.out.println("Invalid year.");
		else
			this.year = year;
		
		
		if (month < 1 || month > 12)
			System.out.println("Invalid month");
		else
			this.month = month;
		
		if (month == 2 && day > 28)
			System.out.println("Invalid month and day");
		else if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30)
			System.out.println("Invalid month and day");
		else
			this.day = day;
	}
	
	public int getYear() {
		return year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getDay() {
		return day;
	}
	
	
	public int compare(Date dt) {
		int difference = Math.abs((dayOfYear() + 365 * year) - (dt.dayOfYear() + 365 * dt.year));
		int months = difference/30;
		if (months <= 60)
			return months;
		else
			return 60;
	}
	
	public int dayOfYear() {
		
		int totalDays = 0;
		switch (month) {
		
		case 12: totalDays += 30;
		case 11: totalDays += 31;
		case 10: totalDays += 30;
		case 9 : totalDays += 31;
		case 8 : totalDays += 31;
		case 7 : totalDays += 30;
		case 6 : totalDays += 31;
		case 5 : totalDays += 30;
		case 4 : totalDays += 31;
		case 3 : totalDays += 28;
		case 2 : totalDays += 31;
		
		}
		
		totalDays += day;
		return totalDays;
		
		}
	
	}
