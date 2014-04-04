package kn.multinote.ui.object;

import kn.multinote.dto.NoteDto;

public abstract class NoteBase {
	protected byte type;
	protected String name;
	protected String params;
	protected String value;

	public NoteBase(byte type, String name, String params, String value) {
		this.type = type;
		this.name = name;
		this.params = params;
		this.value = value;
	}

	public NoteBase(NoteDto note) {
		this.type = note.getTypeNote();
		this.name = note.getNameNote();
		this.params = note.getParams();
		this.value = note.getValue();
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
