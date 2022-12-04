// A Divide and Conquer based Java
// program for maximum subarray sum
// problem
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


class DivisaoConquista{
    HashMap<String, Integer> resultado = new HashMap<>();


    public DivisaoConquista(String nomeArquivo) {
        this.leituraArquivo(nomeArquivo);
    }

    private void leituraArquivo(String nomeArquivo){
        try{
            File file = new File(nomeArquivo);
            Scanner sc = new Scanner(file);
            int contador = 1;
            while (sc.hasNextLine()){             
                String[] linha = sc.nextLine().split(";");
                List<String> strTemparaturas = Arrays.asList(linha);
                List<Integer> temperaturasTemp = strTemparaturas.stream().map(Integer::parseInt).collect(Collectors.toList());

                this.calculaMaiorTemperatura(temperaturasTemp, contador);
                contador++;
            }

        }catch (FileNotFoundException error){
            System.out.println(error);
        }
    }

    private void calculaMaiorTemperatura(List<Integer> array, int ano){
        int [] temperaturas = array.stream().mapToInt(Integer::intValue).toArray();
        this.maxSubArraySum(temperaturas);
        this.printDados(this.getHashMap(), ano);
        this.resultado.clear();
    }

    private void printDados(Map.Entry<String, Integer> dados, int ano){
        System.out.println("No ano " + ano + " as maiores temperaturas ocorreram nos dias: " + dados.getKey() + " e a soma das temperaturas e de: " + dados.getValue());
    }

    private int maxCrossingSum(int arr[], int l, int m, int h)
    {
        ArrayList<Integer> leftArray = new ArrayList<>();
        ArrayList<Integer> rightArray = new ArrayList<>();
        // Include elements on left of mid.
        int sum = 0;
        int left_sum = Integer.MIN_VALUE;
        leftArray.clear();
        for (int i = m; i >= l; i--) {
            sum = sum + arr[i];
            if (sum > left_sum){
                left_sum = sum;
                leftArray.add(i);
            }
        }
 
        // Include elements on right of mid
        sum = 0;
        int right_sum = Integer.MIN_VALUE;
        rightArray.clear();
        for (int i = m; i <= h; i++) {
            sum = sum + arr[i];
            if (sum > right_sum){
                right_sum = sum;
                rightArray.add(i);
            }
        }
 
        // Return sum of elements on left
        // and right of mid
        // returning only left_sum + right_sum will fail for
        // [-2, 1]
        int sumary_sum = left_sum + right_sum - arr[m];

        if(sumary_sum >= left_sum && sumary_sum >= right_sum){
            this.saveRightLeftSubarray(rightArray, leftArray, sumary_sum);
            return sumary_sum;
        }else if(left_sum >= right_sum){
            this.saveOneSideSubarray(leftArray, left_sum);
            return left_sum;
        }else{
            this.saveOneSideSubarray(rightArray, right_sum);
            return right_sum;
        }
    }


    private void saveRightLeftSubarray(ArrayList<Integer> rightArray,ArrayList<Integer> leftArray,int sum){
        leftArray.addAll(rightArray);
        Collections.sort(leftArray);
        
        int start = leftArray.get(0);
        int end = leftArray.get(leftArray.size() - 1) + 1;

        List<Integer> temporary = getNumbersUsingIntStreamRange(start, end);
        Integer[] response = temporary.toArray(new Integer[0]);

        this.resultado.put(Arrays.toString(response), sum);
    }

    private List<Integer> getNumbersUsingIntStreamRange(int start, int end) {
        return IntStream.range(start, end)
          .boxed()
          .collect(Collectors.toList());
    }
    
    private void saveOneSideSubarray(ArrayList<Integer> array,int sum){
        Collections.sort(array);

        int start = array.get(0);
        int end = array.get(array.size() - 1) + 1;

        List<Integer> temporary = getNumbersUsingIntStreamRange(start, end);
        Integer[] response = temporary.toArray(new Integer[0]);
        this.resultado.put(Arrays.toString(response), sum);
    }

    Map.Entry<String, Integer> getHashMap(){

        Map.Entry<String, Integer> maxEntry = this.resultado.entrySet().stream()
        .max(Comparator.comparing(Map.Entry::getValue))
        .orElse(null);
        return maxEntry;

        // for (Map.Entry<String, Integer> set :
        //      this.resultado.entrySet()) {
 
        //     // Printing all elements of a Map
        //     System.out.println(set.getKey() + " = "
        //                        + set.getValue());
        // }
    }


    // Returns sum of maximum sum subarray
    // in aa[l..h]
    private int maxSubArraySum(int arr[], int l, int h)
    {
          //Invalid Range: low is greater than high
        if (l > h)
            return Integer.MIN_VALUE;
        // Base Case: Only one element
        if (l == h)
            return arr[l];
 
        // Find middle point
        int m = (l + h) / 2;
 
        /* Return maximum of following three
        possible cases:
        a) Maximum subarray sum in left half
        b) Maximum subarray sum in right half
        c) Maximum subarray sum such that the
        subarray crosses the midpoint */
        return Math.max(
            Math.max(maxSubArraySum(arr, l, m-1),
                     maxSubArraySum(arr, m + 1, h)),
            maxCrossingSum(arr, l, m, h));
    }

    public int maxSubArraySum(int arr[]){
        int lower = 0;
        int higher = arr.length - 1;
        return this.maxSubArraySum(arr, lower, higher);
    }
}
