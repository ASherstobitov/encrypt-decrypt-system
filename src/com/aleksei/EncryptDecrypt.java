package com.aleksei;

import java.io.*;

public class EncryptDecrypt {

    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean mode;
        int key = 0;
        String data;
        String alg;

        while (true) {
            System.out.println("Select the operation: enc or dec");
            String modeParam = null;
            try {
                modeParam = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (modeParam.equals("enc") || modeParam.equals("")) {
                mode = false;
                break;
            } else if (modeParam.equals("dec")) {
                mode = true;
                break;
            } else {
                System.out.println("Selected wrong operation, try again!!!");
            }
        }

        while (true) {
            try {
                System.out.println("Enter the key more than 0");
                String temp = reader.readLine();
                if (temp.equals("")) {
                    break;
                }
                key = Integer.parseInt(temp);
                if (key < 0) {
                    System.out.println("Entered the number less than 0, try again!!!");
                } else if (key >= 0) {
                    break;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Entered not number!!!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        StringBuilder builder = new StringBuilder();
        String in = "";
        while (true) {
            System.out.println("Enter the file name with data. Example: input.txt");
            try {
                in = reader.readLine();
            if (in.equals("")) {
                break;
            }
            boolean checkIn = new File(in).exists();
            if (!checkIn) {
                System.out.println("File not found, try again or type space for manual typing data");
            } else {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(in));
                String line = bufferedReader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
                break;
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Enter data");
        String manualData = null;
        try {
            manualData = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (!in.equals("") && manualData.equals("")) {
            data = builder.toString();
        } else if (in.equals("") && manualData.equals("")){
            data = "";
        } else {
            data = manualData;
        }

        String result;
        while (true) {
            System.out.println("Select the algorithm: unicode or shift");
            try {
                alg = reader.readLine();
            if (alg.equals("shift") || alg.equals("")) {
                result = alphabetCrypt(data, key, mode);
                break;
            } else if (alg.equals("unicode")) {
                result = asciiCrypt(data, key, mode);
                break;
            } else {
                System.out.println("Selected wrong operation, try again!!!");
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        while (true) {
            System.out.println("Enter the target file name. Example: output.txt");
            String out = null;
            try {
                out = reader.readLine();
            if (out.equals("")) {
                System.out.println(result);
                break;
            }
            boolean checkIn = new File(out).exists();
            if (!checkIn) {
                System.out.println("File not found, try again or type space for getting the result in the console");
            } else {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(out)));
            writer.write(result);
            writer.close();
            break;
            }
            reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String asciiCrypt(String date, int key, boolean mode) {

        char[] symbols = date.toCharArray();
        int realKey = setKey(key, mode);
        for (int i = 0; i < symbols.length; i++) {
            int syb = symbols[i];
            syb += realKey;
            symbols[i] = (char) syb;
        }
        String result = String.valueOf(symbols);
        return result;
    }

    public static String alphabetCrypt(String date, int key, boolean mode) {
        char[] symbols = new char[date.length()];
        for (int i = 0; i < date.length(); i++) {
            int realKey = setKey(key, mode);
            char sym = setSym(mode);
            int ch = date.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                ch = ((ch - sym + realKey) % 26) + sym;
            } else if (ch >= 'A' && ch <= 'Z') {
                ch = ((ch - Character.toUpperCase(sym) + realKey) % 26) + Character.toUpperCase(sym);
            }
            symbols[i] = (char) ch;
        }
        String result = String.valueOf(symbols);
        return result;
    }

    private static char setSym(boolean mode) {
        return !mode ? 'a' : 'z';
    }

    private static int setKey(int key, boolean mode) {
        return !mode ? key : -1 * key;
    }
}
