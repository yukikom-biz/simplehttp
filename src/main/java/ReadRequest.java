import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class ReadRequest {
    private Socket socket;
    private String filePath;
    private String fileDirectory;
    private static final String ROOT_FILE_PATH = "public/";

    ReadRequest(Socket socket) {
        this.socket = socket;
        this.setFilePath();
    }

    private void setFilePath() {
        String leadRequest;
        try {
            InputStream inputStreamRequest = socket.getInputStream();
            BufferedReader bufferedReaderRequest = new BufferedReader(new InputStreamReader(inputStreamRequest));
            while (true) {
                leadRequest = bufferedReaderRequest.readLine();
                if (leadRequest.isBlank())
                    break;

//                System.out.println(leadRequest);
                if (leadRequest.startsWith("GET")) {
                    filePath = leadRequest.split(" ")[1].replaceFirst("/", "");
                    if (filePath.endsWith("/")){
                        filePath = filePath.substring(0, filePath.length() - 1);
                    }
                }
            }
//            System.out.println("filepath? " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    boolean notExistsIndexHTML() {
        // リクエストされたパスがディレクトリの場合index.htmlが存在するかどうかをチェックする。 ex: localhost:8000/dir
        File findDir = new File(ROOT_FILE_PATH + filePath);
        if (findDir.isDirectory()) {
            File findIndex = new File(ROOT_FILE_PATH + filePath + "/index.html");
            return !findIndex.exists();
        }
        return false;
    }

    String getFileList() {
        File targetDir = new File(ROOT_FILE_PATH + filePath);
        String[] fileList = targetDir.list();
        List<String> collect = Arrays.stream(fileList)
                .filter(s -> {
                    if (s.charAt(0) == '.') {
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());

        if (collect.size() == 0) {
            return "empty";
        }

        StringBuilder output = new StringBuilder();
        for (String fileName : collect) {
            output.append("<li>" + fileName + "</li>");
        }
        return output.toString();
    }

    File getResponseFile() {

        File responseFile = new File(ROOT_FILE_PATH + filePath);
        File findIndex = new File(ROOT_FILE_PATH + filePath + "/index.html");
//        System.out.println(filePath);
//        System.out.println(responseFile.getAbsolutePath());
//        System.out.println(responseFile.isFile());
//        System.out.println(this.isAllowedDir(responseFile));
        if (responseFile.isFile() && this.isAllowedDir(responseFile)) {
            return responseFile;
        } else if (responseFile.isDirectory()) {
            if (findIndex.exists()) {
                return findIndex;
            }
            //fileDirectoryはpublic/以下のディレクトリレベルのパス
            // "public/dir/subdir/sample.html"のような場合は fileDirectoryが１回目'dir/'、２回目'dir/subdir/'
            if (fileDirectory != null) {
                fileDirectory += filePath.split("/")[0] + "/";
            } else {
                fileDirectory = filePath.split("/")[0] + "/";
            }
            filePath = filePath.replace(fileDirectory, "");
            return this.getResponseFile();
        }
        return null;
    }

    boolean isAllowedDir(File checkFile){
        return checkFile.getAbsolutePath().contains(ROOT_FILE_PATH);
    }
}
