package binaryTree;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 代码对应的图示：https://www.cnblogs.com/qm-article/p/9349681.html
 * 我们想构建一颗按照升序排列的二叉树（左孩子的值比根节点的小，右孩子的值比根节点大）
 */
public class balanceBinaryTree {
    //用来说明是左边的子树需要调整（因为是左边的比右边的高出两层）
    private static final int LEFT=0;
    //用来说明是右边的子树需要调整（因为是右边的比左边的高出两层）
    private static final int RIGHT=1;
    //这个size是用来标注这个二叉树里面有多少个节点的
    private int size;

    public int getSize() {
        return size;
    }

    //这个是用来标记二叉树的根节点的
    private Node root;

    //从根节点开始比较，插入元素到二叉树中，如果在二叉树中已经有值了，那么插入不成功。类似于Set集合
    public boolean put(int val){
        return putVal(root,val);
    }
    private boolean putVal(Node node,int val){
        if(node == null){// 初始化根节点
            node = new Node(val);
            root = node;
            size++;
            return true;
        }
        Node temp = node;
        Node parent;
        int t;
        /**
         * 通过do while循环迭代获取最佳节点，
         */
        do{
            parent = temp;
            t = temp.val-val;
            if(t > 0){
                temp = temp.leftChild;
            }else if(t < 0){
                temp = temp.rightChild;
            }else{
                //如果插入的值在这棵树已经有了，则不重复创建节点（直接返回false）
                temp.val = val;
                return false;
            }
        }while(temp != null);
        Node newNode = new Node(parent, val);
        if(t > 0){
            parent.leftChild = newNode;
        }else if(t < 0){
            parent.rightChild = newNode;
        }
        rebuild(parent);// 使二叉树平衡的方法
        size++;
        return true;
    }

    /**
     *
     * @param p 代表新的插入节点的父节点
     */
    private void rebuild(Node p){
        //不断的往上面递归，直到根节点。这样只会计算新添加进来节点所属于的所有子树，和这个新加进来的子节点没有关系的子树，也会有深度差的改变
        while(p != null){
            if(calcNodeBalanceValue(p) == 2){// 说明以p为根节点的子树的左子树高，需要右旋或者 先左旋后右旋
                fixAfterInsertion(p,AdjustTree.LEFT);// 调整操作
            }else if(calcNodeBalanceValue(p) == -2){
                fixAfterInsertion(p,AdjustTree.RIGHT);
            }
            //开始不断的向上递归，直到根节点
            p = p.parent;
        }
    }

    private int calcNodeBalanceValue(Node node){
        if(node != null){
            return getHeightByNode(node);
        }
        return 0;
    }
    // 计算node节点的高度
    public int getChildDepth(Node node){
        if(node == null){
            return 0;
        }
        return 1+Math.max(getChildDepth(node.leftChild),getChildDepth(node.rightChild));
    }
    public int getHeightByNode(Node node){
        if(node == null){
            return 0;
        }
        return getChildDepth(node.leftChild)-getChildDepth(node.rightChild);
    }


    /**
     * 调整树结构 ，该方法有瑕疵，本想直接修改，但为了起参考作用就留在这，正解见评论。修改与2019-08-03 12:13
     * 这已经是修正后的代码了
     * @param p
     * @param type
     */
    private void fixAfterInsertion(Node p, AdjustTree type) {
        // TODO Auto-generated method stub
        if(type == AdjustTree.LEFT){
            final Node leftChild = p.leftChild;
            if(calcNodeBalanceValue(leftChild) == -1){// 先左旋后右旋
                leftRotation(leftChild);
            }
            rightRotation(p);
        }else{
            final Node rightChild = p.rightChild;
            if(calcNodeBalanceValue(rightChild) == 1){// 先右旋后左旋
                rightRotation(rightChild);
            }
            leftRotation(p);
        }
    }

