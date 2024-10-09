package edu.bu.metcs622;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SkipListSearch {

    private class Node {
        String keyword;
        List<Article> articles;
        List<Node> forward;

        Node(String keyword, List<Article> articles, int level) {
            this.keyword = keyword;
            this.articles = articles;
            this.forward = new ArrayList<>(level + 1);
            for (int i = 0; i <= level; i++) {
                forward.add(null);
            }
        }
    }

    private static final float PROBABILITY = 0.5f;
    private static final int MAX_LEVEL = 16;
    private Node head;
    private int level;
    private Random random;

    public SkipListSearch() {
        this.head = new Node(null, null, MAX_LEVEL);
        this.level = 0;
        this.random = new Random();
    }

    // Insert a keyword and associated articles into the skip list
    public void insert(String keyword, List<Article> articles) {
        Node[] update = new Node[MAX_LEVEL + 1];
        Node current = head;

        for (int i = level; i >= 0; i--) {
            while (current.forward.get(i) != null && current.forward.get(i).keyword.compareTo(keyword) < 0) {
                current = current.forward.get(i);
            }
            update[i] = current;
        }

        current = current.forward.get(0);

        if (current == null || !current.keyword.equals(keyword)) {
            int newLevel = randomLevel();
            if (newLevel > level) {
                for (int i = level + 1; i <= newLevel; i++) {
                    update[i] = head;
                }
                level = newLevel;
            }

            Node newNode = new Node(keyword, articles, newLevel);
            for (int i = 0; i <= newLevel; i++) {
                newNode.forward.set(i, update[i].forward.get(i));
                update[i].forward.set(i, newNode);
            }
        }
    }

    // Search for a keyword in the skip list and return the associated articles
    public List<Article> search(String keyword) {
        // Start time measurement
        long startTime = System.nanoTime();

        Node current = head;
        for (int i = level; i >= 0; i--) {
            while (current.forward.get(i) != null && current.forward.get(i).keyword.compareTo(keyword) < 0) {
                current = current.forward.get(i);
            }
        }

        current = current.forward.get(0);

        // End time measurement
        long endTime = System.nanoTime();
        long duration = endTime - startTime; // Duration in nanoseconds

        // Print the response time
        System.out.println("Response time for skip list search: " + duration + " ns");

        if (current != null && current.keyword.equals(keyword)) {
            return current.articles;
        }

        return new ArrayList<>();
    }

    // Generate a random level for a new node
    private int randomLevel() {
        int newLevel = 0;
        while (newLevel < MAX_LEVEL && random.nextFloat() < PROBABILITY) {
            newLevel++;
        }
        return newLevel;
    }

    // Print the skip list (for debugging purposes)
    public void printSkipList() {
        for (int i = level; i >= 0; i--) {
            Node current = head.forward.get(i);
            System.out.print("Level " + i + ": ");
            while (current != null) {
                System.out.print(current.keyword + " -> ");
                current = current.forward.get(i);
            }
            System.out.println("null");
        }
    }
}
