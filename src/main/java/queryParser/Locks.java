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
                            if (columnSplit[2].equals("true")) {
                                isTablelocked = true;   // the table is locked
                            }
                            else{
                                isTablelocked = false;
                                setLock(username,tablename);
                            }
                        }
                    }
                currentLine = bufferedReader.readLine();
            }
            setLock(username, tablename);
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isTablelocked;
    }

    public static void setLock(String username, String tablename) {
        try {
            boolean isTablelocked = false;
            int count=0;
            File lockFile = new File("Output/Table_Locks.txt");
            FileReader fileReaderTemp = new FileReader(lockFile);
            BufferedReader bufferedReaderTemp = new BufferedReader(fileReaderTemp);
            File temporaryFile = new File("temp.txt");  //the file where we will copy
            FileWriter fileWriterTemp = new FileWriter(temporaryFile, true);
            String currentLineTemp;
            if(count==0) {
                fileWriterTemp.append(username).append(" ").append(tablename).append(" ").append("false").append("\n");
                count++;
            }
            while ((currentLineTemp = bufferedReaderTemp.readLine()) != null) {
                String columnSplitTemp[] = currentLineTemp.split(" ");
                if (username.equals(columnSplitTemp[0])) {
                        if (tablename.equals(columnSplitTemp[1])) {
                            if (columnSplitTemp[2].equals("true")) {
                                isTablelocked = true;  // the table is already locked
                            } else if (columnSplitTemp[2].equalsIgnoreCase("false")) {
                                // We will Lock table i it's not locked.
                                fileWriterTemp.append("[ Username:").append(columnSplitTemp[0]).append("]").append(" ")
                                        .append("[ TableName: ").append(columnSplitTemp[1]).append("]").append(" ")
                                        .append(" Lock Status: TRUE").append("\n");
                            }
                        } else {
                            fileWriterTemp.write(currentLineTemp);
                            fileWriterTemp.write("\n");
                        }
                    } else {
                        fileWriterTemp.write(currentLineTemp);
                        fileWriterTemp.write("\n");
                    }
                }
            fileWriterTemp.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public static void removeLock(String username, String tablename) {
        try {
            File lockFile = new File("Output/Table_Locks.txt");
            FileReader fileReader = new FileReader(lockFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            File tempFile = new File("temp.txt");  //the file where we will cop y
            FileWriter fileWriterTemp = new FileWriter(tempFile, true);
            String currentLine ;
            while ((currentLine =bufferedReader.readLine()) != null)   //run through entire data text file and copy to new one
            {
                String splitColumns[] = currentLine.split(" ");
                if (username.equalsIgnoreCase(splitColumns[0])) {
                    if (tablename.equalsIgnoreCase(splitColumns[1])) {
                        fileWriterTemp.append("[ Username:").append(splitColumns[0]).append("]").append(" ")
                                .append("[ TableName: ").append(splitColumns[1]).append("]").append(" ")
                                .append(" Lock Status: TRUE").append("\n");
                    } else {
                        fileWriterTemp.write(currentLine);
                        fileWriterTemp.write("\n");
                    }
                } else {
                    fileWriterTemp.write(currentLine);
                    fileWriterTemp.write("\n");
                }

                //currentLine = bufferedReader.readLine();
            }
            fileWriterTemp.flush();
            bufferedReader.close();
            fileWriterTemp.close();
            lockFile.delete();
            tempFile.renameTo(new File("Output/Table_Locks.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
