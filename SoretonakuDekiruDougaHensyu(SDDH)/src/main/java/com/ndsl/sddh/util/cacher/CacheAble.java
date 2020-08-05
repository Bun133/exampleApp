package com.ndsl.sddh.util.cacher;

public abstract class CacheAble<T> {
    protected boolean isLoaded=true;
    protected T data;
    public CacheAble(T t){this.data=t;}

    public abstract void flush();
    public abstract void reload();
    public abstract int getIndex();
    public abstract void setIndex(int index);
    public abstract T get();
}
