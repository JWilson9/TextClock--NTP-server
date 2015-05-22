package ie.dit.student.wilson.james;

/**
 * ----------------------
 * Name: James Wilson 
 * ID: C12527253     
 * Assignment 1: TxtClock             
 * Date:28/01/2015    
 * ----------------------
 */

/**this is necessary as it reads text from the character input stream*/
import java.io.BufferedReader;

/**this is necessary  for the use of the try and catch*/
import java.io.IOException;

/**this is necessary for the inputStreamReader as it is used
 to directly read characters*/
import java.io.InputStreamReader;

/**this is necessary to represent the internet protocol*/
import java.net.InetAddress;

/*this is necessary for thr client to make connection request*/
import java.net.Socket;

/**this is necessary to format the specific date/ time*/
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * TxtClock class will be taking in arguments from command line and also be
 * taking in the function that will display the NTP and local system time in a
 * string
 */
public class TxtClock {

	/*
	 * these var's are private to the class and their function is to handle
	 * every element in the string
	 */

	/*
	 * for example 01:23:40 -first 0 will be firstElement - secondElement will
	 * be 1 and so on
	 */
	private static int firstElement;
	private static int secondElement;
	private static int thirdElement;
	private static int fourthElement;
	private static int fifthElement;
	private static int sixthElement;

	/*
	 * this is a string array, that contains all the tens numbers, this makes it
	 * much efficient rather than having an array from 1-59
	 */
	private static final String[] tensNames = { "", " ten", " twenty",
			" thirty", " fourty", " fifty" };

	/*
	 * this is an array for the units, that will go up to 19, this way i can
	 * have tens and units , so 20 and 1 would be 21 for example
	 */
	private static final String[] numNames = { "", " one", " two", " three",
			" four", " five", " six", " seven", " eight", " nine", " ten",
			" eleven", " twelve", " thirteen", " fourteen", " fifteen",
			" sixteen", " seventeen", " eighteen", " nineteen" };

	/*
	 * This is my main function where the arguments are passed through as a
	 * string
	 */
	public static void main(String[] args) throws Exception {

		/*
		 * If the separated.length is equal to 3, so if the user input the
		 * correct amount of arguments it executes this if
		 */

		try {

			/* If there is an input in the command line*/
			if (args.length > 0) {
				/*
				 * TimeString is equal to the string that was passed through the
				 * command line argument*/
				String timeString = args[0];
				/*
				 * The separated string array is equal to the timeString, and i
				 * am using the split function so the string is split wherever
				 * there is a colon in the string*/
				String[] separated = timeString.split(":");
				
				/* If there is 3 strings in the string array separated the if executes*/
				if (separated.length == 3) {

					/* Initialing hours , minutes, and seconds  */
					int hours = Integer.parseInt(separated[0]);
					int mins = Integer.parseInt(separated[1]);
					int secs = Integer.parseInt(separated[2]);

					/* toCodonFormat is putting it colon separated*/
					/* printing out code like 10:10:10*/
					//String colonTimeFormat = toColonFormat(hours, mins, secs);
					
					/* calling the function checkTime to see if time input is correct */
					checkTime(hours, mins, secs);

					/* function covert all user input to string*/
					String ConvertInputToString = ConvertingInput(hours, mins,
							secs);
					/* printing off the string format */
					System.out.println(ConvertInputToString);
					/* If the separated.length of the string is not a value of 3, then there was input error and runs the else*/
				}/*end if statement where separated==3*/
				else {
					/* prints off error message*/
					System.out.println("this is am invalid entry for the textCLock");
				}/*end the else statement*/

			}/* end if statement where args.length > 0*/
		}// end try
		/* the catch executes if the user input a character instead of an int*/
		catch (NumberFormatException nfe) {
			/* prints off this error message and it exits out of the program */
			System.out.println("you cannot enter characters");
			System.exit(0);
		}// end catch

		/*If there was no arguments passed through the if executes and call the function
		 * NTP */
		if (args.length == 0) {
			NTPServer();
		}/* End if statement */
	}/* end main function */

