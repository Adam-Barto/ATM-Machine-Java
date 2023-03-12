import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    public void history(Account acc){

    }

    public void log(Account acc,String action,double amount){
        try{
            FileWriter fw = new FileWriter("ATM/log.txt", true);
            switch (action){
                case "SavingWithdraw":
                    fw.write("?" + acc.getPinNumber() +" had a Saving Withdraw of "+ amount);
                    break;
                case "CheckingWithdraw":
                    fw.write("?" + acc.getPinNumber() +" had a Checking Withdraw of "+ amount);
                    break;
                case "CheckingDeposit":
                    fw.write("?" + acc.getPinNumber() +" had a Checking Deposit of "+ amount);
                    break;
                case "SavingDeposit":
                    fw.write("?" + acc.getPinNumber() +" had a Saving Deposit of "+ amount);
                    break;
                case "CheckTransfer":
                    fw.write("?" + acc.getPinNumber() +" had a Check Transfer of "+ amount);
                    break;
                case "SavingTransfer":
                    fw.write("?" + acc.getPinNumber() +" had a Saving Transfer of "+ amount);
                    break;
            }
            fw.close();
        }catch(IOException e){
            System.out.println("File not found");
        }

    }
}
