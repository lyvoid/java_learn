package client;

import yang.Graph;

public class Client {
    public static void main(String[] args) {
        Graph<Integer, Integer> graph = new Graph<>(10);
        for (int i = 0; i < 10; i++) {
            graph.setVertexData(i, i);
        }
        for (int i = 1; i < 10; i++) {
            graph.addEdge(i%2, i, null);
        }
        graph.DFS();
        System.out.println("--");
        for (int i = 0; i < 10; i++) {
            System.out.println(graph.getParent(i));
        }
    }
    

}
