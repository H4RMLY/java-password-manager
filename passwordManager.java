import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class main{ // Main function - Program driver
    public static void main(String[] args){
            CreateFile storage = new CreateFile("STORAGE");
            storage.create();
            storage.overwrite("REPLACED");
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
