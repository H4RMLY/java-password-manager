import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

class main{ // Main function - Program driver
    public static void main(String[] args){	
		FileHandler testFile = new FileHandler("TESTFILE");
		GenString sg = new GenString();
		testFile.create();
		String username = "HARMLY";
		testFile.write(username + ":" + sg.charPassword(10) + "\n");
		testFile.write(username + ":" + sg.threeWordPassword() + "\n");
		testFile.write(username + ":" + sg.charPassword(15) + "\n");	
		ArrayList fileContent = testFile.read();
		Display disp = new Display(fileContent);
		disp.buildTable();
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
	ArrayList<String[]> data;
	public Display(ArrayList<String[]> data){
		this.data = data;
	}
	public void buildTable(){
		String breaker = "=";
		String columnTitleOne = "Username";
		String columnTitleTwo = "Password";
		System.out.printf("%20s%30s\n", columnTitleOne, columnTitleTwo);
		for (String[] line : data){
			System.out.println(" " + breaker.repeat(55));
			System.out.printf("| %20s | %30s |\n", line[0], line[1]);
		}
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
            System.out.println("File updated");
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
			System.out.println("YOu fucked the file write");
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
