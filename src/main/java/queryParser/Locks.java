package queryParser;

import java.io.*;

public class Locks {
    public static boolean checkLock(String username, String tablename) {
        boolean isTablelocked = false;
        try {
            File lockFile = new File("Output/Table_Locks.txt");
            FileReader locks = new FileReader(lockFile);
            BufferedReader bufferedReader = new BufferedReader(locks);
            String currentLine;
            while ((currentLine=bufferedReader.readLine()) != null) {
                String columnSplit[] = currentLine.split(" ");
                if (username.equals(columnSplit[0]))
                {
                    if (tablename.equals(columnSplit[1])) {
                        if (columnSplit[2].equals("TRUE")) {
                            isTablelocked = true;   // the table is locked
                        }
                        else{
                            isTablelocked = false;
                        }
                    }
                }
                currentLine = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isTablelocked;
    }

    public static void setLock(String username, String tablename) throws IOException {
        File lockFile = new File("Output/Table_Locks.txt");
        FileReader fileReaderTemp = new FileReader(lockFile);
        BufferedReader bufferedReaderTemp = new BufferedReader(fileReaderTemp);
        File temporaryFile = new File("Output/temp.txt");  //the file where we will copy
        FileWriter fileWriterTemp = new FileWriter(temporaryFile, true);
        try {
            String currentLineTemp;
            int flag = 0;
            while ((currentLineTemp = bufferedReaderTemp.readLine()) != null) {
                String columnSplitTemp[] = currentLineTemp.split(" ");
                if ((username.equals(columnSplitTemp[0])) && (tablename.equals(columnSplitTemp[1])) && (columnSplitTemp[2].equalsIgnoreCase("FALSE"))) {
                    fileWriterTemp.append(username + " ").append(tablename + " ").append("TRUE").append("\n");
                    flag =1;
                }
                else {
                    fileWriterTemp.append(columnSplitTemp[0]+" ").append(columnSplitTemp[1]+" ").append(columnSplitTemp[2]).append("\n");
                }
            }
            if(flag == 0) {
                fileWriterTemp.append(username + " ").append(tablename + " ").append("TRUE").append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            bufferedReaderTemp.close();
            fileWriterTemp.flush();
            lockFile.delete();
            temporaryFile.renameTo(lockFile);
        }
    }

    public static void removeLock(String username, String tablename) throws IOException {
        File lockFile = new File("Output/Table_Locks.txt");
        FileReader fileReader = new FileReader(lockFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        File tempFile = new File("temp.txt");  //the file where we will cop y
        FileWriter fileWriterTemp = new FileWriter(tempFile, true);
        try {
            String currentLine ;
            while ((currentLine =bufferedReader.readLine()) != null)   //run through entire data text file and copy to new one
            {
                String columnSplitTemp[] = currentLine.split(" ");
                if ((username.equals(columnSplitTemp[0])) && (tablename.equals(columnSplitTemp[1])) && (columnSplitTemp[2].equalsIgnoreCase("TRUE"))) {
                    fileWriterTemp.append(username + " ").append(tablename + " ").append("FALSE").append("\n");
                }
                else {
                    fileWriterTemp.write(currentLine);
                    fileWriterTemp.write("\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            fileWriterTemp.flush();
            bufferedReader.close();
            fileWriterTemp.close();
            lockFile.delete();
            tempFile.renameTo(new File("Output/Table_Locks.txt"));
        }
    }
}




