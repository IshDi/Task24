import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        Scanner scanner = new Scanner(System.in);
        String textStart = "Список возможных товаров для покупки:";
        String textOption = "Выберите товар и количество или введите `end`";

        int[] prices = {80, 100, 75, 200, 150};
        String[] fruit = {"Банан", "Апельсин", "Груша", "Арбуз", "Слива"};

        Basket basket = null;
        ClientLog clLog = new ClientLog();

        Config loadFile = configFunc("load");
        Config saveFile = configFunc("save");
        Config logFile = configFunc("log");

        if (loadFile.isEnabled()) {
            basket = basket.loadFromTxtFile(loadFile.getFileName());
        } else {
            if (saveFile.isEnabled()) {
                saveFile.getFileName().createNewFile();
            }
            basket = new Basket(prices, fruit, new int[prices.length]);
        }

        while (true) {
            System.out.printf("%s \n", textStart);
            for (int i = 0; i < basket.getNamesProduct().length; i++) {
                System.out.printf("%d. %s %d руб/шт \n", i + 1, basket.getNamesProduct()[i], basket.getPricesProduct()[i]);
            }
            System.out.printf("%s \n", textOption);
            String input = scanner.nextLine();
            if (input.equals("end")) {
                basket.printCart();
                if (logFile.isEnabled()) {
                    clLog.exportAsCSV(logFile.getFileName());
                }
                break;
            }
            String[] data = input.split(" ");
            basket.addToCart(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
            clLog.log(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
            if (saveFile.isEnabled()) {
                basket.saveTxt(saveFile.getFileName());
            }
        }
    }

    public static Config configFunc(String optional) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("shop.xml"));
        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        boolean enabled = true;
        String fileName = null;
        String format = null;

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                if (node.getNodeName() == optional) {
                    NodeList loadList =  nodeList.item(i).getChildNodes();
                    for (int j = 0; j < loadList.getLength(); j++) {
                        Node nodeOption = loadList.item(j);
                        if (Node.ELEMENT_NODE == nodeOption.getNodeType()) {
                            if (nodeOption.getNodeName() == "enabled") {
                                enabled = Boolean.parseBoolean(nodeOption.getTextContent());
                            }
                            if (nodeOption.getNodeName() == "fileName") {
                                fileName = nodeOption.getTextContent();
                            }
                            if (nodeOption.getNodeName() == "format") {
                                format = nodeOption.getTextContent();
                            }
                        }
                    }
                }
            }
        }
        return new Config(enabled, fileName, format);
    }
}
