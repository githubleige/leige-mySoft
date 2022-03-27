package cglib.interfaceMethod;

public interface C extends A,B {
    @Override
    default void foo() {
        B.super.foo();
    }
}
