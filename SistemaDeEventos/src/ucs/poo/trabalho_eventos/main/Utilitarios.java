package ucs.poo.trabalho_eventos.main;

import java.util.Scanner;

public abstract class Utilitarios {
		
	//Criado essa função auxiliar, pois o sc.nextInt deixa o caractere de "Enter" (quebra de linha) no buffer.
	//Alem disso ele ja faz a verificação de se a entrada é realmente um numero
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
	
}
