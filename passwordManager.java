import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

class main{ // Main function - Program driver
    public static void main(String[] args){	
			Arithmatic arth = new Arithmatic();
			Long keyA = arth.generatePrime();
			Long keyB = arth.generatePrime();
			Encryption e = new Encryption(keyA, keyB);
			System.out.println(e.encrypt("HELLO"));
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

	public Long generatePrime(){
		Random r = new Random();
		Long number = r.nextLong();
		if (checkPrime(number)){
			return number;
		} else {
			number = generatePrime();
			return number;
		}
	}

}

class Conversion{
	public String doubleToHex(Double number){
		return Double.toHexString(number);
	}

	public Double hexToDouble(String hex){
		return Double.parseDouble(hex);		
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
	Long keyA;
	Long keyB;
	public Encryption(Long keyA, Long keyB){
		this.keyA = keyA;
		this.keyB = keyB;
	}

	public Long createEncryptionKey(){
		return keyA * keyB;
	}
	
	public String encrypt(String plaintext){
		Conversion conv = new Conversion();
		Long plInt = conv.textToLong(plaintext);
		Long secretKey = createEncryptionKey();
		Double ctInt = Math.pow(plInt, keyA) % secretKey;
		String ciphertext = conv.doubleToHex(ctInt);
		return ciphertext;
	}
}
