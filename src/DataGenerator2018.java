import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataGenerator2018 {
    /**
     * See Readme for an overview of the data generating process. The result is
     * being written to the file given as the first command line argument.
     *
     * @param args
     *
     *
     *
     *            Author: Peter Dolan (based off work by Elena Machkasova)
     */

    // relative probabilities of each length up to maxLength; add up to 1500:
    private static Random rand = new Random();
    private static String corpus;

    public static void main(String[] args) throws FileNotFoundException,IOException {
        corpus=new String(Files.readAllBytes(Paths.get("corpus.txt"))); //I've hardwired in the corpus.txt requirement... bad Peter!
        makeSet("data0.txt",10000);
        makeSet("data1.txt",10000);
        makeSet("data2.txt",10000);
        makeSet("data3.txt", 10000);
    }


    private static void makeSet(String outFileName, int n) throws FileNotFoundException,IOException {
        PrintWriter out = new PrintWriter(outFileName);
        generateData(n, out);

        if (out != null) {
                out.close();
        }

    }
    // generate data and print it to a file:

    private static void generateData(int numElts, PrintWriter out) {

            // numbers are generated digit-by-digit.
            // An array to store the digits:
            int start;
            int length;
            String sample;

            for (int i = 0; i < numElts; ++i) {
                    // generate length:
                    length = 100+rand.nextInt(100); // Random int between 30 and 80
                    start = 1+rand.nextInt(corpus.length()-length);

                    sample=corpus.substring(start,start+length);

                    if (out == null) { // no file specified, so print to standard output
                            System.out.println(sample);
                    } else { // print to the print writer:
                            out.println(sample);
                            }
                    }
            }
    }
