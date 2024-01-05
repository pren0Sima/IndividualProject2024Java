package org.transportCompanyProject.interfaces;

import org.transportCompanyProject.exceptions.AmountShouldBePositiveException;

import java.math.BigDecimal;

public interface Accounting<T> {
    void addToBalance(BigDecimal amount, T entity) throws AmountShouldBePositiveException;
    void subtractFromBalance(BigDecimal amount, T entity) throws AmountShouldBePositiveException;
}
