import java.util.Scanner;

class User {
    // User attributes
    String firstName, lastName, fullName, mobileNumber, mailID, adhaarCard, panCard, addressCity, dateOfBirth, customerID, accountNumber, mPin, ifscCode, loginPassword;
    double balance; // User account balance

    String[] transaction; // Array to store transaction history
    int transactionCount; // Counter for transactions

    // Loan management attributes
    LoanManagement.PersonalLoan personalLoan;
    LoanManagement.HomeLoan homeLoan;

    FixedDeposit[] fixedDeposits; // Array to store fixed deposits
    int fdCount; // Counter for fixed deposits

    // Default constructor
    User() {
    }

    // Parameterized constructor to initialize user details
    User(String firstName, String lastName, String mobileNumber, String mailID, String adhaarCard, String panCard, String addressCity, String dateOfBirth, String accountNumber, String customerID, String mPin, String ifscCode, String loginPassword, double initialBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + " " + lastName;
        this.mobileNumber = mobileNumber;
        this.mailID = mailID;
        this.adhaarCard = adhaarCard;
        this.panCard = panCard;
        this.addressCity = addressCity;
        this.dateOfBirth = dateOfBirth;
        this.customerID = customerID;
        this.accountNumber = accountNumber;
        this.mPin = mPin;
        this.ifscCode = ifscCode;
        this.loginPassword = loginPassword;
        balance = initialBalance;

        // Initialize transaction history and add first transaction (initial balance)
        this.transaction = new String[100];
        this.transactionCount = 0;
        transaction[transactionCount++] = (transactionCount) + ". Initial Balance: " + initialBalance + " | Balance: " + balance;

        // Initialize fixed deposits array
        this.fixedDeposits = new FixedDeposit[10];
        this.fdCount = 0;
    }

    // Method to display user profile details
    void displayProfile() {
        System.out.println("Customer ID    : " + customerID);
        System.out.println("Account number : " + accountNumber);
        System.out.println("IFSC code      : " + ifscCode);
        System.out.println("Name           : " + fullName);
        System.out.println("Mobile number  : " + mobileNumber);
        System.out.println("E-Mail id      : " + mailID);
        System.out.println("Adhaar number  : " + adhaarCard);
        System.out.println("PAN number     : " + panCard);
        System.out.println("City name      : " + addressCity);
        System.out.println("Date of Birth  : " + dateOfBirth);
    }

    // Method to apply for a personal loan
    void applyForPersonalLoan(String purpose, double principal, int termMonths, String morgage) {
        this.personalLoan = new LoanManagement().new PersonalLoan(purpose, principal, termMonths, morgage);
    }

    // Method to apply for a home loan
    void applyForHomeLoan(String purpose, double principal, int termMonths, String morgage) {
        this.homeLoan = new LoanManagement().new HomeLoan(purpose, principal, termMonths, morgage);
    }

    // Method to display details of all active loans
    void displayLoanDetails() {
        if (personalLoan != null) {
            personalLoan.displayLoanDetails();
        }
        if (homeLoan != null) {
            homeLoan.displayLoanDetails();
        }
    }

    // Method to pay EMI for loans
    void payEMI(User[] user, String customerID) {
        if (personalLoan != null) {
            personalLoan.payEMI(user, customerID);
        }
        if (homeLoan != null) {
            homeLoan.payEMI(user, customerID);
        }
    }

    // Method to repay the loan in full
    void repayLoanInFull(User[] user, String customerID) {
        if (personalLoan != null) {
            personalLoan.repayLoanInFull(user, customerID);
        }
        if (homeLoan != null) {
            homeLoan.repayLoanInFull(user, customerID);
        }
    }

    // Method to add money to the user's account from a loan
    double addMoney(User[] user, String customerID) {
        if (personalLoan != null) {
            return personalLoan.addMoney(user, customerID);
        }
        if (homeLoan != null) {
            return homeLoan.addMoney(user, customerID);
        }
        return 0;
    }

    // Method to create a fixed deposit
    void createFixedDeposit(double amount) {
        this.fixedDeposits[fdCount++] = new FixedDeposit(amount, transactionCount);
    }

    // Method to display all active fixed deposits
    void displayFixedDeposits() {
        if (fdCount == 0) {
            System.out.println("No active FDs found");
            return;
        }
        for (int i = 0; i < fdCount; i++) {
            System.out.print("-------------------------------------------------------------");
            System.out.println("\nFD " + (i + 1) + ":");
            fixedDeposits[i].displayDetails(transactionCount);
        }
    }

    // Method to close a fixed deposit and receive the matured amount
    double closeFixedDeposit(int index) {
        // Validate if the FD index is within range and active
        if (index < 0 || index >= fdCount || !fixedDeposits[index].isActive) {
            System.out.println("Invalid FD or FD already closed");
            return 0;
        }

        FixedDeposit fd = fixedDeposits[index];
        double returnAmount = fd.calculatePrematureAmount(transactionCount);

        // Update user balance after FD closure
        balance += returnAmount;
        fd.isActive = false;
        transaction[transactionCount++] = (transactionCount) + ". FD Closed: +" + returnAmount + " | Balance: " + balance;

        return returnAmount;
    }
}

