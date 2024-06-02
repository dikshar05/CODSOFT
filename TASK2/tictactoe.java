import java.util.Scanner;

public class tictactoe {

    // Function to print the Tic-Tac-Toe board
    public static void printBoard(String[][] board) {
        for (String[] row : board) {
            System.out.println(String.join(" | ", row));
            System.out.println("-".repeat(5));
        }
    }

    // Function to check if someone has won
    public static boolean checkWinner(String[][] board, String player) {
        for (String[] row : board) {
            if (row[0].equals(player) && row[1].equals(player) && row[2].equals(player)) {
                return true;
            }
        }
        for (int col = 0; col < 3; col++) {
            if (board[0][col].equals(player) && board[1][col].equals(player) && board[2][col].equals(player)) {
                return true;
            }
        }
        if ((board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) ||
                (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player))) {
            return true;
        }
        return false;
    }

    // Function to check if the board is full
    public static boolean isBoardFull(String[][] board) {
        for (String[] row : board) {
            for (String cell : row) {
                if (cell.equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    // Function for the AI move using Minimax algorithm
    public static int[] aiMove(String[][] board, int depth, boolean maximizingPlayer) {
        if (checkWinner(board, "X")) {
            return new int[]{-10 + depth, -1, -1};
        } else if (checkWinner(board, "O")) {
            return new int[]{10 - depth, -1, -1};
        } else if (isBoardFull(board)) {
            return new int[]{0, -1, -1};
        }

        if (maximizingPlayer) {
            int bestScore = Integer.MIN_VALUE;
            int[] bestMove = new int[]{-1, -1};
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].equals("")) {
                        board[i][j] = "O";
                        int[] scoreAndMove = aiMove(board, depth + 1, false);
                        board[i][j] = "";
                        if (scoreAndMove[0] > bestScore) {
                            bestScore = scoreAndMove[0];
                            bestMove[0] = i;
                            bestMove[1] = j;
                        }
                    }
                }
            }
            return new int[]{bestScore, bestMove[0], bestMove[1]};
        } else {
            int bestScore = Integer.MAX_VALUE;
            int[] bestMove = new int[]{-1, -1};
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].equals("")) {
                        board[i][j] = "X";
                        int[] scoreAndMove = aiMove(board, depth + 1, true);
                        board[i][j] = "";
                        if (scoreAndMove[0] < bestScore) {
                            bestScore = scoreAndMove[0];
                            bestMove[0] = i;
                            bestMove[1] = j;
                        }
                    }
                }
            }
            return new int[]{bestScore, bestMove[0], bestMove[1]};
        }
    }

    // Function to play the game
    public static void playGame() {
        String[][] board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
            }
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Tic-Tac-Toe!");
        printBoard(board);

        while (true) {
            // Human player's move
            while (true) {
                System.out.print("Enter the row (0, 1, 2): ");
                int row = scanner.nextInt();
                System.out.print("Enter the column (0, 1, 2): ");
                int col = scanner.nextInt();
                if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col].equals("")) {
                    board[row][col] = "X";
                    break;
                } else {
                    System.out.println("Invalid move! Try again.");
                }
            }
            printBoard(board);

            // Check if human player wins
            if (checkWinner(board, "X")) {
                System.out.println("Congratulations! You win!");
                break;
            }
            // Check if it's a tie
            if (isBoardFull(board)) {
                System.out.println("It's a tie!");
                break;
            }

            // AI player's move
            int[] scoreAndMove = aiMove(board, 0, true);
            board[scoreAndMove[1]][scoreAndMove[2]] = "O";
            System.out.println("AI's move:");
            printBoard(board);

            // Check if AI wins
            if (checkWinner(board, "O")) {
                System.out.println("AI wins! Better luck next time.");
                break;
            }
            // Check if it's a tie
            if (isBoardFull(board)) {
                System.out.println("It's a tie!");
                break;
            }
        }
    }

    public static void main(String[] args) {
        playGame();
    }
}
