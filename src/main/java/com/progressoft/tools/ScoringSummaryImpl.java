package com.progressoft.tools;


import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Path;
import java.util.*;

public class ScoringSummaryImpl implements ScoringSummary {

    private String [] numbersSTR ;
   // double sum = 0;

   public ScoringSummaryImpl(Path csvPath, Path destPath, String colToStandardize)
   {

       this.numbersSTR = CSVEditer.ScanCSV(  csvPath,colToStandardize);


   }
   public String[] getNumbersSTR()
   {
       return numbersSTR;

   }


   public void sortArray(){
     if(numbersSTR != null) {
         int n = numbersSTR.length;
         Arrays.sort(numbersSTR, 0, n, new Comparator<String>() {
             @Override
             public int compare(String o1, String o2) {
                 BigDecimal big1 = new BigDecimal(o1);
                 BigDecimal big2 = new BigDecimal(o2);
                 return big2.compareTo(big1);
             }
         });
     }


   }

    public BigDecimal mean() {
       BigDecimal sum = new BigDecimal(0.0);
        Object s;
        for (String s1:numbersSTR)
        {
            sum.add(new BigDecimal(s1));
        }
        BigDecimal mean = sum.divide(BigDecimal.valueOf(numbersSTR.length));
        return mean;
    }

    @Override
    public BigDecimal standardDeviation() {
        BigDecimal sum = new BigDecimal(0.0);
        BigDecimal standardDeviation;
        BigDecimal x;
        BigDecimal sub;
        BigDecimal mean = mean();
        for (String s1:numbersSTR)
        {
            sum.add(new BigDecimal(s1).subtract(mean).pow(2));
        }
        standardDeviation = sum.divide(BigDecimal.valueOf(numbersSTR.length)).sqrt(new MathContext(100));


        return standardDeviation;
    }

    @Override
    public BigDecimal variance() {
        BigDecimal variance;
        variance = standardDeviation().pow(2);

        return variance;
    }

    @Override
    public BigDecimal median() {
        BigDecimal median = null;
        sortArray();
        int n = numbersSTR.length;
        if (n % 2 == 1) {
            int index = n / 2;
            median = new BigDecimal(numbersSTR[index]);
        } else {

            int index1 = n / 2 ;
            int index2 = index1 - 1;
            BigDecimal first = new BigDecimal(numbersSTR[index1]);
            BigDecimal secound = new BigDecimal(numbersSTR[index2]);


            median = first.add(secound).divide(BigDecimal.valueOf(2));

        }
        return median;
    }

    @Override
    public BigDecimal min() {

       BigDecimal min = new BigDecimal(numbersSTR[0]);




        return min;
    }

    @Override
    public BigDecimal max() {
       int index = numbersSTR.length -1;
       BigDecimal max =new BigDecimal(numbersSTR[index]);
        return max;
    }
}
