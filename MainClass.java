package BankManegementSystem;

import java.util.Scanner;

public class MainClass {
private static Scanner scanner=new Scanner(System.in);
private static void operation(){
    boolean status=true;
   while (status) {
       System.out.println("1. DEPOSIT");
       System.out.println("2. WITHDRAW");
       System.out.println("3. TRANSFER TO ACCOUNT");
       System.out.println("4. STATEMENT");
       System.out.println("5. EXIT");
       System.out.println("ENTER YOUR CHOICE");
       int choice= scanner.nextInt();
       switch (choice) {
           case 1:
               deposite();
               break;
           case 2:
               withdraw();
               break;
           case 3:
               transfer();
               break;
           case 4:
               break;
           case 5:
               System.out.println("End !");
               status=false;
               break;
           default:
               System.out.println("invalid choice");

       }
   }
}
private static void deposite(){
    System.out.println("ENTER ACCOUNT NUMBER");
    int acc= scanner.nextInt();
    System.out.println("ENTER NAME ");
    String name= scanner.next();
    System.out.println("ENTER BALANCE");
    double bal= scanner.nextDouble();

    bankOperation.deposit(acc,name,bal);
}

    private static void withdraw(){
        System.out.println("ENTER ACCOUNT NUMBER");
        int acc= scanner.nextInt();
        System.out.println("ENTER NAME ");
        String name= scanner.next();
        System.out.println("ENTER BALANCE");
        double bal= scanner.nextDouble();

        bankOperation.withdraw(acc,name,bal);
    }
    private static void transfer(){
        System.out.println("ENTER FROM ACCOUNT");
        int fromAccount= scanner.nextInt();
        System.out.println("ENTER TO ACCOUNT");
        int toAccount= scanner.nextInt();
        System.out.println("ENTER AMOUNT");
        double amount= scanner.nextDouble();

        bankOperation.transfer(fromAccount,toAccount,amount);

    }




    public static void main(String[] args) {
        operation();
    }
}
