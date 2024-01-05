package org.transportCompanyProject.interfaces;

import org.transportCompanyProject.entity.Company;

import java.math.BigDecimal;

public interface Accounting {
    void addToExpenses(BigDecimal amount, Company company);
    void addToIncome(BigDecimal amount, Company company);
    BigDecimal calculateProfit(Company company);
}
