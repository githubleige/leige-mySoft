package cglib.study1;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface WorkflowNode {
    /**
     * 节点编码
     */
    public String nodeCode();
}
