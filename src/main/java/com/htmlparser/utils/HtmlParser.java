package com.htmlparser.utils;

import com.htmlparser.entity.MarathonBet;
import lombok.SneakyThrows;
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

        Document page = Jsoup.connect("https://www.marathonbet.com/su/popular/Tennis+-+2398").maxBodySize(0).get();


        Elements elementsName = page.select("div.today-member-name.nowrap");
        Elements elementsTomorrowName = page.select("div.category-content");
        Elements elementsCoefficient = page.select("td.price.height-column-with-price.coupone-width-1");

//        elementsTomorrowName.forEach(System.out::println);
        System.out.println(page);

        elementsName.forEach(element -> {
            Element nameElement1 = element.child(0);
            String name = nameElement1.text();
            marathonBetDataName.add(new MarathonBet(name));
        });

        elementsCoefficient.forEach(element -> {

            Element coefficientElement = element.child(0);
            if(!coefficientElement.text().isEmpty()){
                double coefficient = Double.parseDouble(coefficientElement.text());
                marathonBetDataCoefficient1.add(coefficient);
            }
        });

//       for(int i = 0; i < marathonBetDataName.size(); i++){
//           if(i % 2 == 0)
//               System.out.println("\n");
//           System.out.println(i + "  " + marathonBetDataName.get(i) + "\t------\t" + marathonBetDataCoefficient1.get(i));
//       }
    }
}
