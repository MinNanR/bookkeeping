package site.minnan.bookkeeping.userinterface.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseEntity {

    private String code;

    private String message;

    private Object data;

    public ResponseEntity(ResponseCode responseCode){
        this.code = responseCode.code();
        this.message = responseCode.message();
    }

    public static ResponseEntity success(){
        return new ResponseEntity(ResponseCode.SUCCESS);
    }

    public static ResponseEntity success(Object data){
        ResponseEntity responseEntity = new ResponseEntity(ResponseCode.SUCCESS);
        responseEntity.setData(data);
        return responseEntity;
    }

    public static ResponseEntity fail(ResponseCode responseCode){
        return new ResponseEntity(responseCode);
    }

    public static ResponseEntity fail(ResponseCode responseCode, Object data){
        ResponseEntity responseEntity = new ResponseEntity(responseCode);
        responseEntity.setData(data);
        return responseEntity;
    }
}
