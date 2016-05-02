package codegeneration;

public class JumpEntry {
	private String jumpId;
	private String distance;
	
	public JumpEntry() {
		jumpId = null;
		distance = null;
	}
	
	public JumpEntry(String newJumpId, String newDistance) {
		jumpId = newJumpId;
		distance = newDistance;
	}

	public String getJumpId() {
		return jumpId;
	}

	public void setJumpId(String jumpId) {
		this.jumpId = jumpId;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}
}
