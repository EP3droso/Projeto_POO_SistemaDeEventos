package ucs.poo.trabalho_eventos.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;



public class Sistema {
	
	private static final String nomeDoArquivo = "database.json";
	private ObjectMapper mapper;
	
	
	public Sistema() {
		this.mapper = new ObjectMapper();
	}
	
	public boolean ArquivoExiste() {
		try {
			Path caminho = Paths.get(this.nomeDoArquivo);
			System.out.println(caminho);
			if (!Files.exists(caminho)) {
				Files.write(caminho, new byte[0]); 
			}
			return true;
		}
		catch(IOException e) {
			return false;
		}
		
	}
	
	
	private String lerArquivo() throws IOException{
		StringBuilder sr = new StringBuilder();
		FileInputStream fr = new FileInputStream(this.nomeDoArquivo);
		InputStreamReader is = new InputStreamReader(fr);
		BufferedReader br = new BufferedReader(is);
    	String linha;
    	
    	while((linha = br.readLine()) != null) {
    		sr.append(linha);
    		sr.append("\n");
    	}
    	fr.close();
    	return sr.toString();
	}
	
	private void gravaArquivo(Empresa empresa) throws IOException{
		FileWriter fw = new FileWriter(this.nomeDoArquivo);
		
		//Transforma empresa em JSON
		 String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(empresa);
		 
		 //Sobrescreve o arquivo com o novo valor de empresa
		 fw.write(json);
		 fw.close();
	}
	
	
	
	public Empresa desserializarEmpresa(){
		String json = null;
    	Empresa empresa = null;
    	try {
			json = this.lerArquivo();
			empresa = this.mapper.readValue(json, Empresa.class);
			
		} catch (IOException e) {
			System.out.println("Erro na leitura da empresa");
			System.out.println(e);
		}
    	

    	return empresa;	
	}

	
	public void serializarEmpresa(Empresa empresa){
		try {
			this.gravaArquivo(empresa);
		}
		catch(IOException e) {
			System.out.println("Erro na gravação da empresa");
		}
	}
}
