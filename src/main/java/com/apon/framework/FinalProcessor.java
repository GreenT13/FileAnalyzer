package com.apon.framework;

public class FinalProcessor<T, C extends Context> implements IProcessor<T, C> {
    @Override
    public IProcessor<T, C> setNextProcessor(IProcessor<T, C> nextProcessor) {
        return nextProcessor;
    }

    @Override
    public void processObject(T object, C context) { }
}