class Validations {
    // Validates if the input name contains only uppercase letters
    boolean validateName(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!(c >= 'A' && c <= 'Z')) {
                System.out.println("Please enter a valid name in the correct format.");
                return false;
            }
        }
        return true;
    }

    // Validates if the mobile number starts with 6, 7, 8, or 9 and has exactly 10 digits
    boolean validateMobileNumber(String input) {
        if (input.length() == 10) {
            if (!(input.startsWith("6") || input.startsWith("7") || input.startsWith("8") || input.startsWith("9"))) {
                System.out.println("Please enter a valid Mobile no. in the correct format.");
                return false;
            }
        } else {
            System.out.println("The mobile number must be exactly 10 digits long.");
            return false;
        }
        return true;
    }

    // Validates if the Aadhaar number has exactly 12 digits
    boolean validateAadhaarNumber(String input) {
        if (!(input.length() == 12)) {
            System.out.println("Please enter a valid Aadhaar no. in the correct format.");
            return false;
        }
        return true;
    }

    // Validates if the city input is not empty
    boolean validateCity(String input) {
        if (input.isEmpty()) {
            System.out.println("you entered invalid city input");
            return false;
        }
        return true;
    }

    // Validates if the PAN number follows the required pattern (first 5 characters as letters, next 4 as digits, last as letter)
    boolean validatePAN(String input) {
		if(input.length()!=10){
			System.out.println("Invalid PAN number. Please enter a 10-character PAN in the correct format (e.g., ABCDE1234F).");
			return false;
		}
			
        String first5 = input.substring(0, 5);
        for (int i = 0; i < 5; i++) {
            if (first5.charAt(i) < 'A' || first5.charAt(i) > 'Z') {
                System.out.println("Invalid PAN number. Please enter a 10-character PAN in the correct format (e.g., ABCDE1234F).");
                return false;
            }
        }
        String middleDigits = input.substring(5, 9);
        for (int i = 0; i < 4; i++) {
            if (middleDigits.charAt(i) < '0' || middleDigits.charAt(i) > '9') {
                System.out.println("Invalid PAN number. Please enter a 10-character PAN in the correct format (e.g., ABCDE1234F).");
                return false;
            }
        }
        if (!(input.charAt(9) >= 'A' && input.charAt(9) <= 'Z')) {
            System.out.println("Invalid PAN number. Please enter a 10-character PAN in the correct format (e.g., ABCDE1234F).");
            return false;
        }
        return true;
    }

    // Validates if the date of birth is in the correct format and within a valid range
    boolean validateDateOfBirth(String input) {
        int count = 0;
        for (char x : input.toCharArray()) {
            if (x == '/')
                count++;
        }
        if (count != 2) {
            System.out.println("Enter date in valid format(e.g.1/1/2006)");
            return false;
        }
        String[] dob = input.split("/");
        int date = Integer.parseInt(dob[0]);
        int month = Integer.parseInt(dob[1]);
        int year = Integer.parseInt(dob[2]);
        if (year < 1930 || year > 2025) {
            System.out.println("you must enter year between (1930 to 2025) ");
            return false;
        }
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            if (date > 31 || date < 1) {
                System.out.println("you entered invalid date input");
                return false;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (date > 30 || date < 1) {
                System.out.println("you entered invalid date input");
                return false;
            }
        } else if (month == 2) {
            if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
                if (date > 29 || date < 1) {
                    System.out.println("you entered invalid date input");
                    return false;
                }
            } else {
                if (date > 28 || date < 1) {
                    System.out.println("you entered invalid date input");
                    return false;
                }
            }
        } else {
            System.out.println("you entered invalid month input");
            return false;
        }
        return true;
    }

    // Validates if the email follows the correct format with '@' and domain validation
    boolean validateEmail(String input) {
        int atCount = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '@')
                atCount++;
        }
        if (atCount != 1) {
            System.out.println("Enter email in invalid format");
            return false;
        }
        String[] parts = input.split("@");

        for (int i = 0; i < parts[0].length(); i++) {
            char c = parts[0].charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9'))) {
                System.out.println("your entered mail id is in invalid format.");
                return false;
            }
        }
        if (parts.length < 2 || !parts[1].equals("gmail.com")) {
            System.out.println("invalid mail format");
            return false;
        }
        return true;
    }

    // Validates if the password meets length, character, and complexity requirements
    boolean validateLoginPassword(String input) {
        if (input.length() < 8 || input.length() > 15) {
            System.out.println("Password length must be (8-15) characters only.");
            return false;
        }
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                System.out.println("Blank spaces not allowed in password.");
                return false;
            }
        }

        boolean valid = false;
        int i = 0;

        while (i < input.length() && !valid) {
            if (input.charAt(i) >= 'a' && input.charAt(i) <= 'z') {
                valid = true;
                break;
            }
            i++;
        }
        if (!valid) {
            System.out.println("add atleast one lowercase alphabet.");
            return false;
        }

        valid = false;
        i = 0;
        while (i < input.length() && !valid) {
            if (input.charAt(i) >= 'A' && input.charAt(i) <= 'Z') {
                valid = true;
                break;
            }
            i++;
        }
        if (!valid) {
            System.out.println("add atleast one uppercase alphabet.");
            return false;
        }
        valid = false;
        i = 0;
        while (i < input.length() && !valid) {
            if (input.charAt(i) >= '0' && input.charAt(i) <= '9') {
                valid = true;
                break;
            }
            i++;
        }
        if (!valid) {
            System.out.println("add atleast one numeric characters.");
            return false;
        }
        valid = false;
        i = 0;
        while (i < input.length() && !valid) {
            char ch = input.charAt(i);
            if ((ch >= 32 && ch <= 47) || (ch >= 58 && ch <= 64) || (ch >= 91 && ch <= 96) || (ch >= 123 && ch <= 126)) {
                valid = true;
                break;
            }
            i++;
        }
        if (!valid) {
            System.out.println("add atleast special characters.");
            return false;
        }

        return true;
    }

    // Validates if the MPIN is exactly 4 digits long and contains only numbers
    boolean validateMpin(String input) {
        if (input.length() == 4) {
            for (int i = 0; i < input.length(); i++) {
                char ch = input.charAt(i);
                if (!(ch >= '0' && ch <= '9')) {
                    System.out.println("mpin must contain digits only");
                    return false;
                }
            }
        } else {
            System.out.println("mpin length must be of 4 characters only.");
            return false;
        }
        return true;
    }

    // Validates if the initial balance is at least 2000
    boolean validateInitialBalance(double input) {
        if (input < 2000) {
            System.out.println("Required initial balance must be more than 2000");
            return false;
        }
        return true;
    }
}

class BankingOperations extends Validations {
    Scanner sc = new Scanner(System.in);
    static int userCount;

