public class Main {

    public static void main(String[] args) {
        // Fibonacci's
        int[] testValues = {4, 8, 16, 32}; 
        int[] testValues2 = {128, 1000, 10000};

        for (int n : testValues) {
            convoqueTodos(n);
        }
        for (int n : testValues2) {
            convoqueTodos(n);
        }

        // MOCHILA
        int[][] itens = {{2, 3}, {3, 4}, {4, 5}, {5, 6}}; 
        int mochilaCapacidade = 10;
        System.out.println("Mochila: " + backPackPD(itens.length, mochilaCapacidade, itens));

        // Distância de Edição
        String s1 = "Casablanca";
        String s2 = "Portentoso";
        System.out.println("Distância de Edição entre \"" + s1 + "\" e \"" + s2 + "\": " + distEdProgDina(s1, s2));
    }

    public static int fiboRec(int n, int[] counter, int[] instructions) {
        instructions[0]++;
        counter[0]++;
        if (n <= 1) {
            instructions[0]++;
            return n;
        } else {
            instructions[0]++;
            return fiboRec(n - 1, counter, instructions) + fiboRec(n - 2, counter, instructions);
        }
    }

    // Fibo (Iterativo)
    public static int fibo(int n, int[] counter, int[] instructions) {
        instructions[0]++;
        int[] result = new int[n + 1];
        result[0] = 0;
        result[1] = 1;
        for (int i = 2; i <= n; i++) {
            result[i] = result[i - 1] + result[i - 2];
            counter[0]++;
            instructions[0]++;
        }
        return result[n];
    }

    public static int memoizedFibo(int[] f, int n, int[] counter, int[] instructions) {
        for (int i = 0; i <= n; i++) {
            f[i] = -1;
        }
        return lookUpFibo(f, n, counter, instructions);
    }

    public static int lookUpFibo(int[] f, int n, int[] counter, int[] instructions) {
        instructions[0]++;
        counter[0]++;
        if (f[n] >= 0) {
            instructions[0]++;
            return f[n];
        }
        if (n <= 1) {
            instructions[0]++;
            f[n] = n;
        } else {
            instructions[0]++;
            f[n] = lookUpFibo(f, n - 1, counter, instructions) + lookUpFibo(f, n - 2, counter, instructions);
        }
        return f[n];
    }

    public static void convoqueTodos(int n) {
        int[] counter = {0};
        int[] instructions = {0};

        long startTime = System.nanoTime();

        System.out.println("-----------------------------------------------------------");

        if (n <= 32) { 
            long startRec = System.nanoTime();
            System.out.println("FiboRec de " + n + ": " + fiboRec(n, counter, instructions) + " - Iterações: " + counter[0] + " - Instruções: " + instructions[0]);
            long endRec = System.nanoTime();
            System.out.println("Tempo FiboRec: " + (endRec - startRec) / 1000000000.0 + " segundos");
        }

        counter[0] = 0;
        instructions[0] = 0;

        // Teste do Fibo Iterativo
        long startFibo = System.nanoTime();
        System.out.println("Fibo de " + n + ": " + fibo(n, counter, instructions) + " - Iterações: " + counter[0] + " - Instruções: " + instructions[0]);
        long endFibo = System.nanoTime();
        System.out.println("Tempo Fibo: " + (endFibo - startFibo) / 1000000000.0 + " segundos");

        // Resetando contadores
        counter[0] = 0;
        instructions[0] = 0;

        // Teste do Memoized Fibo
        long startMemoized = System.nanoTime();
        System.out.println("Memoized Fibo de " + n + ": " + memoizedFibo(new int[n + 1], n, counter, instructions) + " - Iterações: " + counter[0] + " - Instruções: " + instructions[0]);
        long endMemoized = System.nanoTime();
        System.out.println("Tempo Memoized Fibo: " + (endMemoized - startMemoized) / 1000000000.0 + " segundos");

        // Tempo total para essa execução
        long endTime = System.nanoTime();
        System.out.println("Tempo total de execução para n = " + n + ": " + (endTime - startTime) / 1000000000.0 + " segundos");
        System.out.println("-----------------------------------------------------------");
    }



    public static int backPackPD(int N, int C, int[][] itens) {
        int[][] maxTab = new int[N + 1][C + 1];
        int iterations = 0; 
        
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= C; j++) {
                iterations++;
                if (i == 0 || j == 0) {
                    maxTab[i][j] = 0;
                } else if (itens[i - 1][0] <= j) {
                    maxTab[i][j] = Math.max(maxTab[i - 1][j], itens[i - 1][1] + maxTab[i - 1][j - itens[i - 1][0]]);
                } else {
                    maxTab[i][j] = maxTab[i - 1][j];
                }
            }
        }
        System.out.println("Iterações no problema da mochila: " + iterations);
        return maxTab[N][C];
    }

    public static int distEdProgDina(String A, String B) {
        int m = A.length();
        int n = B.length();
        int[][] matriz = new int[m + 1][n + 1];
        int iterations = 0;
        
        for (int i = 0; i <= m; i++) {
            matriz[i][0] = i;  // Remoções
            iterations++;
        }
        for (int j = 0; j <= n; j++) {
            matriz[0][j] = j;  // Inserções
            iterations++;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                iterations++;
                int custoExtra = (A.charAt(i - 1) == B.charAt(j - 1)) ? 0 : 1;
                matriz[i][j] = Math.min(
                    Math.min(matriz[i - 1][j] + 1, matriz[i][j - 1] + 1), 
                    matriz[i - 1][j - 1] + custoExtra);
            }
        }
        System.out.println("Iterações na distância de edição: " + iterations);
        return matriz[m][n];
    }
}
