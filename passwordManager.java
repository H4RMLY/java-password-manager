import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

class main{ // Main function - Program driver
    public static void main(String[] args){
		Inputs inputs = new Inputs();
		Display display = new Display();
		System.out.println("WELCOME TO YOUR PASSWORD MANAGER");
		display.showCommands();
		inputs.getUserInput();
	}	
}

class Inputs {
	Scanner scanner;
	Display display;
	FileHandler storage;
	Management mngr;
	GenString generator;
	public Inputs(){
		this.scanner = new Scanner(System.in);
		this.display = new Display();
		this.storage = new FileHandler("storage");
		storage.create();
		this.mngr = new Management(storage);
		this.generator = new GenString();
	}
	public void getUserInput(){
		System.out.print("-<o>-: ");
		String userInput = scanner.nextLine();
		checkInput(userInput);
		getUserInput(); // THIS NEEDS TO BE CHANGED AS THIS FUNCTION NEVER RETURNS UNLESS EXITED
	}

	public void checkInput(String input){
		if (input.equals("EXIT") || input.equals("exit")){
			System.out.println("Have a lovely day :)\nExiting...");
			System.exit(0);
		
		} else if (input.equals("SHOW") || input.equals("show")){
			display.buildTable(storage);
		
		} else if (input.equals("ADD") || input.equals("add")){
			System.out.print("Account Name: ");
			String accountName = scanner.nextLine();
			System.out.print("Username: ");
			String username = scanner.nextLine();
			System.out.println("Password\n	rand: generates password of 14 random characters\n	rand3: generates password of three random words\n	Other: will enter as password ");
			String passInput = scanner.nextLine();
			String password;
			if (passInput.equals("rand")){
				password = generator.charPassword(14);
			} else if (passInput.equals("rand3")){
				password = generator.threeWordPassword();
			} else {
				password = passInput;
			}
			mngr.addAccount(accountName, username, password);

		} else if (input.equals("HELP") || input.equals("help")){
			display.showCommands();
		
		} else if (input.equals("RM") || input.equals("rm")){
			System.out.print("Account Name: ");
			mngr.removeAccount(scanner.nextLine());

		}
	}
}

class GenString { // Random String Generator
    public String charPassword(int passLen){ // Random password of n characters
        Random r = new Random();
        String charSelection = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_-+=@/!£$&(){}[]";
        String password = "";
        for (int i = 0; i < passLen; i++){
            int randInt = r.nextInt(charSelection.length());
            char randChar = charSelection.charAt(randInt);
            password += randChar;       
        }    
        return password;
    }

    public String threeWordPassword(){ // Random three word password
        String[] words = {
        "Acorn", "Banana", "Cactus", "Dragonfly", "Eclipse", "Falcon", "Geyser", "Harbor", "Icicle", "Jungle",
        "Koala", "Lantern", "Meadow", "Nebula", "Oasis", "Penguin", "Quartz", "Rainbow", "Sunset", "Tornado",
        "Umbrella", "Volcano", "Whistle", "Xylophone", "Yacht", "Zebra", "Amber", "Blizzard", "Crater", "Dolphin",
        "Ember", "Feather", "Galaxy", "Horizon", "Island", "Jewel", "Kingdom", "Lagoon", "Meteor", "Nimbus",
        "Oracle", "Prairie", "Quest", "River", "Starlight", "Tundra", "Unicorn", "Vortex", "Wilderness", "Zephyr",
        "Almond", "Boulder", "Canyon", "Desert", "Everest", "Fjord", "Glacier", "Harpoon", "Inferno", "Jasmine",
        "Kettle", "Lynx", "Mammoth", "Nectar", "Outpost", "Panther", "Quiver", "Raccoon", "Sapphire", "Timber",
        "Utopia", "Violet", "Wagon", "Xerox", "Yarrow", "Zenith", "Antler", "Brisket", "Cypress", "Dandelion",
        "Echo", "Fable", "Galleon", "Hammock", "Inkling", "Jigsaw", "Knight", "Lighthouse", "Mosaic", "Nugget",
        "Orbit", "Pinnacle", "Quasar", "Rustle", "Shrine", "Thicket", "Unity", "Voyage", "Wanderer", "Zodiac"
        };
        Random r = new Random();
        String password = "";
        for (int i = 0; i < 3; i++){
           password += words[r.nextInt(words.length)];
        }
        return password;
    }
}

class Display{	
	public void buildTable(FileHandler file){
		ArrayList<String[]> data = file.read();
		String breaker = "=";
		String columnTitleOne = "Account";
		String columnTitleTwo = "Username";
		String columnTitleThree = "Password";
		System.out.printf("%20s%20s%30s\n", columnTitleOne, columnTitleTwo, columnTitleThree);
		for (String[] line : data){
			System.out.println(" " + breaker.repeat(78));
			System.out.printf("| %20s | %20s | %30s |\n", line[0], line[1], line[2]);
		}
	}
	public void showCommands(){
		System.out.println("OPTIONS:");
		System.out.println("	EXIT:	Closes the program\n	HELP:	Show available commands\n	SHOW:	Displays all accounts usernames and passwords\n	ADD:	Saves an account\n	RM:	Removes an account");	
	}
}

