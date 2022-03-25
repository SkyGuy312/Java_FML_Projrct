package backend;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
//data access object
public class WuzzufDao{
    private Dataset<Row> dataset;
    public WuzzufDao() {
        Logger.getLogger("org.apache").setLevel(Level.ERROR);
        final SparkSession sparkSession = SparkSession.
                builder (). //factory method in SparkSession class
                        appName ("application name"). //optional appere in console and report
                        master ("local[*]"). //specify how many core we will work with
                        getOrCreate (); //you need it if you only on Windows

        this.dataset = sparkSession.read().option("header",true) //file has header
                .option("inferSchema",true)
                .csv( "src/main/resources/Wuzzuf_Jobs.csv");
        dataset.show(10);
    }


    public Dataset<Row> getDataset() {
        return dataset;
    }
    public void summry (Dataset<Row> dataset){
        System.out.println("dataset count ="+ dataset.count());
        System.out.println("******************describe data************************");
        dataset.describe().show();
        System.out.println("******************data schema************************");
        dataset.printSchema();
    }
    public Dataset<Row> drop_duplicate(Dataset<Row> dataset) {
        System.out.println("***************************removing dublicate*********************");
        Dataset<Row> data= dataset.dropDuplicates();
        System.out.println("**********count of data is "+data.count());
        return data;
    }
    public Dataset<Row> drop_null(Dataset<Row> dataset) {
        System.out.println("***************************removing null*********************");
        Dataset<Row> data= dataset.na().drop();
        System.out.println("**********count of data is "+data.count());
        return data;
    }
    public Dataset<Row> clean_dataset(){
        this.dataset = drop_null(dataset);
        this.dataset =drop_duplicate(dataset);
        return dataset;
    }

}
