import java.io.*;

class ResponseFile {
    private String responseFile;
    private File readFile;

    String getResponseFileType() {
        if (readFile.getName().endsWith(".html")) {
            return "text/html";
        }else if (readFile.getName().endsWith(".css")){
            return "text/css";
        }else if (readFile.getName().endsWith(".js")) {
            return "text/javascript";
        }else if (readFile.getName().endsWith(".jpg")){
            // TODO yuki.komatsu これだけではできないので後ほど対応を考える。 (2019-06-20)
            return "image/jpg";
        }else {
            return "";
        }
    }

    ResponseFile(File readFile) throws IOException {
        this.readFile = readFile;
        responseFile = this.createBodyHTML();
    }

    String getResponseFile() {
        return responseFile;
    }

    long getFileSize() {
        return readFile.length();
    }
    private String createBodyHTML() throws IOException {
        StringBuilder outputFile = new StringBuilder();

        FileInputStream fileInputStream = new FileInputStream(readFile);
//        BufferedReader readBody = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));

        byte[] bytes = fileInputStream.readAllBytes();

//        int ch;
//        // TODO yuki.komatsu readyはだめらしい… (2019-06-21)
//        while (readBody.ready()){
//                ch = readBody.read();
//                outputFile.append(ch);
//        }

//        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
//
//        while (inputStreamReader.ready()){
//            ch = inputStreamReader.read();
//            outputFile.append(ch);
//        }

//        while (readBody.ready()){
//            outputFile.append(readBody.readLine());
//            outputFile.append("\n");
//        }
        return outputFile.toString();
    }

    public byte[] getResponseFileByte() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(readFile);
       return fileInputStream.readAllBytes();

    }
}
