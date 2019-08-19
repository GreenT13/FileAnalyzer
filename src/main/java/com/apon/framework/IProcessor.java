package com.apon.framework;

public interface IProcessor<T, C extends Context> {

    void processObject(T object, C context);

    IProcessor<T, C> setNextProcessor(IProcessor<T, C> nextProcessor);

}
