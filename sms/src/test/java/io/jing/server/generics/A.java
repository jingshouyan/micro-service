package io.jing.server.generics;

import lombok.Data;

@Data
public class A<T extends A> implements Comparable<T> {

    T old;
    @Override
    public int compareTo(T o) {
        return 0;
    }
}
