package com.linkstec.solr;

import com.linkstec.pojo.Item;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class TestQuery {

    @Test
    public void testQueryDocument() throws SolrServerException {
        HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/core2");

        //创建查询条件对象
        SolrQuery solrQuery = new SolrQuery("title:手机");
        //执行查询并得到响应
        QueryResponse response = solrServer.query(solrQuery);
        //从响应结果中取得结果数据
        SolrDocumentList results = response.getResults();

        for (SolrDocument d : results
                ) {
            System.out.println("id: " + d.getFieldValue("id"));
            System.out.println("title: " + d.getFieldValue("title"));
            System.out.println("price: " + d.getFieldValue("price"));
        }
    }

    @Test
    public void testQueryBeans() throws SolrServerException {
        HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/core2");

        //创建查询对象
        SolrQuery solrQuery = new SolrQuery("title:手机");
        //根据查询对象得到响应
        QueryResponse response = solrServer.query(solrQuery);
        //根据响应得到数据
        List<Item> beans = response.getBeans(Item.class);

        System.out.println(beans.size());
    }

    @Test
    public void testQueryOr() throws SolrServerException {
        HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/core2");

        //创建查询对象
        SolrQuery solrQuery = new SolrQuery("title:小米 OR title:华为");
        //根据查询对象得到响应
        QueryResponse response = solrServer.query(solrQuery);

        //根据响应得到数据
        List<Item> beans = response.getBeans(Item.class);

        for (Item item : beans
                ) {
            System.out.println(item);
        }

    }

    @Test
    public void testQueryRange() throws SolrServerException {
        HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/core2");

        //创建查询对象
        SolrQuery solrQuery = new SolrQuery("price:[* TO 4000]");
        //根据查询对象得到响应
        QueryResponse response = solrServer.query(solrQuery);

        //根据响应得到数据
        List<Item> beans = response.getBeans(Item.class);

        for (Item item : beans
                ) {
            System.out.println(item);
        }

    }

    @Test
    public void testQueryLike() throws SolrServerException {
        HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/core2");

        //创建查询对象
        SolrQuery solrQuery = new SolrQuery("title:vica~2");
        //根据查询对象得到响应
        QueryResponse response = solrServer.query(solrQuery);

        //根据响应得到数据
        List<Item> beans = response.getBeans(Item.class);

        for (Item item : beans
                ) {
            System.out.println(item);
        }

    }

    @Test
    public void testQuerySort() throws SolrServerException {
        HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/core2");

        //创建查询对象
        SolrQuery solrQuery = new SolrQuery("title:手机");
        solrQuery.setSort("price", SolrQuery.ORDER.asc);
        //根据查询对象得到响应
        QueryResponse response = solrServer.query(solrQuery);

        //根据响应得到数据
        List<Item> beans = response.getBeans(Item.class);

        for (Item item : beans
                ) {
            System.out.println(item);
        }

    }


    @Test
    public void testQueryPage() throws SolrServerException {
        HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/core2");

        int currentPage = 2;// 当前页
        final int PAGE_SIZE = 5;// 每页显示条数
        int start = (currentPage - 1) * PAGE_SIZE;// 当前页的起始条数

        //创建查询对象
        SolrQuery solrQuery = new SolrQuery("title:手机");
        solrQuery.setStart(start);
        solrQuery.setRows(PAGE_SIZE);
        //根据查询对象得到响应
        QueryResponse response = solrServer.query(solrQuery);

        //根据响应得到数据
        List<Item> beans = response.getBeans(Item.class);
        System.out.println("当前第" + currentPage + "页，本页共" + beans.size() + "条数据。");
        for (Item item : beans
                ) {
            System.out.println(item);
        }

    }

    @Test
    public void testQueryHighLight() throws SolrServerException {
        HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/core2");

        //创建查询对象
        SolrQuery solrQuery = new SolrQuery("title:手机");
        solrQuery.setHighlightSimplePre("<em>");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.addHighlightField("title");
        //根据查询对象得到响应
        QueryResponse response = solrServer.query(solrQuery);

        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        
        List<Item> beans = response.getBeans(Item.class);
        System.out.println(highlighting.keySet());
        System.out.println(highlighting.get("1"));

        for (Item item : beans) {
            String id = item.getId();
            System.out.println("id: " + id);
            System.out.println("title: " + highlighting.get(id).get("title").get(0));
            System.out.println("price: " + item.getPrice());
        }

    }
}
