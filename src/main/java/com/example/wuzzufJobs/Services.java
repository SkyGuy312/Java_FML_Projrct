package com.example.wuzzufJobs;


import backend.Charts;
import backend.ReturnObject;
import backend.WuzzufPOJO;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import scala.Tuple2;

import java.io.IOException;
import java.util.*;

@Service
public class Services {
//convert pojo function to return list of map to show as json
    private final RestTemplate restTemplate;
public Services(RestTemplateBuilder restTemplateBuilder) {


    restTemplate = restTemplateBuilder.build();
}
    WuzzufPOJO analysis = new WuzzufPOJO();
    Charts ch = new Charts();

    ReturnObject k_means = analysis.k_means();

    public List<Map> display_dataset() {
        List<Map> mapList = new ArrayList<>();
        analysis.getDataset().limit(50).collectAsList().forEach(row->{
            Map<Object,Object> mm = new HashMap<>();
            Arrays.stream(analysis.getDataset().columns()).forEach(c->{
                mm.put(c,row.get(row.fieldIndex(c)));});
            mapList.add(mm);
        });
        return mapList;
    }

    public List<Map> summary() {
        Dataset<Row> dataset = analysis.getDataset();
        List<Map> mapList = new ArrayList<>();
        //print count
        mapList.add(new HashMap<Object,Object>(){{put("count",dataset.count());}});

        //print descripe
        Map<Object,Object> subMap = new HashMap<>();

        dataset.describe().collectAsList().forEach(row->{ //loop over each row
            Map<Object,Object> mm = new HashMap<>();

            Arrays.stream(dataset.describe().columns()).forEach(c->{//loop over each column
                mm.put(c,row.get(row.fieldIndex(c))); //{col:value,col:value}
            });
            //System.out.println(mm);
            mm.remove("summary");//remove dataset descripe row label
            subMap.put(row.getString(0),mm);//{row:{col:value,col:value},row:{col:value,col:value}}
            //System.out.println(subMap);
        });
        mapList.add(new HashMap<Object,Object>(){{put("data_descripe",subMap);}});
        //note** list call map by reference
        //print schema
        Map<Object,Object> subMap2 = new HashMap<>();
        dataset.schema().toList().foreach(sf->{

            subMap2.put(sf.name(),
                    new HashMap<Object,Object>(){{
                        put("dataType",sf.dataType().toString());
                        put("nullable",sf.nullable());
            }}); //{col :{dataType : value, nullable : value}}

            return null;
        });
        mapList.add(new HashMap<Object,Object>(){{put("data_schema",subMap2);}});
        return mapList;
    }

    public List<Map>popular_company(){
        List<Map> mapList = new ArrayList<>();
        Dataset<Row> dataset = analysis.compny_job_count().limit(20);

        dataset.collectAsList().forEach(row->{
            Map<Object,Object> mm = new HashMap<>();
            Arrays.stream(dataset.columns()).forEach(c->{
                mm.put(c,row.get(row.fieldIndex(c)));});
            mapList.add(mm);
        });
        return mapList;
    }

    public List<Map>popular_title (){
        List<Map> mapList = new ArrayList<>();
        Dataset<Row> dataset = analysis.popular_title().limit(20);

        dataset.collectAsList().forEach(row->{
            Map<Object,Object> mm = new HashMap<>();
            Arrays.stream(dataset.columns()).forEach(c->{
                mm.put(c,row.get(row.fieldIndex(c)));});
            mapList.add(mm);
        });
        return mapList;
    }

    public List<Map>popular_area (){
        List<Map> mapList = new ArrayList<>();
        Dataset<Row> dataset = analysis.popular_area().limit(20);

        dataset.collectAsList().forEach(row->{
            Map<Object,Object> mm = new HashMap<>();
            Arrays.stream(dataset.columns()).forEach(c->{
                mm.put(c,row.get(row.fieldIndex(c)));});
            mapList.add(mm);
        });
        return mapList;
    }

    public List<Map>popular_skill (){
        List<Map> mapList = new ArrayList<>();
        Dataset<Row> dataset = analysis.popular_skill().limit(20);

        dataset.collectAsList().forEach(row->{
            Map<Object,Object> mm = new HashMap<>();
            Arrays.stream(dataset.columns()).forEach(c->{
                mm.put(c,row.get(row.fieldIndex(c)));});
            mapList.add(mm);
        });
        return mapList;
    }

    public List<Map>after_factorize (){
        List<Map> mapList = new ArrayList<>();
        Dataset<Row> dataset = analysis.ordinal_encoder(Arrays.asList("Title","Company","Location","Type","Level","YearsExp","Country"));

        dataset.limit(20).collectAsList().forEach(row->{
            Map<Object,Object> mm = new HashMap<>();
            Arrays.stream(dataset.columns()).forEach(c->{
                mm.put(c,row.get(row.fieldIndex(c)));});
            mapList.add(mm);
        });
        return mapList;
    }

    public List<Map>kMeans (){
        List<Map> mapList = new ArrayList<>();
        Dataset<Row> dataset =k_means.getDataset().limit(50);
        dataset.show();
        dataset.limit(20).collectAsList().forEach(row->{
            Map<Object,Object> mm = new HashMap<>();
            Arrays.stream(dataset.columns()).forEach(c->{
                mm.put(c,row.get(row.fieldIndex(c)));});
            mapList.add(mm);
        });
        return mapList;
    }

    public String top_compny_char(){


        try {
            return ch.top_compny_char(analysis.compny_job_count());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String popular_title_char(){


        try {
            return ch.popular_title_char(analysis.popular_title());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String popular_area_char(){


        try {
            return ch.popular_area_char(analysis.popular_area());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String KMean_char(){


        try {
            //return ch.KMean(analysis.k_means().getDlist());
            return ch.KMean(k_means.getDlist());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    }



