package CSAppointmentSchedulerFaces;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FormatterFactory {
	
	public static String format(String str) {
		if(str == null) {
			return "";
		} else {
			return str;
		}
	}
	
	public static String dateFormat(String date) throws ParseException {
		if(date == null) {
			return "";
		}
		
		DateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
		fromFormat.setLenient(false);
		DateFormat toFormat = new SimpleDateFormat("MMMM dd, yyyy");
		toFormat.setLenient(false);

		return (toFormat.format(fromFormat.parse(date)));
	}
	
	public static String timeFormat(String time) throws ParseException {
		if(time == null) {
			return "";
		}
		//return time;
		
		DateFormat fromFormat = new SimpleDateFormat("k:m:s");
		DateFormat toFormatH = new SimpleDateFormat("h");
		DateFormat toFormatM = new SimpleDateFormat("m");
		DateFormat toFormatA = new SimpleDateFormat("a");
		
		String h = toFormatH.format(fromFormat.parse(time));
		String m = toFormatM.format(fromFormat.parse(time));
		int mInt = 0;
		try {
	        mInt = Integer.parseInt(m);
	        if(mInt < 10) {
	        	m = "0" + m;
	        }
	    } catch(Exception e){
	    	
	    }
		String a = toFormatA.format(fromFormat.parse(time));

		return  (h + ":" + m + " " + a);
	}

}
