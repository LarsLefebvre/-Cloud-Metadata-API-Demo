package com.example.demo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class DemoApplication {
    public static void main(String[] args) {
      SpringApplication.run(DemoApplication.class, args);
    }

    @Controller
    public class MyErrorController implements ErrorController  {
    @RequestMapping("/error")
    public String handleError() {
        return "error";
        }
    }
	@PostMapping("/checkAvailability")
    public String checkAvailability(@RequestParam("itemName") String itemName) {
        // Make an HTTP request to your internal stock exchange URL
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(itemName, String.class);
        
        // Process the result as needed

        return result;
    }

    @PostMapping("/checkRCE")
    public String checkRCE(@RequestParam("RCEName") String itemName) {
        // Make an HTTP request to your internal stock exchange URL
        try {
            Process process = new ProcessBuilder().command("sh", "-c", itemName).start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Command executed successfully.");
                return readProcessOutput(process.getInputStream());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return itemName;
        
    }
    private static String readProcessOutput(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        return output.toString();
    }
}