    // Method to register a new user
    User registration() {
        boolean valid = false;
        String fname = null, lname = null, mobileNo = null, aadhar = null, dob = null, pan = null, city = null, email = null, password = null, mpin = null;
        double balance = 0;

        // Get and validate first name
        while (!valid) {
            System.out.print("Enter Your First Name (ABC):");
            fname = sc.next();
            valid = validateName(fname.toUpperCase());
        }
        valid = false;

        // Get and validate last name
        while (!valid) {
            System.out.print("Enter Your Last Name (ABC):");
            lname = sc.next();
            valid = validateName(lname.toUpperCase());
        }
        valid = false;

        // Get and validate mobile number
        while (!valid) {
            System.out.print("Enter Your Mobile number (9876543210):");
            mobileNo = sc.next();
            valid = validateMobileNumber(mobileNo);
        }
        valid = false;

        // Get and validate Aadhaar number
        while (!valid) {
            System.out.print("Enter Your Aadhaar Number (987654321012):");
            aadhar = sc.next();
            valid = validateAadhaarNumber(aadhar);
        }
        valid = false;

        // Get and validate PAN number
        while (!valid) {
            System.out.print("Enter Your Pan Number (ABCDE1234F):");
            pan = sc.next().toUpperCase();
            valid = validatePAN(pan);
        }
        valid = false;

        // Get and validate city
        while (!valid) {
            System.out.print("Enter Your City:");
            city = sc.next();
            valid = validateCity(city);
        }
        valid = false;

        // Get and validate Date of Birth
        while (!valid) {
            System.out.print("Enter Your Date of Birth (dd/mm/yyyy):");
            dob = sc.next();
            valid = validateDateOfBirth(dob);
            if (valid) {
                String[] part = dob.split("/");
                if (part[1].length() == 1) {
                    dob = part[0] + "/0" + part[1] + "/" + part[2];
                }
                if (part[0].length() == 1) {
                    dob = "0" + dob;
                }
            }
        }
        valid = false;

        // Get and validate email
        while (!valid) {
            System.out.print("Enter Your Email ID (abc12@gmail.com):");
            email = sc.next();
            valid = validateEmail(email);
        }
        valid = false;

        sc.nextLine();
        // Get and validate password
        while (!valid) {
            System.out.println();
            System.out.println("valid format for password :- ");
            System.out.println("--> 1. length of password must be between(8-15)");
            System.out.println("--> 2. it must include at least 1 lowercase character");
            System.out.println("--> 3. it must include at least 1 uppercase character");
            System.out.println("--> 4. it must include at least 1 numeric character");
            System.out.println("--> 5. it must include at least 1 special character");
            System.out.println("--> 6. it must not have any blank spaces");
            System.out.println();
            System.out.print("Create your password (Abc!@123): ");
            password = sc.nextLine();
            valid = validateLoginPassword(password);
            if (!valid) {
                System.out.println("Enter password in valid format");
            } else {
                System.out.print("Confirm password: ");
                if (!password.equals(sc.nextLine())) {
                    System.out.println("Password and confirm password must be same");
                    valid = false;
                }
            }
        }
        valid = false;

        // Get and validate initial balance
        while (!valid) {
            System.out.print("To get started, kindly enter your initial balance. The initial balance must be more than 2000: ");
            balance = sc.nextDouble();
            valid = validateInitialBalance(balance);
        }
        valid = false;

        // Get and validate MPIN
        while (!valid) {
            System.out.println("Your MPIN must contain only numbers and be exactly 4 digits long.");
            System.out.print("Create Your mpin:");
            mpin = sc.next();
            valid = validateMpin(mpin);
        }

        // Generate account details
        String accountNumber = createAccountNo(dob);
        String customerID = createCustomerId(dob, accountNumber);
        String ifscCode = createIFSCcode();

        return new User(fname, lname, mobileNo, email, aadhar, pan, city, dob, accountNumber, customerID, mpin, ifscCode, password, balance);
    }

    // Method to generate account number based on date of birth
    String createAccountNo(String dob) {
        String[] part = dob.split("/");
        int randomNumber = 20 + userCount;
        return part[0] + part[1] + "013310" + randomNumber;
    }

    // Method to generate IFSC code
    String createIFSCcode() {
        return "OBS0" + (int) (Math.random() * 10000);
    }

    // Method to generate customer ID
    String createCustomerId(String dob, String aNo) {
        String[] part = dob.split("/");
        return part[2] + aNo.substring(aNo.length() - 4);
    }

    // Method to verify login credentials
    boolean loginVerification(User[] users, String customerID, String password) {
        for (int i = 0; i < userCount; i++) {
            if ((users[i].customerID.equals(customerID)) && (users[i].loginPassword.equals(password))) {
                return true;
            }
        }
        return false;
    }

    void deposite(User[] user, String customerID) {
        for (int i = 0; i < user.length; i++) {
            if (user[i].customerID.equals(customerID)) {
                int attempt = 0;
                while (attempt < 3) {
                    System.out.print("Enter Your mPin :- ");
                    String mpin = sc.next();
                    if (user[i].mPin.equals(mpin)) {
                        System.out.print("Enter Amount for Deposite :- ");
                        double deposite = sc.nextDouble();
                        if (deposite < 0) {
                            System.out.println("Invalid Entered Amount");
                        } else if (deposite > 0) {
                            user[i].balance += deposite;
                            int count = user[i].transactionCount++;
                            user[i].transaction[count] = (count + 1) + ". Deposite: +" + deposite + " | Balance: " + user[i].balance;
                            System.out.println("Deposit successful.");
							break;
                        }
                    }
                    else {
                        attempt++;
                        System.out.println("Invalid mPin!");
                    }
                }
                break;
            }
        }
    }

    void withdraw(User[] user, String customerID) {
        for (int i = 0; i < user.length; i++) {
            if (user[i].customerID.equals(customerID)) {
                int attempt = 0;
                while (attempt < 3) {
                    System.out.print("Enter Your mPin :- ");
                    String mpin = sc.next();
                    if (user[i].mPin.equals(mpin)) {
                        System.out.print("Enter Amount for Withdraw :- ");
                        double withdraw = sc.nextDouble();
                        if (withdraw < 0) {
                            System.out.println("Invalid Entered Amount");
                        } else if (user[i].balance < withdraw) {
                            System.out.println("Not sufficent balance in your account!");
                        } else {
                            user[i].balance -= withdraw;
                            int count = user[i].transactionCount++;
                            user[i].transaction[count] = (count + 1) + ". withdraw: -" + withdraw + " | Balance: " + user[i].balance;
                            System.out.println("Withdraw successful!");
                            break;
                        }
                    } else {
                        attempt++;
                        System.out.println("Invalid mPin! Your attempt left " + (3 - attempt));
                    }
                }
                break;
            }
        }
    }

    void checkBalance(User[] user, String customerID) {
        for (int i = 0; i < user.length; i++) {
            if (user[i].customerID.equals(customerID)) {
                System.out.println("Your Current Balance = " + user[i].balance);
                break;
            }
        }
    }

    void printStatement(User[] user, String customerID) {
        for (int i = 0; i < user.length; i++) {
            if (user[i].customerID.equals(customerID)) {
                System.out.println("Transaction Statement for Customer ID: " + user[i].customerID);
                for (int j = 0; j < user[i].transactionCount; j++) {
                    System.out.println(user[i].transaction[j]);
                }
                break;
            }

        }
    }

