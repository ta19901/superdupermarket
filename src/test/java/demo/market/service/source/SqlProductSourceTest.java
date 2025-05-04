package demo.market.service.source;

import demo.market.service.source.SqlProductSource.JdbcConn;
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
        var jdbcConn = new JdbcConn(
                "localhost",
                postgreSQLContainer.getFirstMappedPort(),
                postgreSQLContainer.getDatabaseName(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        );
        var sqlProductSource = new SqlProductSource(jdbcConn);

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