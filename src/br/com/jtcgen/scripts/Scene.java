package br.com.jtcgen.scripts;

public class Scene {
	
	private String var;
	private String content;
	
	public Scene(String var, String content) {
		super();
		this.var = var;
		this.content = content;
	}
	
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
