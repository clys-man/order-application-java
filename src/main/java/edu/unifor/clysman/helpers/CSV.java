package edu.unifor.clysman.helpers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class CSV {
    private final String path;

    public CSV(String path) {
        this.path = path;
    }

    public String toCSV(String[] values) {
        StringBuilder builder = new StringBuilder();

        for (String value : values) {
            builder.append(value).append(",");
        }

        return builder.toString();
    }

    public void write(String content) {
        try {
            FileWriter writer = new FileWriter(this.path);
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void writeLine(String content) {
        try {
            FileWriter writer = new FileWriter(this.path, true);
            writer.write(content + "\n");
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void writeLine(String[] content) {
        writeLine(toCSV(content));
    }

    public String[] getHeaders() {
        try {
            FileReader reader = new FileReader(this.path);
            StringBuilder builder = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                if (character == '\n') {
                    break;
                }
                builder.append((char) character);
            }
            reader.close();
            return builder.toString().split(",");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object[][] getRows() {
        try {
            FileReader reader = new FileReader(this.path);
            StringBuilder builder = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                builder.append((char) character);
            }
            reader.close();
            String[] lines = builder.toString().split("\n");
            Object[][] rows = new Object[lines.length][];
            for (int i = 0; i < lines.length; i++) {
                rows[i] = lines[i].split(",");
            }
            return rows;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
