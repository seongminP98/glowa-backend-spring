package glowa.glowabackendspring.payload;

public class ResponseCode {
    private ResponseCode() {
    }
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int OK = 200;
    public static final int AUTHENTICATION_ERROR = 401;
    public static final int CLIENT_ERROR = 400;
    public static final int CONFLICT_ERROR = 409;
}
