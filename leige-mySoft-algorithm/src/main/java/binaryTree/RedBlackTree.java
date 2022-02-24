package binaryTree;

import org.junit.Test;

/**
 * 红黑树必须满足的五个特性：
 * （1）每个节点或者是黑色，或者是红色。
 * （2）根节点是黑色。
 * （3）每个叶子节点（NIL）是黑色。 [注意：这里叶子节点，是指为空(NIL或NULL)的叶子节点！这里说的叶子节点其实就是二叉树中的叶子节点的左右空指针,为什么这么做]
 * （4）如果一个节点是红色的，则它的子节点必须是黑色的。（红色节点的左右孩子节点必须是黑色，不能出现两个红色节点相邻的情况）
 * （5）从一个节点到该节点的子孙节点的所有路径上包含相同数目的黑节点。
 *
 * 处理红黑树的核心思想（）：将红色的节点移到根节点；然后，将根节点设为黑色。
 */
public class RedBlackTree {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private RBTNode<Integer> root;


    /*
     * 将结点插入到红黑树中
     *
     * 参数说明：
     *     node 插入的结点        // 对应《算法导论》中的node
     */
    private void insert(RBTNode<Integer> node) {
        int cmp;
        RBTNode<Integer> y = null;
        RBTNode<Integer> x = this.root;

        // 1. 将红黑树当作一颗二叉查找树，将节点添加到二叉查找树中。
        while (x != null) {
            y = x;
            //如果返回-1说明node.key<x.key。如果返回0，说明相等，如果返回1，说明node.key>x.key
            cmp = node.key.compareTo(x.key);
            if (cmp < 0)
                x = x.left;
            else
                x = x.right;
        }
        //进行上面的比较查找，找到要插入元素的父节点
        node.parent = y;

        if (y!=null) {
            cmp = node.key.compareTo(y.key);
            if (cmp < 0)
                y.left = node;
            else
                y.right = node;
        } else {
            this.root = node;
        }

        // 2. 设置节点的颜色为红色（刚插入的节点都是红色，然后进行修正）
        node.color = RED;

        // 3. 将它重新修正为一颗二叉查找树
        insertFixUp(node);
    }

    /*
     * 新建结点(key)，并将其插入到红黑树中
     *
     * 参数说明：
     *     key 插入结点的键值
     */
    public void insert(Integer key) {
        RBTNode<Integer> node=new RBTNode<Integer>(key,BLACK,null,null,null);

        // 如果新建结点失败，则返回。
        if (node != null)
            insert(node);
    }