	/* converting the input to a string */
	public static String ConvertingInput(int h, int m, int s) {

		/* this string is equal to the colon format of the time that is an input or from a server */
		String timeCodon = toColonFormat(h, m, s);
		/*Prints out the time in colon format */
		System.out.println(timeCodon);
		
		/*Checks the hours to see if it is above 12 or it is 00, so it equals it to 12 or -12 
		 * depending on the outcome */
		int newHours = checkHours(h);
		
		/* converting the hours mins and secs to strings */
		String InputHourString = convertHoursMinsSecs(newHours);
		String InputMinutesString = convertHoursMinsSecs(m);
		String InputSecondsString = convertHoursMinsSecs(s);
		
		/* This is just for a neater print statement for both seconds and minutes for UI*/
		String printMinutes = "It is currently " + InputMinutesString
				+ " minutes and ";
		String printSeconds = InputSecondsString + " seconds " + "past"  ;
		
		
		/*Returns a string of this format */
		return printMinutes + printSeconds + InputHourString + " " + getLocalDate();

	}/*Ends the function/ method of converting input */

	/* this function here is getting the local time for the system */
	public static String getLocalTime() {

		// declare and create a Calendar instance
		Calendar calendar = Calendar.getInstance();
		// declare and set the value of hours, minutes and seconds
		int h = calendar.get(Calendar.HOUR_OF_DAY);
		int m = calendar.get(Calendar.MINUTE);
		int s = calendar.get(Calendar.SECOND);
		
		/* This is converting the hours minutes and seconds to a string */
		String convertToString = ConvertingInput(h, m, s);

		/*Returning a string in this format with the variable string convertToString  */
		return convertToString + " from the system";

	} // end declaration of method to generate a word string for the Time

	/* this is a function that gets the local date */
	public static String getLocalDate() {

		/* this is just a string for printing */
		String tellDate = "The date is: ";
		/* this takes the default locale, time-zone into account when
		 *  deciding which Calendar implementation to return */
		Calendar calendar = Calendar.getInstance();

		// declare and set the value of day
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		// declare and set the value of month
		int month = calendar.get(Calendar.MONTH);

		// declare and set the value of year
		int year = calendar.get(Calendar.YEAR);

		/* returns a string in this format */
		return tellDate + day + "-" + month + "-" + year;

	}

	/* this function returning the input/ or from a server in the format of 00:00:00 */
	public static String toColonFormat(int h, int m, int s) {
		/* the first element is hours divided by 10  */ 
		firstElement = h / 10;
		/* the second element is modulus 10 so the reminder of the value of the hour variable */
		secondElement = h % 10;
		//this is the index on position 3, it is the division of 10 for the minutes that are two digits long
		thirdElement = m / 10;
		//this is the remainder of the minute input
		fourthElement = m % 10;
		fifthElement = s / 10;
		sixthElement = s % 10;
		//if the first 2 elements are 24 let it equal to 00
		if (firstElement == 2 && secondElement == 4) {
			firstElement = 0;
			secondElement = 0;
		}
		//return string format of the time in colons
		return firstElement + "" + secondElement + ":" + "" + thirdElement + ""
				+ fourthElement + "" + ":" + fifthElement + "" + sixthElement;
	}

	/* this is a function to check the hours */
	public static int checkHours(int hours) {

		/* if first element and second element is 0 then it lets hours = 12 */
		if (firstElement == 0 && secondElement == 0) {
			hours = 12;
		}/*end if*/

		/* if the hours is greater than 12 executes the if statement */
		if (hours > 12) {
			/* Hours is minus by 12  */
			hours = hours - 12;
		}
		return hours;
	}

