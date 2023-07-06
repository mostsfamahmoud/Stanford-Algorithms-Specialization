package Week2;

public class StrassenMatrixMultiplication {

    public static void main(String[] args) {
        int[][] A = {{1, 2}, {3, 4}};
        int[][] B = {{5, 6}, {7, 8}};


        int[][] result = multiply(A, B);

        // Print the result matrix
        for (int[] row : result) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }

    public static int[][] multiply(int[][] A, int[][] B) {
        int n = A.length;

        // Create the result matrix
        int[][] C = new int[n][n];

        // Base case: if the matrices are 1x1, multiply directly
        if (n == 1) {
            C[0][0] = A[0][0] * B[0][0];
        } else {
            // Divide the matrices into quadrants
            int half = n / 2;
            int[][] A11 = new int[half][half];
            int[][] A12 = new int[half][half];
            int[][] A21 = new int[half][half];
            int[][] A22 = new int[half][half];
            int[][] B11 = new int[half][half];
            int[][] B12 = new int[half][half];
            int[][] B21 = new int[half][half];
            int[][] B22 = new int[half][half];

            // Initialize the quadrants of A and B
            for (int i = 0; i < half; i++) {
                for (int j = 0; j < half; j++) {
                    A11[i][j] = A[i][j];
                    A12[i][j] = A[i][j + half];
                    A21[i][j] = A[i + half][j];
                    A22[i][j] = A[i + half][j + half];

                    B11[i][j] = B[i][j];
                    B12[i][j] = B[i][j + half];
                    B21[i][j] = B[i + half][j];
                    B22[i][j] = B[i + half][j + half];
                }
            }

            // Recursively calculate the seven products
            int[][] P1 = multiply(A11, subtract(B12, B22));
            int[][] P2 = multiply(add(A11, A12), B22);
            int[][] P3 = multiply(add(A21, A22), B11);
            int[][] P4 = multiply(A22, subtract(B21, B11));
            int[][] P5 = multiply(add(A11, A22), add(B11, B22));
            int[][] P6 = multiply(subtract(A12, A22), add(B21, B22));
            int[][] P7 = multiply(subtract(A11, A21), add(B11, B12));

            // Calculate the quadrants of the result matrix
            int[][] C11 = subtract(add(add(P5, P4), P6), P2); // P5 + P4 + P6 - P2
            int[][] C12 = add(P1, P2);
            int[][] C21 = add(P3, P4);
            int[][] C22 = subtract(add(P5, P1),add(P3, P7)); // P1 + P5 - (P3 + P7)

            // Combine the quadrants into the result matrix
            for (int i = 0; i < half; i++) {
                for (int j = 0; j < half; j++) {
                    C[i][j] = C11[i][j];
                    C[i][j + half] = C12[i][j];
                    C[i + half][j] = C21[i][j];
                    C[i + half][j + half] = C22[i][j];
                }
            }
        }

        return C;
    }

    public static int[][] add(int[][] A, int[][] B)
    {
        int n = A.length;
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] + B[i][j];
            }
        }

        return result;
    }

    public static int[][] subtract(int[][] A, int[][] B) {
        int n = A.length;
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] - B[i][j];
            }
        }

        return result;
    }
}