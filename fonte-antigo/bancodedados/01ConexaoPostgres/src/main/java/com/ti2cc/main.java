package com.ti2cc;
//Ane

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DAO dao = new DAO();
        Scanner sc = new Scanner(System.in);

        if (!dao.conectar()) {
            System.out.println("Falha na conexão com o banco!");
            return;
        }

        int opcao = 0;

        do {
            System.out.println("\n===== MENU CRUD USUÁRIO =====");
            System.out.println("1 - Inserir usuário");
            System.out.println("2 - Listar todos os usuários");
            System.out.println("3 - Atualizar usuário");
            System.out.println("4 - Excluir usuário");
            System.out.println("5 - Listar usuários masculinos");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    System.out.print("Código: ");
                    int codigo = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Login: ");
                    String login = sc.nextLine();
                    System.out.print("Senha: ");
                    String senha = sc.nextLine();
                    System.out.print("Sexo (M/F): ");
                    char sexo = sc.nextLine().toUpperCase().charAt(0);

                    Usuario u = new Usuario(codigo, login, senha, sexo);
                    if (dao.inserirUsuario(u)) {
                        System.out.println("Usuário inserido com sucesso!");
                    }
                    break;

                case 2:
                    System.out.println("\nLista de todos os usuários:");
                    Usuario[] todos = dao.getUsuarios();
                    for (Usuario user : todos) {
                        System.out.println(user.getCodigo() + " - " + user.getLogin() + " - " + user.getSenha() + " - " + user.getSexo());
                    }
                    break;

                case 3:
                    System.out.print("Informe o código do usuário a atualizar: ");
                    int codAtualizar = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Novo login: ");
                    String novoLogin = sc.nextLine();
                    System.out.print("Nova senha: ");
                    String novaSenha = sc.nextLine();
                    System.out.print("Novo sexo (M/F): ");
                    char novoSexo = sc.nextLine().toUpperCase().charAt(0);

                    Usuario uAtualizar = new Usuario(codAtualizar, novoLogin, novaSenha, novoSexo);
                    if (dao.atualizarUsuario(uAtualizar)) {
                        System.out.println("Usuário atualizado com sucesso!");
                    }
                    break;

                case 4:
                    System.out.print("Informe o código do usuário a excluir: ");
                    int codExcluir = sc.nextInt();
                    if (dao.excluirUsuario(codExcluir)) {
                        System.out.println("Usuário excluído com sucesso!");
                    }
                    break;

                case 5:
                    System.out.println("\nLista de usuários masculinos:");
                    Usuario[] masculinos = dao.getUsuariosMasculinos();
                    for (Usuario user : masculinos) {
                        System.out.println(user.getCodigo() + " - " + user.getLogin() + " - " + user.getSenha() + " - " + user.getSexo());
                    }
                    break;

                case 6:
                    System.out.println("Saindo...");
                    dao.close();
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 6);

        sc.close();
    }
}
