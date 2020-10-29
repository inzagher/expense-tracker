package inzagher.expense.tracker.server.dto;

public class OperationResultDTO {
    private Boolean success;
    private String error;

    public OperationResultDTO(Boolean success, String error) {
        this.success = success;
        this.error = error;
    }
    
    public static OperationResultDTO succeeded() {
        return new OperationResultDTO(true, null);
    }
    
    public static OperationResultDTO failed(String error) {
        return new OperationResultDTO(false, error);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
