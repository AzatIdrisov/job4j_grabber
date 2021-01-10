package ru.job4j.html;

import ru.job4j.quartz.AlertRabbit;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            cnn = DriverManager.getConnection(
                    cfg.getProperty("jdbc.url"),
                    cfg.getProperty("jdbc.username"),
                    cfg.getProperty("jdbc.password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream in = PsqlStore.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @Override
    public void save(Post post) {
        String sql = "INSERT INTO posts (name, text, link, created) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = cnn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, post.getPostName());
            preparedStatement.setString(2, post.getText());
            preparedStatement.setString(3, post.getLink());
            preparedStatement.setDate(4, new Date(post.getDateCreation().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        String sql = "select * from posts ";
        try (PreparedStatement preparedStatement = cnn.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                posts.add(new Post(resultSet.getString("name"),
                        resultSet.getString("text"),
                        resultSet.getDate("created"),
                        resultSet.getString("link")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findById(String id) {
        Post post;
        String sql = "select * from posts where id = ?";
        try (PreparedStatement preparedStatement = cnn.prepareStatement(sql)) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                post = new Post(resultSet.getString("name"),
                        resultSet.getString("text"),
                        resultSet.getDate("created"),
                        resultSet.getString("link"));
                return post;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static void main(String[] args) throws Exception {
        Properties properties = PsqlStore.getProperties();
        PsqlStore psqlStore = new PsqlStore(properties);
        Post post1 = Post.createPost("https://www.sql.ru/forum/1332197/v-poiskah-full-stack-razrabotchika");
        Post post2 = Post.createPost("https://www.sql.ru/forum/1331624/razrabotchik-ms-sql");
        Post post3 = Post.createPost("https://www.sql.ru/forum/1329807/sql-razrabotchik");
        Post post4 = Post.createPost("https://www.sql.ru/forum/1331407/java-scala-razrabotchik-so-znaniem-hadoop");
        Post post5 = Post.createPost("https://www.sql.ru/forum/1330594/java-razrabotchik-udalenno");
        psqlStore.save(post1);
        psqlStore.save(post2);
        psqlStore.save(post3);
        psqlStore.save(post4);
        psqlStore.save(post5);
        psqlStore.getAll().forEach(System.out::println);
        System.out.println(psqlStore.findById("1"));
        System.out.println(psqlStore.findById("5"));
    }
}