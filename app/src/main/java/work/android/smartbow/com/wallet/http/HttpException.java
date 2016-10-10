/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.http;

public class HttpException extends Exception {
	private static final String TAG = "HttpException";
	
    private final int statusCode;
    private final String statusDescription;

    /** Constructs a new exception with the specified error code.
      * Numeric error codes are specified by the HTTP standard.
      */
    HttpException(int statusCode) {
        this.statusCode = statusCode;
        this.statusDescription = HttpStatus.getStatusDescription(statusCode);
    }

    /** Returns the error code of this exception.*/
    public int getCode() {
        return statusCode;
    }

    /** Returns the HTML error page that should be shown to the
      * HTTP client.
      */
    public String getErrorPage() {
        StringBuffer page = new StringBuffer(120);
        page.append("<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\"");
        page.append("<HTML><BODY><H1 ALIGN=CENTER>Error Message: ");
        page.append( statusDescription + ". Error Code: " + statusCode );
        page.append("</H1></BODY></HTML>");
        return page.toString();
    }

}
