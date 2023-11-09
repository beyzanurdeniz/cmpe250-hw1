import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//TODO: create other operations in while loop
//TODO: add necessary comments and documentation

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java Main <input_file> <output_file>");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));


            String line;
            line = reader.readLine();

            String[] parts = line.split(" ");
            double GMS = Double.parseDouble(parts[1]);
            String name = parts[0];
            AVLTree<String, Double> tree = new AVLTree<>(GMS, name, writer);

            while ((line = reader.readLine()) != null) {
                // Process the content as needed, for example, you can simply write it to the output file
                parts = line.split(" ");
                    String command = parts[0];
                    if(command.equals("MEMBER_IN")){
                        name = parts[1];
                        GMS = Double.parseDouble(parts[2]);
                        tree.insert(name, GMS);
                    }

            }

            reader.close();
            writer.close();
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
