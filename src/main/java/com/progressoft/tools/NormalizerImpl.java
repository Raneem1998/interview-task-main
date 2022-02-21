package com.progressoft.tools;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NormalizerImpl implements Normalizer {





    @Override
    public ScoringSummary zscore(Path csvPath, Path destPath, String colToStandardize) {

            CSVEditer csvEditer = new CSVEditer();
            ScoringSummaryImpl summary = new ScoringSummaryImpl( csvPath,  destPath,  colToStandardize);


            String [] numbers = summary.getNumbersSTR();
            if(numbers != null) {

                String[] zscores = new String[numbers.length];
                BigDecimal mean = summary.mean();
                BigDecimal standardDeviation = summary.standardDeviation();
                NullChecker(csvPath);
                NullChecker(destPath);
                NullChecker(colToStandardize);
                int i = 0;
                for (String s : numbers) {
                    BigDecimal answer = new BigDecimal(s).subtract(mean);
                    answer = answer.divide(standardDeviation);
                    answer = answer.setScale(3, RoundingMode.HALF_EVEN);
                    zscores[i] = answer.toString();
                    i++;
                }

                try {
                    csvEditer.writeAnswers(zscores, colToStandardize, destPath, csvPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("csv file is empty");
            }

        return summary;
    }

    @Override
    public ScoringSummary minMaxScaling(Path csvPath, Path destPath, String colToNormalize) {
        ScoringSummaryImpl summary = new ScoringSummaryImpl( csvPath,  destPath,  colToNormalize);

        NullChecker(csvPath);
        NullChecker(destPath);
        NullChecker(colToNormalize);
        String [] numbers = summary.getNumbersSTR();
        String [] minMax = new String[numbers.length];
        BigDecimal min = summary.min();
        BigDecimal max = summary.max();

        int i = 0;
        for(String m:numbers)
        {
            BigDecimal answer = new BigDecimal(m).subtract(min);
            answer =answer.divide(max.subtract(min));
            minMax[i] = answer.toString();
            i++;
        }


        return summary;
    }


    public String NullChecker(String name) {
        if (name == null) return "null";
        return name;
    }


    public Path NullChecker(Path path) {
        if (path == null) return Paths.get("null path");
        return path;
    }
}
