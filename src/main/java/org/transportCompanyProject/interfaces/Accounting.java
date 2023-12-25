package org.transportCompanyProject.interfaces;

import org.transportCompanyProject.entity.Company;

import java.math.BigDecimal;

public interface Accounting {
    public void addToExpenses(BigDecimal amount, Company company);
    public void addToIncome(BigDecimal amount, Company company);
    public BigDecimal calculateProfit(Company company);
}
