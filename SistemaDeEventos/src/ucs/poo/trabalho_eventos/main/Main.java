package ucs.poo.trabalho_eventos.main;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String stringEntrada;
		int intEntrada;
		
		System.out.println("Seja bem vindo ao menu principal do Sistema de Controle de Eventos, o menu visual será inserido posteriormente");
		
		while(!"0".equals(stringEntrada = sc.nextLine())) {
			try {
				intEntrada = Integer.parseInt(stringEntrada);
				switch(intEntrada) {
				case 1:
					System.out.println("Em andamento");
				}
			}
			catch(NumberFormatException e){
				System.out.println("Entrada não corresponde a nenhuma das opções do menu");
			}
		}
	}
	
	private void mostrarMenu() {
		System.out.println("MENU");
		System.out.println("1 - Cadastro de Evento");
		System.out.println("2 - Menu de Eventos");
		System.out.println("3 - Menu de Recursos");
		System.out.println("4 - Relatorios");
	}
}
