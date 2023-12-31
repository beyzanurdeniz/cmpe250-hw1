import java.io.*;

//TODO: time test

public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
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
                parts = line.split(" ");
                String command = parts[0];
                if (command.equals("MEMBER_IN")) {
                    name = parts[1];
                    GMS = Double.parseDouble(parts[2]);
                    tree.printMemberIn(name, GMS, writer);
                    tree.insert(name, GMS);
                } else if (command.equals("MEMBER_OUT")) {
                    name = parts[1];
                    GMS = Double.parseDouble(parts[2]);
                    tree.remove(name, GMS);
                } else if (command.equals("INTEL_TARGET")) {
                    String name1 = parts[1];
                    Double GMS1 = Double.parseDouble(parts[2]);
                    String name2 = parts[3];
                    Double GMS2 = Double.parseDouble(parts[4]);
                    tree.intelTarget(GMS1, name1, GMS2, name2);
                } else if (command.equals("INTEL_DIVIDE")) {
                    tree.intelDivide();
                } else if (command.equals("INTEL_RANK")) {
                    name = parts[1];
                    GMS = Double.parseDouble(parts[2]);
                    tree.intelRank(name, GMS);
                }

            }
            long end = System.currentTimeMillis();
            System.out.println("Time: " + (end - start) + "ms");

            reader.close();
            writer.close();
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }

    }
}
