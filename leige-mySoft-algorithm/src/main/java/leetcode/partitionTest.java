package leetcode;

import java.util.ArrayDeque;

/**
 * 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
 *
 * 你应当 保留 两个分区中每个节点的初始相对位置。
 *
 */
public class partitionTest {

    public static void main(String[] args) {
        ListNode n1 = new ListNode(5);
        ListNode n2 = new ListNode(4);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(1);
        ListNode n5 = new ListNode(5);
        ListNode n6 = new ListNode(2);
        ListNode n7 = new ListNode(7);
        ListNode n8 = new ListNode(8);

        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n6;
        n6.next = n7;
        n7.next = n8;

        ListNode head=partition(n1,3);
        while (head != null) {
            System.out.print(head.val+"  ");
            head = head.next;
        }
    }

    public static ListNode partition(ListNode head, int x) {
        ArrayDeque<ListNode> queue=new ArrayDeque<>();
        ListNode next=head,newHead=null;
        while(next !=null){
            if(next.val>=x){
                newHead=next;
                break;
            }
            next=next.next;
        }
        if(newHead==null){
            return head;
        }
        ListNode prev=new ListNode(0);
        prev.next=head;
        next=head;
        while(next!=null){
            if(next.val<x){
                queue.offer(next);
                next=next.next;
                prev.next=next;
            }else{
                prev=next;
                next=next.next;
            }
        }
        ListNode temp;
        if(queue.isEmpty()){
            return head;
        }else{
            head=queue.poll();
            temp=head;
            while(!queue.isEmpty()){
                temp.next=queue.poll();
                temp=temp.next;
            }
        }
        temp.next=newHead;
        return head;
    }
}