    void changeMpin(User[] user, String customerID) {
        for (int i = 0; i < user.length; i++) {
            if (user[i].customerID.equals(customerID)) {
                System.out.print("Enter Your Mobile No for Change Pin : ");
                String mobile = sc.next();
                String newPin = "";
                while (true) {
                    if (user[i].mobileNumber.equals(mobile)) {
                        int otp = (int) (Math.random() * 10000);
                        System.out.println("Your OTP for verification : " + otp);
                        System.out.print("Enter OTP for change Pin : ");
                        if (otp == sc.nextInt()) {
                            boolean valid = false;
                            while (!valid) {
                                System.out.println("your mpin must include only numerics");
                                System.out.print("Enter New Mpin  :");
                                newPin = sc.next();
                                valid = validateMpin(newPin);
                            }
                            user[i].mPin = newPin;
                        } else {
                            System.out.println("Your entered otp doesn't match/n try again later");
                        }
                        break;
                    } else {
                        System.out.println("Your entered mobile number doesn't found.");
                    }
                }
                break;
            }
        }
    }

    void updateName(User[] user, int i, boolean isFirstName) {
        boolean valid = false;
        while (!valid) {
            if (isFirstName)
                System.out.print("Enter new First Name :");
            else
                System.out.print("Enter new Last Name :");
            String name = sc.next();
            valid = validateName(name.toUpperCase());
            if (valid) {
                if (isFirstName) {
                    user[i].firstName = name;
                } else {
                    user[i].lastName = name;
                }
            } else {
                System.out.println("Invalid Name!");
            }
        }
        user[i].fullName = user[i].firstName + " " + user[i].lastName;
    }

    void updateEmail(User[] user, int i) {
        boolean valid = false;
        while (!valid) {
            System.out.print("Enter EmailId to Update: ");
            String email = sc.next();
            valid = validateEmail(email);
            if (valid) {
                user[i].mailID = email;
            } else {
                System.out.println("Invalid EmailId!");
            }

        }
    }

    void updateMobileNo(User[] user, int i) {
        boolean valid = false;
        while (!valid) {
            System.out.print("Enter Mobile No to Update: ");
            String mobile = sc.next();
            valid = validateMobileNumber(mobile);
            if (valid) {
                user[i].mobileNumber = mobile;
            } else {
                System.out.println("Invalid Mobile No!");
            }
        }
    }
}

class LoanManagement {

    class PersonalLoan {
        double principal, interestRate = 8.5, remainingBalance; // Loan amount, interest rate (fixed at 8.5%), and remaining balance
        int termMonths; // Loan duration in months
        String purpose, morgage; // Purpose of loan and mortgage details

        // Constructor to initialize loan details
         PersonalLoan(String purpose, double principal, int termMonths, String morgage) {
            this.purpose = purpose; // Assign purpose of loan
            this.principal = principal; // Assign principal amount
            this.termMonths = termMonths; // Assign loan term in months
            this.remainingBalance = calculateMonthlyEMI() * termMonths; // Calculate total amount payable over the term
            this.morgage = morgage; // Assign mortgage details
        }

        // Method to calculate monthly EMI using the formula for loan amortization
         double calculateMonthlyEMI() {
            double monthlyRate = interestRate / 12 / 100; // Convert annual interest rate to monthly
            return principal * (monthlyRate * Math.pow(1 + monthlyRate, termMonths))
                    / (Math.pow(1 + monthlyRate, termMonths) - 1); // EMI formula
        }

        // Method to add loan amount to user's balance when loan is approved
        double addMoney(User[] user, String customerID) {
            for (int i = 0; i < user.length; i++) { // Iterate through the list of users
                if (user[i].customerID.equals(customerID)) { // Find the user with the given customer ID
                    int count = user[i].transactionCount++; // Get current transaction count
                    user[i].balance += principal; // Add loan amount to user's balance
                    user[i].transaction[count] = (count + 1) + ". Loan approval : +" + principal + " | Balance: " + user[i].balance; // Record transaction
                    return user[i].balance; // Return updated balance
                }
            }
            return 0; // Return 0 if user not found
        }

        // Method to pay one EMI towards the loan
         void payEMI(User[] user, String customerID) {
            for (int i = 0; i < user.length; i++) { // Iterate through users
                if (user[i].customerID.equals(customerID)) { // Find user with matching ID
                    double emi = calculateMonthlyEMI(); // Calculate EMI amount
                    if (remainingBalance <= 0) // Check if loan is already repaid
                        System.out.println("Loan already fully repaid!");
                    else if (emi >= remainingBalance) { // If EMI is greater than remaining balance, finalize repayment
                        if (user[i].balance >= emi) { // Check if user has sufficient balance
                            System.out.println("Final EMI paid! Loan fully repaid.");
                            int count = user[i].transactionCount++;
                            user[i].transaction[count] = (count + 1) + ". full loan repay : -" + remainingBalance + " | Balance: " + user[i].balance; // Record transaction
                            user[i].balance -= remainingBalance; // Deduct remaining balance
                            user[i].personalLoan = null; // Remove loan reference
                            remainingBalance = 0; // Set balance to zero
                        }
                    } else {
                        if (user[i].balance >= emi) { // If user has sufficient balance to pay EMI
                            remainingBalance -= emi; // Deduct EMI from remaining balance
                            user[i].balance -= emi; // Deduct EMI from user balance
                            int count = user[i].transactionCount++;
                            user[i].transaction[count] = (count + 1) + ". EMI : -" + emi + " | Balance: " + user[i].balance; // Record transaction
                            System.out.println("One EMI paid. Remaining Loan Amount: $" + remainingBalance);
                        }
                    }
                    break;
                }
            }
        }

        // Method to repay the entire remaining loan amount in one go
         void repayLoanInFull(User[] user, String customerID) {
            for (int i = 0; i < user.length; i++) { // Iterate through users
                if (user[i].customerID.equals(customerID)) { // Find user with matching ID
                    if (remainingBalance <= 0) { // If loan is already repaid
                        System.out.println("Loan already fully repaid!");
                    } else {
                        if (user[i].balance >= remainingBalance) { // Check if user has enough balance to repay fully
                            System.out.println("Full loan repaid! Amount paid: $" + remainingBalance);
                            user[i].balance -= remainingBalance; // Deduct remaining balance from user account
                            int count = user[i].transactionCount++;
                            user[i].transaction[count] = (count + 1) + ". Loan full repay : -" + remainingBalance + " | Balance: " + user[i].balance; // Record transaction
                            remainingBalance = 0; // Set balance to zero
                            user[i].personalLoan = null; // Remove loan reference
                        }
                    }
                    break;
                }
            }
        }

