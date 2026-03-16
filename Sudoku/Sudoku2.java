import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

// Sudoku2 - programa interactivo para manipular quadrículas Sudoku.

public class Sudoku2 {

    // Essas constantes servem apenas para testes internos.

    static final int[][] QUADRICULA_TESTE_CERTA = {
            {5,3,4,6,7,8,9,1,2},
            {6,7,2,1,9,5,3,4,8},
            {1,9,8,3,4,2,5,6,7},
            {8,5,9,7,6,1,4,2,3},
            {4,2,6,8,5,3,7,9,1},
            {7,1,3,9,2,4,8,5,6},
            {9,6,1,5,3,7,2,8,4},
            {2,8,7,4,1,9,6,3,5},
            {3,4,5,2,8,6,1,7,9}
    };

    static final int[][] QUADRICULA_TESTE_ERRADA = {
            {5,3,4,6,7,8,9,1,2},
            {6,7,2,1,9,5,3,4,8},
            {1,9,8,3,4,2,5,6,7},
            {8,5,9,7,6,1,4,2,3},
            {4,2,6,8,5,3,7,9,1},
            {7,1,3,9,2,4,8,5,6},
            {9,6,1,5,3,7,2,8,4},
            {2,8,7,4,1,9,6,3,5},
            {3,4,5,2,8,6,1,7,7} // duplicado 7 -> inválida
    };

    // Entradas do menu guardadas num array para imprimir o menu de forma compacta.
    public static final String[] MENU = {
            "0 - Sair",
            "1 - Permutar dois números",
            "2 - Permutar duas linhas na mesma faixa",
            "3 - Permutar duas colunas na mesma faixa",
            "4 - Permutar faixas horizontais",
            "5 - Permutar faixas verticais",
            "6 - Reflexão horizontal",
            "7 - Reflexão vertical",
            "8 - Introduzir nova quadrícula"
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Construir uma quadrícula inicial utilizando a função construirGQ().
        // "GQ" (Gerador de Quadrícula)

        int[][] grid = construirGQ();
        // Esta variável vai guardar a quadrícula atual do Sudoku.

        int opcao = -1; // variável que guarda a opção escolhida pelo utilizador, inicia com um valor inválido de propósito
        boolean mostrarQuadricula = true; // Controlar se imprimimos a quadrícula antes do menu.

        do {
            if (mostrarQuadricula) {
                System.out.println("Quadricula corrente:");
                imprimirQuadricula(grid); // mostra a quadrícula atual
            }

            printMenu();

            // Assume que o utilizador introduz sempre um inteiro.
            opcao = sc.nextInt();

             switch (opcao) {
                case 1:
                    // Caso 1: permutar dois números em toda a quadrícula.

                    Random rnd = new Random();
                    int a = rnd.nextInt(9) + 1; // escolhe um número aleatório entre 1 e 9.
                    int b; // ainda não dou valor.
                    // garante que b é diferente de a; se for igual, gera outro até ser diferente.
                    do {
                        b = rnd.nextInt(9) + 1;
                    } while (b == a);

                    // aplica a permuta global e recebe nova quadrícula.
                    grid = swapGlobal(grid, a, b);
                    mostrarQuadricula = true;
                    break;


                case 2:
                    // Caso 2: permutar duas linhas que pertençam à mesma faixa de 3 linhas.
                    // A função pedirLinhasMesmaFaixa trata da interação com o utilizador e validação.
                    grid = pedirLinhasMesmaFaixa(sc, grid);
                    mostrarQuadricula = true;
                    break;

                case 3:
                    // Caso 3: permutar duas colunas na mesma faixa vertical de 3 colunas.
                    grid = pedirColunasMesmaFaixa(sc, grid);
                    mostrarQuadricula = true;
                    break;

                case 4:
                    // Caso 4: permutar duas faixas horizontais (cada faixa contém 3 linhas).
                    grid = pedirFaixasHorizontais(sc, grid);
                    mostrarQuadricula = true;
                    break;

                case 5:
                    // Caso 5: permutar duas faixas verticais (cada faixa contém 3 colunas).
                    grid = pedirFaixasVerticais(sc, grid);
                    mostrarQuadricula = true;
                    break;

                case 6:
                    // Caso 6: reflexão horizontal (inverte a ordem das linhas: cima <-> baixo).
                    grid = reflexaoHorizontal(grid);
                    mostrarQuadricula = true;
                    break;

                case 7:
                    // Caso 7: reflexão vertical (inverte a ordem das colunas: esquerda <-> direita).
                    grid = reflexaoVertical(grid);
                    mostrarQuadricula = true;
                    break;

                case 8:
                    // O utilizador introduz manualmente 81 dígitos para definir uma nova quadrícula.
                    sc.nextLine(); // consome o newline que ficou no buffer após nextInt()
                    System.out.println("Introduza os 81 dígitos (valores de 1 a 9).\n");
                    int[][] nova = lerQuadriculaSimples(sc); // lê e constrói a quadrícula.

                    // valida a quadrícula introduzida (verificar linhas/colunas/blocos).
                    if (validarQuadricula(nova)) {
                        grid = nova; // se correcta, substitui a quadrícula actual.
                        System.out.println("Quadrícula correcta.");
                    } else {
                        System.out.println("Quadrícula incorreta.");
                    }
                    mostrarQuadricula = true;
                    break;

                case 9:
                    // Caso 9: executa um teste automático de validação usando as constantes acima.
                    testarValidacao();
                    mostrarQuadricula = true;
                    break;

                case 0:
                    // Opção 0: sair do programa.
                    System.out.println("Saindo...");
                    mostrarQuadricula = true;
                    break;

                default:
                    // Qualquer outra entrada inteira é inválida.
                    System.out.println("Opção inválida.");
                    mostrarQuadricula = true;
                    break;
            }

        } while (opcao != 0);

        sc.close();
    }

