package backend;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;

public class ReturnObject {
    Dataset<Row> dataset;
    List<Double> dlist;

    public Dataset<Row> getDataset() {
        return dataset;
    }

    public void setDataset(Dataset<Row> dataset) {
        this.dataset = dataset;
    }

    public List<Double> getDlist() {
        return dlist;
    }

    public void setDlist(List<Double> dlist) {
        this.dlist = dlist;
    }

    public ReturnObject(Dataset<Row> dataset, List<Double> dlist) {
        this.dataset = dataset;
        this.dlist = dlist;
    }
}
