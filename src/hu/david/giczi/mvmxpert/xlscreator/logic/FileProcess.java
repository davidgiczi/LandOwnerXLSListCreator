package hu.david.giczi.mvmxpert.xlscreator.logic;

import hu.david.giczi.mvmxpert.xlscreator.model.LandOwnerData;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileProcess {

    public static List<LandOwnerData> OWNER_DATA;
    private LandOwnerData landOwnerData;
    private boolean isParcelId;

    public void readData()  {
        OWNER_DATA = new ArrayList<>();
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("foldkonyv.txt")).getFile());
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)
        ) {

            String line;
            while ( (line = reader.readLine()) != null) {
                setParcelId(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setParcelId(String line){
        line = line.trim();
        String[] input = line.split("\\s+");
        if( input.length == 1 && input[0].split("/").length == 2 &&
                Character.isDigit(input[0].charAt(0))){
            landOwnerData = new LandOwnerData();
            landOwnerData.getParcelId().add(input[0].trim());
            isParcelId = true;
        }
    }

    private void setOwnerShip(String line){

    }

    private void setOwnerName(String line){

    }

    private void setOwnerHome(String line){

    }

}
