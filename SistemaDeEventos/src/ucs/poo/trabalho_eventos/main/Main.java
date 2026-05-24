package ucs.poo.trabalho_eventos.main;

import java.util.Scanner;

import ucs.poo.trabalho_eventos.models.*;
import ucs.poo.trabalho_eventos.main.Utilitarios;

public class Main {
	
	private String nomeEmpresa;
	
	private String getNomeEmpresa() {
		return nomeEmpresa;
	}

	private void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}
	
	private static void mostrarMenuPrincipal(Main main) {
		System.out.println("MENU de " + main.getNomeEmpresa());
		System.out.println("1 - Cadastro de Evento");
		System.out.println("2 - Cadastro de Colaboradores");
		System.out.println("3 - Menu de Eventos");
		System.out.println("4 - Menu de Recursos");
		System.out.println("5 - Relatorios");
		System.out.println("0 - Sair");
	}
	
	
	private static void mostrarMenuEventos(){
		System.out.println("MENU de Eventos");
		System.out.println("1 - Cadastro de Tarefa");
		System.out.println("0 - Sair");
	}
	
	
	private static void login(Main main) {
		Scanner sc = new Scanner(System.in);
		System.out.println("LOGIN");
		System.out.println("Insira o nome de sua Empresa:");
		String nomeEmpresa = sc.nextLine();
		main.setNomeEmpresa(nomeEmpresa);
		System.out.println("Insira seu usuario:");
		String nome = sc.nextLine();
		System.out.println("Insira sua senha:");
		sc.next();
		System.out.println("Seja muito bem vindo " + nome + "!");
		
	}
	


	public static void main(String[] args) {
		Main main = new Main();
		Scanner sc = new Scanner(System.in);
		int intEntrada;
		
		
		login(main);
		Empresa empresa = new Empresa(main.getNomeEmpresa());
		
		System.out.println("Seja bem vindo ao menu principal do Sistema de Controle de Eventos (**o menu visual será inserido posteriormente)");
		
		//LOOP Principal 
		for(;;) {
			mostrarMenuPrincipal(main);
			
			intEntrada = Utilitarios.lerInteiroComVerificacao();
				
			if(intEntrada == 1) {
				System.out.println("Insira o nome do evento:");
				String nome = sc.nextLine();
				
				System.out.println("Insira o tipo do evento:\n1-Festa\n2-Formatura\n3-Evento Corporativo");
				Evento eventoAux;
				int case1Entrada = Utilitarios.lerInteiroComVerificacao();
				
				
				if(case1Entrada == 1) {
					eventoAux = new Festa(nome);
					empresa.cadastrarEvento(eventoAux);
				}
				
				if(case1Entrada == 2) {
					eventoAux = new Formatura(nome);
					empresa.cadastrarEvento(eventoAux);
				}
				
				if(case1Entrada == 3) {
					eventoAux = new EventoCorporativo(nome);
					empresa.cadastrarEvento(eventoAux);
				}
			}
			
			else if(intEntrada == 2) {
				System.out.println("Insira o nome do colaborador:");
				String nome = sc.nextLine();
				
				System.out.println("Insira o email do colaborador:");
				String email = sc.nextLine();
				
				System.out.println("Insira o senha do colaborador:");
				String senha = sc.nextLine();
				
				System.out.println("Insira o funcao do colaborador:");
				String funcao = sc.nextLine();
			}	
			
			else if(intEntrada == 3) {
				empresa.listarEventos();
				mostrarMenuEventos();
				int case2Entrada = Utilitarios.lerInteiroComVerificacao();
				
				if(case2Entrada == 1) {
					System.out.println("a");
				}
				if(case2Entrada == 2) {
					System.out.println("Retornando ao menu principal");

				}
			}
			
			
			else if(intEntrada == 4) {
				System.out.println("Em construção");
			}	
			
			else if(intEntrada == 5) {
				System.out.println("Em construção");
			}
			
			else if(intEntrada == 0) {
				break;
			}
			
			else {
				System.out.println("O numero entrado não é uma das opções do menu!");
			}
				
			

		}
	}
}
