package com.htmlparser.controller;

import com.htmlparser.utils.HtmlParser;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParserController {

    private HtmlParser htmlParser;

    public ParserController(HtmlParser htmlParser) {
        this.htmlParser = htmlParser;
    }

    @GetMapping("")
    public void dataParsing() {

        htmlParser.startParsing();
    }
}
