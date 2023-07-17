package model;

import java.sql.Date;
import java.util.Objects;

public class Account {

    private int id;
    private int customerId;
    private Float expense;
    private Date dateIncurred;
    

    public Account() {
    }

    public Account(int id, int customerId, Float expense, Date dateIncurred) {
        this.id = id;
        this.customerId = customerId;
        this.expense = expense;
        this.dateIncurred = dateIncurred;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Float getExpense() {
        return this.expense;
    }

    public void setExpense(Float expense) {
        this.expense = expense;
    }

    public Date getDateIncurred() {
        return this.dateIncurred;
    }

    public void setDateIncurred(Date dateIncurred) {
        this.dateIncurred = dateIncurred;
    }

    public Account id(int id) {
        setId(id);
        return this;
    }

    public Account customerId(int customerId) {
        setCustomerId(customerId);
        return this;
    }

    public Account expense(Float expense) {
        setExpense(expense);
        return this;
    }

    public Account dateIncurred(Date dateIncurred) {
        setDateIncurred(dateIncurred);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return id == account.id && customerId == account.customerId && Objects.equals(expense, account.expense) && Objects.equals(dateIncurred, account.dateIncurred);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, expense, dateIncurred);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", customerId='" + getCustomerId() + "'" +
            ", expense='" + getExpense() + "'" +
            ", dateIncurred='" + getDateIncurred() + "'" +
            "}";
    }

}
