package hu.david.giczi.mvmxpert.xlscreator.app;

import hu.david.giczi.mvmxpert.xlscreator.logic.FileProcess;


import java.io.File;

public class LandOwnerXLSListCreatorApp {

    public static void main(String[] args) {

      FileProcess fp = new FileProcess();
      fp.openInputFile();
      if( FileProcess.FOLDER_PATH == null || FileProcess.FILE_NAME == null ){
          System.exit(0);
      }
      fp.readData();
      fp.getReport();
      fp.openSaveFolder();
      fp.saveXLSFile();
    }

}
