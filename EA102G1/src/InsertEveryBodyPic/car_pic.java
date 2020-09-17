package InsertEveryBodyPic;

import java.util.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class car_pic {
	
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "ea102g1";
	private static final String PASSWORD = "123456";
	
	private static final String INSERT_CARPIC_STMT = "INSERT INTO CAR_PIC(CPIC_ID, CID, C_PIC)VALUES (?, ?, ?)";

	private static final String update_venderImg = "UPDATE VENDER SET VEN_EVIDENCE_PIC=? WHERE VENDER_ID = ?";

	
	public static void main(String[] args) throws IOException, ClassNotFoundException {		

		
			Connection con = null;
			PreparedStatement pstmt = null;

			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection(URL, USER, PASSWORD);
				pstmt = con.prepareStatement(INSERT_CARPIC_STMT);
				

				for(int i=1;i<8;i++) {
				pstmt.setString(1, "WCP10"+i);
				pstmt.setString(2, "WCC00"+i);
				String url = "WebContent/img/img_for_carousels/car/"+i+".jpg";
				byte[] pic = getPictureByteArray(url);
				pstmt.setBytes(3, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
				}
				
				pstmt = con.prepareStatement(update_venderImg);
				String url = "WebContent/img/img_for_carousels/car/mik.png";
				byte[] pic = getPictureByteArray(url);
				pstmt.setBytes(1,pic);
				pstmt.setString(2,"V016");
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
				
				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
				// Clean up JDBC resources
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			
		}// end of insert
	}
	
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}
}
