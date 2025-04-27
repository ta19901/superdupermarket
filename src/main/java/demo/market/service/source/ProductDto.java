package demo.market.service.source;

public record ProductDto(
    String name,
    String type,
    int expiryDate,
    int quality,
    double basePrice
) {}
