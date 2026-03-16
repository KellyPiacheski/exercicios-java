import java.util.Scanner;
import java.util.ArrayList;

public class EX04 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Solicita o texto
        System.out.println("=== Analisador de Texto ===");
        System.out.print("Digite o texto para análise: ");
        String textoOriginal = sc.nextLine();

        // Formata o texto removendo espaços extras nas extremidades
        String textoFormatado = formatarTexto(textoOriginal);

        // Divide o texto em array de palavras
        String[] palavras = obterPalavras(textoFormatado);

        // Exibe todos os resultados
        exibirResultados(textoOriginal, textoFormatado, palavras);

        // Função de pesquisa
        realizarPesquisa(sc, palavras);

        sc.close();
    }

    // ==================== FORMATAÇÃO E DIVISÃO ====================

    public static String formatarTexto(String texto) {
        // trim() remove espaços, tabs e quebras de linha das extremidades
        return texto.trim();
    }

    /**
     * Divide o texto em array de palavras
     * Usa split() para separar por espaços
     * o split é um método que tem o regex como Required.
     * A regular expression defining the separators where the string is split.
     */
    public static String[] obterPalavras(String texto) {
        // Verifica se o texto está vazio
        if (texto.isEmpty()) {
            return new String[0]; // Retorna array vazio
        }
        // \\s+ significa "um ou mais espaços" (ignora espaços extras)
        return texto.split("\\s+");
    }

    // ==================== ANÁLISE DE PALAVRAS ====================

    public static int contarPalavras(String[] palavras) {
        // .length retorna o tamanho do array
        return palavras.length;
    }

    public static String palavrasMaisLongas(String[] palavras) {
        if (palavras.length == 0) return "";

        // 1. Encontra o tamanho máximo
        int tamanhoMax = 0;
        for (int i = 0; i < palavras.length; i++) {
            if (palavras[i].length() > tamanhoMax) {
                tamanhoMax = palavras[i].length();
            }
        }

        // 2. Coleta TODAS as palavras com tamanho máximo
        ArrayList<String> palavrasLongas = new ArrayList<>();
        for (int i = 0; i < palavras.length; i++) {
            if (palavras[i].length() == tamanhoMax) {
                palavrasLongas.add(palavras[i]);
            }
        }

        // 3. Monta string com todas as palavras separadas por vírgula
        String resultado = "";
        for (int i = 0; i < palavrasLongas.size(); i++) {
            resultado += palavrasLongas.get(i);
            if (i < palavrasLongas.size() - 1) {
                resultado += ", ";
            }
        }

        return resultado;
    }

    public static String palavrasMaisCurtas(String[] palavras) {
        if (palavras.length == 0) return "";

        // 1. Encontra o tamanho mínimo
        int tamanhoMin = palavras[0].length();
        for (int i = 1; i < palavras.length; i++) {
            if (palavras[i].length() < tamanhoMin) {
                tamanhoMin = palavras[i].length();
            }
        }

        // 2. Coleta TODAS as palavras com tamanho mínimo
        ArrayList<String> palavrasCurtas = new ArrayList<>();
        for (int i = 0; i < palavras.length; i++) {
            if (palavras[i].length() == tamanhoMin) {
                palavrasCurtas.add(palavras[i]);
            }
        }

        // 3. Monta string com todas as palavras separadas por vírgula
        String resultado = "";
        for (int i = 0; i < palavrasCurtas.size(); i++) {
            resultado += palavrasCurtas.get(i);
            if (i < palavrasCurtas.size() - 1) {
                resultado += ", ";
            }
        }

        return resultado;
    }

    public static int tamanhoMaisLonga(String[] palavras) {
        if (palavras.length == 0) return 0;

        int tamanhoMax = 0;
        for (int i = 0; i < palavras.length; i++) {
            if (palavras[i].length() > tamanhoMax) {
                tamanhoMax = palavras[i].length();
            }
        }
        return tamanhoMax;
    }

    public static int tamanhoMaisCurta(String[] palavras) {
        if (palavras.length == 0) return 0;

        int tamanhoMin = palavras[0].length();
        for (int i = 1; i < palavras.length; i++) {
            if (palavras[i].length() < tamanhoMin) {
                tamanhoMin = palavras[i].length();
            }
        }
        return tamanhoMin;
    }

    public static double mediaComprimento(String[] palavras) {
        if (palavras.length == 0) return 0.0;

        int totalLetras = 0;

        // Ciclo for-each: percorre cada palavra do array
        for (String palavra : palavras) {
            totalLetras += palavra.length();
        }

        // Cast para double garante divisão com decimais
        return (double) totalLetras / palavras.length;
    }

    // ==================== ANÁLISE DE CARACTERES ====================

    public static int contarCaracteres(String texto) {
        return texto.length();
    }

    public static int contarLetras(String texto) {
        int count = 0;

        // Ciclo for: percorre cada caractere da string
        for (int i = 0; i < texto.length(); i++) {
            // charAt(i) pega o caractere na posição i
            // isLetter() verifica se é letra
            if (Character.isLetter(texto.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    public static int contarEspacos(String texto) {
        int count = 0;

        for (int i = 0; i < texto.length(); i++) {
            // Verifica se o caractere é um espaço ' '
            if (texto.charAt(i) == ' ') {
                count++;
            }
        }
        return count;
    }

    public static int contarMaiusculas(String texto) {
        int count = 0;

        for (int i = 0; i < texto.length(); i++) {
            if (Character.isUpperCase(texto.charAt(i))) {
                count++;
            }
        }
        return count;
    }

     public static int contarMinusculas(String texto) {
        int count = 0;

        for (int i = 0; i < texto.length(); i++) {
            if (Character.isLowerCase(texto.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    // ==================== PESQUISA ====================

    public static int procurarPalavra(String[] palavras, String busca) {
        // Ciclo for: percorre todas as palavras
        for (int i = 0; i < palavras.length; i++) {
            // equalsIgnoreCase() compara palavras completas ignorando maiúsculas/minúsculas
            if (palavras[i].equalsIgnoreCase(busca)) {
                return i + 1; // Retorna posição normal, sem ser do index
            }
        }
        return -1; // Não encontrado
    }

    // ==================== EXIBIÇÃO DE RESULTADOS ====================

    public static void exibirResultados(String original, String formatado, String[] palavras) {
        System.out.println("\n=== Resultados da Análise ===");
        System.out.println("Texto original: \"" + original + "\"");
        System.out.println("Texto formatado: \"" + formatado + "\"");

        // SEÇÃO 1: Análise de Palavras
        System.out.println("\n1. Análise de Palavras:");
        System.out.println("   - Quantidade de palavras: " + contarPalavras(palavras));

        // Palavra(s) mais longa(s) - mostra todas se houver empate
        String maisLongas = palavrasMaisLongas(palavras);
        int tamanhoLonga = tamanhoMaisLonga(palavras);
        System.out.println("   - Palavra mais longa: " + maisLongas +
                " (" + tamanhoLonga + " letras)");

        // Palavra(s) mais curta(s) - mostra todas se houver empate
        String maisCurtas = palavrasMaisCurtas(palavras);
        int tamanhoCurta = tamanhoMaisCurta(palavras);

        if (tamanhoCurta == 1) {
            System.out.println("   - Palavra mais curta: " + maisCurtas +
                    " (" + tamanhoCurta + " letra)");
        } else {
            System.out.println("   - Palavra mais curta: " + maisCurtas +
                    " (" + tamanhoCurta + " letras)");
        }

        // printf com %.2f formata double com 2 casas decimais
        System.out.printf("   - Média de comprimento: %.2f letras\n",
                mediaComprimento(palavras));

        // SEÇÃO 2: Análise de Caracteres
        System.out.println("\n2. Análise de Caracteres:");
        System.out.println("   - Total de caracteres: " + contarCaracteres(formatado));
        System.out.println("   - Total de letras: " + contarLetras(formatado));
        System.out.println("   - Espaços: " + contarEspacos(formatado));
        System.out.println("   - Letras maiúsculas: " + contarMaiusculas(formatado));
        System.out.println("   - Letras minúsculas: " + contarMinusculas(formatado));
    }

    public static void realizarPesquisa(Scanner sc, String[] palavras) {
        System.out.println("\n3. Função de Pesquisa:");
        System.out.print("Digite palavra para procurar: ");
        String busca = sc.nextLine();

        // Procura a palavra
        int posicao = procurarPalavra(palavras, busca);

        // Verifica se encontrou
        if (posicao != -1) {
            // Pega a palavra original do array (posição - 1 pois array é 0-based)
            String palavraEncontrada = palavras[posicao - 1];

            System.out.println("Encontrado \"" + palavraEncontrada +
                    "\" (ignorando maiúsc./minúsc.) na posição: " + posicao +
                    " //" + posicao + "ª palavra do texto");
        } else {
            System.out.println("Palavra \"" + busca + "\" não encontrada no texto.");
        }
    }
}