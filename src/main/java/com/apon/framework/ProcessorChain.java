package com.apon.framework;

/**
 * Dummy implementation representing the entry point of a chain of {@link IProcessor}s.
 */
public class ProcessorChain<T, C extends Context> implements IProcessor<T, C> {
    private IProcessor<T, C> nextProcessor;

    @Override
    public IProcessor<T, C> setNextProcessor(IProcessor<T, C> nextProcessor) {
        this.nextProcessor = nextProcessor;
        return nextProcessor;
    }

    @Override
    public void processObject(T object, C context) {
        nextProcessor.processObject(object, context);
    }
}
