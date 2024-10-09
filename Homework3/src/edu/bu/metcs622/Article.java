package edu.bu.metcs622;

import java.util.List;

public class Article {
    public String title;
    public String year;
    public String url;
    public List<String> authors;
    public List<String> editors;

	// ToString for better readability
    @Override
    public String toString() {
        return "Title: " + title + ", Year: " + year + ", URL: " + url + ", Authors: " + authors + ", Editors: " + editors;
    }

	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return this.title;
	}

	public List<String> getAuthors() {
		// TODO Auto-generated method stub
		return this.authors;
	}

	public  List<String> getEditors() {
		// TODO Auto-generated method stub
		return this.editors;
	}

	public String getYear() {
		// TODO Auto-generated method stub
		return this.year;
	}

	public String getURL() {
		// TODO Auto-generated method stub
		return this.url;
	}
}

