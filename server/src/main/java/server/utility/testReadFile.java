package server.utility;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import data.Address;
import data.Coordinates;
import data.Organization;
import data.OrganizationType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class testReadFile {
    public static void main(String[] args) {
        String env = "TOAN";
        String path = System.getenv().get(env);
        System.out.println(path);
        Gson gson = new Gson();

        long id = 1;
        String name = "ndndd";
        Coordinates coordinates = new Coordinates(12, 21);
        java.time.LocalDateTime creationDate = LocalDateTime.now();
        long annualTurnover = 10000;
        OrganizationType organizationType = OrganizationType.GOVERNMENT;
        Address address = new Address("nguyen", "100000hnnnn");
        Organization organization = new Organization(
                id,
                name,
                coordinates,
                creationDate,
                annualTurnover,
                organizationType,
                address);
        ArrayList<Organization> collection = new ArrayList<>();
        collection.add(organization);


        System.out.println(collection);
        CollectionFileManager collectionFileManager = new CollectionFileManager(env);
        collectionFileManager.writeCollection(collection);
    }
}
