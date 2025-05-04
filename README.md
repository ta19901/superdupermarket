# Superdupermarket

Superdupermarket simulates the management of various products based on their quality, price, and
expiration date. General and product-specific rules update the products accordingly.
When products expire or their quality drops below a certain threshold, they are removed on the same
day.

## Usage

Run `mvn package` to build the application jar.

The application accepts the following parameters:

- `--iterations <iterations>`: Number of iterations (days) to simulate. The default is 50 iterations.
- `--csv <path>`: Path to a CSV containing the products to be simulated. The CSV must not
  contain a header, and must use `,` as delimiter. If no CSV is passed the application provides a
  static list of products.

> [!WARNING]
> You must provide either --csv or all required SQL parameters, not both.

To load data from a **PostgreSQL** database provide following parameters instead of `--csv <path>`:

- `--host <host>`: Host where the database is running.
- `--port <port>`: Port on which the database is listening.
- `--db <database>`: Name of the database.
- `--user <user>`: Name of the user.
- `--password <password>`: Password of the user.

The application expects a table named `products` to exist in the specified database.

### Examples

#### CSV

**example.csv**:

```csv
silvaner, wine, 10, 20, 15.3
spätburgunder, wine, 10, 20, 15.3
emmentaler, cheese, 30, 35, 6.5
le gruyer, cheese, 30, 35, 6.5
double toaster, toaster, -1, 100, 80.0
```

To run the application with `example.csv` and 100 iterations use:

```bash
java -jar app.jar --iterations 100 --csv example.csv
```

#### SQL

You can find the schema used in tests at:
[`src/test/java/resources/sql-init/10-schema.sql`](src/test/java/resources/sql-init/10-schema.sql).

Example PostgreSQL configuration:

| Parameter | Value            |
|-----------|------------------|
| host      | localhost        |
| port      | 5432             |
| database  | superdupermarket |
| user      | admin            |
| password  | nimda            |


To run the application using this configuration run:

```bash
java -jar app.jar \
  --iterations 100 \
  --host localhost \
  --port 5432 \
  --db superdupermarket \
  --user admin \
  --password nimda
```
