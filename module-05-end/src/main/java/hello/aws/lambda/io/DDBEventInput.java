package hello.aws.lambda.io;

import java.util.List;

public class DDBEventInput {

	private String id;
  private String prefix;
  private String photoInfo;
  private String transInfo;
  
  public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getPhotoInfo() {
		return photoInfo;
	}
	public void setPhotoInfo(String photoInfo) {
		this.photoInfo = photoInfo;
	}
	public String getTransInfo() {
		return transInfo;
	}
	public void setTransInfo(String transInfo) {
		this.transInfo = transInfo;
	}
  
}