	/* this function returns the number as a string (soFar variable)*/
	public static String convertHoursMinsSecs(int number) {

		/* this is the string, that the number will be stored in */
		String soFar;

		////////////////////////////////////////////////////////////////////////////////////////
		//took out mod 100
		/* if the number is smaller then 20 then execute the if statement */
		if (number  % 100 < 20) {

			/* SoFar is equal to the number mod 100, is if its between 1-19 */
			soFar = numNames[number % 100];

			//divides the variable by 10
			number /= 100;

		} //end if
		else {
			/* if the number was greater then 20 the else is executed*/
			// soFar string is numNames the number passed through mod 10 so 4 mod 10 = 4
			soFar = numNames[number % 10];
			/* divides the variable by ten */
			number /= 10;
			/*soFar is equal to tensNames index of mod 10 puls soFar (numNames) */
			soFar = tensNames[number % 10] + soFar;
			/* variable divided by 10*/
			number /= 10;

		}/* end else*/
		/* returning string so far	*/
		return soFar;
	}/* */

	public static void NTPServer() throws Exception {

		/* I am using port 13 because it is the standard port on all computers*/

		/*try and catch exception so that if there is no internet connection
		 * then it reads the local server time */
		try {
			//this is the host NTP server Address i am connecting to
			//you could also provide the IP address
			String hosts = "ptbtime1.ptb.de";
			
			//i am connectiog to the NTP client vi port 13
			int daytimeport = 13;
			//the socket connection allows for the communication to happen 
			Socket theSocket = new Socket(hosts, daytimeport);
			
			/*the bufferReader provides buffering to your readers
			 * the purpose of the InputStreamReader is for the reader to adapt an inputStream
			 * and the socket to getting the inputStream
			 */
			BufferedReader br = new BufferedReader(new InputStreamReader(
					theSocket.getInputStream()));
			
			//i am printing off the server time here and the machine + port it is running off
			System.out.println("NTP server is starting on port " + daytimeport
					+ " machine " + hosts);
			
			//timestamp is the bufferedReader reading by line
			String timestamp = br.readLine();

			/*
			 * I used date here because when you try to split the string it will
			 * split it into its appropriate value, so it formats it to
			 * HH:MM:SS, instead of having date values in separated
			 */
			Date date = new SimpleDateFormat("dd MMM yyyy HH:mm:ss")
					.parse(timestamp);
			String newString = new SimpleDateFormat("HH:mm:ss").format(date);

			/*
			 * this separated string is a string array, which is equal to the
			 * the string that is split for example if the sting is "10:11:122"
			 * it splits the string where there is a codon ":"
			 */
			String[] separated = newString.split(":");

			/* i parsed the int hours, minutes and seconds here so that they are = to separated[0] [1] [2]*/
			int hours = Integer.parseInt(separated[0]);
			int mins = Integer.parseInt(separated[1]);
			int secs = Integer.parseInt(separated[2]);

			/*
			 * i am here calling the string function Converting Input which
			 * converts hours/minutes/seconds to string format
			 */
			String newS = ConvertingInput(hours, mins, secs);
			System.out.println(newS);
			// prints out the time stamp with is received from the server
			System.out.println("local time " + timestamp);
			
			/* this closes the socket connection */
			theSocket.close();
		}// end try
		catch (IOException e) {
			/*
			 * if the server fails it runs the get localTime function and the
			 * getLoclaDate function
			 */
			System.out.println(getLocalTime() + "\n" + getLocalDate());
		}//end catch

	}// end function

	/*
	 * This function/ method is error checking, passing through hours, minutes
	 * and seconds
	 */
	public static void checkTime(int h, int m, int s) {

		/*
		 * If the h is above or below 24 its exits out of the program with an
		 * error message same goes for the seconds and minutes that are passed
		 * though
		 */
		if (h > 24 || h < 0 || m > 60 || m < 0 || s > 60 || s < 0) {
			System.out.println("invalid entry");
			System.exit(0);
		}//end if

	}//end checkTime

}//end class TxtClock
