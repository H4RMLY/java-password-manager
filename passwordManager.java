import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

class main{ // Main function - Program driver
    public static void main(String[] args){	
			Arithmatic arth = new Arithmatic();
			Long keyA = arth.generateLongPrime();
			Long keyB = arth.generateLongPrime();
			Encryption e = new Encryption(keyA, keyB);
			//String ciphertext = e.encrypt(args[0]);
			System.out.println(e.generateKeys());
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

class CreateFile{ // Create Empty File
    String filename;
    public CreateFile(String filename){
        this.filename = filename;
    }
    public void create(){
        try {
            File file = new File(String.format("%s.txt", filename));
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

	public Long HCFs(Long num1, Long num2){
		Long hcf = 0;
		for (Long i = 1; i <= num1 || i <= num2; i++){
			if (num1%i == 0 && num2%i == 0){
				hcf = i;
			}
		}
		return hcf;
	}

	public Long generateRelativePrime(Long p1){
		Long p2 = generateLongPrime();
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

	public Long createSecretKey(){
		Long secretKey = p.multiply(q);
		return secretKey;
	}
	
	public String encrypt(String plaintext){
		Conversion conv = new Conversion();
		BigInteger plInt = BigInteger.valueOf(conv.textToLong(plaintext));
		System.out.println("plInt Encrypted: " + plInt);
		BigInteger secretKey = createSecretKey();
		BigInteger ctInt = plInt.modPow(p, secretKey);
		System.out.println("ctInt Encrypted: " + ctInt);
		String ciphertext = conv.BIToHex(ctInt);
		return ciphertext;
	}

	public String decrypt(String ciphertext){
		Conversion conv = new Conversion();
		BigInteger ctInt = conv.hexToBI(ciphertext);
		System.out.println("ctInt Decrypted " + ctInt);
		BigInteger secretKey = createSecretKey();
		BigInteger plInt = ctInt.modPow(q, secretKey);
		System.out.println("plInt Decrypted: " + plInt);
		return "OUTPUT";
	}

	public BigInteger generateKeys(){
		Arithmatic arth = new Arithmatic();
		BigInteger eulerTotient = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));
		BigInteger e = arth.generateRelativePrime(eulerTotient);
		return e;
	}
}
