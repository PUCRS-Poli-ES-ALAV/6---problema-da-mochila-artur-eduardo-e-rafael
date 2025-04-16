import java.util.*;

public class DistanciaEdicao {

    static int iteracoesDC = 0;
    static int iteracoesPD = 0;


public static int distanciaForcaBruta(String s1, String s2) {
    iteracoesDC = 0;
    int m = s1.length();
    int n = s2.length();

    class Estado {
        int i, j, custo;
        Estado(int i, int j, int custo) {
            this.i = i;
            this.j = j;
            this.custo = custo;
        }
    }

    Stack<Estado> pilha = new Stack<>();
    pilha.push(new Estado(m, n, 0));

    // Visitação com custo mínimo
    Map<String, Integer> visitado = new HashMap<>();

    int resultado = Integer.MAX_VALUE;

    while (!pilha.isEmpty()) {
        Estado atual = pilha.pop();
        iteracoesDC++;

        int i = atual.i;
        int j = atual.j;
        int custo = atual.custo;

        String chave = i + "," + j;

        // Poda se já passamos por aqui com custo menor ou igual
        if (visitado.containsKey(chave) && visitado.get(chave) <= custo) {
            continue;
        }

        visitado.put(chave, custo);

        if (i == 0 && j == 0) {
            resultado = Math.min(resultado, custo);
            continue;
        }

        if (i > 0 && j > 0 && s1.charAt(i - 1) == s2.charAt(j - 1)) {
            pilha.push(new Estado(i - 1, j - 1, custo));
        } else {
            if (i > 0 && j > 0) {
                pilha.push(new Estado(i - 1, j - 1, custo + 1)); // substituição
            }
            if (i > 0) {
                pilha.push(new Estado(i - 1, j, custo + 1)); // remoção
            }
            if (j > 0) {
                pilha.push(new Estado(i, j - 1, custo + 1)); // inserção
            }
        }
    }

    return resultado;
}

    
    
    
    public static int distanciaPD(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] matriz = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            matriz[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            matriz[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                iteracoesPD++;
                int custoExtra = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                matriz[i][j] = Math.min(
                    Math.min(
                        matriz[i - 1][j] + 1,
                        matriz[i][j - 1] + 1
                    ),
                    matriz[i - 1][j - 1] + custoExtra
                );
            }
        }

        return matriz[m][n];
    }

    public static void main(String[] args) {
        String s1 = "Casablanca";
        String s2 = "Portentoso";
        String s3 = "Maven, a Yiddish word meaning accumulator of knowledge, began as an attempt to " +
                       "simplify the build processes in the Jakarta Turbine project. There were several" +
                       " projects, each with their own Ant build files, that were all slightly different." +
                       "JARs were checked into CVS. We wanted a standard way to build the projects, a clear " + 
                       "definition of what the project consisted of, an easy way to publish project information" +
                       "and a way to share JARs across several projects. The result is a tool that can now be" +
                       "used for building and managing any Java-based project. We hope that we have created " +
                       "something that will make the day-to-day work of Java developers easier and generally help " +
                       "with the comprehension of any Java-based project.";
        String s4 = "This post is not about deep learning. But it could be might as well. This is the power of " +
                       "kernels. They are universally applicable in any machine learning algorithm. Why you might" +
                       "ask? I am going to try to answer this question in this article." + 
                       "Go to the profile of Marin Vlastelica Pogančić" + 
                       "Marin Vlastelica Pogančić Jun";

        iteracoesDC = 0;
        int distanciaDC = distanciaForcaBruta(s1, s2);
        System.out.println("\n\n=== Divisão e Conquista Duas Palavras ===");
        System.out.println("Distância de edição: " + distanciaDC);
        System.out.println("Iterações: " + iteracoesDC);

        iteracoesPD = 0;
        int distanciaPD = distanciaPD(s1, s2);
        System.out.println("\n=== Programação Dinâmica Duas Palavras ===");
        System.out.println("Distância de edição: " + distanciaPD);
        System.out.println("Iterações: " + iteracoesPD);

        iteracoesDC = 0;
        distanciaDC = distanciaForcaBruta(s3, s4);
        System.out.println("\n=== Divisão e Conquista Dois Parágrafos ===");
        System.out.println("Distância de edição: " + distanciaDC);
        System.out.println("Iterações: " + iteracoesDC);

        iteracoesPD = 0;
        distanciaPD = distanciaPD(s3, s4);
        System.out.println("\n=== Programação Dinâmica Dois Parágrafos ===");
        System.out.println("Distância de edição: " + distanciaPD);
        System.out.println("Iterações: " + iteracoesPD + "\n\n");
    }
}
