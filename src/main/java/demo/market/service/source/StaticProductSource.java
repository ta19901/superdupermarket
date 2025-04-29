package demo.market.service.source;

import java.util.List;

public record StaticProductSource(List<ProductDto> products) implements ProductSource {}
