package com.htmlparser.utils;

import com.htmlparser.entity.MarathonBet;
import lombok.SneakyThrows;
import net.minidev.json.JSONUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HtmlParser {

    @SneakyThrows
    public void startParsing() {

        List<MarathonBet> marathonBetDataName = new ArrayList<>();
        List<Double> marathonBetDataCoefficient1 = new ArrayList<>();

        Document page = Jsoup.connect("https://www.marathonbet.com/su/popular/Tennis+-+2398").timeout(5000).get();

        Elements elementsName = page.select("div.today-member-name.nowrap");
        Elements elementsCoefficient1 = page.select("td.price.height-column-with-price.first-in-main-row.coupone-width-1");

        elementsName.forEach(element -> {
            Element nameElement1 = element.child(0);
            String name = nameElement1.text();
            marathonBetDataName.add(new MarathonBet(name));
        });

        marathonBetDataName.forEach(System.out::println);

        elementsCoefficient1.forEach(element -> {
            Element coefficientElement = element;

            double coefficient = Double.parseDouble(coefficientElement.text());
            marathonBetDataCoefficient1.add(coefficient);
        });



        marathonBetDataCoefficient1.forEach(System.out::println);

    }
}
