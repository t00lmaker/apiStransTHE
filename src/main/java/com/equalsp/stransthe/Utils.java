package com.equalsp.stransthe;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Alguns métodos utilitários. 
 * 
 * @author toolmaker
 *
 */
class Utils {

	static boolean expired(Date date, int seconds) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.SECOND, seconds);
		Date time = new Date();
		return time.compareTo(c.getTime()) >= 0;
	}

}
