package BankManegementSystem;

import java.sql.*;

public class bankOperation {

private static Connection con;

static{
    try {
        con= DriverManager.getConnection("jdbc:mysql://localhost:3306/1ejm10","root","");
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

}

    private static String deposit="insert into bank(account_number,account_name,deposite,balance)" +
            " values(?,?,?,?)";
    private static String withdraw="insert into bank(account_number,account_name,withdraw,balance) values(?,?,?,?)";
    private static String getBalance="SELECT BALANCE FROM BANK WHERE ACCOUNT_NUMBER=? " +
            "ORDER BY TX_ID DESC LIMIT 1;";
    private static String fromAccountQuery="insert into bank(account_number,withdraw,to_account,balance) values(?,?,?,?)";

    private static String toAccountQuery="insert into bank(account_number,deposite,from_account,balance) values(?,?,?,?)";

    public static void deposit(int acc, String name, double bal) {
        try {
            PreparedStatement pstmt1= con.prepareStatement(getBalance);
            pstmt1.setInt(1,acc);
            ResultSet rs= pstmt1.executeQuery();

            if(rs.next()){
                double tempBalance=rs.getDouble(1);
                PreparedStatement pstmt= con.prepareStatement(deposit);
                pstmt.setInt(1,acc);
                pstmt.setString(2,name);
                pstmt.setDouble(3,bal);
                pstmt.setDouble(4,bal+tempBalance);

                int count=pstmt.executeUpdate();

                if(count>0){
                    System.out.println(bal+" DEPOSITED SUCCESSFULLY");
                }else {
                    System.out.println("ERROR !");
                }

            }else{
                PreparedStatement pstmt= con.prepareStatement(deposit);
                pstmt.setInt(1,acc);
                pstmt.setString(2,name);
                pstmt.setDouble(3,bal);
                pstmt.setDouble(4,bal);

                int count=pstmt.executeUpdate();

                if(count>0){
                    System.out.println(bal+" DEPOSITED SUCCESSFULLY");
                }else {
                    System.out.println("ERROR !");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void withdraw(int acc, String name, double bal) {
        PreparedStatement pstmt1= null;
        try {
            pstmt1 = con.prepareStatement(getBalance);
            pstmt1.setInt(1,acc);
            ResultSet rs= pstmt1.executeQuery();

            if(rs.next() && rs.getDouble(1)>=bal) {
                double tempBalance = rs.getDouble(1);

                PreparedStatement pstmt= con.prepareStatement(withdraw);
                pstmt.setInt(1,acc);
                pstmt.setString(2,name);
                pstmt.setDouble(3,bal);
                pstmt.setDouble(4,tempBalance-bal);

                int count=pstmt.executeUpdate();



                if(count>0){
                    System.out.println(bal+" WITHDRAW SUCCESSFULLY");
                }else {
                    System.out.println("ERROR !");
                }

            }else {
                System.out.println("ERROR");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void transfer(int fromAccount, int toAccount, double amount) {
        String check1="select * from bank where account_number=?";
        String check2="select * from bank where account_number=?";
        try {
            PreparedStatement pstmt1=con.prepareStatement(check1);
            pstmt1.setInt(1,fromAccount);
            ResultSet rs1=pstmt1.executeQuery();

            if(rs1.next()){
                PreparedStatement pstmt2=con.prepareStatement(check2);
                pstmt2.setInt(1,toAccount);
                ResultSet rs2=pstmt2.executeQuery();

                if(rs2.next()){

                    PreparedStatement pstmt3=con.prepareStatement(getBalance);
                    pstmt3.setInt(1,fromAccount);
                    ResultSet rs3=pstmt3.executeQuery();
                    if(rs3.next() && rs3.getDouble(1)>=amount){

                        double tempBalance=rs3.getDouble(1);

                        PreparedStatement pstmt4=con.prepareStatement(fromAccountQuery);
                        pstmt4.setInt(1,fromAccount);
                        pstmt4.setDouble(2,amount);
                        pstmt4.setInt(3,toAccount);
                        pstmt4.setDouble(4,tempBalance-amount);
                        int count1=pstmt4.executeUpdate();

                        PreparedStatement pstmt5=con.prepareStatement(getBalance);
                        pstmt5.setInt(1,toAccount);
                        ResultSet rs5=pstmt5.executeQuery();

                        if(rs5.next()){
                            double tempBalance1=rs5.getDouble(1);
                            PreparedStatement pstmt6= con.prepareStatement(toAccountQuery);
                            pstmt6.setInt(1,toAccount);
                            pstmt6.setDouble(2,amount);
                            pstmt6.setInt(3,fromAccount);
                            pstmt6.setDouble(4,tempBalance1+amount);

                            int count2=pstmt6.executeUpdate();

                            if(count1>0 && count2>0){
                                System.out.println(amount+" TRANSFER SUCCESSFULLY");
                            }

                        }

                    }else {
                        System.out.println("INSUFFICIENT BALANCE");
                    }


                }else {
                    System.out.println(toAccount+" NOT FOUND");
                }

            }else {
                System.out.println(fromAccount+" NOT FOUND");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
