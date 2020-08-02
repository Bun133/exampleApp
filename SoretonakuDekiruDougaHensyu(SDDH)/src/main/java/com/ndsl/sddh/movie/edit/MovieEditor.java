package com.ndsl.sddh.movie.edit;

import com.ndsl.sddh.image.DataSupplier;

import java.util.function.Supplier;

public abstract class MovieEditor<T> {
    public final Class TType;
    public MovieEditor(Class<T> t_class){
        this.TType = t_class;
    }

    /**
     * May be you will use this method for effecting image or something the same,
     * but,You don't have to DataSupplier#markDirt()
     * @return
     */
    public abstract DataSupplier<T> onEdit(DataSupplier<T> supplier);
}
