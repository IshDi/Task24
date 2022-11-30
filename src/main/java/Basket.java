import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
        JSONObject obj = new JSONObject();

        JSONArray listPricesProduct = new JSONArray();
        JSONArray listNamesProduct = new JSONArray();
        JSONArray listBasketShop = new JSONArray();

        for (int price : pricesProduct) {
            listPricesProduct.add(price);
        }

        for (String name : namesProduct) {
            listNamesProduct.add(name);
        }

        for (int arr : basketShop) {
            listBasketShop.add(arr);
        }

        obj.put("Prices Product", listPricesProduct);
        obj.put("Names Product", listNamesProduct);
        obj.put("Basket Shop", listBasketShop);

        try (FileWriter file = new FileWriter(textFile)) {
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Basket loadFromTxtFile(File textFile) throws IOException {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(textFile));
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray pricesObj = (JSONArray) jsonObject.get("Prices Product");
            int[] price = new int[pricesObj.size()];
            for (int i = 0; i < pricesObj.size(); i++) {
                price[i] = Integer.parseInt(pricesObj.get(i).toString());
            }

            JSONArray namesObj = (JSONArray) jsonObject.get("Names Product");
            String[] names = new String[namesObj.size()];
            for (int i = 0; i < namesObj.size(); i++) {
                names[i] = namesObj.get(i).toString();
            }

            JSONArray basketObj = (JSONArray) jsonObject.get("Basket Shop");
            int[] basket = new int[basketObj.size()];
            for (int i = 0; i < basketObj.size(); i++) {
                basket[i] = Integer.parseInt(basketObj.get(i).toString());
            }

            return new Basket(price, names, basket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }
            return null;
    }
}