        // Method to display loan details
         void displayLoanDetails() {
            System.out.println("Personal Loan Purpose : " + purpose);
            System.out.println("Morgage               : " + morgage);
            System.out.println("Principal             : $" + principal);
            System.out.println("Interest Rate         : " + interestRate + "%");
            System.out.println("Term                  : " + termMonths + " months");
            System.out.println("Monthly EMI           : $ " + calculateMonthlyEMI());
            System.out.println("Remaining Total Amount: $ " + remainingBalance);
        }
    }

    class HomeLoan {
        // Variables to store loan details
        double principal, interestRate = 6.0, remainingBalance;
        int termMonths;
        String propertyAddress, morgage;

        // Constructor to initialize HomeLoan object
         HomeLoan(String propertyAddress, double principal, int termMonths, String morgage) {
            this.propertyAddress = propertyAddress;  // Address of the property being mortgaged
            this.principal = principal;  // Loan amount
            this.termMonths = termMonths;  // Duration of the loan in months
            this.remainingBalance = calculateMonthlyEMI() * termMonths; // Calculate total payable amount
            this.morgage = morgage;  // Mortgage details
        }

        // Method to calculate Monthly EMI (Equated Monthly Installment)
         double calculateMonthlyEMI() {
            double monthlyRate = interestRate / 12 / 100; // Convert annual interest rate to monthly
            return principal * (monthlyRate * Math.pow(1 + monthlyRate, termMonths))
                    / (Math.pow(1 + monthlyRate, termMonths) - 1);  // EMI formula
        }

        // Method to add loan money to user's account
        double addMoney(User[] user, String customerID) {
            for (int i = 0; i < user.length; i++) {
                if (user[i].customerID.equals(customerID)) {  // Find user by customer ID
                    int count = user[i].transactionCount++;  // Get the transaction count and increment it
                    user[i].balance += principal;  // Add loan amount to user's balance
                    user[i].transaction[count] = (count + 1) + ". Loan approval : +" + principal + " | Balance: " + user[i].balance;  // Record transaction
                    return user[i].balance;  // Return updated balance
                }
            }
            return 0;  // Return 0 if user not found
        }

        // Method to pay a single EMI
         void payEMI(User[] user, String customerID) {
            for (int i = 0; i < user.length; i++) {
                if (user[i].customerID.equals(customerID)) {  // Find user by customer ID
                    double emi = calculateMonthlyEMI();  // Get EMI amount

                    if (remainingBalance <= 0) {  // Check if loan is already paid off
                        System.out.println("Loan already fully repaid!");
                    } else if (emi >= remainingBalance) {  // If last EMI is greater than remaining balance
                        System.out.println("Final EMI paid! Loan fully repaid.");
                        int count = user[i].transactionCount++;  // Increment transaction count
                        user[i].transaction[count] = (count + 1) + ". full loan repay : -" + remainingBalance + " | Balance: " + user[i].balance;  // Record transaction
                        user[i].balance -= remainingBalance;  // Deduct remaining balance from user account
                        user[i].homeLoan = null;  // Set home loan to null as it is paid off
                        remainingBalance = 0;  // Set remaining balance to zero
                    } else {  // Regular EMI payment
                        remainingBalance -= emi;  // Deduct EMI from remaining balance
                        user[i].balance -= emi;  // Deduct EMI from user balance
                        int count = user[i].transactionCount++;  // Increment transaction count
                        user[i].transaction[count] = (count + 1) + ". EMI : -" + emi + " | Balance: " + user[i].balance;  // Record transaction
                        System.out.println("One EMI paid. Remaining Loan Amount: $" + remainingBalance);
                    }
                    break;  // Exit loop after processing payment
                }
            }
        }

        // Method to repay the entire loan at once
         void repayLoanInFull(User[] user, String customerID) {
            for (int i = 0; i < user.length; i++) {
                if (user[i].customerID.equals(customerID)) {  // Find user by customer ID
                    if (remainingBalance <= 0) {  // Check if loan is already paid off
                        System.out.println("Loan already fully repaid!");
                    } else {  // Full loan repayment
                        System.out.println("Full loan repaid! Amount paid: $" + remainingBalance);
                        user[i].balance -= remainingBalance;  // Deduct remaining balance from user's account
                        int count = user[i].transactionCount++;  // Increment transaction count
                        user[i].transaction[count] = (count + 1) + ". Loan full repay : -" + remainingBalance + " | Balance: " + user[i].balance;  // Record transaction
                        remainingBalance = 0;  // Set remaining balance to zero
                        user[i].homeLoan = null;  // Set home loan to null
                    }
                    break;  // Exit loop after processing repayment
                }
            }
        }

        // Method to display loan details
         void displayLoanDetails() {
            System.out.println("Morgage               : " + morgage);  // Print mortgage details
            System.out.println("Principal             : $" + principal);  // Print loan amount
            System.out.println("Interest Rate         : " + interestRate + "%");  // Print interest rate
            System.out.println("Term                  : " + termMonths + " months");  // Print loan term in months
            System.out.println("Monthly EMI           : $ " + calculateMonthlyEMI());  // Print monthly EMI
            System.out.println("Remaining Total Amount: $ " + remainingBalance);  // Print remaining loan balance
        }
    }
}

class FixedDeposit {
    // Variables to store deposit details
    double depositAmount;
    final double INTEREST_RATE = 5.0, PENALTY_RATE = 1.0; // Fixed interest and penalty rates
    int startTransactionCount;
    boolean isActive;
    final int REQUIRED_TRANSACTIONS = 3; // FD matures after 3 transactions

    // Constructor to initialize Fixed Deposit
     FixedDeposit(double depositAmount, int startTransactionCount) {
        this.depositAmount = depositAmount; // Initial deposit amount
        this.startTransactionCount = startTransactionCount; // Transaction count at deposit creation
        this.isActive = true; // FD is active initially
    }

    // Method to check if FD is mature based on transaction count
    private boolean isMature(int currentTransactionCount) {
        return (currentTransactionCount - startTransactionCount) >= REQUIRED_TRANSACTIONS;
    }

    // Method to calculate maturity amount after required transactions
     double calculateMaturityAmount() {
        return depositAmount + (depositAmount * INTEREST_RATE * REQUIRED_TRANSACTIONS) / 1200;
    }

