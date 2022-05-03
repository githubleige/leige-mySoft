package leetcode;

/**
 * 一条包含字母 A-Z 的消息通过以下映射进行了 编码 ：
 *
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 * 要 解码 已编码的消息，所有数字必须基于上述映射的方法，反向映射回字母（可能有多种方法）。例如，"11106" 可以映射为：
 *
 * "AAJF" ，将消息分组为 (1 1 10 6)
 * "KJF" ，将消息分组为 (11 10 6)
 * 注意，消息不能分组为  (1 11 06) ，因为 "06" 不能映射为 "F" ，这是由于 "6" 和 "06" 在映射中并不等价。
 *
 * 给你一个只含数字的 非空 字符串 s ，请计算并返回 解码 方法的 总数 。
 *
 * 题目数据保证答案肯定是一个 32 位 的整数。
 */
public class numDecodings {
    public static void main(String[] args) {

        System.out.println(numDecodings("230"));
    }

    public  static int numDecodings1(String s) {
        if(s.startsWith("0")){
            return 0;
        }
        if(s.length()==2&&s.endsWith("0")){
            return 1;
        }
        if(s.length()==1||s.equals("")){
            return 1;
        }
        if(s.indexOf('0')==1){
            return numDecodings(s.substring(2));
        }
        if(s.indexOf('0')==2){
            return numDecodings(s.substring(1));
        }

        return numDecodings(s.substring(1))+((Integer.parseInt(s.substring(0,2))<27)?numDecodings(s.substring(2)):0);
    }


    public  static int numDecodings(String s){
        if(s.startsWith("0")||s.equals("")){
            return 0;
        }
        if(s.length()==1){
            return 1;
        }
        int[] arr=new int[s.length()];
        if(s.endsWith("0")){
            arr[s.length()-1]=0;
        }else {
            arr[s.length()-1]=1;
        }

        if(s.charAt(s.length()-2)=='0'){
            arr[s.length()-2]=0;
        }else if((Integer.parseInt(s.substring(s.length()-2))<27)){
            arr[s.length()-2]=1+arr[s.length()-1];
        }else{
            arr[s.length()-2]=arr[s.length()-1];
        }
        boolean flag;
        for(int i=s.length()-3;i>=0;i--){
            if(s.charAt(i)=='0'){
                arr[i]=0;
            }else{
                flag=Integer.parseInt(s.substring(i,i+2))<27;
                arr[i]=arr[i+1]+(flag?arr[i+2]:0);
            }

        }
        return arr[0];
    }

}
