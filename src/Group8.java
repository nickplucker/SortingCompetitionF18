import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/*
Algorithm Created by:
    Nick Plucker and Ethan Uphoff

Algorithm was created with help from the following sources:
       https://pdfs.semanticscholar.org/d1ca/69730531f15ed5906c4c88e86fe949d5ad3f.pdf
       https://www.geeksforgeeks.org/suffix-tree-application-3-longest-repeated-substring/
       https://introcs.cs.princeton.edu/java/42sort/LRS.java.html
 */
public class Group8 {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
        // Data.testM_LRMUS();

        if (args.length < 2) {
            System.out.println("Please run with two command line arguments: input and output file names");
            System.exit(0);
        }

        String inputFileName = args[0];
        String outFileName = args[1];

        // read as strings
        String[] data = readData(inputFileName);
        String[] toSort = data.clone();
        Data[] sorted = sort(toSort); // Warm up the VM
        toSort = data.clone();
        Thread.sleep(10); //to let other things finish before timing; adds stability of runs

        long start = System.currentTimeMillis(); // Begin the timing
        sorted = sort(toSort);
        long end = System.currentTimeMillis();   // End the timing

        System.out.println(end - start);         // Report the results

        writeOutResult(sorted, outFileName);

    }

    // YOUR SORTING METHOD GOES HERE.
    // You may call other methods and use other classes.
    // You may ALSO modify the methods, innner classes, etc, of Data[]
    // But not in way that transfers information from the warmup sort to the timed sort.
    // Note: you may change the return type of the method.
    // You would need to provide your own function that prints your sorted array to
    // a file in the exact same format that my program outputs
    private static Data[] sort(String[] toSort) {
        Quicksort quicksort = new Quicksort();
        Data[] toSortData = new Data[toSort.length];
        for (int i = 0; i < toSort.length; ++i) {
            toSortData[i] = new Data(toSort[i]);
        }

        quicksort.sort(toSortData, 0, toSort.length - 1);
        return toSortData;
    }

    public static class Quicksort {

        // Sorts the array via quicksort
        public Data[] sort(Data[] quicksortArr, int lowerbound, int upperbound) {
            if (lowerbound < upperbound) {
                int q = partition(quicksortArr, lowerbound, upperbound);
                sort(quicksortArr, lowerbound, q - 1);
                sort(quicksortArr, q + 1, upperbound);
            }
            return quicksortArr;
        }

        // Helper function for quicksort to partiton the array
        private int partition(Data[] quicksortArr, int lowerbound, int upperbound) {
            M_LRMUSComparator comparator = new M_LRMUSComparator();
            Data x = quicksortArr[upperbound];
            int i = lowerbound - 1;
            for (int j = lowerbound; j < upperbound; j++) {
                if (comparator.compare(quicksortArr[j], x) <= 0) {
                    i = i + 1;
                    Data temp = quicksortArr[i];
                    quicksortArr[i] = quicksortArr[j];
                    quicksortArr[j] = temp;
                }
            }
            quicksortArr[upperbound] = quicksortArr[i + 1];
            quicksortArr[i + 1] = x;
            return i + 1;
        }

    }

    private static void printArray(String[] Arr, int n) {
        for (int i = 0; i < n; i++) {
            System.out.println(Arr[i]);
        }
    }

    private static String[] readData(String inFile) throws FileNotFoundException, IOException {
        List<String> input = Files.readAllLines(Paths.get(inFile));
        return input.toArray(new String[0]);
    }

    private static void writeOutResult(Data[] sorted, String outputFilename) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(outputFilename);
        for (Data s : sorted) {
            out.println(s.value());
        }
        out.close();
    }

    private static class M_LRMUSComparator implements Comparator<Data> {
        @Override
        public int compare(Data s1, Data s2) {

            /* Length test */
            if (s1.M_LRMUSLength() < s2.M_LRMUSLength()) {
                return -1;
            }
            if (s1.M_LRMUSLength() > s2.M_LRMUSLength()) {
                return 1;
            }

            /* Position test*/
            if (s1.M_LRMUSPosition() < s2.M_LRMUSPosition()) {
                return -1;
            }
            if (s1.M_LRMUSPosition() > s2.M_LRMUSPosition()) {
                return 1;
            }

            /* Alphabetical test */
            int tmp = s1.M_LRMUSStr().compareTo(s2.M_LRMUSStr()); // NOTE:  This typically returns values outside the set {-1,0,1}, but the sign still determines ordering
            if (tmp != 0) {
                return (tmp);
            }

            /* Fallback */
            return (s1.value().compareTo(s2.value())); //This too.
        }
    }

    private static class Data {
        private LRMUS best = null; // Easy way to indicate variables are uninitialized.
        private String string = null;

        // Data constructor
        public Data(String str) {
            string = new String(str);
            best = new LRMUS(string);
            best.findBest(); // Updates best so it contains the best LRMUS
        }

        public int M_LRMUSLength() {
            return best.length;
        }

        public int M_LRMUSPosition() {
            return best.position;
        }

        public String M_LRMUSStr() {
            return best.getLRMUS();
        }

        public String value() {
            return string;
        }


        private class LRMUS {
            public int position = Integer.MAX_VALUE; // Initial Position is as bad as possible
            public int length = Integer.MIN_VALUE;                   // Initial Length is as bad as possible
            public String referenceStr;

            public LRMUS(String str) {
                referenceStr = str; // REFERENCE to original string
            }

            private class LongestRepeatingSubstring {
                private String s;
                private int pos;

                public LongestRepeatingSubstring(String s, int pos) {
                    this.s = s;
                    this.pos = pos;
                }
            }

            // Longest Repeated Substring Comparator
            private class LRSComparator implements Comparator<LongestRepeatingSubstring> {
                @Override
                public int compare(LongestRepeatingSubstring s1, LongestRepeatingSubstring s2) {
                    if (s1.s.compareTo(s2.s) >= 1) {
                        return 1;
                    }
                    if (s1.s.compareTo(s2.s) < 1) {
                        return -1;
                    }

                    return -1;
                }
            }


            // Compute longest repeated substring
            public void initLRS(String str) {
                String s;
                s = str.replaceAll("\\s+", " ");
                ArrayList<String> x = findRepeatedSubstring(s);
                for (int star = 0; star < str.length() - x.get(0).length(); star++) {
                    if (x.contains(str.substring(star, star + x.get(0).length()))) {
                        this.position = star;
                        this.length = x.get(0).length() + 1;
                        break;
                    }
                }
            }

            // Return the longest repeated substring in str
            public ArrayList<String> findRepeatedSubstring(String str) {

                // Create a suffix array from str
                int N = str.length();
                LongestRepeatingSubstring[] suffixes = new LongestRepeatingSubstring[N];
                for (int i = 0; i < N; i++) {
                    suffixes[i] = new LongestRepeatingSubstring(str.substring(i, N), i);
                }

                Arrays.sort(suffixes, new LRSComparator());

                // Find the LRS
                ArrayList<String> LRS = new ArrayList<String>();
                for (int i = 0; i < N - 1; i++) {
                    LongestRepeatingSubstring x = longestCommonPrefix(suffixes[i], suffixes[i + 1]);
                    if (LRS.isEmpty() || x.s.length() > LRS.get(0).length()) {
                        LRS = new ArrayList();
                        LRS.add(x.s);
                    } else if (x.s.length() == LRS.get(0).length()) {
                        LRS.add(x.s);
                    }
                }
                return LRS;
            }

            // Return the LCP (longest common prefix)
            public LongestRepeatingSubstring longestCommonPrefix(LongestRepeatingSubstring s, LongestRepeatingSubstring t) {
                int n = Math.min(s.s.length(), t.s.length());
                for (int i = 0; i < n; i++) {
                    if (s.s.charAt(i) != t.s.charAt(i))
                        return new LongestRepeatingSubstring(s.s.substring(0, i), t.pos);
                }
                return new LongestRepeatingSubstring(s.s.substring(0, n), s.pos);
            }


            public void findBest() {
                initLRS(referenceStr);
            }

            public String getLRMUS() {
                if (referenceStr == null) {
                    System.out.println("ReferenceStr is uninitialized");
                    return "";
                }
                if (length < 0) {
                    System.out.println("No LRMUS was found");
                    System.out.println("Returning full string ->" + referenceStr + "<-");
                    return referenceStr;
                }
                if (position < 0 || position + length > referenceStr.length()) {
                    System.out.println("Error:  Bad starting position " + position);
                    System.out.println("Returning full string ->" + referenceStr + "<-");
                    return referenceStr;
                }
                return referenceStr.substring(position, position + length);
            }

            public void report() {
                System.out.println("LRMUS for " + referenceStr);
                System.out.println("\tLRMUS length: " + length);
                System.out.println("\tLRMUS Position " + position);
                System.out.println("\tLRMUS " + getLRMUS());
            }
        }


        /*public static void testM_LRMUS() {

            Data testItem, testItem2;
            M_LRMUSComparator comparator = new M_LRMUSComparator();

            testItem = new Data("Morrocco");
            testItem.best.report();

            testItem = new Data("baabaabbbbaa");
            testItem.best.report();

            testItem = new Data("pqrpqpqabab");
            testItem.best.report();

            testItem = new Data("abcpqrabpqpq");
            testItem.best.report();

            testItem = new Data("Morrorrcco");
            testItem.best.report();

            testItem = new Data("abcdefghijklm");
            testItem.best.report();

            testItem = new Data("atatat");
            testItem.best.report();

            System.out.println("---");

            testItem = new Data("Zunzibar");
            testItem2 = new Data("rorocco");
            System.out.println(comparator.compare(testItem, testItem2));
            System.out.println(comparator.compare(testItem2, testItem2));
            System.out.println(comparator.compare(testItem2, testItem));
            testItem.best.report();
            testItem2.best.report();

            System.out.println("---");

            testItem = new Data("**cat**");
            testItem2 = new Data("***cat**");
            System.out.println(comparator.compare(testItem, testItem2));
            System.out.println(comparator.compare(testItem2, testItem2));
            System.out.println(comparator.compare(testItem2, testItem));
            testItem.best.report();
            testItem2.best.report();

            System.out.println("---");
        }*/

    }


}
