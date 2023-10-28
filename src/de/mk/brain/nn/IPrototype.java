package de.mk.brain.nn;

public interface IPrototype {

    //* Returns a new instance that has the same parameters as the original instance, but contains no data. **//
    IPrototype cloneEmpty();

    //* Returns a new instance that has the same parameters as the original instance and contains the same data. **//
    IPrototype copyThis();

}
