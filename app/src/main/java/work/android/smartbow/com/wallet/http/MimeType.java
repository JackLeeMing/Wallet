/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class MimeType {
	private static final String TAG = "MimeType";
	
    /** A guess about the size of the dictionary.*/
    private final static int GUESS_MAPPING_SIZE = 160;
    /** The internal storage of the dictionary of mappings.*/
    private final HashMap<String,String> types = new HashMap<String,String>(GUESS_MAPPING_SIZE);

    /** Constructs a new dictionary, reading the mappings from the
      * specified <code>File</code>.
      */
    public MimeType(InputStream mappings) {
        try {
            BufferedReader mappingFile =
                            new BufferedReader(new InputStreamReader(mappings));
            String line, type;
            while ((line = mappingFile.readLine()) != null) {
                StringTokenizer thisLine = new StringTokenizer(line);
                if (thisLine.countTokens() < 2 || line.startsWith("#"))
                    continue; // skip comments and lines without tokens
                
                // the first token in the line is the MIME type
                type = thisLine.nextToken();
                while (thisLine.hasMoreTokens()) {
                    // map the next extension to the MIME type
                    types.put(thisLine.nextToken(), type);
                }
            }
            mappingFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Returns the MIME type of the specified file extension. The file
      * extension should be passed without a leading period. If no
      * MIME type is found, "application/octet-stream" is returned.
      */
    public String getContentType(String extension) {
        String type = (String)types.get(extension);
        if (type == null) {
            return "application/octet-stream";
        } else {
            return type;
        }
    }

    /** Returns the MIME type of the specified <code>File</code>.
      * Currently, this just feeds the extension of the filename to
      * {@link #getContentType(String)}. However, a future
      * implementation may use information from <code>File</code> to
      * help determine the MIME type.
      */
    public String getContentType(File fullFilename) {
        String filename = fullFilename.getName();
        int lastPeriod = filename.lastIndexOf('.');
        String extension;
        if (lastPeriod == -1) {
            // if there was no extension, use an empty string
            extension = "";
        } else {
            extension = filename.substring(lastPeriod+1, filename.length());
        }
        return getContentType(extension);
    }

    /** Adds a mapping to the dictionary. If a mapping for this extension
      * already exists, it is replaced. This method is synchronized, which
      * should allow multiple threads to add and query content types
      * concurrently.
      */
    public synchronized void addContentType(String extension, String type) {
        types.put(extension, type);
    }
}
