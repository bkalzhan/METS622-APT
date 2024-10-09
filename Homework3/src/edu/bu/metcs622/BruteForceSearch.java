package edu.bu.metcs622;

import java.util.ArrayList;
import java.util.List;

public class BruteForceSearch {

    // Brute force search method with article limit
    public static List<Article> bruteForceSearch(List<Article> articles, String keyword, int limit) {
        List<Article> results = new ArrayList<>();
        
        // Start time measurement
        long startTime = System.nanoTime();

        for (int i = 0; i < Math.min(articles.size(), limit); i++) {
            Article article = articles.get(i);
            if (articleContainsKeyword(article, keyword)) {
                results.add(article);
            }
        }
        
        // End time measurement
        long endTime = System.nanoTime();
        long duration = endTime - startTime; // Duration in nanoseconds
        
        System.out.println("Response time: " + (duration));

        return results;
    }

    // Helper function to check if an article contains the keyword
    private static boolean articleContainsKeyword(Article article, String keyword) {
        // Example check: search the title, abstract, or other fields
        if (article.title != null && article.title.contains(keyword)) {
            return true;
        }
        if (article.authors != null) {
            for (String author : article.authors) {
                if (author.contains(keyword)) {
                    return true;
                }
            }
        }
        if (article.editors != null) {
            for (String editor : article.editors) {
                if (editor.contains(keyword)) {
                    return true;
                }
            }
        }
        return false;
    }
}
