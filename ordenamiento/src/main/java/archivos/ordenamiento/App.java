package archivos.ordenamiento;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		try {
			List<String> paths=listFilesForFolder(new File("archivos"));
			
			Mezclador mezclador= new Mezclador();
			PrintStream result = new PrintStream(new File("resultado/Aresult.txt"));
			List<Scanner> archivos=new ArrayList<Scanner>(); 
			for(String path: paths)
				archivos.add(new Scanner(new File(path)));
			mezclador.procesar(archivos, result);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static List<String> listFilesForFolder(final File folder) {
		List<String> pathFiles= new ArrayList<String>(0);
	    for (final File fileEntry : folder.listFiles()) 
	    	pathFiles.add(fileEntry.getPath());  
		return pathFiles;
	}
}
