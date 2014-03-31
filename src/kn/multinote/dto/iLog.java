package kn.multinote.dto;



public class iLog {
	

	private int id;
	

	private String content;
	
	private int type;
	
	public iLog()
	{
		
	}
	
	public iLog(int id, String name, int type)
	{
		this.id = id;
		this.content = name;
		this.type = type;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}
	
	public int getType()
	{
		return type;
	}
	
	public void setType(int type)
	{
		this.type = type;
	}
}