    /*
     * 红黑树插入修正函数
     *
     * 在向红黑树中插入节点之后(失去平衡)，再调用该函数；
     * 目的是将它重新塑造成一颗红黑树。
     *
     * 参数说明：
     * node 新插入的结点        // 对应《算法导论》中的z
     */
    private void insertFixUp(RBTNode<Integer> node) {
        RBTNode<Integer> parent, gparent;

        // 若“父节点存在，并且父节点的颜色是红色”
        while (((parent = node.parent)!=null) && isRed(parent)) {
            //这里为什么不需要gparent是不是null。原因：是因为isRed(parent)，既然已经进来了说明parent是红色的节点。
            //gparent为null只有一种可能，就是parent为root。但是如果为root的话，那么就不能是红色的，所以根据代码isRed(parent)，可判定出parent不是root
            //即使是从下面的循环过来的，node是进行手动改变过颜色的节点，但是node.parent我们并没有改变他的颜色啊，node.parent原来是什么颜色就是什么颜色，
            // 现在他是红色，说明他有父节点，即gparent！=null
            gparent = parent.parent;

            //若“父节点”是“祖父节点的左孩子”
            if (parent == gparent.left) {
                // Case 1条件：叔叔节点是红色
                RBTNode<Integer> uncle = gparent.right;
                if ((uncle!=null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                // Case 2条件：叔叔是黑色，且当前节点是右孩子
                //(为什么断定叔叔是黑色：叔叔是黑色有两种情况。
                // 一、gparent.right=null,看红黑树的（3）条件。
                // 二、gparent.right!=null,那么gparent.right要么是red要么black,上面已经把red的情况截断了，现在只剩下了红色)
                //综上：得出结论叔叔是黑色
                if (parent.right == node) {
                    RBTNode<Integer> tmp;
                    leftRotate(parent);
                    //经过上面的左旋过后，原来的右孩子变成子树的根节点，原来的子树根节点变成左子树
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是左孩子。
                setBlack(parent);
                setRed(gparent);
                //经过Case 3的操作就不会再需要旋转了。
                // 具体原因分析：看旁边的redBlackTreeRotation.txt文档
                rightRotate(gparent);
            } else {    //若“父节点”是“祖父节点的右孩子”
                // Case 1条件：叔叔节点是红色
                RBTNode<Integer> uncle = gparent.left;
                if ((uncle!=null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                // Case 2条件：叔叔是黑色，且当前节点是左孩子
                if (parent.left == node) {
                    RBTNode<Integer> tmp;
                    rightRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是右孩子。
                setBlack(parent);
                setRed(gparent);
                leftRotate(gparent);
            }
        }

        // 将根节点设为黑色
        setBlack(this.root);
    }

    private boolean isRed(RBTNode<Integer> node){
        return node.color==RED;
    }

    private void setBlack(RBTNode<Integer> node){
        node.color=BLACK;
    }

    private void setRed(RBTNode<Integer> node){
        node.color=RED;
    }



    /*
     * 对红黑树的节点(y)进行右旋转（和平衡二叉树的右旋是一样的）
     *
     * 右旋示意图(对节点y进行右旋)：
     *            py                              py
     *           /                               /
     *          y                               x
     *         /  \      --(右旋)-.            /  \                     #
     *        x   ry                          lx  y
     *       / \                                 / \                   #
     *      lx  rx                              rx  ry
     *
     */
    private void rightRotate(RBTNode<Integer> y) {
        // 设置x是当前节点的左孩子。
        RBTNode<Integer> x = y.left;

        // 将 “x的右孩子” 设为 “y的左孩子”；
        // 如果"x的右孩子"不为空的话，将 “y” 设为 “x的右孩子的父亲”
        y.left = x.right;
        if (x.right != null)
            x.right.parent = y;

        // 将 “y的父亲” 设为 “x的父亲”
        x.parent = y.parent;

        if (y.parent == null) {
            this.root = x;            // 如果 “y的父亲” 是空节点，则将x设为根节点
        } else {
            if (y == y.parent.right)
                y.parent.right = x;    // 如果 y是它父节点的右孩子，则将x设为“y的父节点的右孩子”
            else
                y.parent.left = x;    // (y是它父节点的左孩子) 将x设为“x的父节点的左孩子”
        }

        // 将 “y” 设为 “x的右孩子”
        x.right = y;

        // 将 “y的父节点” 设为 “x”
        y.parent = x;
    }


    /*
     * 对红黑树的节点(x)进行左旋转（和平衡二叉树的左旋是一样的）
     *
     * 左旋示意图(对节点x进行左旋)：
     *      px                             px
     *     /                              /
     *    x                              y
     *   /  \      --(左旋)-.           / \                #
     *  lx   y                         x  ry
     *     /   \                      / \
     *    ly   ry                    lx  ly
     *
     *
     */
    private void leftRotate(RBTNode<Integer> x) {
        // 设置x的右孩子为y
        RBTNode<Integer> y = x.right;

        // 将 “y的左孩子” 设为 “x的右孩子”；
        // 如果y的左孩子非空，将 “x” 设为 “y的左孩子的父亲”
        x.right = y.left;
        if (y.left != null)
            y.left.parent = x;

        // 将 “x的父亲” 设为 “y的父亲”
        y.parent = x.parent;

        if (x.parent == null) {
            this.root = y;            // 如果 “x的父亲” 是空节点，则将y设为根节点
        } else {
            if (x.parent.left == x)
                x.parent.left = y;    // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
            else
                x.parent.right = y;    // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
        }

        // 将 “x” 设为 “y的左孩子”
        y.left = x;
        // 将 “x的父节点” 设为 “y”
        x.parent = y;
    }


    /**
     * 用内部类是因为内部类与所在外部类有一定的关系，往往只有该外部类调用此内部类。所以没有必要专门用一个Java文件存放这个类。
     * 要给内部类的定义静态成员，必须要使用静态内部类(如果需要给内部类定义)
     * @param <T> 对应的节点值的类型
     */
    class RBTNode<T extends Comparable<T>> {
        boolean color;        // 颜色
        T key;                // 关键字(键值)
        RBTNode<T> left;    // 左孩子
        RBTNode<T> right;    // 右孩子
        RBTNode<T> parent;    // 父结点

        public RBTNode(T key, boolean color, RBTNode<T> parent, RBTNode<T> left, RBTNode<T> right) {
            this.key = key;
            this.color = color;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

    }

    //二叉树的前序遍历
    private void prePrint(RBTNode<Integer> root){
        if(root==null){
            return;
        }
        System.out.println(root.key);
        prePrint(root.left);
        prePrint(root.right);
    }

    //二叉树的中序遍历
    private void midPrint(RBTNode<Integer> root){
        if(root==null){
            return;
        }
        midPrint(root.left);
        System.out.println(root.key);
        midPrint(root.right);
    }


    @Test
    public void test1(){
        //12 1 9 2 0 11 7
        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.insert(12);
        redBlackTree.insert(1);
        redBlackTree.insert(9);
        redBlackTree.insert(2);
        redBlackTree.insert(0);
        redBlackTree.insert(11);
        redBlackTree.insert(7);
        System.out.println("------前序遍历---------");
        redBlackTree.prePrint(redBlackTree.root);
        System.out.println("------中序遍历---------");
        redBlackTree.midPrint(redBlackTree.root);
    }

}