    // Method to calculate premature withdrawal amount based on transactions completed
     double calculatePrematureAmount(int currentTransactionCount) {
        if (isMature(currentTransactionCount)) { // If FD has matured
            return calculateMaturityAmount();
        } else { // If FD is withdrawn prematurely
            int transactionsCompleted = currentTransactionCount - startTransactionCount;
            double interest = (depositAmount * INTEREST_RATE * transactionsCompleted) / 1200;
            double penalty = interest * (PENALTY_RATE / 100); // Apply penalty for premature withdrawal
            return depositAmount + (interest - penalty);
        }
    }

    // Method to display details of the Fixed Deposit
     void displayDetails(int currentTransactionCount) {
        System.out.println("Deposit Amount                           : $" + depositAmount);
        System.out.println("Interest Rate                            : " + INTEREST_RATE + "%");
        System.out.println("Required Transaction Count for FD Mature : " + (isMature(currentTransactionCount) ? "0" : (REQUIRED_TRANSACTIONS - (currentTransactionCount - startTransactionCount))));
        System.out.println("Maturity Status                          : " + (isMature(currentTransactionCount) ? "Matured" : "Not Matured"));
        System.out.println("Maturity Amount                          : $" + calculateMaturityAmount());
        System.out.println("Pre Maturity Amount                      : $" + calculatePrematureAmount(currentTransactionCount));
        System.out.println("Status                                   : " + (isActive ? "Active" : "Closed"));
    }
}

