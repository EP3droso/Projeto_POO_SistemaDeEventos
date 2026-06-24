package ucs.poo.trabalho_eventos.main;

import java.util.Scanner;

public abstract class Utilitarios {
		
	public static int lerInteiroComVerificacao() {
		Scanner sc = new Scanner(System.in);
		String stringEntrada = sc.nextLine();
		try {
			int intEntrada = Integer.parseInt(stringEntrada);
			return intEntrada;
		}
		catch(NumberFormatException e){
			if("".equals(stringEntrada)) {
				System.out.println("Retornando ao menu principal");
			}
			else {
				System.out.println("Entrada não é um numero");
			}
			return -1;
		}
	}

	public static String lerStringNaoVazia(String mensagem) {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println(mensagem);
			String entrada = sc.nextLine();
			if(!entrada.isBlank()) {
				return entrada.trim();
			}
			System.out.println("Entrada inválida: o campo não pode ser vazio. Tente novamente.");
		}
	}

	//PADRÃO DE VALIDAÇÃO (no modelo): garante que um campo obrigatório não
	//seja nulo nem vazio. Lança IllegalArgumentException caso contrário.
	//Usado pelos construtores/setters das entidades como 2ª camada de proteção.
	public static String exigirNaoVazio(String valor, String nomeCampo) {
		if(valor == null || valor.isBlank()) {
			throw new IllegalArgumentException(nomeCampo + " não pode ser vazio.");
		}
		return valor.trim();
	}

}
