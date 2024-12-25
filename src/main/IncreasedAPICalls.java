public class IncreasedAPICalls {

    private static final int TOTAL_CALLS = 20;
    private static final int CALLS_PER_THREAD = 10;
    private static final int GAP_BETWEEN_CALLS = 6000;

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new ApiTask(0, CALLS_PER_THREAD));
        Thread thread2 = new Thread(new ApiTask(CALLS_PER_THREAD, CALLS_PER_THREAD));

        // Start both threads
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Completed all " + TOTAL_CALLS + " API calls.");
    }


    static class ApiTask implements Runnable {

        private int startCallIndex;
        private int totalCalls;

        public ApiTask(int startCallIndex, int totalCalls) {
            this.startCallIndex = startCallIndex;
            this.totalCalls = totalCalls;
        }

        public void run() {
            for (int i = 0; i < totalCalls; i++) {
                call_me("Request " + (startCallIndex + i + 1));

                try {
                    Thread.sleep(GAP_BETWEEN_CALLS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void call_me(String input) {
            System.out.println(Thread.currentThread().getName() + " - Making API call with input: " + input);
        }
    }
}