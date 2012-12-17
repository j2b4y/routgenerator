
public class advantagecalculator {
	
	public float getEmissionAdv(int userID, int requestID, float emission){
		
		float advantage = 0;
		
		String query = "SELECT MAX(Emission) FROM dbo.temprouts where UserID="+userID+" AND RequestID="+requestID;
		String query2 = "SELECT MIN(Emission) FROM dbo.temprouts where UserID="+userID+" AND RequestID="+requestID;
		
		return advantage;
	}
	
//	public float getComfAdv(int userID, int requestID, float comfort){
//		
//		float advantage = 0;
//		
//		String query = "SELECT MAX(Emission) FROM dbo.temprouts where UserID="+userID+" AND RequestID="+requestID;
//		
//		return advantage;
//	}

	public float getCostAdv(int userID, int requestID, float cost){
		
		float advantage = 0;
		
		String query = "SELECT MAX(Cost) FROM dbo.temprouts where UserID="+userID+" AND RequestID="+requestID;
		String query2 = "SELECT MIN(Cost) FROM dbo.temprouts where UserID="+userID+" AND RequestID="+requestID;
		
		return advantage;
	}
	
//	public float getTimeAdv(int userID, int requestID, float comfort){
//		
//		float advantage = 0;
//		
//		String query = "SELECT MAX(Emission) FROM dbo.temprouts where UserID="+userID+" AND RequestID="+requestID;
//		
//		return advantage;
//	}

}
