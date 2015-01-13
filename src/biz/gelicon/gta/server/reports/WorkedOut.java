package biz.gelicon.gta.server.reports;

public class WorkedOut {
	private String member;
	private String post;
	private Integer postId;
	private Double hours;
	private Double activityScore;
	private Double activityPercent;

	public WorkedOut() {
	}
	
	public WorkedOut(String member, String post, Integer postId, Double hours,
			Double activityBal, Double activityPercent) {
		super();
		this.member = member;
		this.post = post;
		this.postId = postId;
		this.hours = hours;
		this.activityScore = activityBal;
		this.activityPercent = activityPercent;
	}
	
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public Double getHours() {
		return hours;
	}
	public void setHours(Double hours) {
		this.hours = hours;
	}
	public Double getActivityScore() {
		return activityScore;
	}
	public void setActivityScore(Double activityBal) {
		this.activityScore = activityBal;
	}
	public Double getActivityPercent() {
		return activityPercent;
	}
	public void setActivityPercent(Double activityPercent) {
		this.activityPercent = activityPercent;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
}
