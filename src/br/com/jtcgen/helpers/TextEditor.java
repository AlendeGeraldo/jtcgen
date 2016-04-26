package br.com.jtcgen.helpers;

public class TextEditor {

	public static final String TAB = "\t";
	public static final String LINE_BREAK = "\n";

	public static String newLine(String content, int numberOfTabs) {
		StringBuffer line = new StringBuffer(LINE_BREAK);
		for (int i = 0; i < numberOfTabs; i++)
			line.append(TAB);

		line.append(content);

		return line.toString();
	}
}
