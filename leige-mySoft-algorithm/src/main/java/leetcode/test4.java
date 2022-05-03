package leetcode;

import java.util.Stack;

public class test4 {
    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        ListNode n6 = new ListNode(6);
        ListNode n7 = new ListNode(7);
        ListNode n8 = new ListNode(8);

        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n6;
        n6.next = n7;
        n7.next = n8;
        ListNode head=rotate(n1,4);

        while (head != null) {
            System.out.print(head.val+"  ");
            head = head.next;
        }
    }

    public static ListNode rotate(ListNode head, int k){
        Stack<ListNode> stack=new Stack<>();
        ListNode next=head, temp=head;
        int count=0;
        while (next!=null){
            for(int i=0;i<k;i++){
                stack.push(next);
                next=next.next;
                if(next==null){
                    break;
                }
            }
            while (!stack.isEmpty()){
                if(count==0){
                    head=stack.pop();
                    temp=head;
                    count++;
                }else{
                    temp.next=stack.pop();
                    temp=temp.next;
                }
            }
        }
        temp.next=null;
        return head;
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
