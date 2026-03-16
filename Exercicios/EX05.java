// Importação das classes necessárias
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class EX05 {

    // ==================== VARIÁVEIS GLOBAIS ====================
    // Scanner para ler entrada do utilizador
    static Scanner scanner = new Scanner(System.in);

    // Random para gerar números aleatórios
    static Random random = new Random();

    // ArrayList para guardar o histórico de tentativas
    static ArrayList<Integer> historicoTentativas = new ArrayList<>();

    // ==================== MÉTODO PRINCIPAL ====================

    public static void main(String[] args) {
        System.out.println("\n=== Jogo de Adivinhação ===");

        // Pede o nome UMA VEZ no início
        String nomeJogador = obterNomeJogador();

        boolean jogarNovamente = true;

        // Ciclo while: joga enquanto quiser continuar
        while (jogarNovamente) {
            jogarPartida(nomeJogador); // Passa o nome para a partida
            jogarNovamente = perguntarNovaPartida();
        }

        System.out.println("\nObrigado por jogar, " + nomeJogador + "! Até à próxima!");
        scanner.close();
    }

    // ==================== FLUXO DA PARTIDA ====================
    /**
     * Controla uma partida completa do jogo
     * Recebe o nome do jogador como parâmetro (não pede de novo)
     * 1. Escolher dificuldade
     * 2. Configurar jogo
     * 3. Executar jogo
     * 4. Exibir estatísticas
     */
    public static void jogarPartida(String nomeJogador) {
        // Escolher dificuldade (1, 2 ou 3)
        int dificuldade = escolherDificuldade();

        // Obter configuração baseada na dificuldade
        // Array simples com 3 valores: [limiteInferior, limiteSuperior, tentativasMaximas]
        int[] configuracao = obterConfiguracao(dificuldade);
        int limiteInferior = configuracao[0];
        int limiteSuperior = configuracao[1];
        int tentativasMaximas = configuracao[2];

        // Gerar número secreto aleatório
        int numeroSecreto = gerarNumeroAleatorio(limiteInferior, limiteSuperior);

        // Limpar histórico de tentativas anteriores
        historicoTentativas.clear();

        // Exibir informações do jogo
        exibirCabecalho(nomeJogador, dificuldade, limiteInferior, limiteSuperior, tentativasMaximas);

        // Executar o jogo e verificar se acertou
        boolean acertou = executarJogo(numeroSecreto, tentativasMaximas);

        // Exibir estatísticas finais
        exibirEstatisticas(nomeJogador, numeroSecreto, tentativasMaximas, acertou);
    }

    // ==================== CONFIGURAÇÃO DO JOGO ====================

    public static String obterNomeJogador() {
        System.out.print("Digite o seu nome: ");
        return scanner.nextLine();
    }

    public static int escolherDificuldade() {
        System.out.println("\nEscolha a dificuldade:");
        System.out.println("1. Fácil (1-50)");
        System.out.println("2. Médio (1-100)");
        System.out.println("3. Difícil (1-200)");
        System.out.print("Opção: ");
        return scanner.nextInt();
    }

    public static int[] obterConfiguracao(int dificuldade) {
        // Array para guardar configuração: [min, max, tentativas]
        int[] config = new int[3];

        // Switch para definir configuração baseada na dificuldade
        switch (dificuldade) {
            case 1: // Fácil
                config[0] = 1;    // Limite inferior
                config[1] = 50;   // Limite superior
                config[2] = 6;    // Tentativas máximas
                break;
            case 2: // Médio
                config[0] = 1;
                config[1] = 100;
                config[2] = 10;
                break;
            case 3: // Difícil
                config[0] = 1;
                config[1] = 200;
                config[2] = 15;
                break;
            default: // Se digitar número inválido, usa Médio
                config[0] = 1;
                config[1] = 100;
                config[2] = 10;
        }

        return config;
    }

    public static int gerarNumeroAleatorio(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static String obterNomeDificuldade(int dificuldade) {
        switch (dificuldade) {
            case 1: return "Fácil";
            case 2: return "Médio";
            case 3: return "Difícil";
            default: return "Médio";
        }
    }

    public static void exibirCabecalho(String nome, int dificuldade, int min, int max, int tentativas) {
        String nivelDificuldade = obterNomeDificuldade(dificuldade);

        System.out.println("\nBem-vindo " + nome + "! Vamos começar!");
        System.out.println("Dificuldade: " + nivelDificuldade + " (" + min + "-" + max + ")");
        System.out.println("Tentativas máximas: " + tentativas);
        System.out.println();
    }

    // ==================== MECÂNICA DO JOGO ====================

    public static boolean executarJogo(int numeroSecreto, int tentativasMaximas) {
        int tentativasUsadas = 0;
        boolean acertou = false;

        // Ciclo while: continua enquanto tem tentativas E não acertou
        while (tentativasUsadas < tentativasMaximas && !acertou) {
            tentativasUsadas++;

            // Solicita palpite do jogador
            int palpite = obterPalpite(tentativasUsadas);

            // Adiciona palpite ao histórico (ArrayList)
            historicoTentativas.add(palpite);

            // Verifica se acertou
            if (palpite == numeroSecreto) {
                acertou = true;
                exibirMensagemVitoria(tentativasUsadas);
            } else {
                // Se errou, dá dica e mostra informações
                exibirDica(palpite, numeroSecreto);
                exibirHistorico();
                exibirTentativasRestantes(tentativasUsadas, tentativasMaximas);
            }
        }

        // Se saiu do loop sem acertar, perdeu
        if (!acertou) {
            exibirMensagemDerrota(numeroSecreto);
        }

        return acertou;
    }

    public static int obterPalpite(int numeroTentativa) {
        System.out.print("\nTentativa #" + numeroTentativa + ": ");
        return scanner.nextInt();
    }

     public static void exibirDica(int palpite, int numeroSecreto) {
        if (palpite > numeroSecreto) {
            // Palpite maior que o número secreto
            int diferenca = palpite - numeroSecreto;

            if (diferenca > 20) {
                System.out.println("Muito alto!");
            } else if (diferenca > 10) {
                System.out.println("Alto!");
            } else {
                System.out.println("Um pouco alto!");
            }
        } else {
            // Palpite menor que o número secreto
            int diferenca = numeroSecreto - palpite;

            if (diferenca > 20) {
                System.out.println("Muito baixo!");
            } else if (diferenca > 10) {
                System.out.println("Baixo!");
            } else {
                System.out.println("Um pouco baixo!");
            }
        }
    }

    public static void exibirHistorico() {
        System.out.print("Tentativas anteriores: [");

        // Ciclo for: percorre ArrayList
        for (int i = 0; i < historicoTentativas.size(); i++) {
            System.out.print(historicoTentativas.get(i));

            // Se não é o último, adiciona vírgula
            if (i < historicoTentativas.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    public static void exibirTentativasRestantes(int usadas, int maximas) {
        int restantes = maximas - usadas;
        System.out.println("Tentativas restantes: " + restantes);
    }

    // ==================== MENSAGENS DE RESULTADO ====================
    public static void exibirMensagemVitoria(int tentativas) {
        System.out.println("\nParabéns! Acertaste em " + tentativas + " tentativa(s)!");
    }

    public static void exibirMensagemDerrota(int numeroSecreto) {
        System.out.println("\nGame Over! Esgotaste todas as tentativas.");
        System.out.println("O número secreto era: " + numeroSecreto);
    }

    // ==================== ESTATÍSTICAS ====================

    public static void exibirEstatisticas(String nome, int numeroSecreto, int tentativasMaximas, boolean vitoria) {
        System.out.println("\n=== Estatísticas do Jogo ===");
        System.out.println("Jogador: " + nome);
        System.out.println("Número sorteado: " + numeroSecreto);
        System.out.println("Tentativas usadas: " + historicoTentativas.size() + "/" + tentativasMaximas);

        // Exibe histórico completo
        System.out.print("Histórico: [");
        for (int i = 0; i < historicoTentativas.size(); i++) {
            System.out.print(historicoTentativas.get(i));
            if (i < historicoTentativas.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

        // Exibe resultado (operador ternário)
        String resultado = vitoria ? "VITÓRIA" : "DERROTA";
        System.out.println("Resultado: " + resultado);
    }

    // ==================== CONTROLE DE FLUXO ====================

    public static boolean perguntarNovaPartida() {
        String resposta;

        // Limpa buffer ANTES do loop
        scanner.nextLine();

        // Ciclo do-while: repete até resposta válida
        do {
            System.out.print("\nDesejas jogar novamente? (S/N): ");
            resposta = scanner.nextLine().trim().toUpperCase();

            // Valida se é S ou N
            if (!resposta.equals("S") && !resposta.equals("N")) {
                System.out.println("Resposta inválida! Digite S ou N.");
            }
        } while (!resposta.equals("S") && !resposta.equals("N"));

        return resposta.equals("S");
    }
}