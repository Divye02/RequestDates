import java.util.Arrays;

/*
February 17th 2015
Divye Pratap Jain

The particular program, gives all the required files from a given start date (inclusive) to end date (inclusive) with the minimum nu
 */
public class RequestDates {
	public static final int LENGTH_DATE = 8; // "yyyymmdd"
	public static final String START_DATE = "20160201";
	public static final String END_DATE =   "20160228";
	
	/*
	 * Dates must be in a particular format that is "yyyymmdd"
	 * Start date and end date must be valid
	 * Start date must be less than the end date
	 * 
	 * Since, there is no knowledge about the database from where these files are being requested, the program 
	 * prints all the requests that will be made to the db on the console
	 */
	public static void main(String[] args){
		if(args.length < 2){
			throw new IllegalArgumentException("Enter atleast 2 dates");
		}
		if(Integer.parseInt(args[0]) > Integer.parseInt(args[1])){
			throw new IllegalArgumentException("start date must be less than end date");
		}
		String startDate = args[0]; // "yyyymmdd"
		String endDate   = args[1];  // "yyyymmdd"
		if(Integer.parseInt(startDate) > Integer.parseInt(endDate)){
			throw new IllegalArgumentException("Start date must be less than the end date");
		}
		printRequests(startDate, endDate, "");
	}
	
	/*
	 * Recursive method for printing the requests that will be made to the db
	 * 
	 */
	private static void printRequests(String startDate, String endDate, String request) {
		if(startDate.length() == 0 || endDate.length() == 0){ // base case (if reached the end, print the request string)
			System.out.println("GET(\"" + request + "*"+ "\")");
		}else if(firstDate(request).equals(startDate) && lastDate(request).equals(endDate)){ 
			// another base case (if the start date is the first date ever possible and the end date is the last one ever possible then just request everything from that moment
			System.out.println("GET(\"" + request + "*"+ "\")");
		}else{//recursive step
			if(startDate.charAt(0) != endDate.charAt(0)){
				int start = Integer.parseInt(startDate.charAt(0)+"");
				int end = Integer.parseInt(endDate.charAt(0)+"");
				for(int i = start + 1; i < end; i++){
					System.out.println("GET(\"" + request + i + "*" + "\")");
				}
				//for the start date change last date to the last date from the given moment in the start date string
				printRequests(startDate.substring(1), lastDate(request + startDate.charAt(0)), request + startDate.charAt(0));
				//for the end date change first date to the first date from the given moment in the end date string
				printRequests(firstDate(request + endDate.charAt(0)), endDate.substring(1), request + endDate.charAt(0));
			}else{
				printRequests(startDate.substring(1), endDate.substring(1), request + startDate.charAt(0));
			}
				
		}
	}
	
	/*
	 * Calculates the first date possible from the given end date 
	 */
	private static String firstDate(String endDate) {
		String date = "";
		int length = endDate.length();
		for(int i = length; i < LENGTH_DATE; i++){
			int min =  minPossible(endDate);
			date = date + min;
			endDate = endDate + min;
			
		}
		return date;
	}
	
	/*
	 * Calculates the last Date possible from the given start date
	 */
	private static String lastDate(String startDate) {
		String date = "";
		int length = startDate.length();
		for(int i = length; i < LENGTH_DATE; i++){
			int max = maxPossible(startDate);
			date = date + max;
			startDate = startDate + max;
		}
		return date;
	}
	
	/*
	 * Gives the minimum value of the next part of the date from the given moment (which is recorded in date)
	 */
	private static int minPossible(String date){
		return (date.length() < 3 ?  0 : //if the next part of the date is a part of the first 3 ys in "yyyy" min value possible is 0
				(date.length() == 3 ? (Integer.parseInt(date.substring(0,3)) == 0? 1: 0) : //if the next part of the date is a part of last y in "yyyy" min value possible is 1 if first 3 ys are 0 else 0
					(date.length() == 4 ? 0: //if the next part of the date is a part of the first m in "mm" then min value is 0
						(date.length() == 5 ? (Integer.parseInt(date.substring(4,5))== 0 ? 1: 0) : // if the next part of the date is a part of the second m in "mm" then min value is 1 if first m is 0 else it is 0
							(date.length() == 6 ? 0 : (Integer.parseInt(date.substring(6,7)) == 0 ? 1: 0)) ) ) ) );//if the next part of the date is a part of the first d in "dd" min value is 0 and 1 for the second d if first d was 0 else 0
	}
	
	/*
	 * Gives the maximum value of the next part of the date from the given moment (which is recorded in date)
	 */
	private static int maxPossible(String date){
		boolean leap = false;
		if(date.length() > 4){
			int leapYear = Integer.parseInt(date.substring(0, 4));
			leap = leapYear % 4 == 0;
		}
		return (date.length() < 4 ? 9 : //if the next part of the date is a part of "yyyy" max value possible is 9
					(date.length() == 4 ? 1: //if the next part of the date is a part of the first m in "mm" then max value is 1
						(date.length() == 5 ? // if the next part of the date is a part of the second m in "mm" then max value is 9 if the first m was 0 or 1 and 2 if first m was 1
								(Integer.parseInt(date.charAt(date.length() - 1) + "") == 1 ? 2 : 9 ): //---^ 
									(date.length() == 6 ? //if the next part of the date is a part of the first d in "dd"
											(Integer.parseInt(date.substring(4,6)) == 02 ? 2 : 3) : //^---- max value is 2 if "mm" was 02 (February) else it is 3 
												(Integer.parseInt(date.charAt(date.length() - 1) + "") < 2 ? 9 : //if next part of the date is the second d of "dd", max value is 9 if first m is less than 2
													(Integer.parseInt(date.charAt(date.length() - 1) + "") != 2 ? (month30or31(Integer.parseInt(date.substring(4,6))) ? 1 : 0) : //^---- if first d is 3 then max value is 0 or 1 depending on which month 
														((Integer.parseInt(date.substring(4,6)) != 02) || (leap && (Integer.parseInt(date.substring(4,6)) == 02))? 9 : 8)) )) ))); //^---- max value is 8 if first d was 2, "mm" was 02 and if it was not a leap year else max value is 9  

	}
	
	/*
	 * Tell us if it is the month with 30 or 31 days
	 * true if 31 false if 30
	 */
	private static boolean month30or31(int month) {
		if((month < 8 && month % 2 == 1) || (month >= 8 && month % 2 == 0))
			return true;
		return false;
	}
	
}
