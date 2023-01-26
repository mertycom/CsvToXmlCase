package com.magarsus.service;

import com.magarsus.model.User;
import com.magarsus.model.Role;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.jumpmind.symmetric.csv.CsvReader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CsvToXmlService {
    private static final String PATH = "src/main/resources";
    private static final String FILE = PATH + "/input.csv";
    private static final String FTL = PATH + "/FTL.xml";
    private static final String TEMPLATE = PATH + "/template.ftl";

    public static List<User> read() {
        List<User> userList = new ArrayList<>();
        String exUser = "";
        List<String> lineList = new ArrayList<>();
        try {
            CsvReader csvReader = new CsvReader(FILE);
            if(csvReader.readHeaders()){
                User user = new User();
                while (csvReader.readRecord()) {
                    if(!exUser.equals(csvReader.get("username"))) {
                        user = User.builder()
                                .userName(csvReader.get("username"))
                                .firstName(csvReader.get("firstname"))
                                .lastName(csvReader.get("lastname"))
                                .email(csvReader.get("email"))
                                .build();
                        userList.add(user);
                    }
                    user.addRole(new Role(csvReader.get("role")));

                    exUser = csvReader.get("username");
                }
            }
            csvReader.close();
        } catch (FileNotFoundException ex) {
            System.err.println(FILE + " --> " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println(FILE + " --> " + ex.getMessage());
        }
        return userList;
    }

    public static void write(List<User> userList){

        try (Writer file = new FileWriter (new File(FTL))){

            //Freemarker configuration object
            Configuration cfg = new Configuration();
            Template template = cfg.getTemplate(TEMPLATE);

            // Build the data-model
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("users", userList);

            // Console output
            //Writer out = new OutputStreamWriter(System.out);
            //template.process(data, out);
            //out.flush();

            // File output
            template.process(data, file);
            file.flush();

        } catch (IOException ex) {
            System.err.println(FTL + " --> " + ex.getMessage());
        } catch (TemplateException ex) {
            System.err.println(TEMPLATE + " --> " + ex.getMessage());
        }
    }
}
