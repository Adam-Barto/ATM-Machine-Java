import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

public class OptionMenu {
	Scanner menuInput = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");
	HashMap<Integer, Account> data = new HashMap<Integer, Account>();

	public void getLogin() throws IOException {
		boolean end = false;
		int customerNumber = 0;
		int pinNumber = 0;
		while (!end) {
			try {
				System.out.print("\nEnter your customer number: ");
				customerNumber = menuInput.nextInt();
				System.out.print("\nEnter your PIN number: ");
				pinNumber = menuInput.nextInt();
				Iterator it = data.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					Account acc = (Account) pair.getValue();
					if (data.containsKey(customerNumber) && pinNumber == acc.getPinNumber()) {
						getAccountType(acc);
						end = true;
						break;
					}
				}
				if (!end) {
					System.out.println("\nWrong Customer Number or Pin Number");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Character(s). Only Numbers.");
			}
		}
	}

	public void getAccountType(Account acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nSelect the account you want to access: ");
				System.out.println(" Type 1 - Checking Account");
				System.out.println(" Type 2 - Savings Account");
				System.out.println(" Type 3 - Show Statements");
				System.out.println(" Type 4 - Exit");
				System.out.print("\nChoice: ");

				int selection = menuInput.nextInt();

				switch (selection) {
				case 1:
					getChecking(acc);
					break;
				case 2:
					getSaving(acc);
					break;
				case 3:
					getStatements(acc);
					break;
				case 4:
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
	}

	public void getStatements(Account acc){
		System.out.println("\nChecking Account Balance: " + moneyFormat.format(acc.getCheckingBalance())+
				           "\nSavings Account Balance: " + moneyFormat.format(acc.getSavingBalance()));
		saveAccounts(acc);
	}

	public void getChecking(Account acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nChecking Account: ");
				System.out.println(" Type 1 - View Balance");
				System.out.println(" Type 2 - Withdraw Funds");
				System.out.println(" Type 3 - Deposit Funds");
				System.out.println(" Type 4 - Transfer Funds");
				System.out.println(" Type 5 - Exit");
				System.out.print("\nChoice: ");

				int selection = menuInput.nextInt();

				switch (selection) {
				case 1:
					System.out.println("\nChecking Account Balance: " + moneyFormat.format(acc.getCheckingBalance()));
					break;
				case 2:
					acc.getCheckingWithdrawInput();
					break;
				case 3:
					acc.getCheckingDepositInput();
					break;

				case 4:
					acc.getTransferInput("Checking");
					break;
				case 5:
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		saveAccounts(acc);
	}

	public void getSaving(Account acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nSavings Account: ");
				System.out.println(" Type 1 - View Balance");
				System.out.println(" Type 2 - Withdraw Funds");
				System.out.println(" Type 3 - Deposit Funds");
				System.out.println(" Type 4 - Transfer Funds");
				System.out.println(" Type 5 - Exit");
				System.out.print("Choice: ");
				int selection = menuInput.nextInt();
				switch (selection) {
				case 1:
					System.out.println("\nSavings Account Balance: " + moneyFormat.format(acc.getSavingBalance()));
					break;
				case 2:
					acc.getsavingWithdrawInput();
					break;
				case 3:
					acc.getSavingDepositInput();
					break;
				case 4:
					acc.getTransferInput("Savings");
					break;
				case 5:
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		saveAccounts(acc);
	}

	public void createAccount() throws IOException {
		int cst_no = 0;
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nEnter your customer number ");
				cst_no = menuInput.nextInt();
				Iterator it = data.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					if (!data.containsKey(cst_no)) {
						end = true;
					}
				}
				if (!end) {
					System.out.println("\nThis customer number is already registered");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		System.out.println("\nEnter PIN to be registered");
		int pin = menuInput.nextInt();
		data.put(cst_no, new Account(cst_no, pin));
		System.out.println("\nYour new account has been successfuly registered!");
		System.out.println("\nRedirecting to login.............");
		//getLogin();
		mainMenu();
	}
	public void loadAccounts() {
		try {
			List<String> loadAcc = Collections.singletonList(Files.readString(Paths.get("ATM/saveAccounts.txt")));
			FileWriter fw = new FileWriter("ATM/saveAccounts.txt", false);
			fw.write("");
			for (String s: loadAcc) {
				String[] k = s.split("a");
				int customer_Number = Integer.parseInt(k[1]);
				int pin_Number = Integer.parseInt(k[2]);
				double checking_Balance = Double.parseDouble(k[3]);
				double savings_Balance = Double.parseDouble(k[4]);
				data.putIfAbsent(customer_Number,new Account(customer_Number, pin_Number, checking_Balance, savings_Balance));
				}
//			}

		}catch(IOException e){
			System.out.println("Failed");
		}
	}

	public void saveAccounts(Account acc) {
		int customer_Number = acc.getCustomerNumber();
		int pin_Number = acc.getPinNumber();
		double checking_Balance = acc.getCheckingBalance();
		double savings_Balance = acc.getSavingBalance();
		if (data.containsKey(customer_Number)){
			data.remove(customer_Number);
			data.put(customer_Number,new Account(customer_Number, pin_Number, checking_Balance, savings_Balance));
		}
		try{
			FileWriter fw = new FileWriter("ATM/saveAccounts.txt", true);
//			StringBuilder to_save = new StringBuilder(" cn" + customer_Number + "pin" + pin_Number + "ch" + checking_Balance + "sb" + savings_Balance);
			StringBuilder to_save = new StringBuilder(" a" + customer_Number + "a" + pin_Number + "a" + checking_Balance + "a" + savings_Balance);
			fw.write(String.valueOf(to_save));
			fw.close();
		}catch(IOException e){
				System.out.println("File not found");
			}
	}

	public void log(Account acc,String action,double amount){
		try{
			FileWriter fw = new FileWriter("ATM/log.txt", true);
			fw.write("Hi");
			fw.close();
		}catch(IOException e){
			System.out.println("File not found");
		}

	}
	public void mainMenu() throws IOException {
		//data.putIfAbsent(952141, new Account(952141, 191904, 1000, 5000));
		//data.putIfAbsent(123, new Account(123, 123, 20000, 50000));
		saveAccounts(new Account(952141, 191904, 1000, 5000));
		saveAccounts(new Account(123, 123, 20000, 50000));

		boolean end = false;
		loadAccounts();
		while (!end) {
			try {
				System.out.println("\n Type 1 - Login");
				System.out.println(" Type 2 - Create Account");
				System.out.print("\nChoice: ");
				int choice = menuInput.nextInt();
				switch (choice) {
				case 1:
					getLogin();
					end = true;
					break;
				case 2:
					createAccount();
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		System.out.println("\nThank You for using this ATM.\n");
		menuInput.close();
		System.exit(0);
	}
}
