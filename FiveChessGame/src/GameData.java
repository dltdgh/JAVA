import java.io.Serializable;


public class GameData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isPos;
	public void setPos(boolean isPos) {
		this.isPos = isPos;
	}

	public boolean isPos() {
		return isPos;
	}
	private Integer pos;
	private Integer id;
	private String localAddress = null;
	private Integer ans = 0;
	public Integer getAns() {
		return ans;
	}

	public void setAns(Integer ans) {
		this.ans = ans;
	}
	private boolean checked = false;
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	GameData(int pos, int id, boolean isPos){
		this.pos = pos;
		this.id = id;
		this.isPos = isPos;
	}
	
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLocalAddress() {
		return localAddress;
	}
	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}
	
}
