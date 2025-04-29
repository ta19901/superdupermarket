package demo.market.service.source;

import demo.market.exception.CsvParsingException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CsvProductSource implements ProductSource {

    private final CsvProductParser parser;
    private final String csv;
    private final Path csvPath;

    public CsvProductSource(String delimiter, String csv) {
        this.parser = new CsvProductParser(Pattern.quote(delimiter));
        this.csv = csv;
        this.csvPath = null;
    }

    public CsvProductSource(String delimiter, Path csvPath) {
        this.parser = new CsvProductParser(Pattern.quote(delimiter));
        this.csv = null;
        this.csvPath = csvPath;
    }

    @Override
    public List<ProductDto> products() {
        try {
            return getProducts();
        } catch (Exception e) {
            throw new CsvParsingException(e);
        }
    }

    private List<ProductDto> getProducts() throws IOException {
        if (csvPath != null) {
            return parser.parseCsv(csvPath);
        } else {
            return parser.parseCsv(csv);
        }
    }

    private record CsvProductParser(String delimiter) {

        private List<ProductDto> parseCsv(String csv) throws IOException {
            return doParse(new BufferedReader(new StringReader(csv)));
        }

        private List<ProductDto> parseCsv(Path csv) throws IOException {
            return doParse(new BufferedReader(new FileReader(csv.toFile())));
        }

        private ArrayList<ProductDto> doParse(BufferedReader br) throws IOException {
            var products = new ArrayList<ProductDto>();

            try (br) {
                String line;
                while ((line = br.readLine()) != null) {
                    var lineValues = Arrays.stream(line.split(delimiter))
                            .map(String::trim)
                            .toList();
                    products.add(createProductDto(lineValues));
                }
            }

            return products;
        }

        private ProductDto createProductDto(List<String> lineValues) {
            if (lineValues.size() != 5) {
                throw new CsvParsingException(
                        "Incorrect number of columns. Must be exactly 5 but was " + lineValues.size() + ".");
            }

            return new ProductDto(
                    lineValues.get(0),
                    lineValues.get(1),
                    Integer.parseInt(lineValues.get(2)),
                    Integer.parseInt(lineValues.get(3)),
                    Double.parseDouble(lineValues.get(4)));
        }
    }
}
