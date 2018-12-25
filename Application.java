import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Application {

    public static void main(String args[]) {

        initialize();

        Position p = new Position();
        GameTree tree = new GameTree();
        Node node = new Node(null);

        int evaluation = 0;

        boolean isHumanPlayer = false;

        node.setNodeType(Node.NodeType.MAX_NODE);

        int board = Board.getSurfer(node.isWhite());

        // Initialize white move
        Move whiteMove = new Move();
        whiteMove.setTo(board);
        node.setMove(whiteMove);

        Node child = new Node(node);

        board = Board.getSurfer(child.isWhite());

        // Initialize black move
        Move blackMove = new Move();
        blackMove.setTo(board);
        child.setMove(blackMove);

        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to the game of amazons");

        System.out.println("Set Heuristic(Killer/History): (K/H) ");
        String heuristic = scan.next();
        if ("K".equals(heuristic)) {
            tree.setHeuristic(GameTree.Heuristic.Killer_Heuristic);
        } else {
            tree.setHeuristic(GameTree.Heuristic.History_Heuristic);
        }

        while(true) {

            Board.printBoard(Board.getSurfer(true), Board.getSurfer(false), Board.getAmazonsBoard(), false);
            System.out.println("Please Enter a valid move in format: <From> <To> <Depth_Charge>");

            String input = scan.nextLine();

            String[] play = input.split(" ");

            int start = p.getPosition(play[0].trim());

            int end = p.getPosition(play[1].trim());

            int depthCharge = p.getPosition(play[2].trim());

            Node player = new Node(child);

            if (Move.isPossibleMove(start, end, true)) {
                if (Move.isPossibleMove(end, depthCharge, true)) {
                    Move move = new Move();
                    move.setFrom(start);
                    move.setTo(end);
                    move.setDepth_charge(depthCharge);
                    player.setMove(move);
                    Board.makeMove(player);
                }
            } else continue;

            if (endOfgame(Board.getSurfer(player.isWhite()))) {
                System.out.println("Game Ended");
                return;
            }
            evaluation = tree.playAIPlayer(player);
            System.out.println("Evaluation value of AI player: " + evaluation);
        }
    }

    /**
     * Initialize board, positions and masks
     */
    private static void initialize() {
        Board.initiateBoard();

        Position.initializePositions();

        Move.initializeMasks();
    }

    /**
     * Tells if it is end of game for player
     * @param player player board integer representation
     * @return True or False
     */
    private static boolean endOfgame(int player) {
        int board = Board.getAmazonsBoard();
        boolean surfer1 = true, surfer2 = true;

        boolean[] surfers = {surfer1, surfer2};
        for(int j=0; j<2; j++) {
            boolean surfer = surfers[j];
            int positionOfSurfer = Move.getBitPosition(player);
            player = Move.setBit(player, positionOfSurfer, 0);
            ArrayList<Mask> masks = Move.positionList.get(positionOfSurfer);
            for (int i = 0; i < masks.size(); i++) {
                if (masks.get(i).maskPosition == positionOfSurfer - 1 || masks.get(i).maskPosition == positionOfSurfer + 1 ||
                        masks.get(i).maskPosition == positionOfSurfer - 5 || masks.get(i).maskPosition == positionOfSurfer + 5 ||
                        masks.get(i).maskPosition == positionOfSurfer - 4 || masks.get(i).maskPosition == positionOfSurfer + 4 ||
                        masks.get(i).maskPosition == positionOfSurfer - 6 || masks.get(i).maskPosition == positionOfSurfer + 6) {
                    if (!((masks.get(i).maskValue & board) > 0)) {
                        surfer = false;
                    };
                }
            }
            surfers[j] = surfer;
        }

        return surfers[0] && surfers[1];

    }

}
