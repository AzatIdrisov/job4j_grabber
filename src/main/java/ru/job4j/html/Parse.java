package ru.job4j.html;

import java.io.IOException;
import java.util.List;

public interface Parse {

    List<Post> list(String link) throws Exception;

    Post detail(String link) throws Exception;
}
