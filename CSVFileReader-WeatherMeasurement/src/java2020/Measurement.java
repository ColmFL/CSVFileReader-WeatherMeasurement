package java2020;

import java.time.LocalDateTime; //Import for use of LocalDateTime.

public class Measurement {

	//Below are all of the variables needed
	//declared as public to avoid getters.
	public LocalDateTime dateTime; 
	public double temp;
	public double rain;
	public double webt;
	public double dewpt;
	public double vappr;
	public double rhum;
	public double msl;
	public double wdsp;
	public double wddir;
	public double sun;
	
	//Constructor which takes in all of the above mentioned variables.
	public  Measurement(LocalDateTime dateTime, double rain, double temp, double webt, 
			double dewpt, double vappr, double rhum, double msl, double wdsp, double wddir, double sun) 
	{
		this.dateTime = dateTime;
		this.temp = temp;
		this.rain = rain;
		this.webt = webt;
		this.dewpt = dewpt;
		this.vappr = vappr;
		this.rhum = rhum;
		this.msl = msl;
		this.wdsp = wdsp;
		this.wddir = wddir;
		this.sun = sun;
	}
	//This one getter proved necessary.
	public double getTemp() {
		return temp;
	}
}
