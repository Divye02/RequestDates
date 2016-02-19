# RequestDates
The particular program, gives all the required files from a given start date (inclusive) to end date (inclusive) with the minimum number of requests to the 
database. 
Since, I have no knowledge about the database from where these files are being requested, the program prints all the requests that will be made to the db on the console

(Running it through the terminal)<br /> 
First compile the program:<br /> 
**javac RequestDates.java**

Then run it:<br /> 
**java RequestDates startDate endDate**<br /> 
e.g:<br /> 
**java RequestDates 20140101 20151231**
<br /> 
<br /> 
To run it from an IDE like Eclipse: <br />
Step 1) : Comment out these lines (which is in the main method)<br /> 
if(args.length < 2){<br /> 
<tab to=t1>			throw new IllegalArgumentException("Enter atleast 2 dates");<br /> 
	}<br /> 
if(Integer.parseInt(args[0]) > Integer.parseInt(args[1])){<br /> 
<tab to=t1>			throw new IllegalArgumentException("start date must be less than end date");<br /> 
	}<br /> 

