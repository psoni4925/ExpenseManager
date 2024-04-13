package in.pranjal.expensetrackerapi.exception;

import in.pranjal.expensetrackerapi.entity.ErrorObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObject> handleGeneralException(Exception exception,  WebRequest request){
        ErrorObject errorObject  = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObject.setTimeStamp(new Date());
        errorObject.setMessage(exception.getMessage());
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorObject> handleExpenseNotFoundException(ResourceNotFound expenseNotFound , WebRequest request){

        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setTimeStamp(new Date());
        errorObject.setMessage(expenseNotFound.getMessage());

        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ItemAlreadyExistException.class)
    public ResponseEntity<ErrorObject> handleItemExistException(ItemAlreadyExistException ex, WebRequest request){
        ErrorObject object = new ErrorObject();
        object.setStatusCode(HttpStatus.CONFLICT.value());
        object.setMessage(ex.getMessage());
        object.setTimeStamp(new Date());
        return new ResponseEntity<ErrorObject>(object, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorObject> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setTimeStamp(new Date());
        errorObject.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.BAD_REQUEST);
    }

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//
//        Map<String, Object> body = new HashMap<>();
//
//        body.put("timestamp",new Date());
//        body.put("statuscode", HttpStatus.BAD_REQUEST.value());
//        List<String> errors = ex.getBindingResult()
//                        .getFieldErrors()
//                                .stream()
//                                        .map(x -> {
//                                            return x.getDefaultMessage();
//                                        })
//                                                .collect(Collectors.toList());
//
//        body.put("ErrorMessage",errors);
//
//        return new ResponseEntity<Object>(body,HttpStatus.BAD_REQUEST);
//
//    }



}
