import java.util.*;

public class QuarantineActivity {
    private double timeCommitment;
    private String activityName;
    private boolean relieveBoredom;
    private boolean zoomFriendly;

    public QuarantineActivity(double timeCommitment, String activityName,
                              boolean relieveBoredom, boolean zoomFriendly) {
        this.timeCommitment = timeCommitment;
        this.activityName = activityName;
        this.relieveBoredom = relieveBoredom;
        this.zoomFriendly = zoomFriendly;
    }

    public String getName() { return activityName;}
    public boolean entertaining() { return relieveBoredom;}
    public double getTimeCommitment() { return timeCommitment;}
    public boolean isSocialDistancing() { return zoomFriendly;}
    public String toString() {
        return (activityName + ":" + (relieveBoredom ? "Fun!" : "boring"));
    }

    public static void main(String[] args){
        // Creating the map:
        Map<String, List<QuarantineActivity>> myActivities = new HashMap<>();

        // Insert/put into map
        // TODO: add at least 3 activities to "eating"
        myActivities.put("physical",
                Arrays.asList(
                        new QuarantineActivity(30.0, "Yoga", true, true),
                        new QuarantineActivity(25.5, "Run", false, false)));

        myActivities.put("creative",
                Arrays.asList(
                        new QuarantineActivity(40.0, "Paint", true, true),
                        new QuarantineActivity(15.0, "scrapbooking", true, true),
                        new QuarantineActivity(30.0, "baking", true, true)));

        myActivities.put("intellectual",
                Arrays.asList(
                        new QuarantineActivity(60.0, "Read", true, false),
                        new QuarantineActivity(1800.0, "Homework", false, true)));
        myActivities.put("communication",
                Arrays.asList(
                        new QuarantineActivity(45.0, "Snapchat", true, false),
                        new QuarantineActivity(45.0, "Instagram", true, false),
                        new QuarantineActivity(45.0, "Netflix", true, true),
                        new QuarantineActivity(45.0, "Zoom", true, true)));

        myActivities.put("eating",
                Arrays.asList(
                        new QuarantineActivity(30.0, "Breakfast", true, true),
                        new QuarantineActivity(30.0, "Lunch", true, true),
                        new QuarantineActivity(30.0, "Dinner", true, true)));

        // Traversing the map:
        /* start by making sure you can print out data in map */
        for (String key : myActivities.keySet()) {
            System.out.println("Activity Type: " + key);
            List<QuarantineActivity> theActivities = myActivities.get(key);
            if (theActivities != null) {
                for (QuarantineActivity a: theActivities) {
                    System.out.println(a);
                }
            }
        }
      /* TODO: create a list of activities from the "creative" and "intellectual" keys
	    that are less than or equal to 30 minutes then print out the list
	    */
        List<String> creativeIntellectual = new ArrayList<>();
        for (String key : myActivities.keySet()){
            List<QuarantineActivity> theActivities = myActivities.get(key);
            if (key.equals("creative") || key.equals("intellectual")) {
                for (QuarantineActivity a : theActivities){
                    if (a.getTimeCommitment() <= 30.0) {
                        creativeIntellectual.add(a.getName());
                    }
                }
            }
        }
        System.out.println(creativeIntellectual);

        /* TODO: print out all physical activities */
        for (String key : myActivities.keySet()){
            List<QuarantineActivity> theActivites = myActivities.get(key);
            for (QuarantineActivity a : theActivites){
                if(key.equals("physical")){
                    System.out.println(a.getName());
                }
            }
        }

        /* TODO: make a list of all the activities that can be done socially (zoom friendly), then print out the list */
        List<String> socialDistancingList = new ArrayList<>();
        for (String key : myActivities.keySet()){
            List<QuarantineActivity> theActivities = myActivities.get(key);
            for (QuarantineActivity a : theActivities){
                if(a.isSocialDistancing()){
                    socialDistancingList.add(a.getName());
                }
            }
        }
        System.out.println(socialDistancingList);
    }
}
