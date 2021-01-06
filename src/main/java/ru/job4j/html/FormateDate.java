package ru.job4j.html;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormateDate {

    private static final String PATTERN = "dd MMM yy, HH:mm";

    public static Date formate(String date) throws ParseException {
        Date today = new Date();
        Calendar day = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy,");
        String[] str = date.split(" ");
        if (str[0].contains("сегодня")) {
            str[0] = dateFormat.format(today);
        } else if (str[0].contains("вчера")) {
            day.add(Calendar.DAY_OF_YEAR, -1);
            str[0] = dateFormat.format(day.getTime());
        } else if (str[1].equals("сен")) {
            str[1] = "сент.";
        } else if (str[1].equals("ноя")) {
            str[1] = "нояб.";
        } else if (str[1].equals("май")) {
            str[1] = "мая";
        } else {
            str[1] = str[1] + ".";
        }
        return new SimpleDateFormat(PATTERN).parse(String.join(" ", str));
    }
}
