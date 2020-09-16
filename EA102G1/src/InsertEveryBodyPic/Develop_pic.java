package InsertEveryBodyPic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Develop_pic {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "EA102G1";
	private static final String PASSWORD = "123456";
	private static final String SQL = "INSERT INTO DRESS_CASE_PIC(drpic_id, drcase_id, drpic)"+
	"VALUES('WDP'|| LPAD(TO_CHAR(dressCasePic_seq.NEXTVAL),3,'0'),?,?)";
	
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL);
			
			for (int i  = 1 ; i <= 3; i++) {
				pstmt.setString(1,"WDC001");
				byte[] pic = getPictureByteArray("WebContent/img/img_for_drcase/WDC001_"+i+".jpg");
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
			
			for (int i = 1; i <=4; i++) {
				pstmt.setString(1,"WDC002");
				byte[] pic = getPictureByteArray("WebContent/img/img_for_drcase/WDC002_"+i+".png");
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
			for (int i = 1; i<=5; i++) {
				pstmt.setString(1,"WDC003");
				byte[] pic = getPictureByteArray("WebContent/img/img_for_drcase/WDC003_"+i+".jpg");
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
			for (int i = 1; i<=5; i++) {
				pstmt.setString(1,"WDC004");
				byte[] pic = getPictureByteArray("WebContent/img/img_for_drcase/WDC004_"+i+".jpg");
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
			for (int i = 1; i<=3; i++) {
				pstmt.setString(1,"WDC005");
				byte[] pic = getPictureByteArray("WebContent/img/img_for_drcase/WDC005_"+i+".jpg");
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
			for (int i = 1; i<=3; i++) {
				pstmt.setString(1,"WDC006");
				byte[] pic = getPictureByteArray("WebContent/img/img_for_drcase/WDC006_"+i+".jpg");
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
			
		} catch (SQLException se) {
			System.out.println(se);
		} catch (IOException ie) {
			System.out.println(ie);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}
		}
	}
	
	// 使用byte[]方式
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