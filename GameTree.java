import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class GameTree {

    private final int SURFER_IN_CORNER_CELL = 30;
    private final int SURFER_IN_EDGE_CELL = 25;
    private final int SURFER_NEXT_TO_OTHER_SURFER = 20;
    private final int SURFER_NEXT_TO_DC = 15;
    private final int SURFER_ABLE_TO_MOVE_BETWEEN_TWO_DC = 10;
    private final int SURFER_UNABLE_TO_MOVE_NEXT_TO_PARTNER = 5;
    private final int ACHIEVABLE = -1000;
    private final int HOPE = 1000;
    private final int DEPTH = 4;


    @Getter
    @Setter
    private Node root;

    @Getter
    @Setter
    private int depth;

    @Getter
    @Setter
    private Heuristic heuristic;

    private int evaluationValue;

    private ArrayList<Move> killerMoves = new ArrayList<Move>();
    private ArrayList<Move> historyMoves = new ArrayList<Move>();

    private static int[] evaluationCounter = new int[6];

    @Getter
    @Setter
    Node playNode;

    public enum Heuristic{
        History_Heuristic,
        Killer_Heuristic
    }

    GameTree() {
        root = null;
        depth = 0;
        evaluationValue = 0;
        heuristic = Heuristic.Killer_Heuristic;
    }

    public int playAIPlayer(Node root) {
        evaluationValue = alphaBeta(root, DEPTH, ACHIEVABLE, HOPE, root.isWhite());
        Board.makeMove(playNode);
        return evaluationValue;
    }

    /**
     * Alpha beta search with option to switch between killer and history heuristic
     * @param root
     * @param depth
     * @param alpha
     * @param beta
     * @param maximizingPlayer
     * @return
     */
    private int alphaBeta(Node root, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if(depth == 0 || root.getChildrenCount() == 0) {
            return evaluate(root);
        } else {

            if(this.getHeuristic() == Heuristic.Killer_Heuristic) {
                Move killMove = killerMoves.get(depth);
                Node killNode = new Node(root);
                killNode.setMove(killMove);
            } else if (this.getHeuristic() == Heuristic.History_Heuristic) {
                for(int i=0; i< historyMoves.size(); i++) {
                    Move historyMove = historyMoves.get(i);
                    Node historyNode = new Node(root);
                    historyNode.setMove(historyMove);
                }
            }

            if(maximizingPlayer) {
                root.setNodeType(Node.NodeType.MAX_NODE);
                ArrayList<Node> children = getAllChildren(root);
                for(int i=0; i<children.size(); i++) {
                    alpha = Math.max(alpha, alphaBeta(root.getChildren().get(i), depth-1, alpha, beta, false));
                    if( beta <= alpha) {
                        if(this.getHeuristic() == Heuristic.Killer_Heuristic) {
                            Node node = root.getChildren().get(i);
                            Move move = node.getMove();
                            killerMoves.add(depth, move);
                        } else if(this.getHeuristic() == Heuristic.History_Heuristic) {
                            Node node = root.getChildren().get(i);
                            Move move = node.getMove();
                            historyMoves.add(move);
                        }
                        playNode = root.getChildren().get(i);
                        break;
                    }
                }
                return alpha;
            } else {
                root.setNodeType(Node.NodeType.MIN_NODE);
                ArrayList<Node> children = getAllChildren(root);
                for(int i=0; i<children.size(); i++) {
                    beta = Math.min(beta, alphaBeta(root.getChildren().get(i), depth-1, alpha, beta,true));
                    if (beta <= alpha) {
                        if(this.getHeuristic() == Heuristic.Killer_Heuristic) {
                            Node node = root.getChildren().get(i);
                            Move move = node.getMove();
                            killerMoves.add(depth, move);
                        } else if(this.getHeuristic() == Heuristic.History_Heuristic) {
                            Node node = root.getChildren().get(i);
                            Move move = node.getMove();
                            historyMoves.add(move);
                        }
                        playNode = root.getChildren().get(i);
                        break;
                    }
                }
                return beta;
            }
        }
    }

    private int evaluate(Node root) {
        int from=0, to=0, depthCharge=0;
        from = root.getMove().getFrom();
        to = root.getMove().getTo();
        depthCharge = root.getMove().getDepth_charge();
        int corner1 = 0, corner2 = 0, corner3 = 0, corner4 = 0;

        corner1 = (int)Math.pow(2,24);
        corner2 = (int)Math.pow(2,20);
        corner3 = (int)Math.pow(2,4);
        corner4 = (int)Math.pow(2,0);

        int staticEvaluation = 0;

        if((to & corner1) > 0 || (to & corner2) > 0 || (to & corner3) > 0 || (to & corner4) > 0) {
            evaluationCounter[0] ++;
            staticEvaluation += SURFER_IN_CORNER_CELL;
        }

        int position = Move.getBitPosition(to);
        if(position%5 == 0 || position<=4 || position>=20 || position%5 == 4) {
            evaluationCounter[1] ++;
            staticEvaluation += SURFER_IN_EDGE_CELL;
        }

        int board = Board.getAmazonsBoard();
        int blackBoard, whiteBoard;

        whiteBoard = Board.getSurfer(root.isWhite());
        blackBoard = Board.getSurfer(!root.isWhite());

        int combinationBoard = whiteBoard ^ blackBoard;

        HashMap<Integer, Boolean> positions = new HashMap<Integer, Boolean>();

        int unsettingBoard = combinationBoard;
        for(int i=0; i<4;i++) {
            int bitPosition = Move.getBitPosition(unsettingBoard);
            if((unsettingBoard & whiteBoard) > 0) {
                positions.put(bitPosition, true);
            } else if ((unsettingBoard & blackBoard) > 0) {
                positions.put(bitPosition, false);
            }
            unsettingBoard = Move.setBit(unsettingBoard, bitPosition, 0);
        }

        Iterator itr = positions.entrySet().iterator();

        // Some more evaluation checks pending



        return staticEvaluation;
    }

    /**
     * Adds nodes for all possible moves from root
     * @param root
     * @return list of children
     */
    private ArrayList<Node> getAllChildren(Node root) {
        ArrayList<Move> moves = Move.possibleMoves(root.getMove().getTo());
        ArrayList<Node> children = new ArrayList<Node>();
        for(int i=0; i< moves.size(); i++) {
            Node node = new Node(root);
            Move move = new Move();
            move.setFrom(root.getMove().getTo());
            move.setTo(moves.get(i).getTo());
            move.setDepth_charge(moves.get(i).getDepth_charge());
            node.setMove(move);
            children.add(node);
        }
        return children;
    }

}