/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.http;

import android.util.Log;
import android.webkit.MimeTypeMap;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpServerConnection;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Date;

public class HttpServerThread extends Thread {
	private static final String TAG = "HttpServerThread";
	
    // per-server resources: these are created once per ServerThread
    private final HttpServerThreadPool mHttpServerThreadPool;
    
    private File mFile = null;
    private boolean mConnectionKeepAlive = false;
    private DefaultHttpServerConnection mHttpServerConnection = null;
    
    private byte[] mLock = new byte[0];
    private boolean mForceExit = false;

    /** Constructs a new ServerThread using the specified values. This should
      * only rarely be called directly: for most purposes, you should spawn
      */
    HttpServerThread(ThreadGroup group, HttpServerThreadPool hstPool) {
        super(group, "");
        mHttpServerThreadPool = hstPool;
        
        mHttpServerConnection = new DefaultHttpServerConnection();
    }
    
    public void stopHttpServerThread(){
    	synchronized( mLock ){
  		     mForceExit = true;
  	    }
    	
    	if( mHttpServerConnection != null && mHttpServerConnection.isOpen() ){
        	try {
        		mHttpServerConnection.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
   }

    /** Begins an infinite loop waiting for connections and serving them.*/
    public void run() {
        
        mForceExit = false;
        
        while (true) {
        	Socket clientSocketConnect = null;

            /* Wait until we find an incoming connection. If
             * pool is non-empty, there is already a connection
             * waiting to be processed, so we can skip the wait().
             * 
             */
             while( mHttpServerThreadPool.isEmpty() ) {
                 try{
                	 synchronized( mHttpServerThreadPool ){
                	     mHttpServerThreadPool.wait();
                	 }
                 } catch (InterruptedException e) {
                     /* We were interrupted by another thread. In the
                      * current design, this means the ServerPool wants
                      * us to die.
                      */
                      return;
                 }
                 
                 break;
             }
             
             // finally, we have an incoming connection
             clientSocketConnect = mHttpServerThreadPool.removeFirstClientConnection();
             
             synchronized( mLock ){
             	if( mForceExit ){
             		//exit the Thread 
            		Log.i(TAG, "Thread is exited!");
            		if( clientSocketConnect != null ){
            		    try {
						    clientSocketConnect.close();
					    } catch (IOException e) {
						    // TODO Auto-generated catch block
						    e.printStackTrace();
					    }
            		}
            		return;
             	}
         	}
                    
             if( clientSocketConnect != null ){
                 // start the HTTP transaction with the client
                 handleHttpRequest( clientSocketConnect );
            
                 mHttpServerThreadPool.decrementBusyThreadNum();
                 
                 if( mHttpServerConnection != null && mHttpServerConnection.isOpen() ){
                 	try {
                 		mHttpServerConnection.close();
         			} catch (IOException e1) {
         				// TODO Auto-generated catch block
         				e1.printStackTrace();
         			}
                 }
       	    }
             
            Log.i(TAG,"run() exited!");
             
            return;
        }
    }
    
    private void handleHttpRequest(Socket clientSocketConnect){
    	try {
		    HttpParams params = new BasicHttpParams();  
		    mHttpServerConnection.bind(clientSocketConnect, params); 
		    while( true ){
		    	if( !mHttpServerConnection.isOpen() )
		    		return;
		    	synchronized( mLock ){
	            	if( mForceExit ){
	            		//exit the Thread 
	           		    Log.i(TAG, "Thread is exited!");
	           		    return;
	            	}
	        	}
		        HttpRequest request = mHttpServerConnection.receiveRequestHeader();
		        Log.i(TAG,"handleHttpRequest= "+request.toString());
		        if( request instanceof BasicHttpRequest ){
		            RequestLine rl = request.getRequestLine();
		            String method = rl.getMethod();
		            String target = rl.getUri();
		            //ProtocolVersion httpVer = rl.getProtocolVersion();
		            Header Connection = request.getFirstHeader("Connection");
		            if( Connection != null ){
		                String connection = Connection.getValue();
		                if( connection != null && connection.equals("Keep-Alive") )
		                    mConnectionKeepAlive = true;
		                else
		                    mConnectionKeepAlive = false;
		            }
		            if( method.equalsIgnoreCase( "GET" ) ){
		                String service = null;
		                int target_end = target.indexOf('/', 1);// "/download/system/data/zsge.mp4"
		                if( target_end >= 0 )
		                    service = target.substring(0, target_end); // "/download"
		                if( service != null && service.equals("/download") ){  //only support 
		                    String filepath = target.substring( target_end );// For example: "/system/data/zsge.mp4"
		                    if( filepath != null && !filepath.equals("") ){
		                        int ret = handlerDownload(request,filepath);
		                        if( ( ret == 0 || ret == 1) && mConnectionKeepAlive )
		                            continue;
		                    }else
		                        sendErrorMessage(404,"File not found!");
		                }else
		                    sendErrorMessage(404,"File not found!");
		            }else if( method.equalsIgnoreCase( "HEAD" ) ){
		            	String service = null;
		                int target_end = target.indexOf('/', 1);// "/download/system/data/zsge.mp4"
		                if( target_end >= 0 )
		                    service = target.substring(0, target_end); // "/download"
		                if( service != null && service.equals("/download") ){  //only support 
		                    String filepath = target.substring( target_end );// For example: "/system/data/zsge.mp4"
		                    if( filepath != null && !filepath.equals("") ){
		                    	File file = new File( filepath);
		                    	if( !file.exists() || file.isDirectory() ){
		                    		sendErrorMessage(404,"File not found!");
		                    		break;
		                    	}
		                    	
		                		boolean needData = false;
		                		long ranges[] = null;
		                    	Header Range = request.getFirstHeader("Range");
		             		    if( Range != null ){
		             		    	ranges = getRange(Range);
		             		    	if( ranges != null )
		             		    		needData = true;
		             		    }
		                    	String mimetype= MimeTypeMap.getSingleton().getMimeTypeFromExtension( MimeTypeMap.getFileExtensionFromUrl( filepath )); 
		                    	HttpResponse response = new BasicHttpResponse(new ProtocolVersion("HTTP",1,1), 206, "Partial Content");
		                        response.addHeader("Date", (new Date(System.currentTimeMillis())).toGMTString());
		                        response.addHeader("Server", "Server: Apache/2.2.14 (Ubuntu)");
		                        //response.addHeader("Last-Modified", "Tue, 28 Feb 2012 08:37:39 GMT");
		                        //response.addHeader("ETag", "\"1ff336-8144770-4ba0223beb436\"");
		                       
		                        response.addHeader("Content-Type", (mimetype != null ? mimetype : "*/*"));
		                        if( needData ){
		                        	response.addHeader("Content-Length", "1");//String.valueOf( file.length() ));
		                            response.addHeader("Accept-Ranges", "bytes");
		                            response.addHeader("Content-Range", "bytes 0-0/"+file.length());
		                        }
		                        response.addHeader("Connection", "Close");
		                        
		                        try {
		            				mHttpServerConnection.sendResponseHeader(response);
		            			} catch (HttpException e) {
		            				// TODO Auto-generated catch block
		            				e.printStackTrace();
		            			} catch (IOException e) {
		            				// TODO Auto-generated catch block
		            				e.printStackTrace();
		            			}
		            			
		            			if( needData ){
		            				InputStream inputStream = null;
			                    	try {
			                			inputStream = new FileInputStream(file);
			                		} catch (FileNotFoundException e1) {
			                			// TODO Auto-generated catch block
			                			e1.printStackTrace();
			                		}
			                		
		            			    inputStream.skip( ranges[0] );
		            			    response.setEntity(new InputStreamEntity(inputStream, ranges[1]-ranges[0]+1));
		            		     
		            		        try {
		            				    mHttpServerConnection.sendResponseEntity(response);
		            			    } catch (HttpException e) {
		            				    // TODO Auto-generated catch block
		            				    e.printStackTrace();
		            			    } catch (IOException e) {
		            				    // TODO Auto-generated catch block
		            				    e.printStackTrace();
		            			    }
		                            inputStream.close();
		            			}
		                    }else
		                        sendErrorMessage(404,"File not found!");
		                }else
		                    sendErrorMessage(404,"File not found!");
		            }else if( method.equalsIgnoreCase( "POST" ) ){
		            	
		            }else if( method.equalsIgnoreCase( "PUT" ) ){
		            	
		            }
		        }else if( request instanceof HttpEntityEnclosingRequest ){ 
		            mHttpServerConnection.receiveRequestEntity((HttpEntityEnclosingRequest) request); 
		            HttpEntity entity = ((HttpEntityEnclosingRequest) request) .getEntity(); 
		            if( entity != null ){ 
		                // ��ʵ���������õ����飬�����ʱ����ʡ�������ݶ������ĵ� 
		                // ���Եײ����ӿ��ܱ����á� 
		                //EntityUtils.consume(entity); 
		            } 
		        }
		        
		        break;
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
    }
    
    /*
     * return 0: end  1: continue  -1: error
     */
    private int handlerDownload(HttpRequest request,String fielPath){
 	    if( request != null && fielPath != null && !fielPath.equals("") ){
    		long ranges[] = null;
        	Header Range = request.getFirstHeader("Range");
 		    if( Range != null ){
 		    	ranges = getRange(Range);
 		    	if( ranges != null ){
 			    	//send file by Partial Content
				    Log.i(TAG,"handlerDownload() sendPartialFile======");
				    sendPartialContentOfFile(fielPath,ranges[0],ranges[1]);
				    return 1;
 			    }
 		    }else{
 		    
 		        //send file by looper
 	 	        Log.i(TAG,"handlerDownload() sendFile======");

 	 	        sendFile( fielPath );
 		    }
 	    }

 	    return 0;
    }
    
    private void sendErrorMessage(int errorCode,String errorMessage){
    	Log.i(TAG,"sendErrorMessage() errorCode:"+errorCode+",errorMessage:"+errorMessage);
    	if( mHttpServerConnection != null ){
    		String html = "<html>\r\n"
                        + "<title>Error Message</title>\r\n"
                        + "<body>\r\n"
                        + "<h1>ErrorCode:"+errorCode+",Message:"+errorMessage+"</h1>\r\n"
	                    + "</body>\r\n"
	                    + "</html>\r\n";
	        
	        HttpResponse response = new BasicHttpResponse(new ProtocolVersion("HTTP",1,1), errorCode, errorMessage); 
		    try {
				response.setEntity(new StringEntity( html ));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    try {
				mHttpServerConnection.sendResponseHeader(response);
				mHttpServerConnection.sendResponseEntity(response);
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    	}
    }
    
    private void sendFile(String filename){
    	File file = new File( filename);
    	if( !file.exists() || file.isDirectory() ){
    		sendErrorMessage(404,"File not found!");
    		return;
    	}
    	
    	InputStream inputStream = null;
    	try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	if( inputStream != null ){
            try {
            	int result = 0;
                String mimetype= MimeTypeMap.getSingleton().getMimeTypeFromExtension( MimeTypeMap.getFileExtensionFromUrl(filename)); 

                HttpResponse response = new BasicHttpResponse(new ProtocolVersion("HTTP",1,1), 200, "OK");
                response.addHeader("Date", (new Date(System.currentTimeMillis())).toGMTString());
                response.addHeader("Server", "Server: Apache/2.2.14 (Ubuntu)");
                //response.addHeader("Last-Modified", "Tue, 28 Feb 2012 08:37:39 GMT");
                //response.addHeader("ETag", "\"1ff336-8144770-4ba0223beb436\"");
                response.addHeader("Content-Length", String.valueOf( file.length() ));
                response.addHeader("Content-Type", (mimetype != null ? mimetype : "*/*"));
                if( mConnectionKeepAlive ){
                	response.addHeader("Keep-Alive", "timeout=15, max=100");
                	response.addHeader("Connection", "Keep-Alive"); 
                }else{
                	response.addHeader("Connection", "Close"); 
                }
                
                try {
    				mHttpServerConnection.sendResponseHeader(response);
    			} catch (HttpException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} 
    			
    			inputStream.skip( 0 );
                
    			response.setEntity(new InputStreamEntity(inputStream, file.length()));
     
    		    try {
    				mHttpServerConnection.sendResponseEntity(response);
    			} catch (HttpException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
                inputStream.close();
	   
                //Log.i(TAG,"sendFile() Send file OK filename:"+filename);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    	}else
        	sendErrorMessage(404,"File Not Found!");
    }
    
    private void sendPartialContentOfFile(String filename,Long start,Long end){ 	
    	if( mFile == null )
    		mFile = new File( filename);
    	
    	if( !mFile.exists() || mFile.isDirectory() ){
    		sendErrorMessage(404,"File not found!");
    		return;
    	}
    	
    	InputStream inputStream = null;
    	try {
			inputStream = new FileInputStream( mFile );
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

    	if( inputStream != null ){
            try {
            	long len = 0;
            	if( start < 0 ){
            		len = -start;
            		start = mFile.length() - len;
            	}else{
                    if( end > start )
                	    len = end - start + 1;
                    else
                	    len = mFile.length() - start;
            	}
                
                String mimetype= MimeTypeMap.getSingleton().getMimeTypeFromExtension( MimeTypeMap.getFileExtensionFromUrl(filename)); 

                HttpResponse response = new BasicHttpResponse(new ProtocolVersion("HTTP",1,1), 206, "Partial Content");
                response.addHeader("Date", (new Date(System.currentTimeMillis())).toGMTString());
                response.addHeader("Server", "Server: Apache/2.2.14 (Ubuntu)");
                response.addHeader("Content-Length", String.valueOf( len ));
                response.addHeader("Content-Type", (mimetype != null ? mimetype : "*/*"));
                response.addHeader("Accept-Ranges", "bytes");
                String bytes = "";
                if( end > start )
                	bytes = start + "-" + end + "/" + mFile.length();
                else
                	bytes = start + "-" + (mFile.length()-1) + "/" + mFile.length();
                response.addHeader("Content-Range", "bytes " + bytes);
                if( mConnectionKeepAlive ){
                	response.addHeader("Keep-Alive", "timeout=15, max=100");
                	response.addHeader("Connection", "Keep-Alive");
                }else{
                	response.addHeader("Connection", "Close"); 
                    //return;
                }
                
                try {
    				mHttpServerConnection.sendResponseHeader(response);
    			} catch (HttpException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			
    			inputStream.skip( start );
    			
    			response.setEntity(new InputStreamEntity(inputStream, len));
    		     
    		    try {
    				mHttpServerConnection.sendResponseEntity(response);
    			} catch (HttpException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    	}else
        	sendErrorMessage(404,"File Not Found!");
    }
    
    /*
     * range: HTTP Range field
     * return: int[0]: start   int[1]: end
     */
    private long[] getRange(Header range){
    	long ranges[] = new long[2];
    	
    	if( range != null ){
		    String range_str = range.getValue();
		    if( range_str != null && !range_str.equals("") ){
		    	String split_str[] = range_str.split("=");
		        if( split_str != null && split_str.length == 2 ){
		    	    if( split_str[1].indexOf('-') >= 0  ){
			            String position[] = split_str[1].split("-");
			            if( position != null && position.length >= 1 ){
			                ranges[0] = Long.parseLong( position[0] );
			                ranges[1] = ranges[0];
				            if( position.length == 2 )
				                ranges[1] = Long.parseLong( position[1] );
				            else if( position.length == 1 )
				                ranges[1] = -1;
				            
				            return ranges;
			            }
		    	    }else{
		    	        ranges[0] = Long.parseLong( split_str[1] );
		    	        ranges[1] = -1;
		    		
		    	        ranges[0] *= -1;
		    	        
		    	        return ranges;
		    	    }
		        }
		    }
		}
    	
		return null;
    }

}
