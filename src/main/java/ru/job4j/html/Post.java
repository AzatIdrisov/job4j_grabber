package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Date;

public class Post {
    private String postName;
    private String link;
    private String author;
    private Date dateCreation;
    private int id;
    private String text;

    public Post(String postName, String text, Date date, String link) {
        this.postName = postName;
        this.text = text;
        this.dateCreation = date;
        this.link = link;
    }

    public static Post createPost(String url) throws Exception {
        Document doc = Jsoup.connect(url).get();
        Elements table = doc.select(".messageHeader");
        Element name = table.get(0);
        Elements messages = doc.select(".msgBody");
        Element messagePost = messages.get(1);
        Elements dates = doc.select(".msgFooter");
        Element date = dates.get(1);
        Date createdDate = FormateDate.formate(date.text().split("\\[")[0]);
        System.out.println(name.text());
        System.out.println(messagePost.text());
        System.out.println(createdDate);
        return new Post(name.text(), messagePost.text(), createdDate, url);
    }

    public static void main(String[] args) throws Exception {
        createPost("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t");
    }
}
