package com.ndsl.sddh.image;

import java.util.function.Supplier;

public class DataSupplier<T> implements Supplier<T> {
    private T t;
    private boolean isDirt=false;
    public DataSupplier(T t){
        this.t=t;
    }

    @Override
    public T get() {
        return t;
    }

    public DataSupplier<T> markDirt(boolean b){
        return b?markDirt():this;
    }

    public DataSupplier<T> markDirt(){
        isDirt=true;
        return this;
    }

    public T clean(boolean b){
        this.isDirt=b;
        return t;
    }

    public T clean(){
        return clean(false);
    }

    public boolean isNeedClean(){
        return this.isDirt;
    }

    public void setData(T t){
        this.t=t;
    }
}
