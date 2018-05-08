package utils;

public class restaurant {
	public String name;
	public String phone;
	
	public restaurant(String resName, String resPhone){
		this.name = resName;
		this.phone = resPhone;
	}
	
	public void setName(String resName){
		this.name = resName;
	}
	
	public void setAddress(String restPhone){
		this.phone = restPhone;
	}
	
	public String getName(){
		return name;
	}
	
	public String getAddress(){
		return phone;
	}

}
