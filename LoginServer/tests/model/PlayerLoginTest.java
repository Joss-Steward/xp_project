package model;

public class PlayerLoginTest
{
	public enum Players{
		JOHN("John", "pw"), MERLIN("Merlin","mypwd");
		
		private String name;
		private String password;

		Players(String n, String pwd)
		{
			this.name = n;
			this.password = pwd;
		}
		
		public String getName()
		{
			return name;
		}
		
		public String getPassword()
		{
			return password;
		}
	}
}
