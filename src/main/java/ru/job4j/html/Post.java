package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Date;

public class Post {
    private String postName;
    private String link;
    private Date dateCreation;
    private int id;
    private String text;

    public Post(String postName, String text, Date date, String link) {
        this.postName = postName;
        this.text = text;
        this.dateCreation = date;
        this.link = link;
    }

    public String getPostName() {
        return postName;
    }

    public String getLink() {
        return link;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public static Post createPost(String url) throws Exception {
        Document doc = Jsoup.connect(url).get();
        Elements table = doc.select(".messageHeader");
        Element name = table.get(0);
        Elements messages = doc.select(".msgBody");
        Element messagePost = messages.get(1);
        Elements info = doc.select(".msgFooter");
        Element date = info.get(0);
        Date createdDate = FormateDate.formate(date.text().split("\\[")[0]);
        return new Post(name.text(), messagePost.text(), createdDate, url);
    }

    public static void main(String[] args) throws Exception {
        createPost("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t");
    }

    @Override
    public String toString() {
        return "Post{"
                + "postName='" + postName + '\''
                + ", link='" + link + '\''
                + ", dateCreation=" + dateCreation
                + ", text='" + text + '\''
                + '}';
    }
}
