package com.mostafawahied.takenotewebapp.controller;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SitemapController {
// add public and private urls to this list, make sure to include all the urls that exist in all the controllers
    private final List<String> URLS = List.of("/", "/login", "/about", "/privacy-policy", "/register", "/forgot_password", "/dashboard", "/showNewClassroomForm", "/reset_password","/notetaker/reading/1on1", "/notetaker/writing/1on1", "/notetaker/reading/guided_reading","/notetaker/reading/strategy_group", "/notetaker/writing/strategy_group", "/notebook/students", "/notebook/reading", "/notebook/writing","/showNewStudentForm","/importStudents","/notetaker/reading","/notetaker/writing");

    @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String generateSitemap() throws IOException {
        String DOMAIN = "https://takenote.tech";
        WebSitemapGenerator wsg = WebSitemapGenerator.builder(DOMAIN, new File(System.getProperty("user.dir"))).build();
        for (String url : URLS) {
            wsg.addUrl(new WebSitemapUrl.Options(DOMAIN + url).build());
        }
        wsg.write(); // Generate the sitemaps first
        File sitemapFile = new File(System.getProperty("user.dir"), "sitemap.xml");
        return new String(Files.readAllBytes(sitemapFile.toPath()));
    }
}