class OnlineBanking {
    static {
        System.out.println("+----------------------------------------------------+");
        System.out.println("\t\tWelocome To The Bank Management System");
        System.out.println("+----------------------------------------------------+");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int choice = 0;
        int loginChoice, loanChoice, fdChoice;
        User[] users = new User[30];
        BankingOperations opration = new BankingOperations();

		System.out.println("--------------------------------------------------------------");
        users[BankingOperations.userCount++] = new User("ansh", "patoliya", "8160369100", "ap1331@gmail.com", "467620849439", "IKCPP5367K", "ahmedabad", "14/08/2006", opration.createAccountNo("14/08/2006"), opration.createCustomerId("14/8/2006", opration.createAccountNo("14/8/2006")), "1234", opration.createIFSCcode(), "Admin@123", 20000);
        users[BankingOperations.userCount - 1].displayProfile();
		System.out.println("--------------------------------------------------------------");
		users[BankingOperations.userCount++] = new User("nidhi", "bhutka", "6355344791", "nidhibhutka1709@gmail.com", "467610849454", "IKCPP5197N", "ahmedabad", "17/09/2007", opration.createAccountNo("17/09/2007"), opration.createCustomerId("17/9/2007", opration.createAccountNo("17/9/2007")), "1234", opration.createIFSCcode(), "Admin@123", 20000);
		users[BankingOperations.userCount - 1].displayProfile();
		System.out.println("--------------------------------------------------------------");
		users[BankingOperations.userCount++] = new User("nihar", "kakani", "8200093751", "niharkakani@gmail.com", "449360165069", "ABCDE1234A", "ahmedabad", "21/11/2006", opration.createAccountNo("21/11/2006"), opration.createCustomerId("21/11/2006", opration.createAccountNo("21/11/2006")), "1234", opration.createIFSCcode(), "Admin@123", 20000);
		users[BankingOperations.userCount - 1].displayProfile();
		System.out.println("--------------------------------------------------------------");
		users[BankingOperations.userCount++] = new User("darshil", "shiyani", "7621987401", "darshilshiyani@gmail.com", "467610849467", "IKCPP5443N", "ahmedabad", "20/02/2007", opration.createAccountNo("20/02/2007"), opration.createCustomerId("20/2/2007", opration.createAccountNo("20/2/2007")), "1234", opration.createIFSCcode(), "Admin@123", 20000);
		users[BankingOperations.userCount - 1].displayProfile();
		System.out.println("--------------------------------------------------------------");
		users[BankingOperations.userCount++] = new User("saloni", "gorsiya", "9429769132", "salonigorasiya7906@gmail.com", "467610849454", "IKCPP5197N", "ahmedabad", "7/09/2006", opration.createAccountNo("7/09/2006"), opration.createCustomerId("7/9/2006", opration.createAccountNo("7/9/2006")), "1234", opration.createIFSCcode(), "Admin@123", 20000);
		users[BankingOperations.userCount - 1].displayProfile();
		System.out.println("--------------------------------------------------------------");

        while (choice != 3) {// start while (for starting window)
            System.out.println();
            System.out.println("1. Registration (New User)");
            System.out.println("2. Login (Existing User)");
            System.out.println("3. Close Application");
            System.out.print("---> Please select one of the above options: ");
            choice = sc.nextInt();

            switch (choice) {// start switch (for starting window)

                case 1://Registration
                    users[BankingOperations.userCount++] = opration.registration();
                    users[BankingOperations.userCount - 1].displayProfile();
                    break;

                case 2://login
                    System.out.print("Enter Customer Id for Login :- ");
                    String customerId = sc.next();
                    System.out.print("Enter Paasword :- ");
                    String password = sc.next();
                    if (opration.loginVerification(users, customerId, password)) {//start if (for login verification successfully )
                        loginChoice = 0;
                        while (loginChoice != 5) {
                            System.out.println();
                            System.out.println("1. Account services");
                            System.out.println("2. Profile details");
                            System.out.println("3. Loan");
                            System.out.println("4. Fixed Deposit");
                            System.out.println("5. Log Out");
                            System.out.print("---> Select an option from the services that are displayed above: ");
                            loginChoice = sc.nextInt();

                            switch (loginChoice) {

                                case 1:
                                    int serviceChoice = 0;
                                    while (serviceChoice != 6) {
                                        System.out.println();
                                        System.out.println("1. Check balance");
                                        System.out.println("2. Withdraw money");
                                        System.out.println("3. Deposite money");
                                        System.out.println("4. Print statements");
                                        System.out.println("5. Change mPin");
                                        System.out.println("6. Back to home-page");
                                        System.out.print("---> Select one of the option from above services: ");
                                        serviceChoice = sc.nextInt();
                                        switch (serviceChoice) {

                                            case 1:
                                                opration.checkBalance(users, customerId);
                                                break;

                                            case 2:
                                                opration.withdraw(users, customerId);
                                                break;

                                            case 3:
                                                opration.deposite(users, customerId);
                                                break;

                                            case 4:
                                                opration.printStatement(users, customerId);
                                                break;

                                            case 5:
                                                opration.changeMpin(users, customerId);
                                                break;

                                            case 6:
                                                break;

                                            default:
                                                System.out.println("Invalid choice!");
                                                break;
                                        }
                                    }
                                    break;
                                case 2:
                                    for (int i = 0; i < users.length; i++) {
                                        if (users[i].customerID.equals(customerId)) {
                                            users[i].displayProfile();
                                            System.out.println();
                                            System.out.print("--> Do you want to update your profile ? (Yes/No) :- ");
                                            String askUpdate = sc.next();
                                            if (askUpdate.equalsIgnoreCase("yes")) {
                                                int updateChoice = 0;
                                                while (updateChoice != 5) {
                                                    System.out.println();
                                                    System.out.println("1. Update first name");
                                                    System.out.println("2. Update last name");
                                                    System.out.println("3. Update email-id");
                                                    System.out.println("4. Update mobile no");
                                                    System.out.println("5. Back");
                                                    System.out.print("--> Select option for update :- ");
                                                    updateChoice = sc.nextInt();
                                                    switch (updateChoice) {
                                                        case 1:
                                                            opration.updateName(users, i, true);
                                                            break;

                                                        case 2:
                                                            opration.updateName(users, i, false);
                                                            break;

                                                        case 3:
                                                            opration.updateEmail(users, i);
                                                            break;

                                                        case 4:
                                                            opration.updateMobileNo(users, i);
                                                            break;
															
														case 5:
                                                            break;
															
                                                        default:
                                                            System.out.println("Enter valid choice!");
                                                            break;
                                                    }
                                                }
                                            } else if (askUpdate.equalsIgnoreCase("no")) {
                                            } else {
                                                System.out.println("Enter valid choice!");
                                            }
                                            break;
                                        }
                                    }
                                    break;
                                case 3:
                                    loanChoice = 0;
                                    while (loanChoice != 6) {//start while (for loan case)
                                        System.out.println();
                                        System.out.println("#Competitive rates.Flexible terms.You made the best choice!");
                                        System.out.println();
                                        System.out.println("1. Apply for a loan");
                                        System.out.println("2. View loan status");
                                        System.out.println("3. Pay EMI");
                                        System.out.println("4. Close loan");
                                        System.out.println("5. EMI calculator");
                                        System.out.println("6. Back to main menu");
                                        System.out.print("---> Select an option from above: ");
                                        loanChoice = sc.nextInt();

                                        switch (loanChoice) {

                                            case 1://apply for a loan
                                                for (int i = 0; i < BankingOperations.userCount; i++) {//for loop for found user in database
                                                    if (users[i].customerID.equals(customerId)) {//found user with customerId
                                                        if (users[i].personalLoan == null && users[i].homeLoan == null) {//start if (check loan has benn taken or not)
                                                            System.out.println("\t1. Personal Loan");
                                                            System.out.println("\t2. Home Loan");
                                                            System.out.print("Choose loan type: ");
                                                            int loanTypeChoice = sc.nextInt();
                                                            String purpose;
                                                            double principal;
                                                            int termMonth;
                                                            String morgage;
                                                            switch (loanTypeChoice) {//choose loan type switch (start)

                                                                case 1://personal loan
                                                                    System.out.print("Enter Your Loan purpose : ");
                                                                    purpose = sc.next();
                                                                    System.out.print("Enter amount for you require : ");
                                                                    principal = sc.nextDouble();
                                                                    System.out.println("1. Select loan term:");
                                                                    System.out.println("\t1. 12 months");
                                                                    System.out.println("\t2. 24 months");
                                                                    System.out.println("\t3. 36 months");
                                                                    System.out.print("Enter your choice (1, 2, or 3): ");
                                                                    int termMonthChoice = sc.nextInt();
                                                                    if (termMonthChoice == 1) //choose term months
                                                                        termMonth = 12;
                                                                    else if (termMonthChoice == 2)
                                                                        termMonth = 24;
                                                                    else if (termMonthChoice == 3)
                                                                        termMonth = 36;
                                                                    else {
                                                                        System.out.println("Enter valid choice.");
                                                                        break;
                                                                    }
                                                                    System.out.print("Enter something for guarantee:");
                                                                    morgage = sc.next();
                                                                    users[i].applyForPersonalLoan(purpose, principal, termMonth, morgage);
                                                                    System.out.println();
                                                                    System.out.println("Loan details :- ");
                                                                    users[i].displayLoanDetails();
                                                                    System.out.println("Your updated balance : " + users[i].addMoney(users, customerId));
                                                                    break;

                                                                case 2://home loan
                                                                    System.out.print("Enter Your Loan purpose : ");
                                                                    purpose = sc.next();
                                                                    System.out.print("Enter amount for you require : ");
                                                                    principal = sc.nextDouble();
                                                                    System.out.println("1. Select loan term:");
                                                                    System.out.println("\t1. 12 months");
                                                                    System.out.println("\t2. 24 months");
                                                                    System.out.println("\t3. 36 months");
                                                                    System.out.print("Enter your choice (1, 2, or 3): ");
                                                                    termMonthChoice = sc.nextInt();
                                                                    if (termMonthChoice == 1)//choose loan term months
                                                                        termMonth = 12;
                                                                    else if (termMonthChoice == 2)
                                                                        termMonth = 24;
                                                                    else if (termMonthChoice == 3)
                                                                        termMonth = 36;
                                                                    else {
                                                                        System.out.println("Enter valid choice.");
                                                                        break;
                                                                    }
                                                                    System.out.print("Enter something for guarantee:");
                                                                    morgage = sc.next();
                                                                    users[i].applyForHomeLoan(purpose, principal, termMonth, morgage);
                                                                    System.out.println();
                                                                    System.out.println("Loan details :- ");
                                                                    users[i].displayLoanDetails();
                                                                    System.out.println("Your updated balance : " + users[i].homeLoan.addMoney(users, customerId));
                                                            }//choose loan type switch (end)
                                                        }//end if (check loan has benn taken or not)
                                                        else {//if any loan has been taken
                                                            System.out.println("Loan has been taken, Another loan will be not taken.");
                                                        }
                                                    }//end if (user found or not)
                                                }//end for(user found or not)
                                                break;

                                            case 2://display loan details
                                                for (int i = 0; i < BankingOperations.userCount; i++) {// for loop for user found
                                                    if (users[i].customerID.equals(customerId)) {//if for found user with customer id
                                                        System.out.println();
                                                        System.out.println("Loan details :- ");
                                                        users[i].displayLoanDetails();
                                                        break;
                                                    }//end if (user found or not)
                                                }//end for(user found or not)
                                                break;

                                            case 3://pay one emi
                                                for (int i = 0; i < BankingOperations.userCount; i++) {// for loop for user found
                                                    if (users[i].customerID.equals(customerId)) {//if for found user with customer id
                                                        users[i].payEMI(users, customerId);
                                                        break;
                                                    }//end if (user found or not)
                                                }//end for(user found or not)
                                                break;

                                            case 4:// pay all emi
                                                for (int i = 0; i < BankingOperations.userCount; i++) {// for loop for user found
                                                    if (users[i].customerID.equals(customerId)) {//if for found user with customer id
                                                        users[i].repayLoanInFull(users, customerId);
                                                        break;
                                                    }//end if (user found or not)
                                                }//end for(user found or not)
                                                break;

                                            case 5://emi calculator
                                                System.out.println("\t1. Personal Loan");
                                                System.out.println("\t2. Home Loan");
                                                System.out.print("Choose loan type: ");
                                                int loanTypeChoice = sc.nextInt();
                                                double interest;
                                                if (loanTypeChoice == 1)
                                                    interest = 8.5;
                                                else if (loanTypeChoice == 2)
                                                    interest = 6;
                                                else
                                                    return;
                                                System.out.print("Enter the principal loan amount : ");
                                                double principal = sc.nextDouble();
                                                System.out.println("1. Select loan term:");
                                                System.out.println("\t1. 12 months");
                                                System.out.println("\t2. 24 months");
                                                System.out.println("\t3. 36 months");
                                                System.out.print("Enter your choice (1, 2, or 3): ");
                                                int termMonthChoice = sc.nextInt();
                                                int termMonth;
                                                if (termMonthChoice == 1)//choose loan term months
                                                    termMonth = 12;
                                                else if (termMonthChoice == 2)
                                                    termMonth = 24;
                                                else if (termMonthChoice == 3)
                                                    termMonth = 36;
                                                else {
                                                    System.out.println("Enter valid choice.");
                                                    break;
                                                }
                                                double monthlyRate = interest / 12 / 100;
                                                double emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, termMonth)) /
                                                        (Math.pow(1 + monthlyRate, termMonth) - 1);
                                                // Display the calculated EMI
                                                System.out.println("Total principal      : $" + principal);
                                                System.out.println("Interest             : " + interest + "%");
                                                System.out.println("Your monthly EMI is  : $" + emi);
                                                System.out.println("Term months          : " + termMonth);
                                                System.out.println("Total payable amount : $" + (emi * termMonth));
                                                System.out.println("Total interest       : $" + ((emi * termMonth) - principal));
                                                break;
                                            case 6://back to main manu
                                                break;
                                            default:
                                                System.out.println("Invalid choice,please enter valid choice.");
                                        }//end switch (for loan case)
                                    }//end while (for loan case)
                                    break;
                                case 4://start fd case
                                    fdChoice = 0;
                                    System.out.println();
                                    System.out.println("#Secure your future with a Fixed Deposite. Enjoy guaranteed returns and flexible terms!");
                                    while (fdChoice != 4) {//start while (for FD case)
                                        System.out.println("1. Create a FD");
                                        System.out.println("2. Display FD status");
                                        System.out.println("3. Close FD");
                                        System.out.println("4. Back to menu");
                                        System.out.print("---> Select an option from above: ");
                                        fdChoice = sc.nextInt();
                                        switch (fdChoice) {//start switch (for FD case)

                                            case 1://start create FD
                                                for (int i = 0; i < BankingOperations.userCount; i++) {//for loop for found user in database
                                                    if (users[i].customerID.equals(customerId)) {//found user with customerId
                                                        System.out.print("Enter FD amount: ");
                                                        double amount = sc.nextDouble();
                                                        if (amount <= 0) {
                                                            System.out.println("Amount must be positive");
                                                            break;
                                                        }
                                                        if (users[i].balance < amount) {
                                                            System.out.println("Insufficient balance");
                                                            break;
                                                        }
                                                        if (users[i].balance >= amount) {
                                                            users[i].balance -= amount;
                                                            int count = users[i].transactionCount++;
                                                            users[i].transaction[count] = (count + 1) + ". FD Created: -" + amount + " | Balance: " + users[i].balance;
                                                            users[i].createFixedDeposit(amount);
                                                        }
                                                        System.out.println("FD created successfully!");
                                                        System.out.println();
                                                    }
                                                }
                                                break;//end create FD

                                            case 2://start FD status
                                                for (int i = 0; i < BankingOperations.userCount; i++) {
                                                    if (users[i].customerID.equals(customerId)) {
                                                        users[i].displayFixedDeposits();
                                                    }
                                                }
                                                break;//end FD status

                                            case 3://start close FD
                                                for (int i = 0; i < BankingOperations.userCount; i++) {
                                                    if (users[i].customerID.equals(customerId)) {
                                                        users[i].displayFixedDeposits();
                                                        if (users[i].fdCount == 0) {
                                                            System.out.println("Not any FD taken by you.");
                                                            break;
                                                        }
                                                        System.out.print("Enter FD number to close: ");
                                                        int fdIndex = sc.nextInt() - 1;

                                                        if (fdIndex < 0 || fdIndex >= users[i].fdCount) {
                                                            System.out.println("Invalid FD number");
                                                            return;
                                                        }
                                                        double amount = users[i].closeFixedDeposit(fdIndex);
                                                        if (amount > 0) {
                                                            System.out.println("Amount credited: $" + amount);
                                                            System.out.println();
                                                        } else {
                                                            System.out.println("FD closure failed");
                                                        }
                                                        break;
                                                    }
                                                }
                                                break;//end close FD
                                            case 4:
                                                break;
                                            default:
                                                System.out.println("Enter valid input!");
                                                break;
                                        }//end switch (for FD case)
                                    }//end while (for FD case)
                                    break;//end fd case
                                case 5://exit case for login case
                                    System.out.println("Thank you for using Online Banking System \nHave a nice day!");
                                    break;
                                default:
                                    System.out.println("Please select one from available option!");
                                    break;
                            }//end switch (for login case)
                        }// end while (for login case)
                        break;
                    }//end if (for login verification successfully )
                    else {//start else (for login verification not successfully )
                        System.out.println("Login verification failed. Please check your customer id or password and try again.");
                    }//end else (for login verification not successfully )
                    break;
                case 3://exit case (for application)
                    System.out.println("Thank you for using this application!");
                    break;
                default:
                    System.out.println("Kindly open the application again");
                    break;
            }//end switch (for starting window)
        }// end while (for starting window)
    }//end main
}//end OnlineBanking class
