package leetcode;

public class divide3 {

    public static void main(String[] args) {
        divide3 aa=new divide3();
        int b=aa.divide1(Integer.MIN_VALUE,2);
        System.out.println(b);
//        System.out.println(Integer.MIN_VALUE);
//        System.out.println(Integer.MAX_VALUE);
    }

    public static int divide(int a, int b) {
        if(a==Integer.MIN_VALUE&&b==-1){
            return Integer.MAX_VALUE;
        }
        if(b==1){
            return a;
        }
        int tempA=a,tempB=b;
        //当a是最小值Integer.MAX_VALUE,下面的值不会进行转化。因为最小值-2147483648,最大值是2147483647。溢出，所以不会转换
        //最好的办法是转化long。tempA=-(long)a;,不能出现tempA=(long)-a,因为还会是-2147483648
        if(a<0) tempA=-a;
        if(b<0) tempB=-b;
        if((tempA<tempB)||(a==0)){
            return 0;
        }
        int sign=1;
        if((a<0&&b>0)||(a>0&&b<0)){
            sign=-1;
        }
        int count=1,sum=tempB;
        while(sum<=tempA){
//            sum+=sum;
            sum<<=1;
            count=count << 1;
        }
        sum>>=1;
        int tempCount=count>>1;
        count=(tempCount+divide(tempA-sum,tempB));
        return sign==-1?-(count):(count);
//        return sign==-1?-(count>>1+):(count);
    }

    public static int divide1(int a, int b) {
        if(a==Integer.MIN_VALUE&&b==-1){
            return Integer.MAX_VALUE;
        }
        long tempA=a,tempB=b;
        if(a<0)
            tempA=-(long)a;
        if(b<0)
            tempB=-(long)b;
        if((tempA<tempB)||(a==0)){
            return 0;
        }
        int sign=1;
        if((a<0&&b>0)||(a>0&&b<0)){
            sign=-1;
        }
        return sign==-1?-((int)subdivide1(tempA,tempB)):((int)subdivide1(tempA,tempB));
    }

    public static long subdivide1(long a,long b){
        if(a<b) return 0;
        long count=1;
        long sum=b;
        while (sum<=a){
            sum<<=1;
            count=count << 1;
        }
        sum>>=1;
        count>>=1;
        return count + subdivide1(a - sum, b);
    }




}
