import java.util.Arrays;

public class Securin {
    public static int total(int n) {
        return n*n;
    }
    
    public static void display(int[] a) {
        System.out.println("All Possible Combinations:");
        for (int i : a) {
            for (int j : a) {
                System.out.printf("(%d, %d) ", i, j);
            }
            System.out.println();
        }
    }
    
    public static int[][] distribution(int[] a) {
        int[][] size = new int[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                size[i][j] = a[i] + a[j];
            }
        }
        return size;
    }
    
    public static double[] probability(int[][] size, int total) {
        double[] probSum = new double[11];  
        for (int i = 0; i < size.length; i++) {
            for (int j = 0; j < size[i].length; j++) {
                int sum = size[i][j];
                probSum[sum - 2] += 1.0 / total;
            }
        }
        return probSum;
    }
    
    public static void main(String[] args) {
        int[] die = {1, 2, 3, 4, 5, 6};
        int total = total(die.length);
        
        int[][] size = distribution(die);
        double[] probSum = probability(size, total);
        
        System.out.println("\nPart-A Results:");
        System.out.println("Total Combinations: " + total);
        System.out.println("\nDistribution Matrix:");
	display(die);
	System.out.println("\nSum of Combination:");
        for (int[] row : size) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("\nProbability of Sums:");
        for (int i = 0; i < probSum.length; i++) {
            System.out.printf("P(Sum = %d) = %.4f%n", i + 2, probSum[i]);
        }
       
    }
}


