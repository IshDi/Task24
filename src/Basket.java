import java.io.*;
import java.util.Arrays;

public class Basket implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public void saveBin(File textFile) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(textFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(new Basket(pricesProduct, namesProduct, basketShop));
        }
    }

    static Basket loadFromBinFile(File textFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(
                new FileInputStream(textFile))) {
            Basket basket = (Basket) objectInputStream.readObject();
            return basket;
        }
    }
}
