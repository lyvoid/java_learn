package agn1_2;

import java.util.Scanner;

/**
 * zuma
 * 
 * @author yang
 *
 */
public class Main {

    public static void main(String[] args) {
        // read
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        String initialSeries = sc.nextLine().trim();
        int round = sc.nextInt();
        sc.nextLine();
        String[] input = new String[round];
        for (int i = 0; i < round; i++) {
            input[i] = sc.nextLine();
        }

        // Pre-process
        Main m = new Main();
        for (char c : initialSeries.toCharArray()) {
            m.add(c);
        }

        // game start
        String[] temp;
        for (int i = 0; i < round; i++) {
            temp = input[i].split(" ");
            m.insert(temp[1].toCharArray()[0], Integer.parseInt(temp[0]));
            m.print();
        }
    }

    Node header;
    Node trailer;

    public Main() {
        header = new Node();
        trailer = new Node();
        header.next = trailer;
        trailer.prev = header;
    }

    public void add(char value) {
        Node node = new Node();
        node.value = value;
        node.prev = trailer.prev;
        node.next = trailer;
        trailer.prev.next = node;
        trailer.prev = node;
    }

    public void print() {
        if (header.next == trailer)
            System.out.print("-");
        Node currentNode = header;
        while ((currentNode = currentNode.next) != trailer) {
            System.out.print(currentNode.value);
        }
        System.out.println();
    }

    public void insert(char value, int pos) {
        Node currentNode = header;
        Node node = new Node();
        for (int i = 0; i < pos; i++) {
            currentNode = currentNode.next;
        }
        node.value = value;
        node.next = currentNode.next;
        currentNode.next.prev = node;
        currentNode.next = node;
        node.prev = currentNode;
        elimit(node);
    }

    private void elimit(Node node) {
        if (node == header || node == trailer)
            return;
        // find previous node numbers
        int count = 1;
        Node currentPrevNode = node;
        while (node.value == ((currentPrevNode = currentPrevNode.prev).value)) {
            count++;
        }
        // find posterior node numbers
        Node currentPostNode = node;
        while (node.value == ((currentPostNode = currentPostNode.next).value)) {
            count++;
        }
        if (count >= 3) {
            currentPostNode.prev = currentPrevNode;
            currentPrevNode.next = currentPostNode;
            elimit(currentPostNode);
        }
    }

    private class Node {
        private char value;
        private Node next;
        private Node prev;
    }

}
