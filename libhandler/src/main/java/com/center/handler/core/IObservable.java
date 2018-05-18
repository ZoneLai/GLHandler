package com.center.handler.core;

public interface IObservable<Type> {

    void addObserver(IObserver<Type> observer);

    void notify(Type type);

}
