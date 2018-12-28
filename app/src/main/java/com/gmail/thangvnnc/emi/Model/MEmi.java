package com.gmail.thangvnnc.emi.Model;

public class MEmi {
    public int id = 0;
    public double monthlyInterest = 0;
    public double monthlyPrincipal = 0;
    public double principalBalance = 0;

    public MEmi() {
    }

    public MEmi(int id, double monthlyInterest, double monthlyPrincipal, double principalBalance) {
        this.id = id;
        this.monthlyInterest = monthlyInterest;
        this.monthlyPrincipal = monthlyPrincipal;
        this.principalBalance = principalBalance;
    }
}
