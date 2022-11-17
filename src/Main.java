import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String textStart = "Список возможных товаров для покупки:";
        String textOption = "Выберите товар и количество или введите `end`";

        int[] prices = {80, 100, 75, 200, 150};
        String[] fruit = {"Банан", "Апельсин", "Груша", "Арбуз", "Слива"};
        File file = new File("./basket.txt");

        Basket basket = new Basket(prices, fruit);

        System.out.printf("%s \n", textStart);
        for (int i = 0; i < basket.getNamesProduct().length; i++) {
            System.out.printf("%d. %s %d руб/шт \n", i + 1, basket.getNamesProduct()[i], basket.getPricesProduct()[i]);
        }

        while (true) {
            if (file.exists()) {
                basket.loadFromTxtFile(file);
            } else {
                file.createNewFile();
            }
            System.out.printf("%s \n", textOption);
            String input = scanner.nextLine();
            if (input.equals("end")) {
                basket.printCart();
                break;
            }
            String[] data = input.split(" ");
            basket.addToCart(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
            basket.saveTxt(file);
        }
    }
}
