import java.util.ArrayList;
import java.util.Scanner;

public class EX03 {

    // ==================== CONSTANTES ====================
    // Limite máximo de produtos diferentes na lista
    static final int MAX_ITENS = 10;

    // Taxa de IVA aplicada (23% = 0.23)
    static final double TAXA_IVA = 0.23;

    // ==================== VARIÁVEIS GLOBAIS (ArrayLists) ====================
    // ArrayList é uma coleção dinâmica que cresce conforme necessário
    // Guarda os nomes dos produtos (String)
    static ArrayList<String> nomesItens = new ArrayList<>();

    // Guarda os preços dos produtos (Double)
    static ArrayList<Double> precosItens = new ArrayList<>();

    // ==================== MÉTODO PRINCIPAL ====================

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Gestor de Lista de Compras ===");

        executarCicloCompras(sc);

        System.out.println("\nObrigado por usar o Gestor de Lista de Compras!");
        sc.close();
    }

    // ==================== CICLO PRINCIPAL ====================

    public static void executarCicloCompras(Scanner sc) {
        String continuar;

        // Ciclo do-while: executa pelo menos uma vez
        do {
            // Solicita quantos itens deseja adicionar
            int quantidade = solicitarQuantidade(sc);

            // Adiciona os itens à lista
            adicionarItens(sc, quantidade);

            // Mostra a lista atualizada com totais
            mostrarLista();

            // Verifica se ainda há espaço para mais itens
            if (nomesItens.size() < MAX_ITENS) {
                // Pergunta se deseja continuar, validando a resposta
                continuar = perguntarContinuar(sc);
            } else {
                // Limite atingido, não pergunta mais
                System.out.println("\nLimite máximo de " + MAX_ITENS + " itens atingido!");
                continuar = "N";
            }

        } while (continuar.equals("S") && nomesItens.size() < MAX_ITENS);
    }

    // ==================== ENTRADA DE DADOS ====================

    public static int solicitarQuantidade(Scanner sc) {
        // Calcula quantos itens ainda podem ser adicionados
        // Exemplo: se MAX_ITENS = 10 e nomesItens.size() = 7, disponiveis = 3
        int disponiveis = MAX_ITENS - nomesItens.size();

        int quantidade;

        // Loop de validação: continua até obter valor válido
        do {
            System.out.print("Quantos itens desejas adicionar? (1-" + disponiveis + "): ");
            quantidade = sc.nextInt();
            sc.nextLine(); // Limpa o buffer após ler número

            // Validação 1: Quantidade não pode ser menor que 1
            if (quantidade < 1) {
                System.out.println("Erro: Deve adicionar pelo menos 1 item!");
            }
            // Validação 2: Quantidade não pode exceder o limite disponível
            else if (quantidade > disponiveis) {
                System.out.println("Erro: Só podes adicionar " + disponiveis + " item(ns)! " +
                        "(Já tens " + nomesItens.size() + " de " + MAX_ITENS + " itens)");
            }

        } while (quantidade < 1 || quantidade > disponiveis);

        return quantidade;
    }

    public static void adicionarItens(Scanner sc, int quantidade) {
        // Ciclo for: repete 'quantidade' vezes
        // i começa em 0 e vai até quantidade-1
        for (int i = 0; i < quantidade && nomesItens.size() < MAX_ITENS; i++) {
            // Mostra o número do item atual
            System.out.println("\n--- Item " + (nomesItens.size() + 1) + " ---");

            String nome;
            do {
                System.out.print("Nome do item: ");
                nome = sc.nextLine().trim(); // trim() remove espaços extras no início/fim

                // Validação: nome não pode ser vazio
                if (nome.isEmpty()) {
                    System.out.println("Erro: O nome não pode estar vazio! Por favor, digite um nome.");
                }

            } while (nome.isEmpty()); // Repete enquanto nome estiver vazio

            // Verifica se o produto já existe na lista
            if (produtoJaExiste(nome)) {
                System.out.println("[Produto já existe na lista! Adicionando mais uma unidade]");
            }

            double preco;
            do {
                System.out.print("Preço: ");
                preco = sc.nextDouble();
                sc.nextLine(); // Limpa o buffer

                // Validação: preço não pode ser negativo ou zero
                if (preco <= 0) {
                    System.out.println("Erro: O preço deve ser maior que 0€!");
                }

            } while (preco <= 0);

            // Adiciona o item à lista
            // .add() insere um novo elemento no final do ArrayList
            nomesItens.add(nome);
            precosItens.add(preco);
        }
    }

    // ==================== VERIFICAÇÃO ====================

    public static boolean produtoJaExiste(String nome) {
        // Ciclo for: percorre todos os itens da lista
        for (int i = 0; i < nomesItens.size(); i++) {
            // .get(i) pega o elemento na posição i
            // .equalsIgnoreCase() compara ignorando maiúsculas/minúsculas
            if (nomesItens.get(i).equalsIgnoreCase(nome)) {
                return true; // Encontrou, retorna verdadeiro
            }
        }
        return false; // Não encontrou, retorna falso
    }

    // ==================== EXIBIÇÃO DA LISTA ====================

    public static void mostrarLista() {
        System.out.println("\n=== Lista de Compras Atual ===");

        for (int i = 0; i < nomesItens.size(); i++) {
            // Formata o número do item com o nome
            String itemNumero = (i + 1) + ". " + nomesItens.get(i);

            // Formata o preço
            String preco = String.format("%.2f€", precosItens.get(i));

            // Calcula quantos pontos são necessários
            // Largura total desejada: 40 caracteres
            int larguraTotal = 40;
            int espacosNecessarios = larguraTotal - itemNumero.length() - preco.length();

            // Se o nome for muito grande, usa mínimo de 3 pontos
            if (espacosNecessarios < 3) {
                espacosNecessarios = 3;
            }

            // Cria string com os pontos
            String pontos = ".".repeat(espacosNecessarios);

            // Imprime a linha completa
            System.out.println(itemNumero + pontos + preco);
        }


        // Calcula os valores
        double subtotal = calcularSubtotal();
        double iva = calcularIVA(subtotal);
        double total = calcularTotal(subtotal, iva);

        // Exibe os totais
        System.out.println("----------------------------------------");
        System.out.println("Subtotal: " + String.format("%.2f€", subtotal));
        System.out.println("IVA (23%): " + String.format("%.2f€", iva));
        System.out.println("Total: " + String.format("%.2f€", total));
    }

    // ==================== CÁLCULOS ====================

    public static double calcularSubtotal() {
        double subtotal = 0;

        // Ciclo for: percorre todos os preços
        for (int i = 0; i < precosItens.size(); i++) {
            // += é o mesmo que: subtotal = subtotal + precosItens.get(i)
            subtotal += precosItens.get(i);
        }

        return subtotal;
    }


    public static double calcularIVA(double subtotal) {
        return subtotal * TAXA_IVA;
    }


    public static double calcularTotal(double subtotal, double iva) {
        return subtotal + iva;
    }

    // ==================== VALIDAÇÃO DE CONTINUAÇÃO ====================

    public static String perguntarContinuar(Scanner sc) {
        String resposta;

        // Ciclo do-while: repete até obter resposta válida
        do {
            System.out.print("\nDesejas adicionar mais itens? (S/N): ");
            resposta = sc.nextLine().trim().toUpperCase(); // Remove espaços e converte para maiúsculas

            // Valida se a resposta é válida
            // Aceita: S, SIM, N, NAO, NÃO
            if (resposta.equals("S") || resposta.equals("SIM")) {
                return "S"; // Retorna S para continuar
            }
            else if (resposta.equals("N") || resposta.equals("NAO") || resposta.equals("NÃO")) {
                return "N"; // Retorna N para parar
            }
            else {
                // Resposta inválida
                System.out.println("Resposta inválida! Por favor, digite S, SIM, N ou NAO.");
            }

        } while (true); // Continua até receber resposta válida (return sai do loop)
    }
    }