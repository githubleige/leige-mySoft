package dynamicProgram;


public class recoverTree {

    TreeNode max=new TreeNode(Integer.MIN_VALUE),min=new TreeNode(Integer.MAX_VALUE);
    Boolean flag=true;

    public static void main(String[] args) {
        recoverTree aa=new recoverTree();
        TreeNode node1=new TreeNode(3);
        TreeNode node2=new TreeNode(1);
        TreeNode node3=new TreeNode(4);
        TreeNode node4=new TreeNode(2);
        node1.left=node2;node1.right=node3;node3.left=node4;
        aa.traverse1(node1);
        System.out.println("============");
        aa.recoverTree(node1);
        aa.traverse1(node1);
    }
    public void recoverTree(TreeNode root) {
        traverse(root);
        swap(max,min);
    }

    public void swap(TreeNode max,TreeNode min){
        int temp=max.val;
        max.val=min.val;
        min.val=temp;
    }

    private void traverse(TreeNode root){
        if (root==null){
            return;
        }
        traverse(root.left);
        if(flag){
            if(root.val>=max.val){
                max=root;
            }else{
                flag=false;
                min=root;
            }
        }else{
            if(root.val<min.val){
                min=root;
                return;
            }
        }
        traverse(root.right);
    }

    private void traverse1(TreeNode root){
        if (root==null){
            return;
        }
        traverse1(root.left);
        System.out.println(root.val);
        traverse1((root.right));
    }


}


/*class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode() {}
     TreeNode(int val) { this.val = val; }
     TreeNode(int val, TreeNode left, TreeNode right) {
         this.val = val;
         this.left = left;
         this.right = right;
     }
}*/


 class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }

