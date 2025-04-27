package demo.market.service.processor.process;

import demo.market.model.Product;

public interface Process {

    void process(Product product, int day);
}
