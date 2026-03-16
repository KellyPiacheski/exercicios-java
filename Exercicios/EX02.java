import java.util.Scanner;

public class EX02 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Ciclo do-while: repete enquanto o utilizador quiser calcular mais notas
        do {

            String nome = solicitarNome(sc);
            double nota = solicitarNota(sc);
            char conceito = converterNotaConceito(nota);
            String situacao = obterSituacao(conceito);
            String feedback = obterFeedback(conceito);
            exibirResultado(nome, nota, conceito, situacao, feedback);
            System.out.print("Desejas calcular outra nota? (S/N): ");

        } while (lerContinuar(sc)); // Continua se responder 'S'

        System.out.println("\nObrigado por usar a Calculadora de Notas! Até logo!");
        sc.close();
    }

    // ==================== ENTRADA DE DADOS ====================

    public static String solicitarNome(Scanner sc) {
        System.out.println("=== Calculadora de Notas ===");
        System.out.print("Digite o nome do aluno: ");
        return sc.nextLine().trim();
    }

    public static double solicitarNota(Scanner sc) {
        double nota;

        do {
            System.out.print("Digite a nota (0-100): ");
            nota = sc.nextDouble();
            sc.nextLine(); // Limpa o buffer

            // Valida se a nota está no intervalo correto
            if (nota < 0 || nota > 100) {
                System.out.println("Nota inválida! Deve estar entre 0 e 100. Tente novamente.");
            }
        } while (nota < 0 || nota > 100);

        return nota;
    }

    public static boolean lerContinuar(Scanner sc) {
        // Ciclo while(true): repete até encontrar um return
        while (true) {
            String input = sc.nextLine().trim().toUpperCase();

            // Se não digitou nada, pede novamente
            if (input.isEmpty()) {
                System.out.print("Digite S ou N: ");
                continue; // Volta ao início do loop
            }

            // Pega o primeiro caractere da resposta
            char resposta = input.charAt(0);

            // Verifica se é 'S' ou 'N'
            if (resposta == 'S') return true;
            if (resposta == 'N') return false;

            // Se não for válido, pede novamente
            System.out.print("Digite S ou N: ");
        }
    }

    // ==================== CONVERSÃO E LÓGICA ====================

    public static char converterNotaConceito(double nota) {
        // Estrutura if-else if encadeada
        if (nota >= 90) {
            return 'A';
        } else if (nota >= 80) {
            return 'B';
        } else if (nota >= 70) {
            return 'C';
        } else if (nota >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }

    public static String obterSituacao(char conceito) {
        // Operador ternário: condição ? valor_se_true : valor_se_false
        return (conceito == 'F') ? "REPROVADO" : "APROVADO";
    }

    public static String obterFeedback(char conceito) {
        // Estrutura switch para selecionar feedback baseado no conceito
        switch (conceito) {
            case 'A':
                return "A - Excelente! Desempenho excecional. Mantém este ritmo!";

            case 'B':
                return "B - Muito bom desempenho! Continua a esforçar-te.\n" +
                        "Horas recomendadas de estudo: 2 horas/dia";

            case 'C':
                return "C - Bom trabalho! Podes melhorar ainda mais.\n" +
                        "Horas recomendadas de estudo: 3 horas/dia";

            case 'D':
                return "D - Suficiente, mas precisas de mais esforço.\n" +
                        "Horas recomendadas de estudo: 4 horas/dia";

            case 'F':
                return "F - Reprovado. É necessário estudar mais.\n" +
                        "Horas recomendadas de estudo: 6 horas/dia";

            default:
                return "Conceito inválido";
        }
    }

    // ==================== EXIBIÇÃO DE RESULTADOS ====================

    public static void exibirResultado(String nome, double nota, char conceito,
                                       String situacao, String feedback) {
        System.out.println("\n=== Resultado ===");
        System.out.println("Aluno: " + nome);
        // printf com %.1f mostra 1 casa decimal
        System.out.printf("Nota numérica: %.1f\n", nota);
        System.out.println("Conceito: " + conceito);
        System.out.println("Situação: " + situacao);
        System.out.println("\nFeedback baseado no conceito:");
        System.out.println(feedback);
        System.out.println();
    }
}