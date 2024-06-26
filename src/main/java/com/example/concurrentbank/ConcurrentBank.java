package com.example.concurrentbank;


import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ConcurrentBank {

  private List<BankAccount> bankAccountList;

  public ConcurrentBank() {
    bankAccountList = new ArrayList<>();
  }

  public BankAccount createAccount(int sum) {
    BankAccount bankAccount = new BankAccount();
    bankAccount.setSum(sum);
    bankAccountList.add(bankAccount);
    return bankAccount;
  }

  public void transfer(BankAccount bankAccount1, BankAccount bankAccount2, int sum) {
    bankAccount1.getLOCK().lock();
    bankAccount2.getLOCK().lock();
    try {
      if (bankAccount1.getSum() == 0 || bankAccount1.getSum() < sum) {
        throw new RuntimeException("Недостаточно средств");
      }

      bankAccount1.setSum(bankAccount1.getSum() - sum);
      bankAccount2.setSum(bankAccount2.getSum() + sum);
    } finally {
      bankAccount1.getLOCK().unlock();
      bankAccount2.getLOCK().unlock();
    }
  }

  public int getTotalBalance() {
    return bankAccountList.stream().mapToInt(BankAccount::getSum).sum();
  }
}
