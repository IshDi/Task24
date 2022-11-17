import java.io.*;

public class Basket {
    protected int[] pricesProduct;
    protected String[] namesProduct;
    protected static int[] basketShop;

    public Basket(int[] pricesProduct, String[] namesProduct) {
        this.pricesProduct = pricesProduct;
        this.namesProduct = namesProduct;
        this.basketShop = new int[pricesProduct.length];
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
            for (int arr : basketShop) {
                out.print(arr + " ");
            }
        }
    }

    static Basket loadFromTxtFile(File textFile) throws IOException {
        try (InputStream inputStream = new FileInputStream(textFile)) {
            int currentByte = 0;
            int i = 0;
            String s;

            while ((currentByte = inputStream.read()) != -1) {
                s = Character.toString((char) currentByte);
                if (!s.equals(" ")) {
                    Basket.basketShop[i] = Integer.parseInt(Character.toString(currentByte));
                    i++;
                }
            }
        }
        return null;
    }
}
