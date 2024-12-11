package update;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Updater {
	public static ArrayList<Updateable> updateableObjects = new ArrayList<Updateable>();
	public static ArrayList<Updateable> addUpdateableObjects = new ArrayList<Updateable>();
	private static ArrayList<Updateable> removeUpdateableObjects = new ArrayList<Updateable>();

	public static void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		for(Updateable object: updateableObjects)
			object.update();
		
		updateableObjects.removeAll(removeUpdateableObjects);
		updateableObjects.addAll(addUpdateableObjects);
		
		addUpdateableObjects.clear();
		removeUpdateableObjects.clear();
	}
	
	// Add clear method
    public static void clearUpdateableObjects() {
        updateableObjects.clear();
    }
	
	public static void addUpdateableObject(Updateable object) {
		addUpdateableObjects.add(object);
	}
	
	public static void removeUpdateableObject(Updateable object) {
		removeUpdateableObjects.add(object);
	}
	
	public static ArrayList<Updateable> getUpdateableObject() {
		return updateableObjects;
	}
}
