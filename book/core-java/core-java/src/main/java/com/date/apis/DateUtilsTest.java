package com.date.apis;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Slf4j
public class DateUtilsTest {

	/**
	 * Method to convert one date format to another date format
	 * @throws ParseException 
	 */
	@Test
	public void convertDateFormat1() throws ParseException {
		/*convert date from yyyy-mm-dd to mm/dd/yyyy*/
		DateFormat fromFormat1 = new SimpleDateFormat("yyyy-mm-dd");
		DateFormat toFormat1 = new SimpleDateFormat("mm/dd/yyyy");

		Date date = fromFormat1.parse("2017-01-27");
		String convertedDate1 = toFormat1.format(date);
		log.info("convertedDate1: {}", convertedDate1);

		/*convert date from yyyy-mm-dd to dd/mm/yyyy*/
		DateFormat fromFormat2 = new SimpleDateFormat("yyyy-mm-dd");
		DateFormat toFormat2 = new SimpleDateFormat("dd/mm/yyyy");
		Date date2 = fromFormat2.parse("2017-01-27");
		String convertedDate2 = toFormat2.format(date2);
		log.info("convertedDate2: {}", convertedDate2);

		/*convert date from dd/mm/yyyy to yyyy-mm-dd*/
		DateFormat fromFormat3 = new SimpleDateFormat("dd/mm/yyyy");
		DateFormat toFormat3 = new SimpleDateFormat("yyyy-mm-dd");
		Date date3 = fromFormat3.parse("27/01/2017");
		String convertedDate3 = toFormat3.format(date3);
		log.info("convertedDate3: {}", convertedDate3);

		/* dd/MMM/yyyy == convert date format. Display short form of month like Jan, May
		 * hh:mm:ss ==  time in 12 hours format. 
		 * HH:mm:ss == time in 24 hours format
		 * a == display AM/PM*/
		DateFormat dateFormat4 = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss a");
		Date date4 = new Date();
		String convertedDate4 = dateFormat4.format(date4);
		log.info("convertedDate4: {}", convertedDate4);
	}

	/**
	 * Print day of week if date is given
	 */
	@Test
	public void getDayOfWeek() {
		int year = 2015;
		int month = 8;
		int day = 5;

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		Date date = calendar.getTime();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
		log.info(simpleDateFormat.format(date).toUpperCase());
	}

	@Test
	public void localDateToGregorianCalendar() {
		LocalDate localDate = LocalDate.now();
		GregorianCalendar gregorianCalendar = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		log.info("date={}", gregorianCalendar.toString());
	}

	@SneakyThrows
	@Test
	public void localDateToXmlGregorianCalendar() {
		LocalDate localDate = LocalDate.now();
		GregorianCalendar gregorianCalendar = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(gregorianCalendar);
		log.info("date={}", xmlGregorianCalendar.toString());
	}

	@Test
	public void utilDateToXmlGregorianCalendar() {
		Date date = new Date();
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		XMLGregorianCalendar xmlGregorianCalendar = null;
		try {
			xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		} catch (Exception e) {
			log.error("Exception while creating XMLGregorianCalendar", e);
		}

		log.info("XMLGregorianCalendar={}", xmlGregorianCalendar);
	}

	@Test
	public void xmlGregorianCalendarToUtilDate() {
		// create XMLGregorianCalendar
		Date date = new Date();
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		XMLGregorianCalendar xmlGregorianCalendar = null;
		try {
			xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		} catch (Exception e) {
			log.error("Exception while creating XMLGregorianCalendar", e);
		}

		// convert to util date
		Date resultUtilDate = xmlGregorianCalendar.toGregorianCalendar().getTime();

		log.info("result util-date={}", resultUtilDate);
	}

	@Test
	public void xmlGregorianCalendarToGregorianCalendar() {
		// create XMLGregorianCalendar
		Date date = new Date();
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		XMLGregorianCalendar xmlGregorianCalendar = null;
		try {
			xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		} catch (Exception e) {
			log.error("Exception while creating XMLGregorianCalendar", e);
		}

		GregorianCalendar resultGregorianCalendar = xmlGregorianCalendar.toGregorianCalendar();
		log.info("xmlGregorianCalendar={}", xmlGregorianCalendar);
		log.info("resultGregorianCalendar={}", resultGregorianCalendar);
	}

	@Test
	public void timeInMillisToGregorian() {
		Date date = new Date();
		long timeInMillis = date.getTime();
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTimeInMillis(timeInMillis);

		log.info("timeInMillis={}", timeInMillis);
		log.info("gregorianCalendar={}", gregorianCalendar);
	}

}