    /**
     * 调整树结构 ，该方法有瑕疵，本想直接修改，但为了起参考作用就留在这，正解见评论。修改与2019-08-03 12:13
     * 这种方法的问题就是：
     *            A
     *        B      C
     *      D   E
     *        F
     * @param p
     * @param type
     */
    private void fixAfterInsertionWithProblem(Node p, int type) {
        // TODO Auto-generated method stub
        if(type == LEFT){
            final Node leftChild = p.leftChild;
            if(leftChild.leftChild != null){//右旋
                rightRotation(p);
            }else if(leftChild.rightChild != null){// 先左旋后右旋
                leftRotation(leftChild);
                rightRotation(p);
            }
        }else{
            final Node rightChild = p.rightChild;
            if(rightChild.rightChild != null){// 左旋
                leftRotation(p);
            }else if(rightChild.leftChild != null){// 先右旋，后左旋
                rightRotation(p);
                leftRotation(rightChild);
            }
        }
    }

    //这是对上面的修正
    private void fixAfterInsertion(Node p, int type) {
        // TODO Auto-generated method stub
        if(type == 1){
            final Node leftChild = p.leftChild;
            if(calcNodeBalanceValue(leftChild) == -1){// 先左旋后右旋
                leftRotation(leftChild);
            }
            rightRotation(p);
        }else{
            final Node rightChild = p.rightChild;
            if(calcNodeBalanceValue(rightChild) == 1){// 先右旋后左旋
                rightRotation(rightChild);
            }
            leftRotation(p);
        }
    }

    /**
     * 下面是二叉树在左右子树的高度差的绝对值大于等于2的时候进行右旋转的代码
     * 注意：进行左旋还是右旋这里的左右是根据我们方法参数旋转后成为了左子树还是右子树
     * 比如说这里的rightRotation方法，在刚进入方法的时候首先获取了他的左子树。要进行旋转只能在node和leftChild之间进行旋转。那么我的node只能
     * 变成leftChild的右子树。原因：因为我们构建的是从左到右的升序平衡二叉树。那么node.value>leftChild.value,旋转后node必然变成leftChild的子节点
     * 前面又说了node.value>leftChild.value，所以node只能变成leftChild的右子树。所以这里之所以叫右旋方法可以这么理解
     * 整个方法的目标就是：把node变成左孩子的右子树，此时必定是左子树的高度大于右子树（不一定是2，因为还有一种可能是先右旋再左旋）
     * @param node
     * @return
     */
    public Node rightRotation(Node node){
        if(node != null){
            //获取需要交换的左子树的根节点
            Node leftChild = node.leftChild;// 用变量存储node节点的左子节点
            //一、把leftChild的右孩子变成node的左孩子
            //原因：马上我想把leftChild变成node对应的子树的根节点。node只会在leftChild的右边，但是我现在如果leftChild的还有右子树的话，
            //就是让leftChild原来的右子树变成node的左子树（原因：因为leftChild原来的右子树也属于node的左子树的子集），所以如果假如node的直接孩子的话，只能是左孩子
            //二、修改leftChild.rightChild的parent，改成node
            //三、因为现在想让leftChild成为根节点，所以要把原来的根节点的父节点赋值给leftChild.parent
            //四、然后把原来指向node根节点的指针指向目前这棵子树的根节点leftChild（下面的三个判断）
            //五、现在已经构建好node对应的子树了，只需要把leftChild.rightChild的指针指过去就可以了
            //六、最后为了一致性，把node.parent变成leftChild
            node.leftChild = leftChild.rightChild;// 将leftChild节点的右子节点赋值给node节点的左节点
            if(leftChild.rightChild != null){// 如果leftChild的右节点存在，则需将该右节点的父节点指给node节点
                leftChild.rightChild.parent = node;
            }
            leftChild.parent = node.parent;
            if(node.parent == null){// 即表明node节点为根节点
                this.root = leftChild;
            }else if(node.parent.rightChild == node){// 即node节点在它原父节点的右子树中
                node.parent.rightChild = leftChild;
            }else if(node.parent.leftChild == node){
                node.parent.leftChild = leftChild;
            }
            leftChild.rightChild = node;
            node.parent = leftChild;
            return leftChild;
        }
        return null;
    }

