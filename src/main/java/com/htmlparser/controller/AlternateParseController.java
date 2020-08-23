package com.htmlparser.controller;

import com.htmlparser.entity.MarathonBetCoupon;
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

@Slf4j
@RestController
public class AlternateParseController {

    @GetMapping("/alternate/parse")
    public List<MarathonBetCoupon> parse() throws IOException {
        Document doc = Jsoup.connect("https://www.marathonbet.com/betting/Tennis+-+2398").get();
        Date parseDate = new Date();
        Elements x = doc.select("div.category-container");
        log.info(x.text());
        return doc.select("div.category-container").stream()
                .flatMap(category -> parseCategory(category).stream())
                .peek(coupon -> coupon.setParseDate(parseDate))
                .collect(toList());
    }

    private List<MarathonBetCoupon> parseCategory(Element category) {
        String categoryName = category.select("table.category-header td.category-label-td a.category-label-link").text();
        return category.select("div.coupon-row").stream()
                .map(coupon -> parseCoupon(categoryName, coupon))
                .collect(toList());
    }

    private MarathonBetCoupon parseCoupon(String categoryName, Element coupon) {
        Elements players = coupon.select("table.member-area-content-table tr");
        String player1 = players.get(0).select("td div div").text();
        String player2 = players.get(1).select("td div div").text();
        String date = players.get(0).select("td.date").text();

        Elements prices = coupon.select("td.height-column-with-price");
        String price1 = prices.get(0).text();
        String price2 = prices.get(1).text();

//        log.info("{} ({}) --- {} ({}) | {}", player1, price1, player2, price2, date);
        return MarathonBetCoupon.builder()
                .category(categoryName)
                .date(date)
                .player1(player1)
                .player2(player2)
                .price1(price1)
                .price2(price2)
                .build();
    }
}
