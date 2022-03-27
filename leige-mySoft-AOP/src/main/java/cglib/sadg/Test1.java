package cglib.sadg;

import java.util.ArrayList;
import java.util.List;

public class Test1 {
    public static void main(String[] args) {

        StringBuffer sb = new StringBuffer();

// for (int i = 0; i < 64; i++) {

// sb.append("开始生成第" + i + "个库：\n");

// System.out.println("开始生成第" + i + "个库：

        int i =18;

        for (String s: getTables()){

// sb.append("第"+i+"个库表："+s+"\n");

// System.out.println("第"+i+"个库表："+s);

            for (int j = 0; j * 64 + i < 4096; j++) {

                System.out.println(s+"_"+String.format("%04d",j * 64 + i));

                sb.append(s+"_"+String.format("%04d",j * 64 + i)+"\n");

            }

        }

    }

// try {

// OutputStream os = new FileOutputStream("D:/a.txt");

// os.write(sb.toString().getBytes());

// } catch (Exception e){

// e.printStackTrace();

// }





    public static List<String> getTables(){

        List<String> list = new ArrayList<String>();

        list.add("SO_ST_ORDI_STATUS");

//        list.add("SO_ORDI_PAY");

        list.add("SO_CONSIGNEE_INFO");

//        list.add("SO_ORDI_EXTENDS_INFO");

        list.add("SO_ORD_INVOICE");

//        list.add("SO_ORDI_REWARD_POINTS");

        list.add("SO_SOURCE_ATTRIBUTION_INFO");

        list.add("SO_ORDI_TRACK");

        list.add("SO_ORDI_DELIVERY_INFO");

        list.add("SO_SELLER_INFO");

        list.add("SO_ORDI_VOUCHER");

//        list.add("SO_ORD_RELATIONSHIP");

//        list.add("SO_ORDI_PROMOTION_VOUCHER");

        list.add("SO_ORDI_OUT_RENT_INFO");

        list.add("SO_ORDI_EXTENDS_INFO_O2O");

        list.add("SO_ORDI_PAY_O2O");

        list.add("SO_ORDI_DEAL_TYPE_O2O");

        return list;

    }
}
