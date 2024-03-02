package hu.david.giczi.mvmxpert.xlscreator.logic;

import hu.david.giczi.mvmxpert.xlscreator.model.LandOwnerData;
import hu.david.giczi.mvmxpert.xlscreator.utils.Prefix;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileProcess {

    public static List<LandOwnerData> OWNER_DATA;
    private LandOwnerData landOwnerData;
    private String parcelId;

    public void readData()  {
        OWNER_DATA = new ArrayList<>();
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("foldkonyv.txt")).getFile());
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.ISO_8859_1);
             BufferedReader reader = new BufferedReader(isr)
        ) {

            String line;
            while ( (line = reader.readLine()) != null) {
                setParcelId(line);
                setOwnerShip(line);
                setOwnerName(line);
                setOwnerHome(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setParcelId(String line){
        line = line.trim();
        String[] input = line.split("\\s+");
        if( input.length == 1 &&
                input[0].split("/").length == 2 &&
                Character.isDigit(input[0].charAt(0))){
            landOwnerData = new LandOwnerData();
            landOwnerData.getParcelId().add(input[0].trim());
            parcelId = landOwnerData.getParcelId().get(0);
           createLandOwner();
        }
    }

    private void setOwnerShip(String line){
        line = line.trim();
        if( line.startsWith(Prefix.OWNER_SHIP_TEXT ) ) {
            landOwnerData.getOwnerShip().add(line.substring(line.indexOf(":") + 1).trim());
        }
    }

    private void setOwnerName(String line){
        line = line.trim();
        if( line.startsWith(Prefix.OWNER_NAME_TEXT) ) {
            landOwnerData.setOwnerName(line.substring(line.indexOf(":") + 1).trim());
        }
    }

    private void setOwnerHome(String line){
        line = line.trim();
        if( line.startsWith(Prefix.OWNER_HOME_TEXT) ) {
            line = line.split(":")[1].trim();
            String[] addressData = line.split(",");
            if( addressData.length == 1){
                landOwnerData.setStreetAndHouseNumber(addressData[0].trim());
            }
            else{
                landOwnerData.setStreetAndHouseNumber(addressData[1].trim());
            }
            String[] townData = addressData[0].trim().split("\\s+");
            if( townData.length == 1 ){
                landOwnerData.setHomeTown(townData[0]);
            }
            else{
                landOwnerData.setZipCode(townData[0].trim());
                landOwnerData.setHomeTown(townData[1].trim());
            }
            createLandOwner();
        }
    }

    private void createLandOwner(){
        if( landOwnerData != null && landOwnerData.getOwnerShip() != null &&
                landOwnerData.getOwnerName() != null && landOwnerData.getHomeTown() != null &&
                !landOwnerData.getParcelId().isEmpty() && landOwnerData.getStreetAndHouseNumber() != null) {
            addLandOwner();
            landOwnerData = new LandOwnerData();
            landOwnerData.getParcelId().add(parcelId);
        }
    }

    private void addLandOwner(){

        if( !OWNER_DATA.contains(landOwnerData) && !landOwnerData.getOwnerShip().isEmpty() ){
            OWNER_DATA.add(landOwnerData);
            return;
        }
        if( OWNER_DATA.contains(landOwnerData) ) {
         OWNER_DATA.get(
                 OWNER_DATA.indexOf(landOwnerData))
                 .getParcelId()
                 .add(landOwnerData.getParcelId().get(0));
            if( landOwnerData.getOwnerShip().isEmpty() ){
                return;
            }
         OWNER_DATA.get(
                 OWNER_DATA.indexOf(landOwnerData))
                    .getOwnerShip()
                    .add(landOwnerData.getOwnerShip().get(0));
        }
    }
}
