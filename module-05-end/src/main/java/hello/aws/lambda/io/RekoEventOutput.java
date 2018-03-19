package hello.aws.lambda.io;

public class RekoEventOutput {
	private String result;
	private String message;

  public RekoEventOutput() {
  }
  
  public RekoEventOutput(String message) {
      setMessage(message);
  }
  
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}