    // Imprime o menu com um prompt.
    public static void printMenu() {
        System.out.println();
        System.out.println("Escolha a opção:");
        for (String line : MENU) {
            System.out.println(" " + line);
        }
        System.out.print("> ");
    }

    // construirGQ: gera uma quadrícula base (9x9) usando uma fórmula dada pelo professor Antonio.

    public static int[][] construirGQ() {
        int[][] q = new int[9][9]; // cria array 9x9 de inteiros.
        // Preenche cada célula com a fórmula: (i / 3 + 3 * (i % 3) + j) % 9 + 1
        // Explicação da fórmula:
        // - i percorre as linhas 0..8, j percorre as colunas 0..8.
        // - i/3 dá a faixa de 3 linhas (0,1,2), i%3 dá a posição dentro dessa faixa.
        // - 3 * (i % 3) desloca grupos de 3 valores, + j desloca por coluna.
        // - %9 +1 garante valores no intervalo 1..9 ciclicamente.
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                q[i][j] = (i / 3 + 3 * (i % 3) + j) % 9 + 1;
        return q;
    }

    // imprimirQuadricula: imprime a quadrícula no formato visual típico do Sudoku.
    public static void imprimirQuadricula(int[][] grid) {
        System.out.println("-------------------------");
        for (int i = 0; i < 9; i++) {
            System.out.print("| "); // primeira barra antes de começar a linha
            for (int j = 0; j < 9; j++) {
                System.out.print(grid[i][j] + " ");
                // depois de cada 3 colunas, imprime uma barra vertical adicional.
                if (j % 3 == 2) System.out.print("| ");
            }
            System.out.println();
            // depois de cada 3 linhas, imprime uma linha horizontal.
            if (i % 3 == 2) System.out.println("-------------------------");
        }
    }

    // copyGrid: devolve uma cópia (por linhas) da quadrícula passada para evitar alterar a quadricula original.
    public static int[][] copyGrid(int[][] grid) {
        int[][] out = new int[9][9]; // cria nova quadrícula vazia.
        for (int i = 0; i < 9; i++){
            out[i] = Arrays.copyOf(grid[i], 9); // copia cada linha- Arrays.copyOf(array, tamanho);
        }
        return out;
    }

    // swapGlobal: troca dois números a e b em toda a quadrícula.
    public static int[][] swapGlobal(int[][] grid, int a, int b) {
        int[][] out = copyGrid(grid); // trabalha sobre uma cópia para preservar o original.
        if (a == b) return out; // se iguais, não há nada a fazer.
        // Percorre todas as células e troca os valores.
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (out[i][j] == a) // igual a a
                {
                    out[i][j] = b; // recebe o valor de b
                }
                else if (out[i][j] == b)// igual a b
                {
                    out[i][j] = a; // recebe a
                }
        return out;
    }

    // swapLinhas: troca duas linhas (índices de 0 a 8) inteiras entre si.
    public static int[][] swapLinhas(int[][] grid, int l1, int l2) {
        int[][] out = copyGrid(grid);
        int[] temp = out[l1]; // guarda referência da primeira linha.
        out[l1] = out[l2]; // move a segunda linha para a primeira.
        out[l2] = temp; // coloca a primeira linha (guardada) na posição da segunda.
        return out;
    }

    // swapColunas: troca duas colunas (índices 0 a 8) entre si.
    // Como linhas são arrays separados, aqui é necessário trocar célula a célula para cada linha.
    public static int[][] swapColunas(int[][] grid, int c1, int c2) {
        int[][] out = copyGrid(grid);
        for (int i = 0; i < 9; i++) {
            int temp = out[i][c1]; // guarda valor da coluna c1 na linha i.
            out[i][c1] = out[i][c2]; // escreve valor da coluna c2 em c1.
            out[i][c2] = temp; // escreve valor guardado em c2.
        }
        return out;
    }

