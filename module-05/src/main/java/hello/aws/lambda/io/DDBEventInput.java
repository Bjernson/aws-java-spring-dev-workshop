package hello.aws.lambda.io;

import java.util.List;

public class DDBEventInput {

	private String id;
	private String bucket;
	private String prefix;
	private String text;
	private String translated;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTranslated() {
		return translated;
	}
	public void setTranslated(String translated) {
		this.translated = translated;
	}
  
}
