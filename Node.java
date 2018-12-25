import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Node {

    @Getter
    @Setter
    private int staticEvaluationValue;

    private ArrayList<Node> children = new ArrayList<Node>();

    public enum NodeType {
        MAX_NODE,
        MIN_NODE;
    }

    @Getter
    @Setter
    private Move move;

    @Getter
    @Setter
    private NodeType nodeType;

    @Getter
    @Setter
    private Node parent;

    Node(Node parent) {
        this.parent = parent;
        if(parent == null)
            nodeType = NodeType.MAX_NODE;
        else {
            nodeType = parent.nodeType == NodeType.MAX_NODE ? NodeType.MIN_NODE : NodeType.MAX_NODE;
        }
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public int getChildrenCount() {
        return children.size();
    }

    public boolean isWhite() {
        return this.nodeType == NodeType.MAX_NODE;
    }

    public boolean isBlack() {
        return this.nodeType == NodeType.MIN_NODE;
    }
}
