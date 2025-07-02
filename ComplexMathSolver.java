import java.util.concurrent.*;
import java.util.*;

public class ComplexMathSolver {

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        Future<double[][]> matrixResult = executor.submit(new MatrixMultiplicationTask());
        Future<Double> polynomialResult = executor.submit(new PolynomialEvaluationTask(5)); // x = 5
        Future<double[]> linearSystemResult = executor.submit(new LinearEquationSolverTask());
        Future<Double> expressionResult = executor.submit(new ExpressionEvaluatorTask("3*x^2 + 2*x + 1", 5)); // x = 5

        System.out.println("Matrix Multiplication Result:");
        printMatrix(matrixResult.get());

        System.out.println("\nPolynomial Result: " + polynomialResult.get());
        System.out.println("Linear Equation Result: " + Arrays.toString(linearSystemResult.get()));
        System.out.println("Expression Result: " + expressionResult.get());

        executor.shutdown();
    }

    static class MatrixMultiplicationTask implements Callable<double[][]> {
        @Override
        public double[][] call() {
            double[][] A = {
                    {1, 2, 3},
                    {4, 5, 6},
                    {7, 8, 9}
            };
            double[][] B = {
                    {9, 8, 7},
                    {6, 5, 4},
                    {3, 2, 1}
            };
            int n = A.length;
            double[][] C = new double[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    for (int k = 0; k < n; k++)
                        C[i][j] += A[i][k] * B[k][j];
            return C;
        }
    }

    static class PolynomialEvaluationTask implements Callable<Double> {
        private final double x;
        public PolynomialEvaluationTask(double x) {
            this.x = x;
        }

        @Override
        public Double call() {
            // 3x^2 + 2x + 1
            return 3 * x * x + 2 * x + 1;
        }
    }

    static class LinearEquationSolverTask implements Callable<double[]> {
        @Override
        public double[] call() {
            // Solving system: A*x = b
            double[][] A = {
                    {2, 3},
                    {1, 2}
            };
            double[] b = {8, 5};
            return solve2x2LinearSystem(A, b);
        }

        private double[] solve2x2LinearSystem(double[][] A, double[] b) {
            double det = A[0][0]*A[1][1] - A[0][1]*A[1][0];
            if (det == 0) throw new ArithmeticException("Matrix is singular");
            double x = (b[0]*A[1][1] - b[1]*A[0][1]) / det;
            double y = (A[0][0]*b[1] - A[1][0]*b[0]) / det;
            return new double[]{x, y};
        }
    }

    static class ExpressionEvaluatorTask implements Callable<Double> {
        private final String expr;
        private final double x;
        public ExpressionEvaluatorTask(String expr, double x) {
            this.expr = expr;
            this.x = x;
        }

        @Override
        public Double call() throws Exception {
            return evaluateExpression(expr, x);
        }

        private double evaluateExpression(String expr, double x) {
            expr = expr.replaceAll("x", Double.toString(x))
                    .replaceAll("\\^", "**"); // Replace ^ with ** for exponentiation
            // Simplified parser using built-in JavaScript engine
            try {
                return (double) ((javax.script.ScriptEngineManager)
                        new javax.script.ScriptEngineManager()
                ).getEngineByName("JavaScript").eval(expr.replaceAll("\\*\\*", "^"));
            } catch (Exception e) {
                throw new RuntimeException("Failed to evaluate expression: " + expr, e);
            }
        }
    }

    static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double val : row) {
                System.out.printf("%8.2f", val);
            }
            System.out.println();
        }
    }
}
