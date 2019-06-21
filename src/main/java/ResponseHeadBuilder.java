import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class ResponseHeadBuilder {
    private static final String LINE_BREAK = "\r\n"; //CR:\r , LF:\n
    private int statusCode;
    private String statusMessage;
    private String date;
    private String contentLength;
    private String contentType;
    private String contentTypeInfo;
    private long messageBodyLength;

    public void setMessageBodyLength(long messageBodyLength) {
        this.messageBodyLength = messageBodyLength;
    }

    void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    void setContentTypeInfo(String contentTypeInfo) {
        this.contentTypeInfo = contentTypeInfo;
    }

    String createLawResponseHeader() {
        StringBuilder serverResponse = new StringBuilder();

        switch (statusCode) {
            case 200:
                statusMessage = "HTTP/1.1 200 OK" + LINE_BREAK;
                break;
            case 404:
                statusMessage = "HTTP/1.1 404 file notfound " + LINE_BREAK;
                break;
        }

        date = "Date: " + LocalDateTime.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.RFC_1123_DATE_TIME) + LINE_BREAK;
        contentLength = "Content-Length: " + messageBodyLength + LINE_BREAK;

        contentType = "Content-Type: " + contentTypeInfo + ";charset=utf-8" + LINE_BREAK;

        serverResponse.append(statusMessage);
        serverResponse.append(date);
        serverResponse.append(contentLength);
        serverResponse.append(contentType);

        return serverResponse.toString();

    }

}
