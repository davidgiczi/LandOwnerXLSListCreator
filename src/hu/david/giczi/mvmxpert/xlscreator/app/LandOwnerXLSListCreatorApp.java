package hu.david.giczi.mvmxpert.xlscreator.app;

import hu.david.giczi.mvmxpert.xlscreator.logic.FileProcess;
import hu.david.giczi.mvmxpert.xlscreator.model.LandOwnerData;

import java.io.File;

public class LandOwnerXLSListCreatorApp {

    public static void main(String[] args) {
      new FileProcess().readData();

      System.out.println(FileProcess.SUMMA_PARCEL_VALUE);
        System.out.println(FileProcess.INPUT_PARCEL_VALUE);
      //FileProcess.OWNER_DATA.forEach(System.out::println);

    }

}
