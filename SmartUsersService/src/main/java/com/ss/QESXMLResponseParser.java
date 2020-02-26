package com.ss;

import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.springframework.stereotype.Component;

@Component
public class QESXMLResponseParser extends XMLResponseParser {
    public QESXMLResponseParser() {
        super();
    }

    @Override
    public String getContentType() {
        return "text/xml; charset=UTF-8";
    }
}