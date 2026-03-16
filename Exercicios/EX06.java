import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class EX06 {

    // ==================== VARIÁVEIS GLOBAIS (ArrayLists para Livros) ====================
    // ArrayLists paralelos para armazenar dados dos livros
    // Cada posição i representa um livro e seus dados
    static ArrayList<String> titulosLivros = new ArrayList<>();
    static ArrayList<String> autoresLivros = new ArrayList<>();
    static ArrayList<String> isbnsLivros = new ArrayList<>();
    static ArrayList<Integer> quantidadesTotais = new ArrayList<>();
    static ArrayList<Integer> quantidadesDisponiveis = new ArrayList<>();

    // ==================== VARIÁVEIS GLOBAIS (ArrayLists para Empréstimos) ====================
    // ArrayLists paralelos para armazenar dados dos empréstimos
    static ArrayList<String> isbnsEmprestimos = new ArrayList<>();
    static ArrayList<String> nomesLeitores = new ArrayList<>();
    static ArrayList<String> numerosLeitores = new ArrayList<>();
    static ArrayList<LocalDate> datasEmprestimos = new ArrayList<>();
    static ArrayList<LocalDate> datasDevolucoesPrevistas = new ArrayList<>();
    static ArrayList<Boolean> emprestimosAtivos = new ArrayList<>();

    // ==================== CONSTANTES ====================
    static final int MAX_LIVROS = 100;
    static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        int opcao = 0;

        // Ciclo while: continua até escolher sair (opção 7)
        while (opcao != 7) {
            exibirMenu();

            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa buffer

            System.out.println();

            // Switch para processar a opção escolhida
            switch (opcao) {
                case 1:
                    adicionarLivro();
                    break;
                case 2:
                    procurarLivros();
                    break;
                case 3:
                    mostrarTodosLivros();
                    break;
                case 4:
                    emprestarLivro();
                    break;
                case 5:
                    devolverLivro();
                    break;
                case 6:
                    verEstatisticas();
                    break;
                case 7:
                    System.out.println("Obrigado por usar o Sistema de Biblioteca!");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }

        scanner.close();
    }

    // ==================== MENU ====================

    public static void exibirMenu() {
        System.out.println("\n=== Sistema de Biblioteca ===");
        System.out.println("1. Adicionar Livro");
        System.out.println("2. Procurar Livros");
        System.out.println("3. Mostrar Todos os Livros");
        System.out.println("4. Emprestar Livro");
        System.out.println("5. Devolver Livro");
        System.out.println("6. Ver Estatísticas");
        System.out.println("7. Sair");
    }

    // ==================== GESTÃO DE LIVROS ====================

    public static void adicionarLivro() {

        // 1. Verifica se o limite máximo de livros foi atingido
        if (titulosLivros.size() >= MAX_LIVROS) {
            System.out.println("Erro: Limite de livros atingido! (Máximo: " + MAX_LIVROS + ")");
            return;
        }

        System.out.println("--- Adicionar Novo Livro ---");

        // 2. Solicita o título do livro
        System.out.print("Digite o título: ");
        String titulo = scanner.nextLine();

        // 3. Solicita o autor do livro
        System.out.print("Digite o autor: ");
        String autor = scanner.nextLine();

        // 4. Solicita o ISBN do livro
        System.out.print("Digite o ISBN: ");
        String isbn = scanner.nextLine();

        // 5. Validação: ISBN deve ter exatamente 10 caracteres
        if (isbn.length() != 10) {
            System.out.println("Erro: ISBN deve ter exatamente 10 caracteres!");
            return;
        }

        // 6. Validação: ISBN deve conter apenas números
        // isbn.charAt(i) Vai buscar um carácter da String isbn
        // Character.isDigit(...) É um método da classe Character, serve para verificar se um carácter é um número (0 a 9)

        for (int i = 0; i < isbn.length(); i++) {
            if (!Character.isDigit(isbn.charAt(i))) { //Se não for um número, a condição é verdadeira
                System.out.println("Erro: ISBN deve conter apenas números!");
                return;
            }
        }

        // 7. Validação: ISBN deve ser único
        if (buscarIndiceLivroPorIsbn(isbn) != -1) {
            System.out.println("Erro: Já existe um livro com este ISBN!");
            return;
        }

        // 8. Solicita a quantidade total de exemplares
        System.out.print("Digite a quantidade: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer

        // 9. Validação: quantidade deve ser maior que zero
        if (quantidade <= 0) {
            System.out.println("Erro: Quantidade deve ser maior que zero!");
            return;
        }

        // 10. Adiciona o livro aos ArrayLists paralelos
        titulosLivros.add(titulo);
        autoresLivros.add(autor);
        isbnsLivros.add(isbn);
        quantidadesTotais.add(quantidade);

        // 11. Inicialmente todas as cópias estão disponíveis
        quantidadesDisponiveis.add(quantidade);

        // 12. Confirmação da operação
        System.out.println("\n=== Livro adicionado com sucesso! ===");
        System.out.println("Título: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("ISBN: " + isbn);
        System.out.println("Quantidade disponível: " + quantidade);
    }

    public static void procurarLivros() {
        System.out.println("--- Procurar Livros ---");
        System.out.println("1. Procurar por Título");
        System.out.println("2. Procurar por Autor");
        System.out.println("3. Procurar por ISBN");
        System.out.print("Tipo de pesquisa: ");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // Limpa buffer

        System.out.print("Digite o termo: ");
        String termo = scanner.nextLine().toLowerCase();

        // ArrayList para guardar índices dos livros encontrados
        ArrayList<Integer> indices = new ArrayList<>();

        // Ciclo for: percorre todos os livros procurando correspondências
        for (int i = 0; i < titulosLivros.size(); i++) {
            boolean encontrado = false;

            if (tipo == 1) {
                // Procurar por título (contains = aceita termo parcial)
                if (titulosLivros.get(i).toLowerCase().contains(termo)) {
                    encontrado = true;
                }
            } else if (tipo == 2) {
                // Procurar por autor
                if (autoresLivros.get(i).toLowerCase().contains(termo)) {
                    encontrado = true;
                }
            } else if (tipo == 3) {
                // Procurar por ISBN
                if (isbnsLivros.get(i).toLowerCase().contains(termo)) {
                    encontrado = true;
                }
            }

            // Se encontrou, guarda o índice
            if (encontrado) {
                indices.add(i);
            }
        }

        // Exibe resultados
        if (indices.size() == 0) {
            System.out.println("\nNenhum livro encontrado.");
        } else {
            System.out.println("\n=== Resultados encontrados ===");

            // Ciclo for: exibe cada livro encontrado
            for (int i = 0; i < indices.size(); i++) {
                int indice = indices.get(i);
                System.out.println((i + 1) + ". \"" + titulosLivros.get(indice) + "\"");
                System.out.println("   Autor: " + autoresLivros.get(indice));
                System.out.println("   ISBN: " + isbnsLivros.get(indice));
                System.out.println("   Disponível: " + quantidadesDisponiveis.get(indice) +
                        "/" + quantidadesTotais.get(indice) + " cópias");
                System.out.println("   Estado: " + obterEstadoLivro(indice));

                // Adiciona linha em branco entre resultados (exceto no último)
                if (i < indices.size() - 1) {
                    System.out.println();
                }
            }
        }
    }

    public static void mostrarTodosLivros() {
        if (titulosLivros.size() == 0) {
            System.out.println("Nenhum livro cadastrado na biblioteca.");
            return;
        }

        System.out.println("=== Lista Completa de Livros ===");

        // Ciclo for: percorre e exibe todos os livros
        for (int i = 0; i < titulosLivros.size(); i++) {
            System.out.println("Título: " + titulosLivros.get(i));
            System.out.println("Autor: " + autoresLivros.get(i));
            System.out.println("ISBN: " + isbnsLivros.get(i));
            System.out.println("Quantidade total: " + quantidadesTotais.get(i));
            System.out.println("Disponível: " + quantidadesDisponiveis.get(i));
            System.out.println("Estado: " + obterEstadoLivro(i));
            System.out.println("Empréstimos ativos: " + contarEmprestimosAtivos(isbnsLivros.get(i)));

            // Separador entre livros (exceto no último)
            if (i < titulosLivros.size() - 1) {
                System.out.println("---------------------------");
            }
        }
    }

    // ==================== SISTEMA DE EMPRÉSTIMO ====================

    public static void emprestarLivro() {
        System.out.println("--- Empréstimo de Livro ---");

        System.out.print("Digite o ISBN do livro: ");
        String isbn = scanner.nextLine();

        // Validação 1: Livro existe?
        int indiceLivro = buscarIndiceLivroPorIsbn(isbn);
        if (indiceLivro == -1) {
            System.out.println("Erro: ISBN não encontrado!");
            return;
        }

        // Validação 2: Há cópias disponíveis?
        if (quantidadesDisponiveis.get(indiceLivro) == 0) {
            System.out.println("Erro: Não há cópias disponíveis deste livro!");
            return;
        }

        System.out.print("Digite o nome do leitor: ");
        String nomeLeitor = scanner.nextLine();

        System.out.print("Digite o número de leitor (exemplo: L00001 ou l00001): ");
        String numeroLeitor = scanner.nextLine();

        // Validação 3: Número de leitor não pode estar vazio
        if (numeroLeitor.isEmpty() || numeroLeitor.trim().isEmpty()) {
            System.out.println("Erro: Número de leitor é obrigatório!");
            return;
        }

        // Validação 4: Formato = L/l + exatamente 5 dígitos
        if (numeroLeitor.length() != 6) {
            System.out.println("Erro: Deve ter exatamente 6 caracteres!");
            return;
        }

        // Primeira posição: DEVE ser 'L' ou 'l'
        char primeiraLetra = Character.toUpperCase(numeroLeitor.charAt(0));
        if (primeiraLetra != 'L') {
            System.out.println("Erro: Deve começar com L ou l (ex: L00001 ou l00001)!");
            return;
        }

        // Posições 2-6: apenas dígitos (0-9)
        for (int i = 1; i < numeroLeitor.length(); i++) {
            if (!Character.isDigit(numeroLeitor.charAt(i))) {
                System.out.println("Erro: Posições 2-6 devem ser números (0-9)!");
                return;
            }
        }

        // Validação 5: Leitor já tem este livro emprestado?
        for (int i = 0; i < isbnsEmprestimos.size(); i++) {
            if (emprestimosAtivos.get(i) &&
                    isbnsEmprestimos.get(i).equals(isbn) &&
                    numerosLeitores.get(i).equals(numeroLeitor)) {
                System.out.println("Erro: Este leitor já tem uma cópia deste livro emprestada!");
                return;
            }
        }

        // Realizar empréstimo
        quantidadesDisponiveis.set(indiceLivro, quantidadesDisponiveis.get(indiceLivro) - 1);

        LocalDate dataEmprestimo = LocalDate.now();
        LocalDate dataDevolucao = dataEmprestimo.plusDays(7);

        isbnsEmprestimos.add(isbn);
        nomesLeitores.add(nomeLeitor);
        numerosLeitores.add(numeroLeitor);
        datasEmprestimos.add(dataEmprestimo);
        datasDevolucoesPrevistas.add(dataDevolucao);
        emprestimosAtivos.add(true);

        // Confirmação
        System.out.println("\n=== Empréstimo realizado com sucesso! ===");
        System.out.println("Livro: " + titulosLivros.get(indiceLivro));
        System.out.println("Leitor: " + nomeLeitor + " (" + numeroLeitor + ")");
        System.out.println("Data de empréstimo: " + dataEmprestimo);
        System.out.println("Data de devolução: " + dataDevolucao);
        System.out.println("Cópias restantes: " + quantidadesDisponiveis.get(indiceLivro) +
                "/" + quantidadesTotais.get(indiceLivro));
    }

    public static void devolverLivro() {
        System.out.println("--- Devolução de Livro ---");

        System.out.print("Digite o ISBN do livro: ");
        String isbn = scanner.nextLine();

        System.out.print("Digite o número do leitor: ");
        String numeroLeitor = scanner.nextLine();

        // Procura empréstimo ativo correspondente
        int indiceEmprestimo = -1;
        for (int i = 0; i < isbnsEmprestimos.size(); i++) {
            if (emprestimosAtivos.get(i) &&
                    isbnsEmprestimos.get(i).equals(isbn) &&
                    numerosLeitores.get(i).equals(numeroLeitor)) {
                indiceEmprestimo = i;
                break; // Para quando encontrar
            }
        }

        // Validação: Empréstimo existe?
        if (indiceEmprestimo == -1) {
            System.out.println("Erro: Empréstimo não encontrado!");
            return;
        }

        int indiceLivro = buscarIndiceLivroPorIsbn(isbn);

        // Realizar devolução
        // 1. Marca empréstimo como inativo
        emprestimosAtivos.set(indiceEmprestimo, false);

        // 2. Aumenta quantidade disponível
        quantidadesDisponiveis.set(indiceLivro, quantidadesDisponiveis.get(indiceLivro) + 1);

        // 3. Calcula se está atrasado
        LocalDate hoje = LocalDate.now();
        LocalDate dataPrevista = datasDevolucoesPrevistas.get(indiceEmprestimo);

        String estado;
        if (hoje.isAfter(dataPrevista)) {
            // Calcular dias de atraso com ciclo while
            long diasAtraso = 0;
            LocalDate dataTemp = dataPrevista;

            // Ciclo while: conta dias até hoje
            while (dataTemp.isBefore(hoje)) {
                dataTemp = dataTemp.plusDays(1);
                diasAtraso++;
            }
            estado = "Atrasado (" + diasAtraso + " dias)";
        } else {
            estado = "No prazo";
        }

        // Confirma devolução
        System.out.println("\n=== Devolução realizada com sucesso! ===");
        System.out.println("Livro: " + titulosLivros.get(indiceLivro));
        System.out.println("Leitor: " + nomesLeitores.get(indiceEmprestimo));
        System.out.println("Data de devolução: " + hoje);
        System.out.println("Estado: " + estado);
        System.out.println("Cópias disponíveis: " + quantidadesDisponiveis.get(indiceLivro) +
                "/" + quantidadesTotais.get(indiceLivro));
    }

    // ==================== ESTATÍSTICAS ====================
    public static void verEstatisticas() {
        int totalLivros = titulosLivros.size();
        int totalCopias = 0;
        int livrosEmprestados = 0;
        int livrosDisponiveis = 0;

        // Ciclo for: percorre todos os livros somando totais
        for (int i = 0; i < titulosLivros.size(); i++) {
            totalCopias += quantidadesTotais.get(i);
            int emprestimosAtivos = quantidadesTotais.get(i) - quantidadesDisponiveis.get(i);
            livrosEmprestados += emprestimosAtivos;
            livrosDisponiveis += quantidadesDisponiveis.get(i);
        }

        System.out.println("=== Estatísticas da Biblioteca ===");
        System.out.println("Total de livros cadastrados: " + totalLivros);
        System.out.println("Total de cópias: " + totalCopias);
        System.out.println("Livros emprestados: " + livrosEmprestados);
        System.out.println("Livros disponíveis: " + livrosDisponiveis);
    }

    // ==================== MÉTODOS AUXILIARES ====================
    public static int buscarIndiceLivroPorIsbn(String isbn) {
        // Ciclo for: procura ISBN no ArrayList
        for (int i = 0; i < isbnsLivros.size(); i++) {
            if (isbnsLivros.get(i).equals(isbn)) {
                return i;
            }
        }
        return -1; // -1 indica não encontrado
    }

    public static String obterEstadoLivro(int indice) {
        int disponivel = quantidadesDisponiveis.get(indice);
        int total = quantidadesTotais.get(indice);

        // Estrutura if-else para determinar estado
        if (disponivel == total) {
            return "Disponível";
        } else if (disponivel == 0) {
            return "Totalmente Emprestado";
        } else {
            return "Parcialmente Emprestado";
        }
    }

    public static int contarEmprestimosAtivos(String isbn) {
        int contador = 0;

        // Ciclo for: percorre empréstimos contando ativos deste ISBN
        for (int i = 0; i < isbnsEmprestimos.size(); i++) {
            if (emprestimosAtivos.get(i) && isbnsEmprestimos.get(i).equals(isbn)) {
                contador++;
            }
        }
        return contador;
    }
}