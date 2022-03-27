package cglib.typeTest;

import java.lang.reflect.Method;
import java.lang.reflect.Field;

public class test5 {
    public static void main(String[] args) throws NoSuchMethodException, SecurityException{

        Field[] fields = orderDto.class.getDeclaredFields();
        System.out.println(fields[1].toString());
        Method method=orderDto.class.getDeclaredMethod("getOrderDetail",new Class[]{});
        System.out.println(method.toString());
    }
}

class orderDto{

    private orderDetail orderDetail;

    public cglib.typeTest.orderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(cglib.typeTest.orderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    private String orderType;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}

class orderDetail{
    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    private String orderItemId;
    private String Oid;

    public String getOid() {
        return Oid;
    }

    public void setOid(String oid) {
        Oid = oid;
    }
}
