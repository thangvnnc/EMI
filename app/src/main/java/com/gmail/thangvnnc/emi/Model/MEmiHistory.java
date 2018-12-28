package com.gmail.thangvnnc.emi.Model;

public class MEmiHistory {
    public int id;
    public double loanAmount;
    public double emi;
    public double interest;
    public int nPayment;

    public MEmiHistory() {
    }

    public MEmiHistory(int id, double loanAmount, double emi, int nPayment) {
        this.id = id;
        this.loanAmount = loanAmount;
        this.emi = emi;
        this.nPayment = nPayment;
    }

    public MEmiHistory(double loanAmount, double emi, int nPayment) {
        this.loanAmount = loanAmount;
        this.emi = emi;
        this.nPayment = nPayment;
    }
}
