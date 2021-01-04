package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {

    public static void main(String[] args) throws Exception {
        String url = "https://www.sql.ru/forum/job-offers/";
        for (int i = 0; i < 5; i++) {
            SqlRuParse.parse(url + i);
        }
    }

    public static void parse(String url) throws Exception {
        Document doc = Jsoup.connect(url).get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Element href = td.child(0);
            System.out.println(href.attr("href"));
            Element date = td.parent().child(5);
            System.out.println(href.text() + " " + FormateDate.formate(date.text()));
        }
    }
}
