package demo.market.service.processor;

import demo.market.model.Product;
import demo.market.service.processor.process.Process;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class Processor {

    private final Process defaultProcess;
    private final Map<String, Process> processMap;

    public void process(Product product, int day) {
        processMap.getOrDefault(product.getType(), defaultProcess).process(product, day);
    }
}
