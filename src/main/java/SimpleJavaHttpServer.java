import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleJavaHttpServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress("localhost", 8000));
            System.out.println("listening HTTP request on localhost port 8000 ...");
            System.out.println("waiting for client request");

            while (true) {
                try (Socket socket = serverSocket.accept()) {

                    ReadRequest readRequest = new ReadRequest(socket);
                    System.out.println("index?");
                    System.out.println(readRequest.notExistsIndexHTML());
                    if (readRequest.notExistsIndexHTML()) {
                        //Return File List in Dir
                        try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
                            ResponseHeadBuilder responseHeadBuilder = new ResponseHeadBuilder();
                            responseHeadBuilder.setStatusCode(200);
                            String fileList = readRequest.getFileList();
                            responseHeadBuilder.setMessageBodyLength(fileList.length());
                            writer.println(responseHeadBuilder.createLawResponseHeader());
                            writer.println(fileList);
                        }

                    } else {
                        final File responseFile = readRequest.getResponseFile();
                        createDefaultResponse(socket, responseFile);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDefaultResponse(Socket socket, File returnHtmlFile) {
        if (returnHtmlFile != null) {
            ResponseFile responseFile = null;
            try {
                responseFile = new ResponseFile(returnHtmlFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            long fileSize = responseFile.getFileSize();
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // TODO yuki.komatsu 混在しているので直さねば… (2019-06-21)
            try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream())) {

                try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
//                    OutputStream outputStream = socket.getOutputStream();
                    ResponseHeadBuilder responseHeadBuilder = new ResponseHeadBuilder();
                    responseHeadBuilder.setStatusCode(200);
                    responseHeadBuilder.setContentTypeInfo(responseFile.getResponseFileType());
                    responseHeadBuilder.setMessageBodyLength(fileSize);
                    writer.println(responseHeadBuilder.createLawResponseHeader());
//                    byteArrayOutputStream.write(responseFile.getResponseFileByte());
                    bufferedOutputStream.write(responseFile.getResponseFileByte());
//                    outputStream.write(responseFile.getResponseFile().getBytes());
//                    bufferedOutputStream.write(responseFile.getResponseFile().getBytes());
//                    writer.println(responseFile.getResponseFile());
                    bufferedOutputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream())) {
                try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
                    File error404 = new File("public/404.html");
                    ResponseFile responseFile = new ResponseFile(error404);
                    long fileSize = responseFile.getFileSize();
                    ResponseHeadBuilder responseHeadBuilder = new ResponseHeadBuilder();
                    responseHeadBuilder.setStatusCode(404);
                    responseHeadBuilder.setContentTypeInfo("text/html");
                    responseHeadBuilder.setMessageBodyLength(fileSize);
                    writer.println(responseHeadBuilder.createLawResponseHeader());
//                    writer.println(responseFile.getResponseFile());
                    bufferedOutputStream.write(responseFile.getResponseFileByte());
                    bufferedOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
