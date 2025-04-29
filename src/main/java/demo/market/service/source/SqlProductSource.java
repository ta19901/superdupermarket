package demo.market.service.source;

import demo.market.exception.SqlProductQueryException;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class SqlProductSource implements ProductSource {

    private final JdbcQuery jdbcQuery;

    @Override
    public List<ProductDto> products() {
        try {
            return jdbcQuery.queryProducts();
        } catch (SQLException e) {
            throw new SqlProductQueryException(e);
        }
    }

    @RequiredArgsConstructor
    public static class JdbcQuery {

        private static final String PRODUCTS_QUERY = """
            SELECT *
            FROM products""";

        private final String connection;
        private final String username;
        private final String password;

        private List<ProductDto> queryProducts() throws SQLException {
            try (Connection c = DriverManager.getConnection(connection, username, password);
                var preparedStatement = c.prepareStatement(PRODUCTS_QUERY)) {
                var resultSet = preparedStatement.executeQuery();
                return convertResultSetToProducts(resultSet);
            }
        }

        private List<ProductDto> convertResultSetToProducts(ResultSet resultSet) throws SQLException {
            var products = new ArrayList<ProductDto>();
            while (resultSet.next()) {
                products.add(new ProductDto(
                    resultSet.getString("name"),
                    resultSet.getString("type"),
                    resultSet.getInt("expiry_date"),
                    resultSet.getInt("quality"),
                    resultSet.getDouble("base_price")
                ));
            }

            return products;
        }
    }
}