class Management{
	FileHandler file;
	public Management(FileHandler file){
		this.file = file;
	}
	public void addAccount(String account, String username, String password){
		String instance = account + ":" + username + ":" + password + "\n";
		file.write(instance);
		System.out.println("Instance Added");
	}
	public void removeAccount (String account){
		ArrayList<String[]> data = file.read();
		for (String[] instance : data){
			if (instance[0].equals(account)){
				data.remove(instance);
				System.out.println("Account Deleted");
			}
		}
		file.update(data, file);
	}
		
}

class FileHandler{ // Create Empty File
    String filename;
    public FileHandler(String filename){
        this.filename = filename;
    }
    public void create(){
        try {
            File file = new File(String.format("%s", filename));
            if (file.createNewFile()){
                System.out.println("File Created: " + file.getName());
            } else {
                System.out.println("File Already Exists");
            }
        } catch (IOException e){
            System.out.println("You fucked creating a file");
            e.printStackTrace();
        }
    }
	
	public void overwrite(String input){ // Main write function. This will overwrite existing content.
        try{
            FileWriter overwriter = new FileWriter(filename);
            overwriter.write(input);
            overwriter.close();
            System.out.println("File overwritten");
        } catch (IOException e){
            System.out.println("You fucked the file write");
            e.printStackTrace();
        }
    }

	public void write(String input){
		try {
			FileWriter writer = new FileWriter(filename, true);
			writer.write(input);
			writer.close();
		} catch (IOException e){
			System.out.println("You fucked the file write");
			e.printStackTrace();
		}
	}

	public ArrayList read(){
		try{
			File file = new File(filename);
			Scanner reader = new Scanner(file);
			ArrayList<String[]> output = new ArrayList<String[]>();
			while (reader.hasNextLine()){
				String data = reader.nextLine();
				String[] values = data.split(":");
				output.add(values);
			}
			return output;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void update(ArrayList<String[]> data, FileHandler file){
		file.overwrite("");
		for (String[] instance : data){
			file.write(instance[0] + ":" + instance[1] + ":" + instance[2] + "\n");
		}
		System.out.println("File updated");	
	}
}

class Arithmatic{
	public boolean checkPrime(Long number){
		if (number <= 1){
			return false;
		}
		for (int i = 2; i <= Math.sqrt(number); i++){
			if (number % i == 0){
				return false;
			}
		}
		return true;
	}

	public Long generateLongPrime(){
		Random r = new Random();
		Long number = r.nextLong();
		if (checkPrime(number)){
			return number;
		} else {
			number = generateLongPrime();
			return number;
		}
	}
	
	public int generateIntPrime(){
		Random r = new Random();
		int number = r.nextInt();
		if (checkPrime(Long.valueOf(number))){
			return number;
		} else {
			number = generateIntPrime();
			return number;
		}
	}

	public int HCFs(int num1, int num2){
		int hcf = 0;
		for (int i = 1; i <= num1 || i <= num2; i++){
			if (num1%i == 0 && num2%i == 0){
				hcf = i;
			}
		}
		return hcf;
	}

	public int generateRelativePrime(int p1){
		int p2 = generateIntPrime();
		if (HCFs(p1, p2) == 1){
			return p2;
		} else {
			p2 = generateRelativePrime(p1);
			return p2;
		}
	}
}

class Conversion{
	public String BIToHex(BigInteger number){
		String hexString = number.toString(16);
		return hexString;
	}

	public BigInteger hexToBI(String hex){
		BigInteger bigInt = new BigInteger(hex, 16);
		return bigInt;
	}

	public Long textToLong(String text){
		try{
			byte[] ascii = text.getBytes("US-ASCII");
			String asciiString = "";
			for (byte character : ascii){
				asciiString += character;
			}
			return Long.parseLong(asciiString);
		} catch (IOException e){
			e.printStackTrace();
			return null;
		}
	}

}

class Encryption{ // (plaintext ** keyA) mod secretKey
	int p;
	int q;
	public Encryption(int p, int q){
		this.p = p;
		this.q = q;
	}

	public int createSecretKey(){
		int secretKey = p * q;
		return secretKey;
	}
	
	public String encrypt(String plaintext){
		Conversion conv = new Conversion();
		BigInteger plInt = BigInteger.valueOf(conv.textToLong(plaintext));
		System.out.println("plInt Encrypted: " + plInt);
		BigInteger secretKey = BigInteger.valueOf(createSecretKey());
		BigInteger ctInt = plInt.modPow(BigInteger.valueOf(p), secretKey);
		System.out.println("ctInt Encrypted: " + ctInt);
		String ciphertext = conv.BIToHex(ctInt);
		return ciphertext;
	}

	public String decrypt(String ciphertext){
		Conversion conv = new Conversion();
		BigInteger ctInt = conv.hexToBI(ciphertext);
		System.out.println("ctInt Decrypted " + ctInt);
		BigInteger secretKey = BigInteger.valueOf(createSecretKey());
		BigInteger plInt = ctInt.modPow(BigInteger.valueOf(q), secretKey);
		System.out.println("plInt Decrypted: " + plInt);
		return "OUTPUT";
	}

	public int generateKeys(){
		Arithmatic arth = new Arithmatic();
		int eulerTotient = (p-1) * (q-1);
		int e = arth.generateRelativePrime(eulerTotient);
		return e;
	}
}
