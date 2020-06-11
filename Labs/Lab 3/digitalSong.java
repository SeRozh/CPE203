import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;

public class digitalSong {
    private double howLong;
    private String title;
    private boolean explicit;

    public digitalSong(double length, String title, boolean explicit) {
        this.howLong = length;
        this.title = title;
        this.explicit = explicit;
    }
    public String getTitle() { return title; }
    public boolean familyFriendly() { return !explicit;}
    public String toString() {
        return title + " : " + (explicit ? "E" : "ok");
    }

    public static void main(String[] args) {
        // instantiate the HashMap
        Map<String, List<digitalSong>> myMusic = new HashMap<>();

        // Add artist/songs to HashMap
        myMusic.put("Beyonce",
                Arrays.asList(
                        new digitalSong(3.4,"Hold Up", false),
                        new digitalSong(3.5, "Sorry", true),
                        new digitalSong(4.49, "Freedom", false)));
        myMusic.put("Modest Mouse",
                Arrays.asList(
                        new digitalSong(8.26, "Spitting Venom", true),
                        new digitalSong(4.23,"Missed the Boat", false),
                        new digitalSong(8.0, "Float On", false)));
        myMusic.put("H.E.R",
                Arrays.asList(
                        new digitalSong(3.0,"Focus", false),
                        new digitalSong(5.0, "Against Me", true)));
        myMusic.put("Journey", null);

        // Iterate over a HashMap
        // Docs @ https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html
        // Map.keySet() returns a set of the HashMap's keys that we can iterate over
        for (String key : myMusic.keySet()) {
            System.out.println("Artist: " + key);
            // Map.get() returns the associated value for the given key.
            // Remember Maps are data structures to store key/value pairs!
            List<digitalSong> theSongs = myMusic.get(key);
            // Best practice: Verify value isn't null before you operate on it
            if (theSongs != null) { // This is sometimes called a "null check"
                for (digitalSong s: theSongs) {
                    System.out.println(s);
                }
            }
            System.out.println();
        }
    }
}
