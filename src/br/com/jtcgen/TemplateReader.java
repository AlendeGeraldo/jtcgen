package br.com.jtcgen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class TemplateReader {

	private String ver;
	private boolean testStatic = false;
	private StringBuffer template;

	public TemplateReader(String ver) {
		this.ver = ver;
	}

	public TemplateReader() {
		this.ver = "4";
	}

	public void loadTemplate() throws FileNotFoundException {
		BufferedReader fr = new BufferedReader(new FileReader(new File(path())));

		// while(fr.)
		// fr.this.template.append();
	}

	private String path() {
		String path = "";
		switch (this.ver) {
		case "4":
			if (testStatic)
				path = "template/TestV4TemplateStatic.java";
			else
				path = "template/TestV4Template.java";
			break;

		case "3":
			path = "template/TestV3Template.java";
			break;

		default:
			break;
		}

		return path;
	}

}