    // swapFaixasHorizontais: troca duas faixas horizontais de 3 linhas cada.
    // f1 e f2 são índices de faixa (cada faixa corresponde a 3 linhas consecutivas).
    public static int[][] swapFaixasHorizontais(int[][] grid, int f1, int f2) {
        int[][] out = copyGrid(grid);
        int base1 = f1 * 3, base2 = f2 * 3;         // calcular a linha inicial de cada faixa (0,3,6)

        // para cada uma das 3 linhas dentro da faixa, trocamos as linhas correspondentes
        for (int i = 0; i < 3; i++) {
            out = swapLinhas(out, base1 + i, base2 + i);
        }

        return out; //  devolve a nova grelha com as faixas trocadas
    }

    // swapFaixasVerticais: análogo ao anterior, mas para faixas de colunas.
    public static int[][] swapFaixasVerticais(int[][] grid, int f1, int f2) {
        int[][] out = copyGrid(grid);
        int base1 = f1 * 3, base2 = f2 * 3; // calcular coluna inicial de cada faixa.
        // para cada das 3 colunas dentro da faixa, trocamos colunas correspondentes.
        for (int j = 0; j < 3; j++) out = swapColunas(out, base1 + j, base2 + j);
        return out;
    }

    // reflexaoHorizontal: inverte a ordem das linhas (linha 0 <-> 8, 1 <-> 7, ...).
    // Implementado com chamadas a swapLinhas.
    public static int[][] reflexaoHorizontal(int[][] grid) {
        int[][] out = copyGrid(grid);
        out = swapLinhas(out, 0, 8);
        out = swapLinhas(out, 1, 7);
        out = swapLinhas(out, 2, 6);
        out = swapLinhas(out, 3, 5);
        return out;
    }


    // Reflexão vertical: devolve uma nova quadrícula que é a versão espelhada
    // horizontalmente da quadrícula de entrada (coluna 0 <-> 8, 1 <-> 7, ...).
    public static int[][] reflexaoVertical(int[][] grid) {

        int[][] out = copyGrid(grid);

        //    Percorre todas as linhas da quadrícula.
        //    Para cada linha vamos trocar as colunas da esquerda com as da direita.
        for (int r = 0; r < 9; r++) {

            // Vamos trocar as colunas: a primeira com a última, a segunda com a penúltima, etc.
            // Só precisamos fazer até à metade, porque depois repetiria as trocas
            for (int c = 0; c < 4; c++) {

                // Guarda o valor da coluna atual (esquerda)
                int tmp = out[r][c];

                // Coloca na posição atual o valor da coluna simétrica (direita).
                out[r][c] = out[r][8 - c];

                // Coloca na posição simétrica o valor original (completa a troca)
                out[r][8 - c] = tmp;
            }
        }

        // Devolve a cópia refletida verticalmente.
        return out;
    }


        // Lê do Scanner até recolher 81 dígitos
        public static int[][] lerQuadriculaSimples(Scanner sc) {
        int[][] q = new int[9][9];
        int count = 0;
        // Continuamos até obter 81 dígitos válidos.
        while (count < 81) {

            String token = sc.next(); // lê o próximo token do input.

            // percorre cada carácter do token e aceita apenas '1'..'9'.
            for (int i = 0; i < token.length() && count < 81; i++) {
                char ch = token.charAt(i);
                if (ch >= '1' && ch <= '9') {
                    q[count / 9][count % 9] = ch - '0'; // converte char para inteiro e coloca na célula.
                    // Dividir por 9 da a linha e o resto da as colunas.
                    count++;
                }
            }
        }
        return q;
    }

   // Implementação usa arrays booleanos para marcar números já vistos.
    public static boolean validarQuadricula(int[][] g) {

        // Verificar se existem exatamente 9 linhas
        if (g.length != 9) return false;

        // Verificar se cada linha tem exatamente 9 colunas
        for (int i = 0; i < 9; i++) {
            if (g[i].length != 9)
                return false;
        }

    /*
     rows[r][i]   -> true se o número (i + 1) já apareceu na linha r
     cols[c][i]   -> true se o número (i + 1) já apareceu na coluna c
     blocks[b][i] -> true se o número (i + 1) já apareceu no bloco b
    */

        boolean[][] rows = new boolean[9][9];
        boolean[][] cols = new boolean[9][9];
        boolean[][] blocks = new boolean[9][9];

        // Percorrer todas as células da grelha
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {

                // Obter o valor da célula atual
                int v = g[r][c];

                // Verifica se o valor está no intervalo válido (1 a 9)
                if (v < 1 || v > 9) return false;

                // Converte o valor (1..9) para índice (0..8) para usar nos arrays booleanos
                int idx = v - 1;

            /*
             Calcular o índice do bloco 3x3
             Resultado: valores entre 0 e 8
            */
                int b = (r / 3) * 3 + (c / 3);

                // Verificar se o número já foi visto
                if (rows[r][idx]) return false;   // repetido na linha
                if (cols[c][idx]) return false;   // repetido na coluna
                if (blocks[b][idx]) return false; // repetido no bloco

                // Marcar o número como visto
                rows[r][idx] = true;
                cols[c][idx] = true;
                blocks[b][idx] = true;
            }
        }

