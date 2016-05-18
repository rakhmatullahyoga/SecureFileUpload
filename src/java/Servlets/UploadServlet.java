/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Signature.ECDSA;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Rakhmatullah Yoga S
 */
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final String UPLOAD_DIRECTORY = "./upload";
    private File uploaded;
    private String pubKey,sigval;
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if(uploaded == null) {
                out.println("<font color='red'>No such file have been uploaded!</font>");
                return;
            }
            response.setContentType("text/html;charset=UTF-8");
            byte[] data = new byte[(int)uploaded.length()];
            FileInputStream in = new FileInputStream(uploaded);
            in.read(data);
            boolean verified = ECDSA.isValidSignature(DatatypeConverter.parseHexBinary(pubKey), data, DatatypeConverter.parseHexBinary(sigval));
            if(verified) {
                out.println("<font color='blue'>Verification succeeded! Your file is safe.</font>");
            }
            else {
                out.println("<font color='red'>Verification failed! Your file might be attacked.</font>");
            }
            uploaded = null;
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException | InvalidKeySpecException ex) {
            out.println("<font color='red'>Verification failed! Your file might be attacked.</font>");
            Logger.getLogger(UploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

	// process only if its multipart content
	if (isMultipart) {
            // Create a factory for disk-based file items
            FileItemFactory factory = new DiskFileItemFactory();
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {   // Parse the request
                List<FileItem> multiparts = upload.parseRequest(request);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        String name = new File(item.getName()).getName();
                        uploaded = new File(getServletContext().getRealPath(UPLOAD_DIRECTORY) + File.separator + name);
                        item.write(uploaded);
                    }
                    else {
                        if (item.getFieldName().equals("pubkey2")) {
                            pubKey = item.getString();
                        }
                        if (item.getFieldName().equals("sigval2")) {
                            sigval = item.getString();
                        }
                    }
                }
            } 
            catch (Exception e) {
                System.out.println("File upload failed");
            }
	}
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for uploading file";
    }// </editor-fold>

}
