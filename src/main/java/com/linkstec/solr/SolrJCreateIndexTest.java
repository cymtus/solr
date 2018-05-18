package com.linkstec.solr;

import com.linkstec.pojo.Item;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

public class SolrJCreateIndexTest {
    @Test
    public void testCreateIndex() throws SolrServerException, IOException {

        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/core2");
        SolrInputDocument document = new SolrInputDocument();

        document.addField("id", "111111");
        document.addField("title", "奥迪双钻，我的伙伴");
        document.addField("price", 20);

        solrServer.add(document);

        solrServer.commit();
    }

    @Test
    public void testInsertIndex() throws SolrServerException, IOException {

        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/core2");

        Item item = new Item();
        item.setId("111");
        item.setTitle("添加javabean");
        item.setPrice(200L);

        solrServer.addBean(item);
        solrServer.commit();
    }
}
