import java.util.concurrent.*;
import java.util.*;

public class ComplexMathSolver {

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        Future<double[][]> matrixResult = executor.submit(new MatrixMultiplicationTask(new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
            }));
        Future<Double> polynomialResult = executor.submit(new PolynomialEvaluationTask("3*x^3 + 2*x^2 + x + 1", 5));
        Future<double[]> linearSystemResult = executor.submit(new LinearEquationSolverTask(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 10}
            }, new double[]{8, 10, 12}));
        Future<Double> expressionResult = executor.submit(new ExpressionEvaluatorTask("3*x^3 + 2*x^2 + x + 1", 5));

        System.out.println("Matrix Multiplication Result:");
        printMatrix(matrixResult.get());

        System.out.println("\nPolynomial Result: " + polynomialResult.get());
        System.out.println("Linear Equation Result: " + Arrays.toString(linearSystemResult.get()));
        System.out.println("Expression Result: " + expressionResult.get());

        executor.shutdown();
    }

    static class MatrixMultiplicationTask implements Callable<double[][]> {
        private final double[][] A;
        public MatrixMultiplicationTask(double[][] A) {
            this.A = A;
        }

        @Override
        public double[][] call() {
            int n = A.length;
            double[][] B = new double[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    for (int k = 0; k < n; k++)
                        B[i][j] += A[i][k] * A[k][j];
            return B;
        }
    }

    static class PolynomialEvaluationTask implements Callable<Double> {
        private final String expr;
        private final double x;
        public PolynomialEvaluationTask(String expr, double x) {
            this.expr = expr;
            this.x = x;
        }

        @Override
        public Double call() throws Exception {
            return evaluateExpression(expr, x);
        }
    }

    static class LinearEquationSolverTask implements Callable<double[]> {
        private final double[][] A;
        private final double[] b;
        public LinearEquationSolverTask(double[][] A, double[] b) {
            this.A = A;
            this.b = b;
        }

        @Override
        public double[] call() {
            return solveLinearSystem(A, b);
        }

        private double[] solveLinearSystem(double[][] A, double[] b) {
            int n = A.length;
            double[] x = new double[n];
            for (int i = 0; i < n; i++) {
                x[i] = b[i];
                for (int j = 0; j < i; j++) {
                    x[i] -= A[i][j] * x[j];
                }
                x[i] /= A[i][i];
            }
            return x;
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
    }

    static double evaluateExpression(String expr, double x) {
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

