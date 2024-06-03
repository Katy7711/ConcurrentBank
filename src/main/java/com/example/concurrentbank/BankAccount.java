package com.example.concurrentbank;

import static com.example.concurrentbank.ConcurrentBank.LOCK;

import lombok.Data;

@Data
public class BankAccount {

  private final int id;
  public static int count = 1;
  private int sum;

  public BankAccount() {
    this.id = count++;
  }


  public void deposit(BankAccount bankAccount, int sum) {
    LOCK.lock();
    try {
      if (bankAccount.getSum() == 0) {
        bankAccount.setSum(sum);
      }
      bankAccount.setSum(bankAccount.getSum() + sum);
    } finally {
      LOCK.unlock();
    }
  }


  public void withdraw(BankAccount bankAccount, int sum) {
    LOCK.lock();
    try {
      if (bankAccount.getSum() == 0 || bankAccount.getSum() < sum) {
        throw new RuntimeException("Недостаточно средств");
      }
      bankAccount.setSum(bankAccount.getSum() - sum);
    } finally {
      LOCK.unlock();
    }
  }

  public int getBalance() {
    return getSum();
  }

}
