package com.pineframework.core.helper;

import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.PersianCalendar;
import com.ibm.icu.util.ULocale;
import oracle.sql.TIMESTAMP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * DateUtils class helps client to access date in local format
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class DateUtils {

    private DateUtils() {
    }

    public static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    public static final ULocale PERSIAN_LOCALE = new ULocale("fa_IR@calendar=persian");

    public static final ULocale US_LOCALE = new ULocale("en_US@calender=gregorian");

    public static final SimpleDateFormat DATE_TIME_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", PERSIAN_LOCALE);

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", PERSIAN_LOCALE);

    public static final SimpleDateFormat DATE_FORMAT_MONTH_OF_YEAR =
            new SimpleDateFormat("yyyy-MMM-dd", PERSIAN_LOCALE);

    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss", PERSIAN_LOCALE);

    public static final ZoneId TEHRAN = ZoneId.of("Asia/Tehran");

    public static final ZoneId GMT = ZoneId.of("Europe/London");

    public static String convertToJalaliDateTimeString(Date date) {
        return DATE_TIME_FORMAT.format(date);
    }

    public static String convertToJalaliDateString(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static String[] getJalaliPartsWithNameOfMonth(LocalDate date) {
        String s = convertToJalaliDateWithNameOfMonthString(date);
        return s.split("-");
    }

    public static String convertToJalaliTimeString(Date date) {
        return TIME_FORMAT.format(date);
    }

    public static String convertToJalaliString(LocalDateTime dateTime) {
        return convertToJalaliDateTimeString(convertToJalaliDate(dateTime));
    }

    public static String convertToJalaliString(LocalDate date) {
        return convertToJalaliDateString(convertToJalaliDate(date));
    }

    public static String convertToJalaliString(LocalTime time) {
        return convertToJalaliTimeString(convertToJalaliDate(time));
    }

    public static String convertToJalaliDateWithNameOfMonthString(LocalDate date) {
        return convertToJalaliDateWithNameOfMonthString(convertToJalaliDate(date));
    }

    public static String convertToJalaliDateWithNameOfMonthString(Date date) {
        return DATE_FORMAT_MONTH_OF_YEAR.format(date);
    }

    public static Date getCurrentJalaliDate() {
        Calendar calendar = Calendar.getInstance(PERSIAN_LOCALE);
        return calendar.getTime();
    }

    public static LocalDateTime convertToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), TEHRAN);
    }

    public static LocalDateTime convertToLocalDateTime(String date, String pattern) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    public static Date convertToJalaliDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(TEHRAN).toInstant());
    }

    public static Date convertToJalaliDate(LocalDate date) {
        return Date.from(date.atStartOfDay().atZone(TEHRAN).toInstant());
    }

    public static Date convertToJalaliDate(LocalTime time) {
        return Date.from(time.atDate(LocalDate.now()).atZone(TEHRAN).toInstant());
    }

    public static String[] getJalaliParts(LocalDate date) {
        String s = convertToJalaliString(date);
        return s.split("-");
    }

    public static String getJalaliYear(LocalDate date) {
        return getJalaliParts(date)[0];
    }

    public static String getJalaliMonth(LocalDate date) {
        return getJalaliParts(date)[1];
    }

    public static String getJalaliNameOfMonth(LocalDate date) {
        return getJalaliPartsWithNameOfMonth(date)[1];
    }

    public static String getJalaliDay(LocalDate date) {
        return getJalaliParts(date)[2];
    }

    // get current date without time
    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    // get current time without date
    public static LocalTime getCurrentLocalTime() {
        return LocalTime.now();
    }

    // get current date and time together without timezone information
    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    // get current date and time together along with timezone information
    public static ZonedDateTime getCurrentDateAndTimeInZone() {
        return ZonedDateTime.now();
    }

    public static Boolean isAfter(LocalDateTime first, LocalDateTime second) {
        return first.isAfter(second);
    }

    public static Boolean isAfter(LocalDate first, LocalDate second) {
        return first.isAfter(second);
    }

    public static Boolean isBefore(LocalDateTime first, LocalDateTime second) {
        return first.isBefore(second);
    }

    public static Boolean isBefore(LocalDate first, LocalDate second) {
        return first.isBefore(second);
    }

    public static Boolean isEqual(LocalDateTime first, LocalDateTime second) {
        return first.isEqual(second);
    }

    public static Boolean isEqual(LocalDate first, LocalDate second) {
        return first.isEqual(second);
    }

    public static Boolean isAfterAndEqual(LocalDate first, LocalDate second) {
        return isEqual(first, second) && isAfter(first, second);
    }

    public static Boolean isAfterAndEqual(LocalDateTime first, LocalDateTime second) {
        return isEqual(first, second) && isAfter(first, second);
    }

    public static Boolean isBeforeAndEqual(LocalDate first, LocalDate second) {
        return isEqual(first, second) && isBefore(first, second);
    }

    public static Boolean isBeforeAndEqual(LocalDateTime first, LocalDateTime second) {
        return isEqual(first, second) && isBefore(first, second);
    }

    public static LocalDate convert(LocalDate date, String pattern) {
        return LocalDate.parse(date.toString(), DateTimeFormatter.ofPattern(pattern));
    }

    public static String convertToString(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String convertToString(LocalDateTime date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate convertToLocalDate(String date, String pattern) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate convertToLocalDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), TEHRAN).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(TIMESTAMP value) {

        try {
            return value.timestampValue().toLocalDateTime();

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return LocalDateTime.now();
    }

    public static LocalDate toLocalDate(TIMESTAMP value) {
        try {
            return value.dateValue().toLocalDate();

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return LocalDate.now();
    }

    public static LocalTime convertToLocalTime(String time, String pattern) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern(pattern));
    }

    public static String convertToGregorian(String date, String format) {
        LocalDate jalaliDate = convertToLocalDate(date, format);
        Calendar calendar = Calendar.getInstance(PERSIAN_LOCALE);
        calendar.set(jalaliDate.getYear(), jalaliDate.getMonthValue() - 1, jalaliDate.getDayOfMonth());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", US_LOCALE);
        return (dateFormat.format(calendar.getTime()));
    }

    public static LocalDate convertToGregorian(LocalDate jalaliDate) {
        return convertToGregorian(jalaliDate, "yyyy-MM-dd", "yyyy-MM-dd");
    }

    public static LocalDate convertToGregorian(LocalDate jalaliDate, String inputFormat, String outputFormat) {
        Calendar calendar = Calendar.getInstance(PERSIAN_LOCALE);
        calendar.set(jalaliDate.getYear(), jalaliDate.getMonthValue() - 1, jalaliDate.getDayOfMonth());
        SimpleDateFormat dateFormat = new SimpleDateFormat(inputFormat, US_LOCALE);
        String gregorianDate = dateFormat.format(calendar.getTime());
        return convertToLocalDate(gregorianDate, outputFormat);
    }

    public static LocalDate convertToGregorian(LocalDate jalaliDate, String format) {
        return convertToGregorian(jalaliDate, format, format);
    }

    public static LocalDateTime convertToGregorian(LocalDateTime jalaliDateTime) {
        Calendar calendar = Calendar.getInstance(PERSIAN_LOCALE);
        calendar.set(jalaliDateTime.getYear(), jalaliDateTime.getMonthValue() - 1, jalaliDateTime.getDayOfMonth());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", US_LOCALE);
        String gregorianDate = dateFormat.format(calendar.getTime());
        return convertToLocalDateTime(gregorianDate, "yyyy-MM-dd HH:mm:ss");
    }

    public static LocalDate convertToJalaliLocalDate(LocalDate date, String inputFormat, String outputFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(inputFormat, PERSIAN_LOCALE);
        dateFormat.setNumberFormat(NumberFormat.getNumberInstance());
        String format = dateFormat.format(convertToJalaliDate(date));
        return convertToLocalDate(format, outputFormat);
    }

    public static LocalDate convertToJalaliLocalDate(LocalDate date) {
        return convertToJalaliLocalDate(date, "yyyy-MM-dd", "yyyy-MM-dd");
    }

    public static LocalDate convertToJalaliLocalDate(LocalDate date, String format) {
        return convertToJalaliLocalDate(date, format, format);
    }

    public static LocalDateTime convertToJalaliLocalDateTime(LocalDateTime date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", PERSIAN_LOCALE);
        dateFormat.setNumberFormat(NumberFormat.getNumberInstance());
        String format = dateFormat.format(convertToJalaliDate(date));
        return convertToLocalDateTime(format, "yyyy-MM-dd HH:mm:ss");
    }

    public static LocalDate addToDate(LocalDate date, int years, int months, int days) {
        Calendar calendar = Calendar.getInstance(PERSIAN_LOCALE);
        calendar.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        calendar.add(Calendar.YEAR, years);
        calendar.add(Calendar.MONTH, months);
        calendar.add(Calendar.DATE, days);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", PERSIAN_LOCALE);
        dateFormat.setNumberFormat(NumberFormat.getNumberInstance());
        String dateStr = dateFormat.format(calendar.getTime());
        return convertToLocalDate(dateStr, "yyyy-MM-dd");
    }

    public static LocalDate addToDate(LocalDate date, int days) {
        return addToDate(date, 0, 0, days);
    }

    public static LocalDate addToDate(LocalDate date, int months, int days) {
        return addToDate(date, 0, months, days);
    }

    public static boolean isLeap(int year) {
        PersianCalendar calendar = (PersianCalendar) Calendar.getInstance(PERSIAN_LOCALE);
        calendar.set(1, year);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR) == 366;
    }

}
