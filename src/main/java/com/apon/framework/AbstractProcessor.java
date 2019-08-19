package com.apon.framework;

public abstract class AbstractProcessor<T, C extends Context> implements IProcessor<T, C> {
    protected IProcessor<T, C> nextProcessor;

    @Override
    public IProcessor<T, C> setNextProcessor(IProcessor<T, C> nextProcessor) {
        this.nextProcessor = nextProcessor;
        return nextProcessor;
    }
}
