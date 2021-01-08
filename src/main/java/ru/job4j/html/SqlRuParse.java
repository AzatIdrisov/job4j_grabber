package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    public static void main(String[] args) throws Exception {
        String url = "https://www.sql.ru/forum/job-offers/";
        for (int i = 1; i <= 5; i++) {
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

    @Override
    public List<Post> list(String link) throws Exception {
        List<Post> posts = new ArrayList<>();
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Element href = td.child(0);
            posts.add(Post.createPost(href.attr("href")));
        }
        return posts;
    }

    @Override
    public Post detail(String link) throws Exception {
        return Post.createPost(link);
    }
}
