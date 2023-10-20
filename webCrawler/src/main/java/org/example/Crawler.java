package org.example;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;

public class Crawler {
    HashSet<String> urlSet;
    int MAX_DEPTH = 1;
    Crawler(){
        urlSet = new HashSet<>();
    }

    public void getPageTextAndLinks(String url, int depth) throws IOException, SQLException, ClassNotFoundException {
        if(urlSet.contains(url)){
            return;
        }
        if(depth>=MAX_DEPTH){
            return;
        }

        if(urlSet.add(url)){
            System.out.println(url);
        }

        depth++;
        // jsoup converts html to java object
        Document document = Jsoup.connect(url).timeout(5000).get();
        Indexer indexer = new Indexer(document,url);

        System.out.println(document.title());
        Elements availableLinksOnPage = document.select("a[href]");

        //DFS call
        for(Element currentLink : availableLinksOnPage){
            getPageTextAndLinks(currentLink.attr("abs:href"), depth);
        }

    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        Crawler c = new Crawler();
        c.getPageTextAndLinks("https://www.javatpoint.com/",0);
    }
}