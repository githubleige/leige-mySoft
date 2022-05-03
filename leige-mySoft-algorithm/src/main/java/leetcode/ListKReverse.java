package leetcode;

public class ListKReverse {

    public static void main(String[] args) {
        ListKReverse s = new ListKReverse();

        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        Node n8 = new Node(8);

        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n6;
        n6.next = n7;
        n7.next = n8;


        Node head = s.ReverseInGroups(n1, 4);
        while (head != null) {
            System.out.print(head.data+"  ");
            head = head.next;
        }
        System.out.println();
    }

    public Node ReverseInGroups(Node current, int k) {
        if (current == null || current.next == null ) return current;
        int n=0;
        Node oldHead=current;
        while(current!=null)
        {
            current=current.next;
            n++;
        }
        System.out.println(n);
        int reverseNum=n/k;
        current=oldHead;
        Node newHead = current;
        Node previousGroupTail = null;
        int count = 1;
        int num=0;
        while (current != null&&num<reverseNum) {
            Node groupTail = current;
            Node prev = null;
            Node next = null;
            for (int i = 1; i <= k && current != null; i++) {
                next = current.next;
                current.next = prev;
                prev = current;
                current = next;
            }
            if (count == 1) {
                newHead = prev;
                count++;
            }
            if (previousGroupTail != null) {
                previousGroupTail.next = prev;
            }
            previousGroupTail = groupTail;
            num++;
        }
        if(current!=null)
            if (previousGroupTail != null)
                previousGroupTail.next = current;

        return newHead;
    }
}

class Node<T> {
    public  T data;
    public  Node<T> next;

    Node(T dataPortion) {
        data = dataPortion;
        next = null;

    }

    Node(T dataPortion, Node<T> nextNode) {
        data = dataPortion;
        next = nextNode;
    }

}

