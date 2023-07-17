package model;
import java.util.Objects;

public class Customer {

    private int id;
    private String username;
    private String password;
    private Float monthlyBudget;
    private Float yearlyBudget;
    

    public Customer() {
    }

    public Customer(int id, String username, String password, Float monthlyBudget, Float yearlyBudget) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.monthlyBudget = monthlyBudget;
        this.yearlyBudget = yearlyBudget;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Float getMonthlyBudget() {
        return this.monthlyBudget;
    }

    public void setMonthlyBudget(Float monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }

    public Float getYearlyBudget() {
        return this.yearlyBudget;
    }

    public void setYearlyBudget(Float yearlyBudget) {
        this.yearlyBudget = yearlyBudget;
    }

    public Customer id(int id) {
        setId(id);
        return this;
    }

    public Customer username(String username) {
        setUsername(username);
        return this;
    }

    public Customer password(String password) {
        setPassword(password);
        return this;
    }

    public Customer monthlyBudget(Float monthlyBudget) {
        setMonthlyBudget(monthlyBudget);
        return this;
    }

    public Customer yearlyBudget(Float yearlyBudget) {
        setYearlyBudget(yearlyBudget);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Customer)) {
            return false;
        }
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(username, customer.username) && Objects.equals(password, customer.password) && Objects.equals(monthlyBudget, customer.monthlyBudget) && Objects.equals(yearlyBudget, customer.yearlyBudget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, monthlyBudget, yearlyBudget);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", monthlyBudget='" + getMonthlyBudget() + "'" +
            ", yearlyBudget='" + getYearlyBudget() + "'" +
            "}";
    }

}
