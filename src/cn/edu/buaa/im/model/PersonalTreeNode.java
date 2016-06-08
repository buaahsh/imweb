package cn.edu.buaa.im.model;

public class PersonalTreeNode extends ExtTreeNode {
	
	public boolean checked;
	
	public PersonalTreeNode(String id, String text) {
		super(id, text);
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
