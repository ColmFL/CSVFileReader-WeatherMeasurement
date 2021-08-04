package java2020;

import java.io.IOException; 
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//Above imports allow for the use of much of the functionality
//needed to complete the application.

public class Main {

	public static void main(String[] args) {
			
		//The entirety of the main method is wrapped
		//in a try and catch. Ends on line 92.
		try {
			//provides date formatting.
			DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
			builder.parseCaseInsensitive();
			builder.appendPattern("dd-MMM-yyyy HH:mm");
			DateTimeFormatter dateFormat = builder.toFormatter();

			//The below line will read in the .csv file as a String object.
			Stream <String> stream = Files.lines(Paths.get("C:\\Users\\clavi\\OneDrive\\Documents\\Course Folder\\hly3723.csv"));  
			List<Measurement> measure = stream 
					.skip(24) //Skips all lines of the file up to and including line 24.
					.map( casement -> {
						String[] cas = casement.split(",");
						//This if statement ensures that any empty cell is converted to a '0.0' value.
						//This ensures that missing data will not crash the application.
						if(cas[6].contains(" "))
						{
							cas[6] = "0.0";
						}
						//Creates an instance of the Measurement class for each line of the .csv file.
						//Also parses the values as the Measurement constructor expects values of type 
						//LocalDateTime, double, double, double, double, etc.
						Measurement measure1 = new Measurement (LocalDateTime.parse(cas[0],dateFormat),
								Double.parseDouble(cas[2]), 
								Double.parseDouble(cas[4]),
								Double.parseDouble(cas[6]), 
								Double.parseDouble(cas[7]), 
								Double.parseDouble(cas[8]), 
								Double.parseDouble(cas[9]), 
								Double.parseDouble(cas[10]), 
								Double.parseDouble(cas[12]), 
								Double.parseDouble(cas[14]), 
								Double.parseDouble(cas[17]));
						return measure1;
					})
					//The '.collect' function packages all of the operations just finished on the measure List.
					//Henceforth, the measure List doesn't consider the lines before 25 and also only considers the necessary columns.
					.collect(Collectors.toList());
			
		//The below stream will get the max of 'temp' using the Comparator function.	
		double max = measure
			      .stream()
			    .max(Comparator.comparing(Measurement::getTemp)).get().getTemp();
		System.out.println("Maximum temperature all data ponits: " + max);
		
		//The below stream will calculate the average sunshine hours across the file. 
		OptionalDouble averageSun = measure
				.parallelStream()
				.mapToDouble(p -> p.sun) //Returns a new stream with only the sun column. 
				.average();	//Gets the average of the sun column.
		System.out.println("Average sunshine hours all datapoints: " + averageSun.getAsDouble());
		
		double sumRain = measure
				.stream() 
			    .mapToDouble(i -> i.rain) //Returns a new stream with only the rain column. 
			    .sum(); //Sums everything in the rain column.
		System.out.println("Total rainfall all data points: " + sumRain);
		
		List<Measurement> sumSunNov2001 = measure
				.stream() 
				//Filters to ensure only 'November' and '2001' entries are returned.
			    .filter((i -> i.dateTime.getMonth().name().contains("NOVEMBER") && i.dateTime.getYear() == 2001))
			    .collect(Collectors.toList()); //Same as '.collect' above.
		double val = sumSunNov2001 //New stream with double as the value type.
				.stream()
				.mapToDouble(i -> i.sun) //Maps for Sun column.
				.sum(); //Sums the sun column only for November 2001 entries.
		System.out.println("Total sunshine hours in November 2001: " + val);
		
		double greaterRain = measure
				.stream() 
			    .mapToDouble(i -> i.rain) //Returns a new stream with only the rain column.
			    .filter((i -> i > 5)) //Ensures only values over '5' are returned.
				.count(); //Count how many instances over '5' are encountered.
		System.out.println("The number of hours where rainfall was greater than 5mm: " + greaterRain);
		
		List<Measurement> pressureTimes = measure
				.stream()
				.filter((i -> i.msl> 1047.2)) //Ensures only instances above '1047.2'.
				.collect(Collectors.toList()); //Same as '.collect' above.
		System.out.println("The list of times when Mean Sea Level pressure exceeded 1047.2 hPa: ");
		//For each time pressureTimes, print a toSting which formats the dates.
		pressureTimes.forEach(i -> System.out.print(i.dateTime.toString()+ " "));
		
			stream.close();	//Important to close the stream.	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
