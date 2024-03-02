package hu.david.giczi.mvmxpert.xlscreator.app;

import hu.david.giczi.mvmxpert.xlscreator.logic.FileProcess;
import hu.david.giczi.mvmxpert.xlscreator.model.LandOwnerData;

public class LandOwnerXLSListCreatorApp {

    public static void main(String[] args) {
      new FileProcess().readData();
      FileProcess.OWNER_DATA.forEach(System.out::println);
    }

}
