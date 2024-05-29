package kr.board.entity;

import lombok.Data;

@Data
public class LhubCrs {
	  private String REGISTRYNO;
	//private int REGISTRYNO; 
	  private String INSTITUTE_CODE;  
	  private String INSTITUTE_NAME;
//	  private String memName;
//	  private int memAge; // <-null, 0
//	  private String memGender;
//	  private String memEmail;
//	  private String memProfile; //사진정보
	  
	    @Override
	    public String toString() {
	        return "LhubCrs{" +
	                "REGISTRYNO='" + REGISTRYNO + '\'' +
	                ", INSTITUTE_CODE='" + INSTITUTE_CODE + '\'' +
	                ", INSTITUTE_NAME='" + INSTITUTE_NAME + '\'' +
	                '}';
	    }
}
