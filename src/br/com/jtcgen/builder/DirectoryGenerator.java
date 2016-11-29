package br.com.jtcgen.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.swing.JOptionPane;

public class DirectoryGenerator {

	private String basePath = "tests";
	private String fileSufix = "Test.java";
	private boolean useAnotherSourcePath = true;
	private Class<?> classe;
	protected static boolean overwriteAllTests = false;

	public DirectoryGenerator(Class<?> classe) {
		this.classe = classe;
	}
	
	public DirectoryGenerator(Class<?> classe, boolean useSourcePath) {

		this.classe = classe;
		this.useAnotherSourcePath = useSourcePath;
	}
	
	public DirectoryGenerator(Class<?> classe, String basePath) {
		this.classe = classe;
		this.basePath = basePath;
	}
	
	public DirectoryGenerator(Class<?> classe, String basePath, boolean useSourcePath) {
		this.classe = classe;
		this.basePath = basePath;
		this.useAnotherSourcePath = useSourcePath;
	}
	
	public DirectoryGenerator(Class<?> classe, String basePath, String fileSufix) {
		this.classe = classe;
		this.basePath = basePath;
		this.fileSufix = fileSufix;
	}
	
	public DirectoryGenerator(Class<?> classe, String basePath, String fileSufix, boolean useSourcePath) {
		this.classe = classe;
		this.basePath = basePath;
		this.fileSufix = fileSufix;
		this.useAnotherSourcePath = useSourcePath;
	}

	public void createTest(String content, boolean makeABackupFile) {
		String path = createDirectories();
		String file = path + classe.getSimpleName() + fileSufix;
		
		try {
			createFileTestCase(file, content, makeABackupFile);
		} catch (FileNotFoundException e) {
			System.out.println("Não foi possivel criar o arquivo da classe diretório: " + classe.getName());
		}
	}

	private String createDirectories() {
		String[] strings = this.classe.getPackage().getName().split("\\.");

		StringBuffer path = new StringBuffer(basePath + "/");
		for (String string : strings)
			path.append(string + "/");

		new File(path.toString()).mkdirs();

		return path.toString();
	}

	private void createFileTestCase(String file, String content, boolean makeABackupFile) throws FileNotFoundException {
		File fileInstance = new File(file);
		
		int showConfirmDialog = 1;
		if(fileInstance.exists() && overwriteAllTests == false && makeABackupFile){
			String[] options = {"Sim, e sobrescreva os proximos.", "Sim", "Não"};
			showConfirmDialog = JOptionPane.showOptionDialog(null, 
					"Deseja sobrescrever o arquivo: " + fileInstance.getPath() + "?", 
					"Sobrescrever Teste", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, "Não");
			
			if(showConfirmDialog == 0) {
				overwriteAllTests = true;
				showConfirmDialog = 1;
			}
		}
		
		if(showConfirmDialog == 1) {
			doABackup(makeABackupFile, fileInstance);
			
			PrintStream input = new PrintStream(fileInstance);
			
			input.print(content);
	
			input.close();
		}
	}
	
	private void doABackup(boolean makeABackupFile, File fileInstance) {
		if(!makeABackupFile)
			return ;
		
		if(!fileInstance.exists())
			return ;
		
		LocalDateTime agora = LocalDateTime.now();
		DateTimeFormatter formatador = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
		agora.format(formatador); //08/04/14 10:02
		String dateString = agora.toString().substring(0, 19).replaceAll("\\s|T", "_").replaceAll(":", "-");
		
		String newFileName = fileInstance.getName().replaceAll("\\.java", dateString +".java.bak");
		
		Path source = Paths.get(fileInstance.getPath());
	    Path target = Paths.get(fileInstance.getPath().replaceAll("[a-zA-Z0-9]+\\.java$", "") + "/" + newFileName );
	    try {
	        Files.copy(source, target);
	    } catch (IOException e1) {
	        e1.printStackTrace();
	    }
		
	}

}
