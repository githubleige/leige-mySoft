package leetcode;

/*给定一个链表，每隔k个元素做一次反转

        Example:
        Inputs: 1->2->3->4->5->6->7->8->NULL and k = 3
        Output: 3->2->1->6->5->4->8->7->NULL.

        Inputs: 1->2->3->4->5->6->7->8->NULL and k = 5
        Output: 5->4->3->2->1->8->7->6->NULL.*/
public class rotateRight {

    public static void main(String[] args) {
        ListNode head=new ListNode(1);
//        head.next=new ListNode(1);
//        head.next.next=new ListNode(2);
//        head.next.next.next=new ListNode(4);
//        head.next.next.next.next=new ListNode(5);
        ListNode temp=head;
        while(temp!=null){
            System.out.println(temp.val);
            temp=temp.next;
        }
        System.out.println("==============");
        temp=head;
        while(temp!=null){
            System.out.println(temp.val);
            temp=temp.next;
        }
    }

    public static ListNode rotate(ListNode head, int k) {
        if(head==null||k==0){
            return head;
        }
        int length=0;
        ListNode temp=head,lasttemp=null,last=null;
        while(temp!=null){
            length++;
            last=temp;
            temp=temp.next;
        }
        k=k%length;
        if(k==0){
            return head;
        }
        int count=k;
        temp=head;
        for(int i=0;i<count;i++){
            lasttemp=temp;
            temp=temp.next;
        }
        last.next=head;
        head=temp;
        lasttemp.next=null;
        return head;
    }


}



