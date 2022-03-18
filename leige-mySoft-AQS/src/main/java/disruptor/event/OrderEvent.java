package disruptor.event;


/**
 * @author Fox
 * 消息载体(事件)
 * 相当于一个消息
 */

public class OrderEvent {

    private long value;
    private String name;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
