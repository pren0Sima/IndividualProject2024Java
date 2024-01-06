package org.transportCompanyProject.interfaces;

import org.transportCompanyProject.exceptions.AmountShouldBePositiveException;

import java.math.BigDecimal;

public interface Accounting<T> {
    void addToBalance(BigDecimal amount, T object) throws AmountShouldBePositiveException;
    void subtractFromBalance(BigDecimal amount, T object) throws AmountShouldBePositiveException;
}
