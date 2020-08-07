package com.htmlparser.utils;

import com.htmlparser.entity.MarathonBet;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HtmlParser {

    @SneakyThrows
    public void startParsing() {

        List<MarathonBet> marathonBetDataName = new ArrayList<>();
        List<Double> marathonBetDataCoefficient = new ArrayList<>();


        Document page = Jsoup.connect("https://www.marathonbet.com/su/popular/Tennis+-+2398").timeout(5000).get();

//        Elements elementsName = page.getElementsByAttribute("data-event-name");
        Elements elementsName = page.select("td.today-name");
        Elements elementsCoefficient = page.select("td.price.height-column-with-price.first-in-main-row.coupone-width-1");

        elementsName.forEach(element -> {
            Element nameElement1 = element.child(0);
            String name = nameElement1.text();
            marathonBetDataName.add(new MarathonBet(name));
        });

//        for (int i = 0; i < elementsName.size(); i++){
//            Element nameElement1 = elementsName.get(i);
//            String name = nameElement1.text();
//            marathonBetDataName.add(new MarathonBet(name, elementsCoefficient.get(i).text()));
//
//        }
//
        marathonBetDataName.forEach(System.out::println);

        elementsCoefficient.forEach(element -> {
            Element coefficientElement = element;

            double coefficient = Double.parseDouble(coefficientElement.text());
            marathonBetDataCoefficient.add(coefficient);
        });
//
//        for (int i = 0; i < marathonBetDataName.size(); i++){
//            fullDataFromBet.put(marathonBetDataName.get(i), marathonBetDataCoefficient.get(i));
//        }
////
        marathonBetDataCoefficient.forEach(System.out::println);
//        fullDataFromBet.forEach((MarathonBet, String) -> System.out.println(MarathonBet + "    " + String));
//        System.out.println(elementsCoefficient.text());

//        return page;
    }
}
