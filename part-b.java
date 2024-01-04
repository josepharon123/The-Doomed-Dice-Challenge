import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class part-b {

    public static void printOrderly(List<Integer> array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static int sumOfArray(List<Integer> array) {
        int ans = 0;
        for (int element : array) {
            ans += element;
        }
        return ans;
    }

    public static int[][] sumDistribution(List<Integer> dieA, List<Integer> dieB) {
        int[][] sumArray = new int[dieA.size()][dieB.size()];
        for (int a = 0; a < dieA.size(); a++) {
            for (int b = 0; b < dieB.size(); b++) {
                sumArray[a][b] = dieA.get(a) + dieB.get(b);
            }
        }
        return sumArray;
    }

    public static double singleProbability(List<Integer> dieA, List<Integer> dieB, int sum) {
        int[][] sumArray = sumDistribution(dieA, dieB);
        int count = 0;
        for (int[] rows : sumArray) {
            for (int column : rows) {
                if (column == sum) {
                    count++;
                }
            }
        }
        return (double) count / 36;
    }

    public static List<Double> allProbability(List<Integer> dieA, List<Integer> dieB) {
        List<Double> probabilities = new ArrayList<>();
        for (int i = 2; i <= 12; i++) {
            probabilities.add(roundTo4DecimalPlaces(singleProbability(dieA, dieB, i)));
        }
        return probabilities;
    }

    public static Set<List<Integer>> possibilitiesCalc(List<Integer> curr, int freeSpace, List<Integer> inputValues, Set<List<Integer>> possibilities, List<Integer> fixedValues, boolean repetition) {
        if (freeSpace == 0) {
            curr.sort(null);
            possibilities.add(new ArrayList<>(curr));
            return possibilities;
        }

        if (repetition) {
            for (int inputValue : inputValues) {
                possibilitiesCalc(new ArrayList<>(curr) {{
                    add(inputValue);
                }}, freeSpace - 1, inputValues, possibilities, fixedValues, true);
            }
        } else {
            for (int i = 0; i < inputValues.size(); i++) {
                final int inputValue = inputValues.get(i);
                List<Integer> remainingInputValues = new ArrayList<>(inputValues.subList(0, i));
                remainingInputValues.addAll(inputValues.subList(i + 1, inputValues.size()));
                possibilitiesCalc(new ArrayList<>(curr) {{
                    add(inputValue);
                }}, freeSpace - 1, remainingInputValues, possibilities, fixedValues, false);
            }
        }
        return possibilities;
    }

    public static List<Integer> transform(List<Integer> dieA, List<Integer> dieB) {
        List<Double> originalProbabilities = allProbability(new ArrayList<>(dieA), new ArrayList<>(dieB));

        List<Integer> fixedValuesA = Arrays.asList(1, 4);
        List<Integer> inputValuesA = Arrays.asList(1, 2, 3, 4);
        int freeSpaceA = 4;
        Set<List<Integer>> possibilitiesA = new HashSet<>();
        List<List<Integer>> newDieAPossibility = new ArrayList<>(possibilitiesCalc(new ArrayList<>(fixedValuesA), freeSpaceA, inputValuesA, possibilitiesA, fixedValuesA, true));

        List<Integer> fixedValuesB = Arrays.asList(1, 8);
        List<Integer> inputValuesB = Arrays.asList(2, 3, 4, 5, 6, 7);
        int freeSpaceB = 4;
        Set<List<Integer>> possibilitiesB = new HashSet<>();
        List<List<Integer>> newDieBPossibility = new ArrayList<>(possibilitiesCalc(new ArrayList<>(fixedValuesB), freeSpaceB, inputValuesB, possibilitiesB, fixedValuesB, false));

        for (List<Integer> a : newDieAPossibility) {
            for (List<Integer> b : newDieBPossibility) {
                if (sumOfArray(a) + sumOfArray(b) == 42) {
                    List<Double> newSumPossibility = allProbability(new ArrayList<>(a), new ArrayList<>(b));
                    if (originalProbabilities.equals(newSumPossibility)) {
                        List<Integer> result = new ArrayList<>(a);
                        result.addAll(b);
                        return result;
                    }
                }
            }
        }

        // Handle the case where no matching combination is found
        throw new IllegalArgumentException("No matching combination found");
    }

    public static double roundTo4DecimalPlaces(double value) {
        return Math.round(value * 10000.0) / 10000.0;
    }

    public static void main(String[] args) {
        List<Integer> dieA = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> dieB = Arrays.asList(1, 2, 3, 4, 5, 6);

        try {
            System.out.println("New Dice are:");
            printOrderly(transform(dieA, dieB));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}