package com.example.wuzzufJobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.*;

@RestController
public class Controller {

    @Autowired
    Services se ;

    @RequestMapping("/show")
    public List<Map> shwoData(){
      return se.display_dataset();
    }

    @RequestMapping("/summary")
    public List<Map> summary(){
        return se.summary();
    }

    @RequestMapping("/popular_company")
    public List<Map> popular_company(){
        return se.popular_company();
    }

    @RequestMapping("/popular_title")
    public List<Map> popular_title(){
        return se.popular_title();
    }

    @RequestMapping("/popular_area")
    public List<Map> popular_area(){
        return se.popular_area();
    }

    @RequestMapping("/popular_skill")
    public List<Map> popular_skill(){
        return se.popular_skill();
    }

    @RequestMapping("/after_factorize")
    public List<Map> after_factorize(){
        return se.after_factorize();
    }

    @RequestMapping("/kMeans")
    public List<Map> kMeans(){
        return se.kMeans();
    }

    @RequestMapping(value = "/company_piechart",produces = IMAGE_JPEG_VALUE)
    public void company_piechart(HttpServletResponse responce) throws IOException {
        responce.setContentType(IMAGE_JPEG_VALUE);
        se.top_compny_char();
        ClassPathResource imgFile = new ClassPathResource("top_compny_char.jpg");
        StreamUtils.copy(imgFile.getInputStream(),responce.getOutputStream());
    }

    @RequestMapping(value = "/popular_title_char",produces = IMAGE_JPEG_VALUE)
    public void popular_title_char(HttpServletResponse responce) throws IOException {
        responce.setContentType(IMAGE_JPEG_VALUE);
        ClassPathResource imgFile = new ClassPathResource(se.popular_title_char());
        StreamUtils.copy(imgFile.getInputStream(),responce.getOutputStream());
    }

    @RequestMapping(value = "/popular_area_char",produces = IMAGE_JPEG_VALUE)
    public void popular_area_char(HttpServletResponse responce) throws IOException {
        responce.setContentType(IMAGE_JPEG_VALUE);
        ClassPathResource imgFile = new ClassPathResource(se.popular_area_char());
        StreamUtils.copy(imgFile.getInputStream(),responce.getOutputStream());
    }

    @RequestMapping(value = "/KMean_char",produces = IMAGE_JPEG_VALUE)
    public void KMean_char(HttpServletResponse responce) throws IOException {
        responce.setContentType(IMAGE_JPEG_VALUE);
        ClassPathResource imgFile = new ClassPathResource(se.KMean_char());
        StreamUtils.copy(imgFile.getInputStream(),responce.getOutputStream());
    }




}
