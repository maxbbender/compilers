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
		if (distance.length() == 1) {
			return "0" + distance;
		} else {
			return distance;
		}
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}
}
