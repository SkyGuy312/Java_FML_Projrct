package backend;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.ml.evaluation.ClusteringEvaluator;
import org.apache.spark.ml.feature.OneHotEncoder;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.DataTypes;
import scala.Tuple2;

import java.util.*;

import static org.apache.spark.sql.functions.*;

//plain old java object
public class WuzzufPOJO {
    //get data
    WuzzufDao dao = new WuzzufDao();
    Dataset<Row> dataset = dao.clean_dataset();

    public Dataset<Row> getDataset() {
        return dataset;
    }

    //clean dataset
    public void cleandata(){
        this.dataset= dao.clean_dataset();
    }

    //4.	Count the jobs for each company and display that in order
    public Dataset<Row> compny_job_count() {

        Dataset<Row>data = this.dataset.
                groupBy("Company").count().alias("jobs_count").orderBy(col("count").desc());
        System.out.println("==============================================");
        System.out.println("most demanding companies for jobs is : '"+ data.first().get(0)+
                "' with number of jobs equal to "+ data.first().get(1) );
        return data;
    }

    //6.	Find out what are the most popular job titles.
    public  Dataset<Row> popular_title() {
        Dataset<Row>data = dataset.
                groupBy("Title").count().alias("jobs_count").orderBy(col("count").desc());
        System.out.println("==============================================");
        System.out.println("most popular job title is : '"+ data.first().get(0)+
                "' with number of jobs equal to "+ data.first().get(1) );
        return data;
    }
    //8.	Find out the most popular areas?
    public  Dataset<Row> popular_area() {
        Dataset<Row>data = dataset.
                        groupBy(col("Location")).
                count().
                orderBy(col("count").desc());
        System.out.println("==============================================");
        System.out.println("most popular job title is : '"+ data.first().get(0)+
                "' with number of jobs equal to "+ data.first().get(1) );
        return data;
    }
    //9.	Show step 8 in bar chart
    //10.	Print skills one by one and how many each repeated and order the output to find out the most important skills required?
    public   Dataset<Row> popular_skill(){
        Dataset<String> skills = dataset.select("Skills")
                .as(Encoders.STRING())           //converted the DataFrame to a Dataset of String
                //Returns a new Dataset by first applying a function to all elements of this Dataset, and then flattening the results.
                .flatMap((FlatMapFunction<String,String>) x->  ///flatmapfunction is functional iterface //
                                Arrays.asList((x.split(","))) //split convet string to list then use arrayaslis
                                        // to convert it to list
                                        .iterator()//itrate over list of strings
                        ,Encoders.STRING()
                );

        Dataset<Row> skillsDf = skills.groupBy(col("value")).count().orderBy(col("count").desc());

        return skillsDf;
    }

    //11.	Factorize the YearsExp feature and convert it to numbers in new col. (Bonus )
    public  Dataset<Row> ordinal_encoder ( List<String> col_name){
        Dataset<Row> dataset =getDataset();
        StringIndexer str_idx = new StringIndexer();

        for (String str :col_name) {
            str_idx.setInputCol(str);
            str_idx.setOutputCol(str+"Idx");
            dataset = str_idx.fit(dataset).transform(dataset);//

            System.out.println("number of class for col '"+str+"'= "+dataset.select(col(str+"Idx")).
                    agg(max(col(str+"Idx").cast(DataTypes.IntegerType))).first().getInt(0));

           // dataset= dataset.drop(col(str));
        }
        return dataset;
    }
    public Dataset<Row> oneHotEncoder (List<String> col_name){
        Dataset<Row> dataset =getDataset();
        StringIndexer indexer = new StringIndexer();
        OneHotEncoder encod = new OneHotEncoder();
        for (String str :col_name) {
            indexer.setInputCol(str);
            indexer.setOutputCol(str+"Idx");
            dataset = indexer.fit(dataset).transform(dataset);
            System.out.println("number of class for col '"+str+"'= "+dataset.select(col(str+"Idx")).
                    agg(max(col(str+"Idx").cast(DataTypes.IntegerType))).first().getInt(0));
            encod.setInputCol(str+"Idx");
            encod.setOutputCol(str+"Vec");
            dataset = encod.fit(dataset).transform(dataset);
           // dataset= dataset.drop(col(str));
            dataset= dataset.drop(col(str+"Idx"));
        }
        return dataset;
    }
    //12.	Apply K-means for job title and companies (Bonus)

    public  ReturnObject k_means(){
        //one hotencoding categorical data
        Dataset<Row> dataset = oneHotEncoder(Arrays.asList("Title","Company","Type","Level","Country","Location","YearsExp"));//,

        ///convert data to vector
        VectorAssembler vector_assembler = new VectorAssembler();
        Dataset<Row>input_data = vector_assembler.setInputCols(new String[]{"TitleVec","CompanyVec","TypeVec","LevelVec","CountryVec","LocationVec","YearsExpVec"}).//,"TypeVec","LevelVec","CountryVec"
                setOutputCol("features").transform(dataset).select("features","Title","Company");
        //train model
        KMeans Kmean = new KMeans();
        List<Double> evaluation = new ArrayList<>();
        double max = 0;
        int cluster_num =2;
        for (int i = 2 ;i<=8 ;i++){
            System.out.println("number of cluster = "+i);
            Kmean.setK(i); //number of classes to seperete on
            KMeansModel model = Kmean.fit(input_data);
            //predict
            Dataset<Row> predict = model.transform(input_data);
            ClusteringEvaluator eva = new ClusteringEvaluator();
            double ev = eva.evaluate(predict);
            evaluation.add(ev);
            if(ev > max){
                max = ev;
                cluster_num = i;
            }
            System.out.println("Silhouette with squared euclidean distance = " + eva.evaluate(predict));
        }
        Kmean.setK(cluster_num); //number of classes to seperete on
        KMeansModel model = Kmean.fit(input_data);
        //predict
        Dataset<Row> predict = model.transform(input_data);
        predict = predict.select("Company","Title","prediction");
        return new ReturnObject(predict,evaluation);
    }



}
