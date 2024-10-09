package edu.bu.metcs622;

import java.util.ArrayList;
import java.util.List;

public class QueryExample {

    public static void main(String[] args) throws Exception {

        // Specify the keywords to search for
        String[] keywords = {"Appearance", "Chatbot", "Personal Assistant"};

        // Define the search limits
        int[] searchLimits = {1, 5, 10, 20, 30, 50, 100};

        // Parse the entire XML dataset (without limiting the number of articles during parsing)
        List<Article> articles = XMLParser.parseXML("/Users/bekzatkalzan/Desktop/BU/METCS622_APT/Homework3/small_part.xml");
        

        // 1. Brute Force Search with article limit
        // Iterate through each keyword and search limit
        System.out.println("Brute Force Search Results:");
        for (String keyword : keywords) {
            System.out.println("Results for keyword: " + keyword);
            for (int searchLimit : searchLimits) {
                System.out.println("Search Limit: " + searchLimit);
                List<Article> bruteForceResults = BruteForceSearch.bruteForceSearch(articles, keyword, searchLimit);
                bruteForceResults.forEach(System.out::println);
                System.out.println();
                
                // 4. Skip List Search
                // List<Article> skipListResults = SkipListSearch.search(articles, keyword, searchLimit);
                // System.out.println("Skip List Search Results:");
                // skipListResults.forEach(System.out::println);
            }
            System.out.println(); // Add a newline for better readability between keywords
        }
        
        System.out.println("Lucene Search Results:");
        for (String keyword : keywords) {
            System.out.println("Results for keyword: " + keyword);
            for (int searchLimit : searchLimits) {
                System.out.println("Search Limit: " + searchLimit);

                // 2. Lucene Search
                 List<Article> luceneResults = LuceneSearch.luceneSearch(articles, keyword, searchLimit);
                 luceneResults.forEach(System.out::println);
                 System.out.println();
            }
            System.out.println(); 
        }
        
        // Create an inverted index and build it from the articles
        InvertedIndex index = new InvertedIndex();
        index.buildIndex(articles);
        
        System.out.println("Inverted Index Search Results:");
        for (String keyword : keywords) {
            System.out.println("Results for keyword: " + keyword);
            for (int searchLimit : searchLimits) {
                System.out.println("Search Limit: " + searchLimit);

                 List<Article> results = index.search(keyword);  // Response time will be printed
                 results.forEach(System.out::println);
                 System.out.println();
                 
                 
            }
            System.out.println(); 
        }
        
        
        
     // Create a skip list and insert articles
        SkipListSearch skipList = new SkipListSearch();
        for (Article article : articles) {
            List<Article> singleArticleList = new ArrayList<>();
            singleArticleList.add(article);
            skipList.insert(article.getTitle(), singleArticleList);  // Index articles by title
        }
        
        System.out.println("Skip List Search Results:");
        for (String keyword : keywords) {
            System.out.println("Results for keyword: " + keyword);
            for (int searchLimit : searchLimits) {
                System.out.println("Search Limit: " + searchLimit);

                 List<Article> results = skipList.search(keyword);  // Response time will be printed
                 results.forEach(System.out::println);
                 System.out.println();
                 
                 
            }
            System.out.println(); 
        }
        

    }
}