    /**
     * 道理和上面的右旋方法相似
     * @param node
     * @return
     */
    public Node leftRotation(Node node){
        if(node != null){
            Node rightChild = node.rightChild;
            node.rightChild = rightChild.leftChild;
            if(rightChild.leftChild != null){
                rightChild.leftChild.parent = node;
            }
            rightChild.parent = node.parent;
            if(node.parent == null){
                this.root = rightChild;
            }else if(node.parent.rightChild == node){
                node.parent.rightChild = rightChild;
            }else if(node.parent.leftChild == node){
                node.parent.leftChild = rightChild;
            }
            rightChild.leftChild = node;
            node.parent = rightChild;

        }
        return null;
    }



    //构建对应的平衡二叉树对应的节点的类
    static class Node{
        Node parent;
        Node leftChild;
        Node rightChild;
        int val;
        public Node(Node parent, Node leftChild, Node rightChild,int val) {
            super();
            this.parent = parent;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.val = val;
        }

        public Node(int val){
            this(null,null,null,val);
        }

        public Node(Node node,int val){
            this(node,null,null,val);
        }

    }

     enum AdjustTree{
        LEFT("调整左子树,左子树比右子树高2层"),RIGHT("调整右子树,右子树比左子树高2层");

        private String desc;

        AdjustTree(String desc) {
            this.desc = desc;
        }
    }


    /**
     * 层次遍历
     */
    public void printLeft(){
        if(this.root == null){
            return;
        }
        int i=1;
        Queue<Node> queue = new LinkedList<>();
        Node temp = null;
        queue.add(root);
        while(!queue.isEmpty()){
            temp = queue.poll();
            System.out.print("节点值："+temp.val+",平衡值:"+calcNodeBalanceValue(temp)+"层次是"+i+"\n");
            if(temp.leftChild != null){
                queue.add(temp.leftChild);
            }
            if(temp.rightChild != null){
                queue.add(temp.rightChild);
            }
            i++;
        }
    }


    @Test
    public void test_balanceTree(){
        balanceBinaryTree bbt = new balanceBinaryTree();
        bbt.put(10);
        bbt.put(9);
        bbt.put(11);
        bbt.put(7);
        bbt.put(12);
        bbt.put(8);
        bbt.put(38);
        bbt.put(24);
        bbt.put(17);
        bbt.put(4);
        bbt.put(3);
//        System.out.println("----删除前的层次遍历-----");
//        bbt.printLeft();
        System.out.println("------前序遍历---------");
        bbt.prePrint(bbt.root);
        System.out.println("------中序遍历---------");
        bbt.midPrint(bbt.root);
//        System.out.println();
//        bbt.delete(9);
//        System.out.println("----删除后的层次遍历-----");
//        bbt.printLeft();
//        System.out.println("------中序遍历---------");
//        bbt.print();
    }
    //二叉树的前序遍历
    private void prePrint(Node root){
        if(root==null){
            return;
        }
        System.out.println(root.val);
        prePrint(root.leftChild);
        prePrint(root.rightChild);
    }
    //二叉树的中序遍历
    private void midPrint(Node root){
        if(root==null){
            return;
        }
        midPrint(root.leftChild);
        System.out.println(root.val);
        midPrint(root.rightChild);
    }

    @Test
    public void test_balanceTree1(){
        balanceBinaryTree bbt = new balanceBinaryTree();
        bbt.put(8);
        bbt.put(4);
        bbt.put(3);
        System.out.println("------前序遍历---------");
        bbt.prePrint(bbt.root);
        System.out.println("------中序遍历---------");
        bbt.midPrint(bbt.root);
    }



//    public void print(){
//        print(this.root);
//    }
//    private void print(Node node){
//        if(node != null){
//            print(node.leftChild);
//            System.out.println(node.val+",");
//            print(node.rightChild);
//        }
//    }
}
