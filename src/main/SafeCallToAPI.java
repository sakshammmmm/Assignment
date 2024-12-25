public class SafeCallToAPI {

    // Actual API
    public static String call_Me(String input) {
        // Simulate API call
        return "Response for input: " + input;
    }

    // Method to make safe API calls within rate limit
    public static void safeApiCall(String[] inputs) throws InterruptedException {
        int callCount = 0;

        for (String input : inputs) {
            // If we've already made 15 calls, wait 1 minute before hitting the API
            if (callCount == 15) {
                System.out.println("Rate limit reached. Waiting for 1 minute...");
                Thread.sleep(60000); // Wait for 1 minute (60,000 milliseconds)
                callCount = 0; // Reset call count after waiting
            }

            // Make the API call and print the response
            String response = call_Me(input);
            System.out.println(response);

            callCount++;

            if (callCount < 15) {
                Thread.sleep(4000); // Wait for 4 seconds
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Example inputs (20 calls)
        String[] inputs = new String[20];
        for (int i = 0; i < 20; i++) {
            inputs[i] = "Input " + (i + 1);
        }

        // Call the safeApiCall method
        safeApiCall(inputs);
    }
}