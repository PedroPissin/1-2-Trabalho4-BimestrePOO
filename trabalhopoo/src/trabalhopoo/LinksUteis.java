package trabalhopoo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.InputStreamReader;

class Link {
    String assunto;
    String url;

    Link(String assunto, String url) {
        this.assunto = assunto;
        this.url = url;
    }
}

public class LinksUteis {
    public static void main(String[] args) {
        ArrayList<Link> listaLinks = new ArrayList<>();
        carregarArquivo(listaLinks);

        int opcao;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Adicionar link útil");
            System.out.println("2. Editar link útil");
            System.out.println("3. Excluir link útil");
            System.out.println("4. Listar links por assunto");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(reader.readLine());

                switch (opcao) {
                    case 1:
                        System.out.println("Insira o assunto do link: ");
                        String assunto = reader.readLine();

                        System.out.println("Insira a URL do link: ");
                        String url = reader.readLine();

                        listaLinks.add(new Link(assunto, url));
                        break;

                    case 2:
                        System.out.println("Insira o número do link a editar (1-" + listaLinks.size() + "):");
                        int linkEditar = Integer.parseInt(reader.readLine());

                        if (linkEditar > 0 && linkEditar <= listaLinks.size()) {
                            System.out.println("Insira o novo assunto do link: ");
                            String novoAssunto = reader.readLine();

                            System.out.println("Insira a nova URL do link: ");
                            String novaUrl = reader.readLine();

                            listaLinks.set(linkEditar - 1, new Link(novoAssunto, novaUrl));
                        } else {
                            System.out.println("Número de link inválido!");
                        }
                        break;

                    case 3:
                        System.out.println("Insira o número do link a excluir (1-" + listaLinks.size() + "):");
                        int linkExcluir = Integer.parseInt(reader.readLine());

                        if (linkExcluir > 0 && linkExcluir <= listaLinks.size()) {
                            listaLinks.remove(linkExcluir - 1);
                        } else {
                            System.out.println("Número de link inválido!");
                        }
                        break;

                    case 4:
                        listarLinksPorAssunto(listaLinks, reader);
                        break;

                    case 5:
                        System.out.println("Saindo...");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                opcao = 5;
            } finally {
                salvarArquivo(listaLinks);
            }
        } while (opcao != 5);
    }

    public static void salvarArquivo(ArrayList<Link> listaLinks) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("links.txt"));
            for (Link link : listaLinks) {
                writer.write("***Assunto***\n" + link.assunto + "\n***URL***\n" + link.url + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void carregarArquivo(ArrayList<Link> listaLinks) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("links.txt"));
            String line = reader.readLine();
            while (line != null) {
                if (line.equals("***Assunto***")) {
                    String assunto = reader.readLine();
                    reader.readLine(); // pula a linha da URL
                    String url = reader.readLine();
                    listaLinks.add(new Link(assunto, url));
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (java.io.FileNotFoundException e) {
            // O arquivo não existe ainda, então vamos ignorar essa exceção.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void listarLinksPorAssunto(ArrayList<Link> listaLinks, BufferedReader reader) {
        if (listaLinks.isEmpty()) {
            System.out.println("Não há links cadastrados.");
            return;
        }

        try {
            System.out.println("Insira o assunto para listar os links: ");
            String assuntoDesejado = reader.readLine();
            System.out.println("Links do assunto " + assuntoDesejado + ":");

            boolean found = false;
            for (Link link : listaLinks) {
                if (link.assunto.equalsIgnoreCase(assuntoDesejado)) {
                    found = true;
                    System.out.println("URL: " + link.url);
                }
            }

            if (!found) {
                System.out.println("Não há links cadastrados para o assunto informado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
