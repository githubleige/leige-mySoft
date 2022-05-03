package leetcode;

import java.util.Map;
import java.util.TreeMap;


public class deleteDuplicates3 {
    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        ListNode n5_1 = new ListNode(5);
        ListNode n6 = new ListNode(6);
        ListNode n7 = new ListNode(7);
        ListNode n8 = new ListNode(8);
        ListNode n9 = new ListNode(8);

        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n5_1;
        n5_1.next=n6;
        n6.next = n7;
        n7.next = n8;
        n8.next=n9;

        TreeMap<Integer,Integer> TreeMap=new TreeMap<>();
        ListNode next=n1,last=n1,head=n1;
        while(next!=null){
            if(TreeMap.containsKey(next.val)){
                TreeMap.put(next.val,TreeMap.get(next.val)+1);
            }else {
                TreeMap.put(next.val,1);
            }
            next=next.next;
        }
       next=n1;
        for(Map.Entry<Integer,Integer> entry: TreeMap.entrySet()){
            if(entry.getValue()==1){
                next.val=entry.getKey();
                last=next;
                next=next.next;
            }
        }
        last.next=null;

        while (head != null) {
            System.out.print(head.val+"  ");
            head = head.next;
        }
    }


}
