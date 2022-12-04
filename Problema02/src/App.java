public class App {
    public static void main(String[] args) throws Exception {
        int[] nums = { 2, -4, 1, 9, -6, 7, -3, 3 };
        
        DivisaoConquista instancia = new DivisaoConquista("temperaturasExtremo.txt");
        System.out.println("The maximum sum of the subarray is " +
                instancia.maxSubArraySum(nums));
        instancia.getHashMap();
    }
        
}
