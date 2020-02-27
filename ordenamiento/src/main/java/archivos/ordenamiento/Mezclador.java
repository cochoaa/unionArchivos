package archivos.ordenamiento;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Scanner;

public class Mezclador {
	public void procesar(List<Scanner> archivos,PrintStream result) {
		try {
			File resultadoTemporal =crearArchivo("temp.txt");
			for(Scanner archivo_1: archivos){
				/********************************************/
				resultadoTemporal=mezclar(archivo_1,resultadoTemporal);
				/********************************************/
				archivo_1.close();
			}
			result.write(Files.readAllBytes(resultadoTemporal.toPath()));
			resultadoTemporal.delete();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			result.close();
		}
	}
	
	private File crearArchivo(String pathname) throws IOException {
		File resultadoTemporal=new File(pathname);
		Files.createFile(resultadoTemporal.toPath());
		return resultadoTemporal;
	}
	
	/**
	 * Mezcla ordenadamente en resultadoTemporal
	 * @param archivo_1
	 * @param resultadoTemporal
	 * @return
	 */
	private File mezclar(Scanner archivo_1,File resultadoTemporal) {
		
		try {
			Scanner archivo_2=new Scanner(resultadoTemporal);
			String valor_1=getSiguienteValor(archivo_1);
			String valor_2=getSiguienteValor(archivo_2);
			File mezclado =crearArchivo("temp2.txt");
			PrintStream psMezclado = new PrintStream(mezclado);
			do {
				try {
					if(compararMenorValor(valor_1,valor_2)) {
						psMezclado.println(valor_1);
						valor_1=getSiguienteValor(archivo_1);
					}else {
						psMezclado.println(valor_2);
						valor_2=getSiguienteValor(archivo_2);
					}
				} catch (Exception e) {
					
					e.printStackTrace();
				}			
			}while (!(valor_1.isEmpty() && valor_2.isEmpty()));//Mientras haya lineas para leer
			psMezclado.close();
			archivo_2.close();
			Files.copy(mezclado.toPath(), resultadoTemporal.toPath(),StandardCopyOption.REPLACE_EXISTING);
			mezclado.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultadoTemporal;
		
	}
	
	/**
	 * Verdadero si valor_1 es menor, Falso en caso contrario
	 * @param valor_1
	 * @param valor_2
	 * @return
	 * @throws Exception
	 */
	private boolean compararMenorValor(String valor_1,String valor_2) throws Exception {
		if(!valor_1.isEmpty() && !valor_2.isEmpty()) {
			return Integer.parseInt(valor_1)<Integer.parseInt(valor_2);
		}else if(!valor_1.isEmpty() && valor_2.isEmpty()) {
			return true;
		}else if(valor_1.isEmpty() && !valor_2.isEmpty()) {
			return false;
		}else {//Este bloque equivale a  valor_1 y valor_2 estan vacio, 
			//es decir los archivos ya no tienen valores para leer 
			 throw  new Exception();
		} 
	}
	
	/**
	 * Retorna el valor de la siguiente linea, Si no hay returna  "" 
	 * @param archivo
	 * @return
	 */
	private String getSiguienteValor(Scanner archivo) {
		if(archivo.hasNext())
			return archivo.nextLine();
		else
			return "";

	}
}
