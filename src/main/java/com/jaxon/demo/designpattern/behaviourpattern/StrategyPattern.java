package com.jaxon.demo.designpattern.behaviourpattern;

public class StrategyPattern {

    public static void main(String[] args) {
        Strategy s = new StrategyA();
        Context c = new Context();
        c.setStrategy(s);
        c.strategyMethod();
    }
}

interface Strategy{

    void strategyMethod();
}

class Context{
    private Strategy strategy;

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    void strategyMethod(){
        this.strategy.strategyMethod();
    }
}

/**
 * 策略模式就是换个一个实现就调当前实现的方法
 */
class StrategyA implements Strategy{

    @Override
    public void strategyMethod() {
        System.out.println("A method");
    }
}
class StrategyB implements Strategy{

    @Override
    public void strategyMethod() {
        System.out.println("B method");
    }
}