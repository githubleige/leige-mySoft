package leetcode;

public class divide1 {

    public static void main(String[] args) {
        divide1 aa=new divide1();
        int b=aa.divide(Integer.MAX_VALUE,2);
        System.out.println(b);

    }

    public int divide(int dividend, int divisor) {
        // 当除数为1，直接返回被除数
        if (divisor == 1) {
            return dividend;
        }
        // 当除数为-1且被除数为Integer.MIN_VALUE时，将会溢出，返回Integer.MAX_VALUE
        if (divisor == -1 && dividend == Integer.MIN_VALUE) {
            return Integer.MAX_VALUE;
        }

        // 把被除数与除数调整为正数,为防止被除数Integer.MIN_VALUE转换为正数会溢出，使用long类型保存参数
        if (dividend < 0 && divisor < 0) {
            return divide(-(long) dividend, -(long) divisor);
        } else if (dividend < 0 || divisor < 0) {
            return -divide(Math.abs((long) dividend), Math.abs((long) divisor));
        } else {
            return divide((long) dividend, (long) divisor);
        }
    }

    public int divide(long dividend, long divisor) {
        // 如果被除数小于除数，结果明显为0
        if (dividend < divisor) {
            return 0;
        }
        long sum = divisor; // 记录用了count个divisor的和
        int count = 1; // 使用了多少个divisor
        while (dividend >= sum) {
            // 每次翻倍
            sum <<= 1;
            count <<= 1;
        }

        // 此时dividend < sum
        sum >>>= 1;
        count >>>= 1;

        // 此时dividend >= sum
        // 将count个divisor从dividend消耗掉，剩下的还需要多少个divisor交由递归函数处理
        return count + divide(dividend - sum, divisor);
    }

    public static int divide3(int a, int b) {
        if(a==Integer.MIN_VALUE&&b==-1){
            return Integer.MAX_VALUE;
        }
        int tempA=a,tempB=b;
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
        count=(tempCount+divide3(tempA-sum,tempB));
        return sign==-1?-(count):(count);
//        return sign==-1?-(count>>1+):(count);
    }


    /**
     * 两个参数都是正数
     * @param dividend
     * @param divisor 不为1且也不为-1
     * @return
     */
    public int divide1(long dividend, long divisor){
        if(dividend<divisor){
            return 0;
        }
        long sum=divisor;
        long count=1;
        while(sum<=dividend){
            sum <<=1;
            count<<=1;
        }
        sum >>>=1;
        count>>>=1;
        return (int)count+divide1(dividend-sum,divisor);
    }

    public int divide2(long dividend, long divisor){
        if(dividend<divisor){
            return 0;
        }
        long sum=divisor;
        int count=1;
        while(sum<=dividend){
            sum+=divisor;
            count++;
        }
        count--;
        return count;
    }

}
