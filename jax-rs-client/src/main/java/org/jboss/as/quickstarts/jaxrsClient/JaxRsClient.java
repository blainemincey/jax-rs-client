/**
 * 
 */
package org.jboss.as.quickstarts.jaxrsClient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import javax.ws.rs.core.MediaType;

/**
 * The purpose of this class is to make an external REST call
 * to the helloworld-rs RESTful application.
 *
 * @author bmincey
 * 
 */
public class JaxRsClient
{

    private static final String XML_URL = "http://localhost:8080/jboss-as-helloworld-rs/xml";
    private static final String JSON_URL = "http://localhost:8080/jboss-as-helloworld-rs/json";

    /**
     *  Constructor initializing calls to runRequest method.
     */
    public JaxRsClient()
    {
        this.runRequest(JaxRsClient.XML_URL, MediaType.APPLICATION_XML_TYPE);
        this.runRequest(JaxRsClient.JSON_URL, MediaType.APPLICATION_JSON_TYPE);
    }

    /**
     * The purpose of this method is to run the external REST request.
     * @param url The url of the RESTful service
     * @param mediaType The mediatype of the RESTful service
     */
    private void runRequest(String url, MediaType mediaType)
    {
        System.out.println("===============================================");
        System.out.println("URL: " + url);
        System.out.println("MediaType: " + mediaType.toString());
        
        try
        {
            // Using the RESTEasy libraries, initiate a client request 
            // using the url as a parameter
            ClientRequest request = new ClientRequest(url);

            // Be sure to set the mediatype of the request
            request.accept(mediaType);

            // Request has been made, now let's get the response
            ClientResponse<String> response = request.get(String.class);

            // Check the HTTP status of the request
            // HTTP 200 indicates the request is OK
            if (response.getStatus() != 200)
            {
                throw new RuntimeException("Failed request with HTTP status: "
                        + response.getStatus());
            }

            // We have a good response, let's now read it
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new ByteArrayInputStream(response.getEntity().getBytes())));

            // Loop over the br in order to print out the contents
            String output;
            System.out.println("\n*** Response from Server ***\n");
            while ((output = br.readLine()) != null)
            {
                System.out.println(output);
            }
        }

        catch (ClientProtocolException cpe)
        {
            System.err.println(cpe);
        }
        catch (IOException ioe)
        {
            System.err.println(ioe);
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
        
        System.out.println("\n===============================================");
    }

    /**
     * This is the main method of the class which kicks off the processing
     *
     * @param args
     */
    public static void main(String[] args)
    {
        new JaxRsClient();
    }
}
