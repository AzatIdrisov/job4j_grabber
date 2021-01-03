package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SqlRuParse {

    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Element href = td.child(0);
            System.out.println(href.attr("href"));
            Element date = td.parent().child(5);
            System.out.println(href.text() + " " + SqlRuParse.formateDate(date.text()));
        }
    }

    public static String formateDate(String date) throws ParseException {
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
        } else {
            str[1] = str[1] + ".";
        }
        Date newDate = new SimpleDateFormat("dd MMM yy, HH:mm").parse(String.join(" ", str));
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(newDate);
    }
}