        // Se nenhuma regra foi violada, a quadrícula é válida
        return true;
    }


    // testarValidacao: função auxiliar para imprimir os testes.
    public static void testarValidacao() {
        System.out.println("\n=== TESTE ===");
        System.out.println("Quadrícula CERTA:");
        imprimirQuadricula(QUADRICULA_TESTE_CERTA);
        System.out.println("Validação: " + validarQuadricula(QUADRICULA_TESTE_CERTA));
        System.out.println("Quadrícula ERRADA:");
        imprimirQuadricula(QUADRICULA_TESTE_ERRADA);
        System.out.println("Validação: " + validarQuadricula(QUADRICULA_TESTE_ERRADA));
        System.out.println("=== FIM DO TESTE ===\n");
    }

    // Funções de interação: pedem ao utilizador linhas/colunas/faixas e validam as entradas.
    public static int[][] pedirLinhasMesmaFaixa(Scanner sc, int[][] grid) {
        System.out.println("Indique duas linhas (1–9). Devem estar na mesma faixa.");
        System.out.print("> ");
        int l1 = sc.nextInt();
        int l2 = sc.nextInt();

        // valida intervalo 1..9
        if (l1 < 1 || l1 > 9 || l2 < 1 || l2 > 9) {
            System.out.println("Valores inválidos.");
            return grid; // retorna a quadrícula original, sem mudanças
        }

        int a = l1 - 1, b = l2 - 1; // converter para índices que começam em zero

        // verifica se pertencem à mesma faixa de 3 linhas (mesmo valor de a/3)
        if (a / 3 != b / 3) {
            System.out.println("Precisam de estar na mesma faixa.");
            return grid; // retorna a quadrícula original, sem mudanças
        }
        // devolve a quadrícula com as linhas trocadas.
        return swapLinhas(grid, a, b);
    }


    public static int[][] pedirColunasMesmaFaixa(Scanner sc, int[][] grid) {
        System.out.println("Indique duas colunas (1–9). Devem estar na mesma faixa vertical.");
        System.out.print("> ");
        int c1 = sc.nextInt();
        int c2 = sc.nextInt();

        // valida intervalo 1..9
        if (c1 < 1 || c1 > 9 || c2 < 1 || c2 > 9) {
            System.out.println("Valores inválidos.");
            return grid; // retorna a quadrícula original, sem mudanças
        }

        int a = c1 - 1, b = c2 - 1; // índices que começam do zero

        // verifica se pertencem à mesma faixa vertical
        if (a / 3 != b / 3) {
            System.out.println("Precisam de estar na mesma faixa vertical.");
            return grid; // retorna a quadrícula original, sem mudanças
        }
        // devolve a quadrícula com as colunas trocadas.
        return swapColunas(grid, a, b);
    }

    public static int[][] pedirFaixasHorizontais(Scanner sc, int[][] grid) {
        System.out.println("Indique duas faixas horizontais (1–3).");
        System.out.print("> ");

        int f1 = sc.nextInt();
        int f2 = sc.nextInt();

        // valida faixas 1..3 e garante que não são iguais
        if (f1 < 1 || f1 > 3 || f2 < 1 || f2 > 3 || f1 == f2) {
            System.out.println("Faixas inválidas.");
            return grid; // retorna a quadrícula original, sem mudanças
        }

        // chama swapFaixasHorizontais usando índices que começam no zero
        return swapFaixasHorizontais(grid, f1 - 1, f2 - 1);
    }


    public static int[][] pedirFaixasVerticais(Scanner sc, int[][] grid) {
        System.out.println("Indique duas faixas verticais (1–3).");
        System.out.print("> ");

        int f1 = sc.nextInt();
        int f2 = sc.nextInt();

        //    Verifica que f1 e f2 estão no intervalo 1..3 (cada faixa vertical é 1, 2 ou 3). Não podem ser iguais
        if (f1 < 1 || f1 > 3 || f2 < 1 || f2 > 3 || f1 == f2) {
           System.out.println("Faixas inválidas.");
            return grid; // Retorna a matriz original sem alterações
        }

        // Converter as faixas para índices baseados em 0:
        // Se o user digitar f1=1 e f2=3, fica sendo 0 e 2 para a função.
        return swapFaixasVerticais(grid, f1 - 1, f2 - 1);
    }
}
