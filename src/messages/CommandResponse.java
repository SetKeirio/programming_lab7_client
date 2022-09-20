package messages;

import java.io.Serializable;

public class CommandResponse implements Serializable {
    private static final long serialVersionUID = 2L;
    private String responseBody;
    private ResponseCodeEnum responceCode;

    public CommandResponse(String responseBody, ResponseCodeEnum responceCode) {
        this.responseBody = responseBody;
        this.responceCode = responceCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public ResponseCodeEnum getResponceCode() {
        return responceCode;
    }


    @Override
    public String toString() {
        return "Response (command)[Код ответа = " + responceCode + ", тело ответа = " + responseBody + "]";
    }
}
