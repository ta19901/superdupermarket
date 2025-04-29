package demo.market.service.processor;

import demo.market.service.pricing.QualityPricing;
import demo.market.service.processor.process.CheeseProcess;
import demo.market.service.processor.process.DefaultProcess;
import demo.market.service.processor.process.ToasterProcess;
import demo.market.service.processor.process.WineProcess;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessorFactory {

    public static Processor defaultProcessor() {
        return new Processor(
            new DefaultProcess(new QualityPricing()),
            Map.of(
                "wine", new WineProcess(),
                "cheese", new CheeseProcess(new QualityPricing()),
                "toaster", new ToasterProcess()
            )
        );
    }
}
