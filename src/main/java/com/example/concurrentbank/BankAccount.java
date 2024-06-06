package com.example.concurrentbank;

import java.util.concurrent.locks.ReentrantLock;
import lombok.Data;

@Data
public class BankAccount {

  public ReentrantLock LOCK;
  private final int id;
  public static int count = 1;
  private int sum;

  public BankAccount() {
    this.id = count++;
    LOCK = new ReentrantLock();
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
