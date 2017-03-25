package yang;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

enum VertexState {
    VISITED, DISCOVERED, UNDISCOVERED
}

enum EdgeState {
    UNDISCOVERED, TREE, CROSS, BACKWARD, FORWARD
}

/**
 * 
 * @author LiuYang
 *
 * @param <T>
 *            data type of vertex
 * @param <M>
 *            data type of edge
 */
public class Graph<T, M> {

    public int vertexNum;
    private Vector<Vector<Edge>> relevanteMatrix;
    private Vector<Vertex> vertexs;

    public Graph(int vertexNum) {
        initRelevantMatrix(vertexNum);
        vertexs = new Vector<Vertex>();
        for (int i = 0; i < vertexNum; i++) {
            vertexs.add(i, new Vertex(null));
        }
        this.vertexNum = vertexNum;
    }

    public void DFS() {
        int[] clock = new int[] { 0 };
        for (int i = 0; i < vertexNum; i++) {
            DFS(i, clock);
        }
    }

    public int getParent(int position) {
        return vertexs.get(position).parent;
    }
    
    public int getVisitedTime(int position){
        return vertexs.get(position).visitedTime;
    }
    
    public int getDiscoveredTime(int position){
        return vertexs.get(position).discoveredTime;
    }

    public void DFS(int position, int[] clock) {
        Vertex currentVertex = vertexs.get(position);
        if (currentVertex.state != VertexState.UNDISCOVERED)
            return;
        currentVertex.state = VertexState.DISCOVERED;
        currentVertex.discoveredTime = ++clock[0];
        System.out.println(vertexs.get(position).data);
        for (int i = firstNeighbor(position); i != -1; i = getNextNeighbor(position, i)) {
            Vertex neighbor = vertexs.get(i);
            switch (neighbor.state) {
            case UNDISCOVERED:
                relevanteMatrix.get(position).get(i).state = EdgeState.TREE;
                neighbor.parent = position;
                DFS(i, clock);
                break;
            case DISCOVERED:
                relevanteMatrix.get(position).get(i).state = EdgeState.BACKWARD;
                break;
            case VISITED:
                if (neighbor.visitedTime < currentVertex.discoveredTime)
                    relevanteMatrix.get(position).get(i).state = EdgeState.CROSS;
                else
                    relevanteMatrix.get(position).get(i).state = EdgeState.FORWARD;
                break;
            default:
                break;
            }
        }
        currentVertex.state = VertexState.VISITED;
        currentVertex.visitedTime = ++clock[0];
    }

    public void BFS() {
        for (int i = 0; i < vertexNum; i++) {
            BFS(i);
        }
    }

    private void BFS(int startPosition) {
        Queue<Integer> visitList = new LinkedList<>();
        visitList.offer(startPosition);
        while (!visitList.isEmpty()) {
            int vertexPosition = visitList.poll();
            if (vertexs.get(vertexPosition).state != VertexState.VISITED) {
                System.out.println(vertexs.get(vertexPosition).data);
            }
            vertexs.get(vertexPosition).state = VertexState.VISITED;
            for (int i = firstNeighbor(vertexPosition); i != -1; i = getNextNeighbor(vertexPosition, i)) {
                if (vertexs.get(i).state == VertexState.VISITED) {
                    relevanteMatrix.get(vertexPosition).get(i).state = EdgeState.BACKWARD;
                } else {
                    relevanteMatrix.get(vertexPosition).get(i).state = EdgeState.TREE;
                    visitList.offer(i);
                }
            }
        }
    }

    private int firstNeighbor(int vertexPosition) {
        Vector<Edge> relevantEdge = relevanteMatrix.get(vertexPosition);
        for (int i = 0; i < vertexNum; i++) {
            if (relevantEdge.get(i) != null)
                return i;
        }
        return -1;
    }

    private int getNextNeighbor(int vertexPosition, int neighborPosition) {
        Vector<Edge> relevantEdge = relevanteMatrix.get(vertexPosition);
        for (int i = neighborPosition + 1; i < vertexNum; i++) {
            if (relevantEdge.get(i) != null)
                return i;
        }
        return -1;
    }

    public void initRelevantMatrix(int vertexNum) {
        relevanteMatrix = new Vector<>();
        for (int i = 0; i < vertexNum; i++) {
            relevanteMatrix.add(new Vector<>());
            for (int j = 0; j < vertexNum; j++) {
                relevanteMatrix.get(i).add(null);
            }
        }
    }

    public void deleteVertex(int i) {
        relevanteMatrix.remove(i);
        for (Vector<Edge> vector : relevanteMatrix) {
            vector.remove(i);
        }
        vertexs.remove(i);
        vertexNum--;
    }

    public int addVertex(T data) {
        vertexs.add(new Vertex(data));
        vertexNum++;
        for (Vector<Edge> vector : relevanteMatrix) {
            vector.add(null);
        }
        relevanteMatrix.add(new Vector<Edge>(vertexNum));
        return vertexNum;
    }

    public void addEdge(int i, int j, M data) {
        relevanteMatrix.get(i).set(j, new Edge(data));
    }

    public void setEdgeState(int i, int j, EdgeState state) {
        relevanteMatrix.get(i).get(j).state = state;
    }

    public void setEdgeData(int i, int j, M data) {
        relevanteMatrix.get(i).get(j).data = data;
    }

    public M getEdgeData(int i, int j) {
        if (relevanteMatrix.get(i).get(j) != null)
            return relevanteMatrix.get(i).get(j).data;
        return null;
    }

    public EdgeState getEdgeState(int i, int j) {
        return relevanteMatrix.get(i).get(j).state;
    }

    public void setVertexData(int i, T data) {
        vertexs.get(i).data = data;
    }

    public void setVertexState(int i, VertexState state) {
        vertexs.get(i).state = state;
    }

    public T getVertexData(int i) {
        return vertexs.get(i).data;
    }

    public VertexState getVertexState(int i) {
        return vertexs.get(i).state;
    }

    private class Vertex {
        private T data;
        private VertexState state;
        private int discoveredTime;
        private int visitedTime;
        private int parent = -1;

        private Vertex(T data) {
            state = VertexState.UNDISCOVERED;
            this.data = data;
        }
    }

    private class Edge {
        private EdgeState state;
        private M data;

        private Edge(M data) {
            state = EdgeState.UNDISCOVERED;
            this.data = data;
        }
    }

}
