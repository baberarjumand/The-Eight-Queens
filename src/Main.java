import java.util.Random;

/**
 * This program solves the classic computer science problem: The Eight Queens.
 * You can read more about this problem here:
 * https://en.wikipedia.org/wiki/Eight_queens_puzzle
 *
 * You can also declare custom chess board sizes and the number of queens to place for
 * solutions other than the standard chess board size (8x8).
 *
 * This is the third project for the Java Code Clinic (by Carlos Rivas) on Lynda.com.
 *
 * Author:  Baber Arjumand
 * Date:    Jul 23 2019
 */

public class Main {

    public static void main(String[] args) {
        if(args.length==2) {
            findSolution(args[0], args[1]);
        } else if (args.length==3) {
            findSolution(args[0], args[1], args[2]);
        } else {
            System.out.println("No input parameters detected or invalid input");
            System.out.println("Executing default scenario");
            findSolution("8", "8");
        }
    }

    private static void findSolution(String arg, String arg1, String arg2) {
        int rows = Integer.parseInt(arg);
        int cols = Integer.parseInt(arg1);
        int numberOfQueensToPlace = Integer.parseInt(arg2);
        char[][] chessBoard = initializeChessBoard(rows,cols);
        startPlacingQueens(numberOfQueensToPlace, chessBoard);
        printBoard(chessBoard);
    }

    private static void findSolution(String arg, String arg1) {
        int rowsAndCols = Integer.parseInt(arg);
        int numberOfQueensToPlace = Integer.parseInt(arg1);
        char[][] chessBoard = initializeChessBoard(rowsAndCols);
        startPlacingQueens(numberOfQueensToPlace, chessBoard);
        printBoard(chessBoard);
    }

    private static int checkNumberOfQueensOnBoard(char[][] chessBoard) {
        int numberOfQueens = 0;
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[0].length; j++) {
                if(chessBoard[i][j]=='Q')
                    numberOfQueens++;
            }
        }
//        System.out.println("Number of Queens found: " + numberOfQueens);
        return numberOfQueens;
    }

    private static void startPlacingQueens(int numberOfQueensToBePlaced, char[][] chessBoard) {
        System.out.println("Number of Queens to be placed on board: " + numberOfQueensToBePlaced);
        System.out.print("Starting search for solution... ");
        int numberOfIterations = 0;
        while(true) {
            placeQueenAt(generateRandomNumber(chessBoard.length), generateRandomNumber(chessBoard[0].length), chessBoard);
            numberOfIterations++;
            if(checkNumberOfQueensOnBoard(chessBoard)==numberOfQueensToBePlaced)
                break;
            // chessBoard is reset every 1000 iterations if solution is not found
            if( numberOfIterations%1000==0 && numberOfIterations>0 )
                resetBoard(chessBoard);
            if( numberOfIterations == 100_000_000) {
                System.out.println("Solution for current parameters does not exist");
                return;
            }
        }
        System.out.println("Solution found!");
        System.out.println("Number of Iterations to solution: " + numberOfIterations);
    }

    private static void resetBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private static int generateRandomNumber(int length) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(length);
    }

    private static void placeQueenAt(int row, int col, char[][] chessBoard) {
        if(chessBoard[row][col]==' ' && checkIfCanBeAttacked(row, col, chessBoard))
            chessBoard[row][col] = 'Q';
    }

    private static boolean checkIfCanBeAttacked(int row, int col, char[][] chessBoard) {
        return !checkIfCanBeAttackedHorizontally(row, col, chessBoard) &&
                !checkIfCanBeAttackedVertically(row, col, chessBoard) &&
                !checkIfCanBeAttackedDiagonally(row, col, chessBoard);
    }

    private static boolean checkIfCanBeAttackedDiagonally(int row, int col, char[][] chessBoard) {
        int posX = row;
        int posY = col;
        // check north-east diagonal
        while( --posX>=0 && ++posY<chessBoard[0].length) {
            if(chessBoard[posX][posY]=='Q'){
//                System.out.println("Can be attacked from north-east diagonal");
                return true;
            }
        }
        // check south-east diagonal
        posX = row;
        posY = col;
        while ( ++posX<chessBoard.length && ++posY<chessBoard[0].length) {
            if(chessBoard[posX][posY]=='Q'){
//                System.out.println("Can be attacked from south-east diagonal");
                return true;
            }
        }
        // check south-west diagonal
        posX = row;
        posY = col;
        while( ++posX<chessBoard.length && --posY>=0) {
            if(chessBoard[posX][posY]=='Q'){
//                System.out.println("Can be attacked from south-west diagonal");
                return true;
            }
        }
        // check north-west diagonal
        posX = row;
        posY = col;
        while ( --posX>=0 && --posY>=0) {
            if(chessBoard[posX][posY]=='Q'){
//                System.out.println("Can be attacked from north-west diagonal");
                return true;
            }
        }
        return false;
    }

    private static boolean checkIfCanBeAttackedVertically(int row, int col, char[][] chessBoard) {
        for (int i = row; i < row+chessBoard.length; i++) {
            if(chessBoard[i%chessBoard.length][col]=='Q'){
//                System.out.println("Can be attacked vertically at " + row + ", " + col);
                return true;
            }
        }
        return false;
    }

    private static boolean checkIfCanBeAttackedHorizontally(int row, int col, char[][] chessBoard) {
        for (int i = col; i < col+chessBoard[0].length; i++) {
            if(chessBoard[row][i%chessBoard[0].length]=='Q'){
//                System.out.println("Can be attacked horizontally at " + row + "," + col);
                return true;
            }
        }
        return false;
    }

    private static void printBoard(char[][] chessBoard) {
        System.out.println("Printing board...");
        for (int i = 0; i < chessBoard[0].length; i++) {
            System.out.print("  - ");
        }
        System.out.println();
        for (int i = 0; i < chessBoard.length ; i++) {
            System.out.print("| ");
            for (int j = 0; j < chessBoard[0].length; j++) {
                System.out.print(chessBoard[i][j] + " | ");
            }
            System.out.println();
            for (int k = 0; k < chessBoard[0].length; k++) {
                System.out.print("  - ");
            }
            System.out.println();
        }
    }

    private static char[][] initializeChessBoard(int rowsAndCols) {
        char[][] board = new char[rowsAndCols][rowsAndCols];
        for (int i = 0; i <board.length ; i++) {
            for (int j = 0; j <board[0].length ; j++) {
                board[i][j] = ' ';
            }
        }
        System.out.println("Initializing board of size: " + board.length + "x" + board[0].length);
        return board;
    }

    private static char[][] initializeChessBoard(int rows, int cols) {
        char[][] board = new char[rows][cols];
        for (int i = 0; i <board.length ; i++) {
            for (int j = 0; j <board[0].length ; j++) {
                board[i][j] = ' ';
            }
        }
        System.out.println("Initializing board of size: " + board.length + "x" + board[0].length);
        return board;
    }
}
