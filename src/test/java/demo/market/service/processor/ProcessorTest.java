package demo.market.service.processor;

import demo.market.model.Product;
import demo.market.service.processor.process.CheeseProcess;
import demo.market.service.processor.process.DefaultProcess;
import demo.market.service.processor.process.WineProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProcessorTest {

    @Mock
    private DefaultProcess defaultProcess;
    @Mock
    private CheeseProcess cheeseProcess;
    @Mock
    private WineProcess wineProcess;

    @DisplayName("delegates to correct process based on product")
    @Test
    void delegatesToCorrectProcessBasedOnProduct() {
        var processMap = Map.of(
            "cheese", cheeseProcess,
            "wine", wineProcess
        );
        var processor = new Processor(defaultProcess, processMap);
        var cheese = Product.builder().type("cheese").build();
        var wine = Product.builder().type("wine").build();
        var unknown = Product.builder().type("unknown").build();

        processor.process(unknown, 0);
        processor.process(cheese, 0);
        processor.process(wine, 0);

        verify(defaultProcess).process(unknown, 0);
        verify(cheeseProcess).process(cheese, 0);
        verify(wineProcess).process(wine, 0);
    }
}
