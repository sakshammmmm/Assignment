public class APIWithLimit {

    private static final int Max_Calls = 15;
    private static final long One_Min = 60000;
    private static final long Penalty_Time = 60000;

    private static int callCount = 0;
    private static long lastRequestTime = 0;
    private static long penaltyEndTime = 0;

    // Simulated API call method
    public static String callMe(String input) {
        long currentTime = System.currentTimeMillis();

        //check if penalty period is ongoing
        if (currentTime < penaltyEndTime) {
            return "You are under penalty.";
        }

        // Check if the current request is within the same minute
        if (currentTime - lastRequestTime < One_Min) {
            if (callCount >= Max_Calls) {
                penaltyEndTime = currentTime + Penalty_Time;
                return "Rate limit exceeded. You are now under a one minute penalty.";
            } else {
                callCount++;
            }
        } else {
            callCount = 1;
        }

        lastRequestTime = currentTime;

        return "Data for :  " + input;
    }

    // Main method for testing
    public static void main(String[] args) throws InterruptedException {
        //Make 15 successful calls
        //Wait for 4 seconds between each request to make 15 calls within 1 minute
        for (int i = 1; i <= 15; i++) {
            System.out.println(callMe("Request " + i));
            Thread.sleep(4000);
        }

        //It should show Rate Limit exceeded
        System.out.println(callMe("Request 16"));

        Thread.sleep(60000);  // Wait for 1 minute

        //It should be successful
        System.out.println(callMe("Request 17"));
    }
}