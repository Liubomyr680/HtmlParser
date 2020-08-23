package com.htmlparser.controller;

import com.htmlparser.entity.XBetCoupon;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;


//div.bcontent

@Slf4j
@RestController
public class XBetParser {

    @GetMapping("/parse")
    public List<XBetCoupon> parse() throws IOException {
        Document doc = Jsoup.connect("http://plusminus.by/bet.php?events=3").get();
        Date parseDate = new Date();
//        Elements x = doc.getElementsByAttributeValue("onclick","ccolor(event,this);");
        log.info(doc.html());
        return doc.select("div.dashboard-champ-content").stream()
                .flatMap(category -> parseCategory(category).stream())
                .peek(coupon -> coupon.setParseDate(parseDate))
                .collect(toList());
    }

    private List<XBetCoupon> parseCategory(Element category) {
//        log.info(category.text());
        String categoryName = category.select("div.c-events__name a.c-events__liga").text();
//        log.info(categoryName);
        return category.select("div.c-events__item_game").stream()
                .map(coupon -> parseCoupon(categoryName, coupon))
                .collect(toList());
    }

    private XBetCoupon parseCoupon(String categoryName, Element coupon) {
        Elements players = coupon.select("div.event--name.long--name");
//        log.info(players.text());
        String player1 = players.get(0).select("span").text();
        String player2 = players.get(0).select("span").text();
        String date = players.get(0).select("div.event--time").text();

        Elements prices = coupon.select("ul.outcome_list li.outcome");
        String price1 = prices.get(0).text();
        String price2 = prices.get(1).text();

        log.info("{} ({}) --- {} ({}) | {}", player1, price1, player2, price2, date);
        return XBetCoupon.builder()
                .category(categoryName)
                .date(date)
                .player1(player1)
                .player2(player2)
                .price1(price1)
                .price2(price2)
                .build();
    }
}
