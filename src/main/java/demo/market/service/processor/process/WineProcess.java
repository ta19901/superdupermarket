package demo.market.service.processor.process;

import demo.market.model.Product;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WineProcess implements Process {

    @Override
    public void process(Product product, int day) {
        if (day % 10 == 0) {
            product.setQuality(product.getQuality() + 1);
        }
    }
}
