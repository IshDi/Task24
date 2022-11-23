import java.io.*;
public class Basket {
    protected int[] pricesProduct;
    protected String[] namesProduct;
    protected int[] basketShop;

    public Basket(int[] pricesProduct, String[] namesProduct, int[] basketShop) {
        this.pricesProduct = pricesProduct;
        this.namesProduct = namesProduct;
        this.basketShop = basketShop;
    }

    public String[] getNamesProduct() {
        return namesProduct;
    }

    public int[] getPricesProduct() {
        return pricesProduct;
    }

    public void addToCart(int productNum, int amount) {
        basketShop[productNum - 1] += amount;
    }

    public void printCart() {
        int counter = 0;
        System.out.println("Ваша корзина:");
        for (int i = 0; i < basketShop.length; i++) {
            if (basketShop[i] > 0) {
                System.out.printf(
                        "%s %d шт. по %d руб./шт. %d руб. в сумме \n",
                        namesProduct[i],
                        basketShop[i],
                        pricesProduct[i],
                        basketShop[i] * pricesProduct[i]
                );
                counter += basketShop[i] * pricesProduct[i];
            }
        }
        System.out.printf("Итого: %d руб.", counter);
    }

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (int price : pricesProduct) {
                out.print(price + " ");
            }
            out.print("\n");
            for (String name : namesProduct) {
                out.print(name + " ");
            }
            out.print("\n");
            for (int arr : basketShop) {
                out.print(arr + " ");
            }
        }
    }

    static Basket loadFromTxtFile(File textFile) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            String[] pricesString = bufferedReader
                    .readLine()
                    .split(" ");

            int[] price = new int[pricesString.length];

            for(int i = 0; i < pricesString.length; i++) {
                price[i] = Integer.parseInt(pricesString[i]);
            }

            String[] names = bufferedReader
                    .readLine()
                    .split(" ");

            String[] basketString = bufferedReader
                    .readLine()
                    .split(" ");

            int[] basket = new int[basketString.length];

            for(int i = 0; i < basketString.length; i++) {
                basket[i] = Integer.parseInt(basketString[i]);
            }

            return new Basket(price, names, basket);
        }
    }
}
