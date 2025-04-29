package demo.market.utility;

import demo.market.model.Product;
import demo.market.service.source.ProductDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductUtility {

    private static final String WINE = "wine";
    private static final String CHEESE = "cheese";

    public static List<ProductDto> createProducts() {
        return List.of(
                new ProductDto("Riesling", WINE, -1, 49, 10D),
                new ProductDto("Pinot Noir", WINE, -1, 10, 20D),
                new ProductDto("Chardonnay", WINE, -1, 33, 12D),
                new ProductDto("Rosé", WINE, -1, 35, 7D),
                new ProductDto("Brie", CHEESE, 50, 48, 5D),
                new ProductDto("Gouda", CHEESE, 55, 55, 7D),
                new ProductDto("Emmentaler", CHEESE, 80, 49, 6D));
    }

    public static List<Product> mapProducts(List<ProductDto> productDtos) {
        return productDtos.stream().map(ProductUtility::mapProduct).toList();
    }

    public static Product mapProduct(ProductDto productDto) {
        return new Product(
                productDto.name(),
                productDto.type(),
                productDto.expiryDate(),
                productDto.basePrice(),
                productDto.basePrice(),
                productDto.quality(),
                false,
                false);
    }
}
