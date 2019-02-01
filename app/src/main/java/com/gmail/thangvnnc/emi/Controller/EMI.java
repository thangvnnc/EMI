package com.gmail.thangvnnc.emi.Controller;

import java.lang.Math;

public class EMI {

//    public static void main(String[] args) {
//        System.out.println("rate :" + calInterest(8000000, 1175987, 8));
//        System.out.println("emi :" + calEmi(8000000, 3.75, 8));
//        System.out.println("totalPayment :" + calTotalAll(8000000, 3.75, 8));
//        System.out.println("totalInterestRatePayment :" + calTotalInterestRatePayment(8000000, 3.75, 8));
//    }
//
//    public static void calculate(double loanAmount, double interest, double nPayments) {
//        double temp = Math.pow(1 + interest, nPayments);
//        double monthly = (loanAmount * temp * interest) / (temp - 1);
//        System.out.println("Hang thang tra: " + Math.round(monthly));
//        System.out.println("Tong cong gop lai va goc: " + Math.round(monthly * nPayments));
//        System.out.println("Tong so tien lai: " + Math.round((monthly * nPayments) - loanAmount));
//    }

    public static double calculatePercent(double loanAmount, double totalInterestPayment, double nPayments) {
        double precent = ((totalInterestPayment / nPayments) * 100) / loanAmount;
        return Math.round(precent * 100) / 100d;
    }

    public static double calInterest(double loanAmount, double emi, double nPayments) {
        double ipmt = emi / 2;
        double newstep = ipmt / 2;
        double step = 0;
        double i = 1000;
        while (newstep > 0.001 && step != newstep && i > 0) {
            step = newstep;
            if ((ipmt / loanAmount) > (Math.pow(emi / (emi - ipmt), (1 / nPayments)) - 1)) {
                ipmt += step;
            } else {
                ipmt -= step;
            }
            ipmt = Math.round(ipmt * 1000) / 1000;
            newstep = Math.round(step / 2 * 1000) / 1000;
            i--;
        }

        double interest = (Math.pow(emi / (emi - ipmt), (1 / nPayments)) - 1) * 100;
        return Math.round(interest * 100) / 100d;
    }

    public static double calEmi(double loanAmount, double interest, double nPayments) {
        double percent = interest / 100;
        double temp = Math.pow(1 + percent, nPayments);
        return (loanAmount * temp * percent) / (temp - 1);
    }

    public static double calTotalInterestAll(double loanAmount, double interest, double nPayments) {
        double monthly = calEmi(loanAmount, interest, nPayments);
        return monthly * nPayments;
    }

    public static double calTotalInterestRatePayment(double loanAmount, double interest, double nPayments) {
        double monthly = calEmi(loanAmount, interest, nPayments);
        return (monthly * nPayments) - loanAmount;
    }
}