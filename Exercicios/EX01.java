import java.util.Scanner;

public class Ex01 {

    // ==================== VARIÁVEIS GLOBAIS ====================

    static String nomeTitular; // Armazena o nome do titular da conta
    static double saldo; // Armazena o saldo atual da conta
    static int numTransacoes = 0; // Contador do número total de transações realizadas
    static boolean contaAtiva = false;  // Indica se a conta está ativa (true) ou inativa (false)

    // ==================== MÉTODO PRINCIPAL ====================

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("--- Criação de Conta ---");

        // Solicita e guarda o nome do titular
        solicitarNome(sc);

        // Solicita e guarda o depósito inicial
        solicitarDeposito(sc);

        // Ativa a conta após criação bem-sucedida
        contaAtiva = true;

        // Incrementa o contador (abertura de conta conta como transação)
        incrementarTransacoes();

        // Mostra confirmação da criação da conta
        exibirCriacaoConta();

        // Inicia o menu principal do banco
        executarMenu(sc);

        // Fecha o Scanner para libertar recursos
        sc.close();
    }

    // ==================== CRIAÇÃO DA CONTA ====================

    public static void solicitarNome(Scanner sc) {
        System.out.print("Digite o nome do titular: ");
        // trim() remove espaços em branco no início e no fim
        nomeTitular = sc.nextLine().trim();
    }

    public static void solicitarDeposito(Scanner sc) {
        System.out.print("Digite o depósito inicial: ");
        double valorTemp = sc.nextDouble();
        // Limpa o buffer do Scanner
        sc.nextLine();

        // Valida se o valor é positivo
        // Se não for, pede novamente até inserir valor válido
        while (valorTemp <= 0) {
            System.out.println("Valor inválido! O depósito inicial deve ser maior que 0€.");
            System.out.print("Digite o depósito inicial: ");
            valorTemp = sc.nextDouble();
            sc.nextLine();
        }

        // Guarda o valor válido no saldo
        saldo = valorTemp;
    }

    public static void exibirCriacaoConta() {
        System.out.println("=== Conta criada com sucesso! ===");
        System.out.println("Titular: " + nomeTitular);
        // String.format("%.2f€", saldo) formata o número com 2 casas decimais
        System.out.println("Saldo inicial: " + String.format("%.2f€", saldo));
        System.out.println("Estado: " + obterEstadoConta());
        System.out.println("Transações: " + obterNumTransacoes());
        System.out.println();
    }

    // ==================== MÉTODOS AUXILIARES ====================

    public static int obterNumTransacoes() {
        return numTransacoes;
    }

    public static void incrementarTransacoes() {
        numTransacoes++;
    }

    public static String obterEstadoConta() {
        // Operador ternário: condição ? valor_se_true : valor_se_false
        return contaAtiva ? "Ativo" : "Inativo";
    }

    // ==================== MENU ====================

    public static void exibirMenu() {
        System.out.println("=== Bem-vindo ao Banco Rich ===");
        System.out.println("1. Depositar");
        System.out.println("2. Levantar");
        System.out.println("3. Verificar Saldo");
        System.out.println("4. Sair");
        System.out.println();
    }

    public static void executarMenu(Scanner sc) {
        int opcao;

        // Ciclo do-while: executa pelo menos uma vez, depois verifica a condição
        do {
            exibirMenu();
            System.out.print("Digite a sua opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1:
                    realizarDeposito(sc);
                    break;
                case 2:
                    realizarLevantamento(sc);
                    break;
                case 3:
                    verificarSaldo();
                    break;
                case 4:
                    System.out.println("Obrigado por usar o Banco Rich! Até à próxima!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 4); // Continua enquanto não escolher sair
    }

    // ==================== OPERAÇÕES BANCÁRIAS ====================

    public static void realizarDeposito(Scanner sc) {
        System.out.println("--- Depósito ---");
        System.out.print("Digite o valor para depósito: ");

        double valor = sc.nextDouble();
        sc.nextLine(); // Limpa o buffer
        System.out.println();

        // Valida se o valor é positivo
        if (valor > 0) {
            // Adiciona o valor ao saldo
            saldo += valor;

            // Incrementa o contador de transações
            incrementarTransacoes();

            // Mostra mensagem de sucesso
            System.out.println("=== Depósito realizado com sucesso! ===");
            System.out.println("Novo saldo: " + String.format("%.2f€", saldo));
            System.out.println("Transações: " + obterNumTransacoes());
            System.out.println();
        } else {
            // Valor inválido (negativo ou zero)
            System.out.println("Valor inválido! Tente novamente com um valor acima de 0.");
        }
    }

    public static void realizarLevantamento(Scanner sc) {
        System.out.println("--- Levantamento ---");
        System.out.print("Digite o valor para levantamento: ");

        double valor = sc.nextDouble();
        sc.nextLine(); // Limpa o buffer

        // Validação 1: Verifica se o valor é positivo
        if (valor <= 0) {
            System.out.println("Valor inválido! Tente novamente com o valor acima de 0.");
            System.out.println();
            return;
        }

        // Validação 2: Verifica se há saldo suficiente
        if (valor > saldo) {
            System.out.println("Saldo insuficiente!");
            System.out.println();
            return;
        }

        // Se passou nas validações, realiza o levantamento
        saldo -= valor; // Subtrai o valor do saldo
        incrementarTransacoes(); // Incrementa contador

        // Mostra mensagem de sucesso
        System.out.println("=== Levantamento realizado com sucesso! ===");
        System.out.println("Novo saldo: " + String.format("%.2f€", saldo));
        System.out.println("Transações: " + obterNumTransacoes());
        System.out.println();
    }

    public static void verificarSaldo() {
        System.out.println("=== Resumo da Conta ===");
        System.out.println("Titular: " + nomeTitular);
        System.out.println("Saldo atual: " + String.format("%.2f€", saldo));
        System.out.println("Número de transações: " + obterNumTransacoes());
        System.out.println("Estado: " + obterEstadoConta());
        System.out.println();
    }
}
