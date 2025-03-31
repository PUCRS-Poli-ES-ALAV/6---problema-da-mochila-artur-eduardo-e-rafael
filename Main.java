

public class Main {

    public static void main(String[] args) {
        int n1 = 4;
        int n2 = 8;
        int n3 = 32;
        convoqueTodos(n1);
        convoqueTodos(n2);
        convoqueTodos(n3);
    }
    public static int fiboRec(int n){
        if(n <= 1){
            return n;
        }
        else{
            return fiboRec(n - 1) + fiboRec(n - 2);
        }
    }
    public static int fibo(int n){
        int[] result = new int [n+1];
        result[0] = 0;
        result[1] = 1;
        for(int i = 2; i < n; i ++){
            result[i] = result[i-1] + result[i-2];
        }
        return result[n - 1];
    }

    public static int memoizedFibo(int []f, int n){
        for(int i = 0; i < n; i ++){
            f[i] = -1;
        }
        return lookUpFibo(f, n);
    }

    public static int lookUpFibo(int[] f, int n){
        if(f[n] >= 0){
            return f[n];
        }
        if(n <= 1){
            f[n] = n;
        }
        else{
            f[n] = lookUpFibo(f, n - 1) + lookUpFibo(f, n - 2);
        }
        return f[n];
    }

    public static void convoqueTodos(int n){
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("FiboRec de " + n + ": " + fiboRec(n));
        System.out.println("Fibo de " + n + ": " + fibo(n));
        System.out.println("Memoized Fibo de " + n + ": " + memoizedFibo(new int[n+1], n));
        System.out.println("---------------------------------------------------------------------------");
    }
}