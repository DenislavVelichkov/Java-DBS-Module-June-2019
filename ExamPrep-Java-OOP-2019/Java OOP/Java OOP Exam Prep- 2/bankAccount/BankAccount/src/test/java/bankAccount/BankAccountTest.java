package bankAccount;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class BankAccountTest {
    private BankAccount bankAccount;

    @Before
    public void setUP() {
        this.bankAccount = new BankAccount("Straxil", new BigDecimal(100.0));
    }

    @Test
    public void getNameShouldReturnCorrectName() {
        Assert.assertEquals("Straxil", this.bankAccount.getName());
    }

    @Test
    public void getBalanceShouldReturnTheBalanceOfTheAccountCorrectly() {
        Assert.assertEquals(new BigDecimal(100.0), this.bankAccount.getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNameShouldThrowExceptionIfNameLengthIsLessThenThreeSymbol() {
        new BankAccount("AB", new BigDecimal(100.0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNameShouldThrowExceptionIfNameLengthIsMoreThenTwentyFiveSymbols() {
        new BankAccount("AbsfasfbasfjsafbaasdDasdaasda", new BigDecimal(100.0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setBalanceShouldThrowExceptionIfAmountIsLessThenZero() {
        new BankAccount("ABDS", new BigDecimal(-1));
    }


    @Test(expected = UnsupportedOperationException.class)
    public void depositMustThrowUnsupportedOperationExceptionIfDepositedAmountIsLessThenZero() {
        this.bankAccount.deposit(new BigDecimal(-100.0));
    }

    @Test
    public void depositShouldAddNewAmountToBalanceCorrectly() {
        this.bankAccount.deposit(new BigDecimal(100.0));
        Assert.assertEquals(new BigDecimal(200.0), this.bankAccount.getBalance());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void withdrawShouldThrowUnsupportedOperationExceptionIfBalanceIsLessThenAmount() {
        this.bankAccount.withdraw(new BigDecimal(200.0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void withdrawShouldThrowUnsupportedOperationExceptionIfAmountIsLessThenZero() {
        this.bankAccount.withdraw(new BigDecimal(-100.0));
    }

    @Test
    public void withdrawShouldSetCorrectly() {
        this.bankAccount.withdraw(new BigDecimal(50.0));
        Assert.assertEquals(new BigDecimal(50.0), this.bankAccount.getBalance());
    }

    @Test
    public void withdrawZero() {
        BigDecimal result = this.bankAccount.withdraw(new BigDecimal(0.0));
        Assert.assertEquals(new BigDecimal(0.0), result);
    }


}