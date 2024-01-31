package com.xinpeng.mp2projectfr;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.net.URL;
import java.util.Scanner;
@Controller
public class FormController {
    private final Form[] forms = new Form[420];

    private int index = 0;
    private static String generateUUID() {
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        String uuid = "";
        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(characters.length());
            uuid += characters.substring(randomIndex,randomIndex+1);
        }
        int count = 0;
        while (true){
            if (count == 9) {
                return uuid;
            }
            int randomIndex = random.nextInt(characters.length());
            uuid += characters.substring(randomIndex,randomIndex+1);
            count++;
        }
    }
    @GetMapping("/form")
    public String formForm(Model model) {
        Form form = new Form();
        form.setId(generateUUID());
        model.addAttribute("form", form);
        return "form";
    }





    @PostMapping("/form")
    public String greetingSubmit(@ModelAttribute Form form, Model model) {
        if (index < forms.length) {
            forms[index++] = form;
        }

        // Get the content from the form
        String content = form.getContent();

        ProcessBuilder processBuilder = new ProcessBuilder("py", "C:\\Users\\xinpe\\Downloads\\rfgwfgwgf.py", "--content", content);
        StringBuilder sentimentResult = new StringBuilder();

        try {
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sentimentResult.append(line);
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("form", form);
        model.addAttribute("sentiment", sentimentResult.toString());
        return "result";
    }


    @GetMapping("/forms")
    public String getForms(Model model) {
        model.addAttribute("forms", forms);
        return "forms";
    }
    @GetMapping("/getFormsData")
    public ResponseEntity<Form[]> getFormsData() {
        return ResponseEntity.ok(forms);
    }

}