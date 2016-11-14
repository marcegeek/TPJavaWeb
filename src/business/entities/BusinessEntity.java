package business.entities;

public class BusinessEntity {
	public enum States {DELETED, NEW, MODIFIED, UNMODIFIED}
	private int id;
	private States state;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public States getState() {
		return state;
	}

	public void setState(States state) {
		this.state = state;
	}
}
