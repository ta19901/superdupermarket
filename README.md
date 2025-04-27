# Superdupermarket

Superdupermarket simulates the management of various products based on their quality, price, and
expiration date. General and product-specific rules update the products accordingly.
When products expire or their quality drops below a certain threshold, they are removed on the same
day.

## Usage

Run `mvn package` to build the application jar.

The application accepts the following parameters:

- `--csv <path>`: Path to a CSV containing the products to be simulated. The CSV must not
  contain a header, and must use `,` as delimiter. If no CSV is passed the application provides a
  static list of products.
- `--i <iterations>`: Number of iterations (days) to simulate. The default is 50 iterations.

### Example

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
java -jar app.jar --i 100 --csv example.csv
```