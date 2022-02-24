package binaryTree;

public class TraversalFactory {
    //二叉树的前序遍历
    public static void prePrint(balanceBinaryTree.Node root){
        if(root==null){
            return;
        }
        System.out.println(root.val);
        prePrint(root.leftChild);
        prePrint(root.rightChild);
    }

    //二叉树的中序遍历
    public static void midPrint(balanceBinaryTree.Node root){
        if(root==null){
            return;
        }
        midPrint(root.leftChild);
        System.out.println(root.val);
        midPrint(root.rightChild);
    }
}
