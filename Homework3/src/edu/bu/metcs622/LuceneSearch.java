package edu.bu.metcs622;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class LuceneSearch {
    private static StandardAnalyzer analyzer = new StandardAnalyzer();
    static FSDirectory index;

    // Method to index all the articles
    public static void indexArticles(List<Article> articles) throws Exception {
    	index = FSDirectory.open(Paths.get("/Users/bekzatkalzan/eclipse-workspace/METCS622classcodes")); 

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(index, config);

        // Iterate over articles and index each one
        for (Article article : articles) {
            addDoc(writer, article);
        }
        writer.close();
    }
    
    private static void addDoc(IndexWriter writer, Article article) throws IOException {
        Document doc = new Document();

        // Add title, year, and URL with null checks
        doc.add(new TextField("title", article.getTitle() != null ? article.getTitle() : "", Field.Store.YES));  
        doc.add(new TextField("year", article.getYear() != null ? article.getYear() : "", Field.Store.YES));  
        doc.add(new TextField("url", article.getURL() != null ? article.getURL() : "", Field.Store.YES));  

        // Handle authors: join the list of authors into a single string or set to an empty string
        if (article.getAuthors() != null && !article.getAuthors().isEmpty()) {
            String authors = String.join(", ", article.getAuthors());
            doc.add(new TextField("authors", authors, Field.Store.YES));
        } else {
            doc.add(new TextField("authors", "", Field.Store.YES));
        }

        // Handle editors: join the list of editors into a single string or set to an empty string
        if (article.getEditors() != null && !article.getEditors().isEmpty()) {
            String editors = String.join(", ", article.getEditors());
            doc.add(new TextField("editors", editors, Field.Store.YES));
        } else {
            doc.add(new TextField("editors", "", Field.Store.YES));
        }

        // Add the document to the index writer
        writer.addDocument(doc);
    }


    // Lucene search method with article limit and response time measurement
    public static List<Article> luceneSearch(List<Article> articles, String keyword, int limit) throws Exception {
    	indexArticles(articles);

        // Start time measurement
        long startTime = System.nanoTime();

        // Build a search query
        Query query = new QueryParser("title", analyzer).parse(keyword);

        // Search the index
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs results = searcher.search(query, limit);

        // Process search results
        List<Article> foundArticles = new ArrayList<>();

        for (ScoreDoc scoreDoc : results.scoreDocs) {
            try {
                // Retrieve the document from the index
                Document doc = searcher.doc(scoreDoc.doc);

                // Extract fields from the document
                String title = doc.get("title");
                String year = doc.get("year");
                String url = doc.get("url");

                // Extract 'authors' and 'editors' as comma-separated strings and split them into lists
                String authorsStr = doc.get("author");
                String editorsStr = doc.get("editor");

                // Convert them back to List<String>
                List<String> authors = authorsStr != null ? Arrays.asList(authorsStr.split(",\\s*")) : null;
                List<String> editors = editorsStr != null ? Arrays.asList(editorsStr.split(",\\s*")) : null;

                // Create an Article object and set its fields
                Article article = new Article();
                article.title = title;
                article.year = year;
                article.url = url;
                article.authors = authors;
                article.editors = editors;

                // Add the article to the results list
                foundArticles.add(article);
            } catch (Exception e) {
                e.printStackTrace();  // Handle exceptions, such as missing fields
            }
        }

        // End time measurement
        long endTime = System.nanoTime();
        long duration = endTime - startTime; // Duration in nanoseconds
        System.out.println("Lucene Search Response time: " + (duration / 1_000_000) + " ms");

        reader.close(); // Close the reader
        return foundArticles;
    }
}
