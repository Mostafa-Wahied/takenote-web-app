package com.mostafawahied.takenotewebapp.util;

import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Utility {
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

//    public static String getWhatsNewContent() throws IOException {
//        Path path = new ClassPathResource("whatsNew.json").getFile().toPath();
//        return new String(Files.readAllBytes(path));
//    }

    // refactor getwhatsnewcontent with more debuggability and print statements because the deployed version is not working and i need logs to debug
    public static String getWhatsNewContent() throws IOException {
        Path path = new ClassPathResource("whatsNew.json").getFile().toPath();
        String content = new String(Files.readAllBytes(path));
        System.out.println("content: " + content);
        return content;
    }
}