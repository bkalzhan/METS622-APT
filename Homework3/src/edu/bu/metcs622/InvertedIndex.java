package edu.bu.metcs622;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvertedIndex {

    // Inverted index map: keyword -> list of articles
    private Map<String, List<Article>> invertedIndex;

    public InvertedIndex() {
        this.invertedIndex = new HashMap<>();
    }

    // Method to build the inverted index from a list of articles
    public void buildIndex(List<Article> articles) {
        for (Article article : articles) {
            indexArticle(article);
        }
    }

    // Method to index an article
    private void indexArticle(Article article) {
        indexField(article.getTitle(), article);
        indexField(article.getYear(), article);
        indexField(article.getURL(), article);
        if (article.getAuthors() != null) {
            for (String author : article.getAuthors()) {
                indexField(author, article);
            }
        }
        if (article.getEditors() != null) {
            for (String editor : article.getEditors()) {
                indexField(editor, article);
            }
        }
    }

    // Helper method to index individual fields
    private void indexField(String field, Article article) {
        if (field != null) {
            String[] words = field.split("\\s+");  // Split the field into words by whitespace
            for (String word : words) {
                word = word.toLowerCase();  // Convert to lowercase for case-insensitive search
                invertedIndex.computeIfAbsent(word, k -> new ArrayList<>()).add(article);
            }
        }
    }

    // Method to search for a keyword in the inverted index and measure response time
    public List<Article> search(String keyword) {
        // Start time measurement
        long startTime = System.nanoTime();

        // Perform the search
        keyword = keyword.toLowerCase();
        List<Article> result = invertedIndex.getOrDefault(keyword, new ArrayList<>());

        // End time measurement
        long endTime = System.nanoTime();
        long duration = endTime - startTime;  // Duration in nanoseconds
        
        // Print the response time
        System.out.println("Response time for inverted index search: " + duration + " ns");

        return result;
    }
}
