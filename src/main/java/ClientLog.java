import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    protected List<List<Integer>> clLog = new ArrayList<>();

    public ClientLog() {

    }

    public void log(int productNum, int amount) {
        List<Integer> element = new ArrayList<>();
        element.add(productNum);
        element.add(amount);
        clLog.add(element);
    }

    public void exportAsCSV(File txtFile) {
        String[] optional = "productNum,amount".split(",");
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile))) {
            writer.writeNext(optional);
            for (List<Integer> el : clLog) {
                String[] elements = new String[el.size()];
                for (int i = 0; i < el.size(); i++) {
                    elements[i] = String.valueOf(el.get(i));
                }
                writer.writeNext(elements);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
