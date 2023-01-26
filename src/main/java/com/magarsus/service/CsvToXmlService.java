package com.magarsus.service;

import com.magarsus.model.User;
import com.magarsus.model.Role;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CsvToXmlService {
    private static final String PATH = "src/main/resources";
    private static final String FILE = PATH + "/input.csv";

    public static List<User> read() {

        String exUser = "";
        List<String> lineList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {

            String line = br.readLine();  // first line contains header data and is ignored.
            while ((line = br.readLine()) != null) {
                lineList.add(line);
            }
        } catch (FileNotFoundException ex) {
            System.err.println(FILE + " --> " + ex.getMessage());
            System.exit(100);
        } catch (IOException ex) {
            System.err.println(FILE + " --> " + ex.getMessage());
            System.exit(100);
        }

        List<User> userList = new ArrayList<>();
        User user = new User();
        for (String line : lineList) {
            String[] entry = line.split(",");
            if (!exUser.equals(entry[0])) {
                user = User.builder().userName(entry[0]).firstName(entry[1]).lastName(entry[2]).email(entry[3]).build();
                userList.add(user);
            }
            user.addRole(new Role(entry[4]));
            exUser = entry[0];
        }
        return userList;
    }

    public static void write(List<User> userList){
        /*
        for (User entity : userList) {
            System.out.println(entity);
        }
        */
        try {
            //Freemarker configuration object
            Configuration cfg = new Configuration();
            Template template = cfg.getTemplate("src/main/resources/template.ftl");

            // Build the data-model
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("users", userList);

            // Console output
            Writer out = new OutputStreamWriter(System.out);
            template.process(data, out);
            out.flush();

            // File output
            Writer file = new FileWriter (new File("src/main/resources/FTL_helloworld.xml"));
            template.process(data, file);
            file.flush();
            file.close();

        } catch (IOException ex) {
            System.err.println(FILE + " --> " + ex.getMessage());
        } catch (TemplateException ex) {
            System.err.println(FILE + " --> " + ex.getMessage());
        }
    }

}
