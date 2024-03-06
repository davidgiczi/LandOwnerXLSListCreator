package hu.david.giczi.mvmxpert.xlscreator.logic;

import hu.david.giczi.mvmxpert.xlscreator.model.LandOwnerData;
import hu.david.giczi.mvmxpert.xlscreator.utils.Prefix;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileProcess {

    public static String FILE_NAME;
    public static String FOLDER_PATH;
    public static List<LandOwnerData> OWNER_DATA;
    public static String SUMMA_PARCEL_VALUE = "0";
    public static int INPUT_PARCEL_VALUE;
    private LandOwnerData landOwnerData;
    private String parcelId;


    public void readData()  {
        OWNER_DATA = new ArrayList<>();
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(FOLDER_PATH +"/" + FILE_NAME + ".txt" );
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
                setSummaParcelValue(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setParcelId(String line){
        line = line.trim();
        String[] inputData = line.split("\\s+");
        String[] idData = inputData[0].split("/");
        if( inputData.length == 1 && idData.length == 2 &&
                isNumber(idData[0]) && isNumber(idData[1])  ){
            INPUT_PARCEL_VALUE++;
            landOwnerData = new LandOwnerData();
            landOwnerData.getParcelId().add(inputData[0].trim());
            parcelId = landOwnerData.getParcelId().get(0);
           createLandOwner();
        }
        else if(inputData.length == 1 && idData.length == 1 &&
        isNumber(idData[0]) ){
            INPUT_PARCEL_VALUE++;
            landOwnerData = new LandOwnerData();
            landOwnerData.getParcelId().add(inputData[0].trim());
            parcelId = landOwnerData.getParcelId().get(0);
            createLandOwner();
        }
    }

    private boolean isNumber(String numberValue) {
        try {
            Integer.parseInt(numberValue);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void setOwnerShip(String line){
        line = line.trim();
        if( line.startsWith( Prefix.OWNER_SHIP_TEXT ) ) {
            landOwnerData.getOwnerShip().add(line.substring(line.indexOf(":") + 1).trim());
        }
    }

    private void setOwnerName(String line){
        line = line.trim();
        if( line.startsWith( Prefix.OWNER_NAME_TEXT ) ) {
            landOwnerData.setOwnerName(line.substring(line.indexOf(":") + 1).trim());
        }
    }

    private void setOwnerHome(String line){
        line = line.trim();
        if( line.startsWith( Prefix.OWNER_HOME_TEXT ) ) {
            line = line.split(":")[1].trim();
            String[] addressData = line.split(",");
            if( addressData.length == 1 ){
                landOwnerData.setStreetAndHouseNumber(addressData[0].trim());
            }
            else{
                landOwnerData.setStreetAndHouseNumber(addressData[1].trim());
            }
            String[] townData = addressData[0].trim().split("\\s+");
            if( townData.length == 1 ){
                landOwnerData.setZipCode(townData[0].trim());
                landOwnerData.setHomeTown(townData[0].trim());
            }
            else{
                landOwnerData.setZipCode(townData[0].trim());
                landOwnerData.setHomeTown(townData[1].trim());
            }
            createLandOwner();
        }
    }

    private void createLandOwner(){
        if( landOwnerData != null &&
                !landOwnerData.getOwnerShip().isEmpty() &&
                landOwnerData.getOwnerName() != null &&
                landOwnerData.getZipCode() != null &&
                landOwnerData.getHomeTown() != null &&
                !landOwnerData.getParcelId().isEmpty() &&
                landOwnerData.getStreetAndHouseNumber() != null ) {
            addLandOwner();
            landOwnerData = new LandOwnerData();
            landOwnerData.getParcelId().add(parcelId);
        }
    }

    private void addLandOwner(){

        if( !OWNER_DATA.contains(landOwnerData) ){
            OWNER_DATA.add(landOwnerData);
        }
        else {
         OWNER_DATA.get(
                 OWNER_DATA.indexOf(landOwnerData))
                 .getParcelId()
                 .add(landOwnerData.getParcelId().get(0));
         OWNER_DATA.get(
                 OWNER_DATA.indexOf(landOwnerData))
                    .getOwnerShip()
                    .add(landOwnerData.getOwnerShip().get(0));
        }
    }

    private void setSummaParcelValue(String line){

        if( line.trim().startsWith(Prefix.SUMMA_PARCEL_TEXT) ){
            SUMMA_PARCEL_VALUE = line.substring(line.indexOf(":") + 1).trim();
        }
    }

    public void openInputFile() {
        JFileChooser jfc = new JFileChooser(){

            private static final long serialVersionUID = 1L;

            @Override
            protected JDialog createDialog( Component parent ) throws HeadlessException {
                JDialog dialog = super.createDialog( parent );
                dialog.setLocationRelativeTo(null);
                dialog.setIconImage(
                        new ImageIcon(Objects.requireNonNull(
                                this.getClass().getResource("/MVM.jpg"))).getImage() );
                return dialog;
            }
        };
        jfc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
            }

            @Override
            public String getDescription() {
                return "*.txt";
            }
        });
        jfc.setCurrentDirectory(FOLDER_PATH == null ?
                FileSystemView.getFileSystemView().getHomeDirectory() : new File(FOLDER_PATH));
        jfc.setDialogTitle("Földkönyv fájl megnyitása");
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            FILE_NAME = selectedFile.getName().substring(0, selectedFile.getName().indexOf("."));
            FOLDER_PATH = selectedFile.getParent();
        }
        else {
            FOLDER_PATH = null;
            FILE_NAME = null;
        }
    }

    public void openSaveFolder() {
        JFileChooser jfc = new JFileChooser(){

            private static final long serialVersionUID = 1L;

            @Override
            protected JDialog createDialog( Component parent ) throws HeadlessException {
                JDialog dialog = super.createDialog( parent );
                dialog.setLocationRelativeTo(null);
                dialog.setIconImage(
                        new ImageIcon(Objects.requireNonNull(
                                this.getClass().getResource("/MVM.jpg"))).getImage() );
                return dialog;
            }
        };
        jfc.setCurrentDirectory(FOLDER_PATH == null ?
                FileSystemView.getFileSystemView().getHomeDirectory() : new File(FOLDER_PATH));
        jfc.setDialogTitle("Földkönyv fájl mentése");
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            FOLDER_PATH = selectedFile.getPath();
        }
        else{
            FOLDER_PATH = null;
            FILE_NAME = null;
        }
    }


    public void getReport(){
        String gotDataValue = "Földhivatal által kiadott földrészletek száma: " + SUMMA_PARCEL_VALUE + " db";
        String inputDataValue = "Feldolgozott földrészletek száma: " + INPUT_PARCEL_VALUE + " db";
        String landOwnerValue = "Tulajdonosok száma: " + OWNER_DATA.size() + " db";
        getInfoMessage("Metaadatok", gotDataValue + "\n" + inputDataValue + "\n" + landOwnerValue);
    }

    public void saveXLSFile(){
        try(XSSFWorkbook workbook = new XSSFWorkbook();
            OutputStream fileOut = Files.newOutputStream(Paths.get(FOLDER_PATH + "/" + FILE_NAME + ".xlsx"));) {
            XSSFSheet sheet = workbook.createSheet("Tulajdonosi adatok");
            addHeader(workbook, sheet);
            addLandOwnerData(workbook, sheet);
            workbook.write(fileOut);
        } catch (IOException e){
            getInfoMessage("\"" + FILE_NAME + ".xlsx\"", "Fájl mentése sikertelen.");
           return;
        }
        getInfoMessage("Tulajdonosi adatok mentve",
                "\"" + FILE_NAME + ".xlsx\" fájl mentve az alábbi mappába:\n" + FOLDER_PATH );
    }

    private void addLandOwnerData(XSSFWorkbook workbook, XSSFSheet sheet){
        CellStyle dataStyle = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);
        dataStyle.setFont(font);
        int row = 1;
        for( int i = 0; i < FileProcess.OWNER_DATA.size(); i++) {
            Row dataRow = sheet.createRow(i + row );
            Cell c0 = dataRow.createCell(0);
            c0.setCellStyle(dataStyle);
            c0.setCellValue(i + 1);
            Cell c1 = dataRow.createCell(1);
            c1.setCellStyle(dataStyle);
            c1.setCellValue(FileProcess.OWNER_DATA.get(i).getOwnerName());
            Cell c2 = dataRow.createCell(2);
            c2.setCellStyle(dataStyle);
            c2.setCellValue(FileProcess.OWNER_DATA.get(i).getHomeTown());
            Cell c3 = dataRow.createCell(3);
            c3.setCellStyle(dataStyle);
            c3.setCellValue(FileProcess.OWNER_DATA.get(i).getZipCode());
            Cell c4 = dataRow.createCell(4);
            c4.setCellStyle(dataStyle);
            c4.setCellValue(FileProcess.OWNER_DATA.get(i).getStreetAndHouseNumber());
            Cell c5 = dataRow.createCell(5);
            c5.setCellStyle(dataStyle);
            c5.setCellValue(FileProcess.OWNER_DATA.get(i).getParcelId().get(0));
            Cell c6 = dataRow.createCell(6);
            c6.setCellStyle(dataStyle);
            c6.setCellValue(FileProcess.OWNER_DATA.get(i).getOwnerShip().get(0));
            int j;
            for (j = 1; j < FileProcess.OWNER_DATA.get(i).getParcelId().size(); j++) {
                dataRow = sheet.createRow(i + j + row);
                Cell c = dataRow.createCell(5);
                c.setCellValue(FileProcess.OWNER_DATA.get(i).getParcelId().get(j));
                c = dataRow.createCell(6);
                c.setCellValue(FileProcess.OWNER_DATA.get(i).getOwnerShip().get(j));
            }
            row += (j - 1);
        }
 }

    private void addHeader(XSSFWorkbook workbook, XSSFSheet sheet){
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        headerStyle.setFont(font);
        sheet.setColumnWidth(0, 2000);
        for(int i = 1; i < 7; i++){
            sheet.setColumnWidth(i, 7000);
        }
        Row header = sheet.createRow(0);
        Cell c0 = header.createCell(0);
        c0.setCellStyle(headerStyle);
        c0.setCellValue("Sorszám");
        Cell c1 = header.createCell(1);
        c1.setCellStyle(headerStyle);
        c1.setCellValue("Tulajdonos neve");
        Cell c2 = header.createCell(2);
        c2.setCellStyle(headerStyle);
        c2.setCellValue("Település");
        Cell c3 = header.createCell(3);
        c3.setCellStyle(headerStyle);
        c3.setCellValue("Irányítószám");
        Cell c4 = header.createCell(4);
        c4.setCellStyle(headerStyle);
        c4.setCellValue("Utca, házszám");
        Cell c5 = header.createCell(5);
        c5.setCellStyle(headerStyle);
        c5.setCellValue("Helyrajzi szám");
        Cell c6 = header.createCell( 6);
        c6.setCellStyle(headerStyle);
        c6.setCellValue("Tulajdoni hányad");
    }
    public void getInfoMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

}
