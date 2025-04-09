import java.util.Arrays;
import java.util.Comparator;

public class App {
    static int iteracoes;
    static int instrucoes;

    // Versão 1: Programação Dinâmica
    public static int mochilaPD(int N, int C, int[][] itens) {
        iteracoes = 0;
        instrucoes = 0;
        int[][] maxTab = new int[N + 1][C + 1];
        long tempoInicio = System.nanoTime();

        for (int i = 1; i <= N; i++) {
            int peso_i = itens[i][0];
            int valor_i = itens[i][1];
            for (int j = 0; j <= C; j++) {
                iteracoes++;
                if (peso_i > j) {
                    maxTab[i][j] = maxTab[i - 1][j];
                    instrucoes++;
                } else {
                    maxTab[i][j] = Math.max(maxTab[i - 1][j], valor_i + maxTab[i - 1][j - peso_i]);
                    instrucoes += 3;
                }
            }
        }

        long tempoFim = System.nanoTime();
        printResumo("Programação Dinâmica", C, itens, N, maxTab[N][C], tempoInicio, tempoFim);
        return maxTab[N][C];
    }

    // Versão 2: Força Bruta (gera todas as combinações possíveis)
    public static int mochilaForcaBruta(int N, int C, int[][] itens) {
        iteracoes = 0;
        instrucoes = 0;
        long tempoInicio = System.nanoTime();
        int melhorValor = forcaBruta(itens, N, C, 0);
        long tempoFim = System.nanoTime();
        printResumo("Força Bruta", C, itens, N, melhorValor, tempoInicio, tempoFim);
        return melhorValor;
    }

    private static int forcaBruta(int[][] itens, int n, int capacidade, int i) {
        iteracoes++;
        if (i == n) return 0;
        instrucoes++;
        int[] item = itens[i + 1];
        if (item[0] > capacidade) {
            return forcaBruta(itens, n, capacidade, i + 1);
        } else {
            int inclui = item[1] + forcaBruta(itens, n, capacidade - item[0], i + 1);
            int exclui = forcaBruta(itens, n, capacidade, i + 1);
            return Math.max(inclui, exclui);
        }
    }

    // Versão 3: Abordagem Recursiva (sem memorização)
    public static int mochilaRecursiva(int N, int C, int[][] itens) {
        iteracoes = 0;
        instrucoes = 0;
        long tempoInicio = System.nanoTime();
        int resultado = recursiva(itens, N, C);
        long tempoFim = System.nanoTime();
        printResumo("Recursiva", C, itens, N, resultado, tempoInicio, tempoFim);
        return resultado;
    }

    private static int recursiva(int[][] itens, int i, int capacidade) {
        iteracoes++;
        if (i == 0 || capacidade == 0) return 0;
        instrucoes++;
        if (itens[i][0] > capacidade) {
            return recursiva(itens, i - 1, capacidade);
        } else {
            int inclui = itens[i][1] + recursiva(itens, i - 1, capacidade - itens[i][0]);
            int exclui = recursiva(itens, i - 1, capacidade);
            return Math.max(inclui, exclui);
        }
    }

    // Versão 4: Algoritmo Guloso (baseado na razão valor/peso)
    public static int mochilaGulosa(int N, int C, int[][] itens) {
        iteracoes = 0;
        instrucoes = 0;
        long tempoInicio = System.nanoTime();

        Double[][] razoes = new Double[N][3];
        for (int i = 1; i <= N; i++) {
            razoes[i - 1][0] = (double) itens[i][0];
            razoes[i - 1][1] = (double) itens[i][1];
            razoes[i - 1][2] = itens[i][1] / (double) itens[i][0];
        }

        Arrays.sort(razoes, Comparator.comparingDouble((Double[] o) -> -o[2]));

        int pesoTotal = 0;
        int valorTotal = 0;

        for (int i = 0; i < N; i++) {
            iteracoes++;
            if (pesoTotal + razoes[i][0] <= C) {
                pesoTotal += razoes[i][0];
                valorTotal += razoes[i][1];
                instrucoes += 3;
            }
        }

        long tempoFim = System.nanoTime();
        printResumo("Gulosa", C, itens, N, valorTotal, tempoInicio, tempoFim);
        return valorTotal;
    }

    public static void printResumo(String abordagem, int C, int[][] itens, int N, int resultado, long tempoInicio, long tempoFim) {
        double tempoMs = (tempoFim - tempoInicio) / 1e6;
        System.out.println("\nAbordagem: " + abordagem);
        System.out.println("Capacidade da mochila: " + C);

        System.out.print("Pesos: ");
        for (int i = 1; i <= N; i++) {
            System.out.print(itens[i][0] + " ");
        }
        System.out.println();

        System.out.print("Valores: ");
        for (int i = 1; i <= N; i++) {
            System.out.print(itens[i][1] + " ");
        }
        System.out.println();

        System.out.println("Número de iterações: " + iteracoes);
        System.out.println("Número de instruções: " + instrucoes);
        System.out.println("Tempo de execução: " + tempoMs + " ms");
        System.out.println("Valor máximo obtido: " + resultado);
    }

    public static void main(String[] args) {
        int N = 6; // Número de itens
        int C = 190; // Capacidade da mochila
        int[][] itens = { {0, 0}, {56, 50}, {59, 50}, {80, 64}, {64, 46}, {75, 50}, {17, 05} }; // Índice 0 é nulo

        mochilaPD(N, C, itens);
        mochilaForcaBruta(N, C, itens);
        mochilaRecursiva(N, C, itens);
        mochilaGulosa(N, C, itens);
    }
}
