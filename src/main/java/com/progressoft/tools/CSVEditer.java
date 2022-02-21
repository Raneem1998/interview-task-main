package com.progressoft.tools;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVEditer {
    static int clmLocation = 0;



    public static String [] ScanCSV(Path csvPath,String colToStandardize ) {

        //IllegalArgumentException
        String inputLine = null;
        Scanner scanIn = null;
        String[] a = null;
        String[] array = null;
        ArrayList numbers = new ArrayList();
        String[] inArray = null; // to have all data in .csv


        try {

            scanIn = new Scanner(new BufferedReader(new FileReader(String.valueOf(csvPath))));
            if (scanIn.hasNextLine()) {
                inputLine = scanIn.nextLine();  // read the csv file header
                inArray = inputLine.split(",");  // save the csv file in "inArray"

                // to get the column location in .csv file

                clmLocation = -1;

                for (int i = 0; i < inArray.length; i++) {
                    if (inArray[i].contentEquals(colToStandardize)) {
                        clmLocation = i;
                        break;
                    }
                }

                if (clmLocation == -1) {
                    throw new IllegalArgumentException("column " + colToStandardize + " not found");
                }
                while (scanIn.hasNextLine()) {
                    inputLine = scanIn.nextLine();  // read the csv file header
                    inArray = inputLine.split(",");  // save the csv file in "inArray"
                    numbers.add(inArray[clmLocation]);
                }
                if (numbers.size() > 0) {
                    array = (String[]) numbers.toArray();
                }

            }// to Add all items on selected  column in .csv file (calculate "sum" and "finalSum" )


        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("source file not found");



    }


        return array;
    }


    public static void writeAnswers(String[] newAnswerClm, String newColName ,  Path destPath ,Path newCSVPath) throws IOException {
        FileReader fileReader;
        BufferedReader bufferedReader;
        try {
            fileReader = new FileReader(newCSVPath.toString());
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("source file not found");
        }
        // to store new file data
        String dataToWrite = "";
        // read file headers and add new col head
        String line = bufferedReader.readLine();
        String[] rowData;
        rowData = line.split(",");
        for (int i = 0; i < rowData.length; i++) {
            dataToWrite += rowData[i] + ((i < rowData.length - 1) ? "," : "");
            if (i == clmLocation) {
                if (i == rowData.length - 1) dataToWrite += ",";
                dataToWrite += newColName;
                if (clmLocation < rowData.length - 1) dataToWrite += ",";
            }
        }
        dataToWrite += "\n";
        // read file values and add new col values
        int idx = 0;
        while ((line = bufferedReader.readLine()) != null) {
            rowData = line.split(",");
            for (int i = 0; i < rowData.length; i++) {
                dataToWrite += rowData[i] + ((i < rowData.length - 1) ? "," : "");
                if (i == clmLocation) {
                    if (i == rowData.length - 1) dataToWrite += ",";
                    dataToWrite += newAnswerClm[idx++];
                    if (clmLocation < rowData.length - 1) dataToWrite += ",";
                }
            }
            dataToWrite += "\n";
        }
        bufferedReader.close();
        fileReader.close();

        //write new data with new col to destPath
        FileWriter fileWriter;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(destPath.toString());
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(dataToWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferedWriter.close();
        fileReader.close();
    }


}





