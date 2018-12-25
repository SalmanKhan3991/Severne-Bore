public class Board {

    private final static char BLACK_SURFER_STRING = '0';

    private final static char WHITE_SURFER_STRING = '#';

    private final static char DEPTH_CHARGER_STRING = 'x';

    private static int WHITE_SURFER;

    private static int BLACK_SURFER;

    private static int BOARD;

    public static void initiateBoard() {
        char board[][] = {
                {' ', '0', ' ', ' ', ' '},
                {'0', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', '#'},
                {' ', ' ', ' ', '#', ' '}};

        arrayToBitboards(board);
    }

    public static int getSurfer(boolean white) {
        if (white) {
            return WHITE_SURFER;
        }
        return BLACK_SURFER;
    }

    public static void setSurfer(int value, boolean white) {
        if(white) {
            WHITE_SURFER = value;
        } else {
            BLACK_SURFER = value;
        }
    }

    public static int getAmazonsBoard() {
        return BOARD;
    }

    public static void setAmazonsBoard(int board) {
        BOARD = board;
    }

    private static void arrayToBitboards(char[][] amazonsBoard) {
        String Binary;
        int whiteSurfer = 0, blackSurfer = 0, board = 0;
        int temp;
        for (int i = 0; i < 25; i++) {
            Binary = "0000000000000000000000000";
            temp = 0;
            Binary = Binary.substring(i + 1) + "1" + Binary.substring(0, i);
            char position = amazonsBoard[i / 5][i % 5];
            if (position == WHITE_SURFER_STRING) {
                temp = convertStringToBitboard(Binary);
                whiteSurfer += temp;
                board += temp;
            } else if (position == BLACK_SURFER_STRING) {
                temp += convertStringToBitboard(Binary);
                blackSurfer += temp;
                board += temp;
            } else if (position == DEPTH_CHARGER_STRING) {
                board += convertStringToBitboard(Binary);
            }
        }

        WHITE_SURFER = whiteSurfer;
        BLACK_SURFER = blackSurfer;
        BOARD = board;

        System.out.println("Forward board");
        printBoard(whiteSurfer, blackSurfer, board, false);

        System.out.println();
        System.out.println("Reverse board");
        printBoard(whiteSurfer, blackSurfer, board, true);
    }

    private static int convertStringToBitboard(String Binary) {
        if (Binary.charAt(0) == '0') {//not going to be a negative number
            return Integer.parseInt(Binary, 2);
        } else {
            return Integer.parseInt("1" + Binary.substring(2), 2) * 2;
        }
    }

    public static void printBoard(int whiteSurfer, int blackSurfer, int board, boolean player2) {

        StringBuilder Binary = new StringBuilder("-------------------------");
        for (int i = 0; i < 25; i++) {

            if (((board >> i) & 1) == 1) {
                if (((whiteSurfer >> i) & 1) == 1) {
                    Binary.setCharAt(i, WHITE_SURFER_STRING);
                } else if (((blackSurfer >> i) & 1) == 1) {
                    Binary.setCharAt(i, BLACK_SURFER_STRING);
                } else {
                    Binary.setCharAt(i, DEPTH_CHARGER_STRING);
                }
            }
        }

        if (player2) {
            Binary = Binary.reverse();
        }

        int count = 0;
        int rowValue = 25;

        System.out.println("  ABCDE");
        for (int i = 0; i < 25; i++) {
            count++;
            if (rowValue % 5 == 0) {
                if(player2) {
                    System.out.print(((25 - rowValue) / 5)+1 + " ");
                } else {
                    System.out.print(rowValue / 5 + " ");
                }
            }
            System.out.print(Binary.charAt(i));
            if (count % 5 == 0) {
                System.out.println();
            }

            rowValue--;

        }

        System.out.println("  ABCDE");
    }

    public static void makeMove(Node player) {
        int playerBoard = Board.getSurfer(player.isWhite());
        Move move = player.getMove();
        int to = move.getTo();
        int from = move.getFrom();
        int depth_charge = move.getDepth_charge();

        int fullBoard = Board.getAmazonsBoard();

        int temp = to & from;
        playerBoard = playerBoard ^ temp;
        Board.setSurfer(playerBoard, player.isWhite());

        fullBoard = fullBoard | depth_charge;
        Board.setAmazonsBoard(fullBoard);
    }
}