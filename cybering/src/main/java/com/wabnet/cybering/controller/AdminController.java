package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.bases.SimpleString;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Admins;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.users.AdminRepository;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import org.json.*;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
public class AdminController {
    private final AdminRepository adminRepository;
    private final AuthenticationRepository authenticationRepository;
    private final ProfessionalRepository professionalRepository;

    public AdminController(AdminRepository adminRepository, AuthenticationRepository authenticationRepository, ProfessionalRepository professionalRepository) {
        this.adminRepository = adminRepository;
        this.authenticationRepository = authenticationRepository;
        this.professionalRepository = professionalRepository;
    }

    @PostMapping(value = "/admin/cybering", headers = "action=admin-check")
    public SimpleString checkAdmin(@RequestHeader HttpHeaders httpHeaders) {
        String cookie = httpHeaders.getFirst("Cookies");
        if ( cookie == null ) {
            System.out.println("\tAttempt to visit admin page without set cookie");
            return new SimpleString("no");
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if(token == null) {
            System.out.println("\tThe cookie doesn't match the records in admin page");
            return new SimpleString("no");
        }
        Optional<Admins> admins = this.adminRepository.findById(token.getProfid());
        if ( admins.isEmpty() ) {
            System.out.println("\tAttempt to visit admin page from: " + token.getProfid());
            return new SimpleString("no");
        }
        return new SimpleString("ok");
    }

    @PostMapping(value = "/admin/cybering", headers = "action=admin-list")
    public Professional[] list(@RequestHeader HttpHeaders httpHeaders) {
        System.out.println("\tAdmin request: list");
        String cookie = httpHeaders.getFirst("Cookies");
        if ( cookie == null ) {
            System.out.println("\tAttempt to visit admin page without set cookie");
            return null;
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if(token == null) {
            System.out.println("\tThe cookie doesn't match the records in admin page");
            return null;
        }
        Optional<Admins> admins = this.adminRepository.findById(token.getProfid());
        if ( admins.isEmpty() ) {
            System.out.println("\tAttempt to visit admin page from: " + token.getProfid());
            return null;
        }

        List<Professional> professionals = this.professionalRepository.findAll();
        return professionals.toArray(new Professional[0]);
    }

    @PostMapping(value = "/admin/cybering", headers = "action=admin-export")
    public SimpleString export(@RequestHeader HttpHeaders httpHeaders) {
        System.out.println("\tAdmin request: export data");
        String cookie = httpHeaders.getFirst("Cookies");
        if ( cookie == null ) {
            System.out.println("\tAttempt to visit admin page without set cookie");
            return new SimpleString("fail");
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if(token == null) {
            System.out.println("\tThe cookie doesn't match the records in admin page");
            return new SimpleString("fail");
        }
        Optional<Admins> admins = this.adminRepository.findById(token.getProfid());
        if ( admins.isEmpty() ) {
            System.out.println("\tAttempt to visit admin page from: " + token.getProfid());
            return new SimpleString("fail");
        }

        try {
            Runtime.getRuntime().exec("mongoexport --db cybering -c professional --out ./exports/professionals.json");
            Runtime.getRuntime().exec("mongoexport --db cybering -c likes --out ./exports/likes.json");
            Runtime.getRuntime().exec("mongoexport --db cybering -c comments --out ./exports/comments.json");
            Runtime.getRuntime().exec("mongoexport --db cybering -c article --out ./exports/articles.json");
            Runtime.getRuntime().exec("mongoexport --db cybering -c connections --out ./exports/connections.json");
        } catch (IOException e) {
            e.printStackTrace();
            return new SimpleString("fail");
        }
        convertToXML();

        return new SimpleString("success");
    }

    public void convertToXML() {

//        JSONTokener tokener = null;
//        try {
//            tokener = new JSONTokener(Files.newInputStream(Paths.get("professionals.json")).toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        JSONArray array = new JSONArray();
//
//            while(true){
//                try {
//                    if (!(tokener.nextClean() != '\u0000')) break;
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                tokener.back();
//                try {
//                    array.put(tokener.nextValue());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
            // Print XML with each array entry named node
        //System.out.println(XML.toString(array, "node"));

//        StringBuilder buf = new StringBuilder();
//        try {
//            Files.lines(Paths.get("professionals.json")).forEach(line -> {
//                JSONObject jsoObject = null;
//                try {
//                    jsoObject = new JSONObject(line);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                buf.append(XML.toString(jsoObject, "node"))
//                        .append(System.lineSeparator());
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //System.out.println(buf.toString());

//        Path line = Paths.get("professionals.json");
//        System.out.println("Path" + line);
//        OutputStream outputStream;
//        try {
//            if(Files.isRegularFile(line)) {
//
//                //System.out.println("FIlename - " + str.substring(0, str.length()-4));
//
//                outputStream = new FileOutputStream("professionals");
//
//                InputStream content = Files.newInputStream(line);
//
//                try (Reader reader = new BufferedReader(new InputStreamReader(content, StandardCharsets.UTF_8)))
//                {
//                    try {
//                        new JsonStreamXMLWriter(reader, new BufferedWriter(new OutputStreamWriter(outputStream))).convert("UTF-8", "1.0");
//                    } catch (XMLStreamException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }finally{
//
//        }



//        File myObj = new File("professionals.xml");
//        FileWriter myWriter = null;
//        try {
//            myWriter = new FileWriter("professionals.xml");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            myWriter.write(XML.toString(array, "node"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            myWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
