package demo.market.service.source;

import demo.market.service.source.SqlProductSource.JdbcQuery;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class SqlProductSourceTest {

    @Container
    public PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17.4")
            .withCopyToContainer(MountableFile.forClasspathResource("sql-init"), "/docker-entrypoint-initdb.d");

    @Test
    void fetchesProductsFromDatabase() {
        var connection = String.format("jdbc:postgresql://localhost:%d/%s", postgreSQLContainer.getFirstMappedPort(),
                postgreSQLContainer.getDatabaseName());
        var jdbcQuery = new JdbcQuery(connection, postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
        var sqlProductSource = new SqlProductSource(jdbcQuery);

        var products = sqlProductSource.products();

        assertThat(products)
                .extracting(ProductDto::name)
                .containsExactly(
                        "silvaner",
                        "spätburgunder",
                        "emmentaler",
                        "le gruyer",
                        "double toaster");
    }
}