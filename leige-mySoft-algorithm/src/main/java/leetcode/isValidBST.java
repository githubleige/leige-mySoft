package leetcode;

public class isValidBST {

    private boolean flag=true;


    private int Max=Integer.MIN_VALUE;

    private boolean firstFlag=true;

    public boolean isValidBST(BSTNode root) {

        visit(root);
        return flag;
    }

    private void visit(BSTNode root){
        if(root==null){
            return;
        }
        if(root.left!=null){
            if(root.left.val>=root.val){
                flag=false;
                return;
            }
            visit(root.left);
        }
        if(firstFlag){
            Max=root.val;
            firstFlag=false;
        }
        else{
            if(root.val<=Max){
            flag=false;
            return;
        }
        Max=Math.max(Max,root.val);
        }
        if(root.right!=null){
            if(root.right.val<=root.val){
                flag=false;
                return;
            }
            visit(root.right);
        }
    }
}



class BSTNode {
    int val;
    BSTNode left;
    BSTNode right;
    BSTNode() {}
    BSTNode(int val) { this.val = val; }
    BSTNode(int val, BSTNode left, BSTNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
