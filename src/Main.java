import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static List<Customer> customerList;
    static List<Order> orderList;
    static List<Product> productList;

    public static void main(String[] args) {
        //1. a list of products belongs to category “Books” with price > 100
        List<Product> cheapBooks = productList.stream().filter(product -> product.getName().toLowerCase().equals("books"))
                .filter(product -> product.getPrice() > 100)
                .toList();

        //2. a list of orders with at least one product belonging to category “Baby”
        List<Order> babyOrder = orderList.stream().filter(order -> order.getProducts().stream()
                .anyMatch(product -> product.getCategory().toLowerCase().equals("baby")))
                .collect(Collectors.toList());

        //3. a list of products with category = “Toys” and then apply 10% discount
        List<Product> toySales = productList.stream()
                .filter(product -> product.getCategory().toLowerCase().equals("toy"))
                .map(product -> {
                    double discountPrice = product.getPrice() * 0.9;
                    return new Product(product.getId(), product.getName(), product.getCategory(), discountPrice);
                })
                .collect(Collectors.toList());

        //4. a list of products ordered by customers of tier 2 between 01-Feb-2021 and 01-Apr-2021
        List<Product> tierTwoCustomersOrder = orderList.stream()
                .filter(order -> order.getCustomer().getTier() == 2)
                .filter(order -> order.getOrderDate().isAfter(LocalDate.of(2021,1,31)) &&
                        order.getOrderDate().isBefore(LocalDate.of(2021,3,31)))
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.toList());

        //5. get the cheapest product of “Books” category
        Optional<Product> cheapestBook = productList.stream()
                .filter(product -> product.getCategory().toLowerCase().equals("books"))
                .min(Comparator.comparingDouble(Product::getPrice));

        //6. get the 3 most recent placed orders
        List<Order> recentOrders = orderList.stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .collect(Collectors.toList());

        //7. get the order with the highest total price
        Optional<Order> highestTotalPriceOrder = orderList.stream().
                max(Comparator.comparing(order -> order.getProducts().stream().
                        mapToDouble(Product::getPrice).sum()));

        //8. calculate order average payment placed on 14-Mar-2021
        LocalDate auxDate = LocalDate.of(2021, 3, 14);
        double avgPayment = orderList.stream().
                filter(order -> order.getOrderDate().equals(auxDate)).
                mapToDouble(order -> order.getProducts().stream().
                        mapToDouble(Product::getPrice).sum()).
                average().orElse(0.0);

        //9. get the most expensive product by category
        Map<String, Optional<Product>> mostExpensiveProductByCategory = productList.stream().
                collect(Collectors.groupingBy(Product::getCategory,
                        Collectors.maxBy(Comparator.comparing(Product::getPrice))));

        //10. get the the product that was ordered the highest number of times
        Map<Product, Long> productCount = orderList.stream().
                flatMap(order -> order.getProducts().stream()).
                collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Product mostOrderedProduct = productCount.entrySet().stream().
                max(Map.Entry.comparingByValue()).
                map(Map.Entry::getKey).orElse(null);

    }
}