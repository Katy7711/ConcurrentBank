package com.example.concurrentbank;


public class ConcurrentBankExample {
  public static void main(String[] args) {
    ConcurrentBank bank = new ConcurrentBank();

    // Создание счетов
    BankAccount account1 = bank.createAccount(1000);
    BankAccount account2 = bank.createAccount(500);

    // Перевод между счетами
    Thread transferThread1 = new Thread(() -> bank.transfer(account1, account2,
        200));
    Thread transferThread2 = new Thread(() -> bank.transfer(account2, account1,
       (100)));
    Thread transferThread3 = new Thread(() -> account1.deposit(account1,200));
    Thread transferThread4 = new Thread(() -> account2.deposit(account2,200));
    Thread transferThread5 = new Thread(() -> account1.withdraw(account1,100));
    Thread transferThread6 = new Thread(() -> account2.withdraw(account2,100));

    transferThread1.start();
    transferThread2.start();
    transferThread3.start();
    transferThread4.start();
    transferThread5.start();
    transferThread6.start();
    try {
      transferThread1.join();
      transferThread2.join();
      transferThread3.join();
      transferThread4.join();
      transferThread5.join();
      transferThread6.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Вывод общего баланса
    System.out.println("Total balance: " + bank.getTotalBalance());
    System.out.println("account1 balance: " + account1.getBalance());
    System.out.println("account2 balance: " + account2.getBalance());
    System.out.println("accounts: " + bank.getBankAccountList());

  }

}
