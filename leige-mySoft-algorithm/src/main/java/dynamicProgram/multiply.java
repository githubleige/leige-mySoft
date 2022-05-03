package dynamicProgram;


import com.sun.xml.internal.ws.util.StringUtils;

public class multiply {

    public static void main(String[] args) {
        String str="123";
        Integer.parseInt(str);

        String num1="123456789";
        String num2="987654321";
//        multiply(num1,num2);
        String[] strs=new String[]{num1,num2};
        System.out.println(multiply(num1,num2));
    }

    public static String multiply(String num1, String num2) {
        if(zeroLength(num1)||zeroLength(num2)){
            return "0";
        }
        char[] digits=num1.toCharArray();
        char[] Xs=num2.toCharArray();
        int c=0;
        StringBuffer tempStr,subStr=new StringBuffer();

        String[] strs=new String[Xs.length];
        int count=0;
        for(int i=Xs.length-1;i>=0;i--){
            tempStr=new StringBuffer();
            c=0;
            for(int j=digits.length-1;j>=0;j--){
                int temp=(Xs[i]-48)*(digits[j]-48)+c;
                int origin=(temp)%10;
                c=temp/10;
                tempStr.append(origin);
            }
            if(c!=0){
                tempStr.append(c);
            }
            strs[count]=tempStr.reverse().append(subStr).toString();
            subStr=subStr.append(0);
            count++;
        }
        return strAdd(strs);
    }

    private static String strAdd(String[] strs){
        String strTemp="0";
        StringBuffer stringBuffer=new StringBuffer();
        int c=0;
        char c1,c2;
        for(int i=0;i<strs.length;i++){
            char[] chars=strTemp.toCharArray();
            char[] charsTemp=strs[i].toCharArray();
            for(int j=0;j<Math.max(chars.length,charsTemp.length);j++){
                if((j>=Math.min(chars.length,charsTemp.length))){
                    if(chars.length>charsTemp.length){
                        c2='0';
                        c1=chars[chars.length-1-j];
                    }else{
                        c1='0';
                        c2=charsTemp[charsTemp.length-1-j];
                    }
                }else{
                    c1=chars[chars.length-1-j];
                    c2=charsTemp[charsTemp.length-1-j];
                }

                int temp=(c1-48)+(c2-48)+c;
                int origin=(temp)%10;
                c=temp/10;
                stringBuffer.append(origin);
            }
            if(c!=0){
                stringBuffer.append(c);
            }
            strTemp=stringBuffer.reverse().toString();
            stringBuffer=new StringBuffer();
            c=0;
        }
        return strTemp;
    }

    private static boolean zeroLength(String str){
        char[] cs=str.toCharArray();
        for(int i=0;i<cs.length;i++){
            if (cs[i] != '0') {
                return false;
            }
        }
        return true;
    }


    public String multiply1(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int[] res = new int[num1.length() + num2.length()];
        for (int i = num1.length() - 1; i >= 0; i--) {
            int n1 = num1.charAt(i) - '0';
            for (int j = num2.length() - 1; j >= 0; j--) {
                int n2 = num2.charAt(j) - '0';
                int sum = (res[i + j + 1] + n1 * n2);
                res[i + j + 1] = sum % 10;
                res[i + j] += sum / 10;
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < res.length; i++) {
            if (i == 0 && res[i] == 0) continue;
            result.append(res[i]);
        }
        return result.toString();
    }

}
