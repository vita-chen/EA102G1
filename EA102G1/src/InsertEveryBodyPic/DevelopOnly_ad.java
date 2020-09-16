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

public class DevelopOnly_ad {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "EA102G1";
	private static final String PASSWORD = "123456";
	private static final String ADSQL="update ad set ad_pic = ? where ad_id = ?";
	
	//INSERT INTO AD(AD_ID, AD_DETAIL, AD_START_TIME, AD_END_TIME) 
	//VALUES ('AD'|| LPAD(SEQ_AD_ID.NEXTVAL,3 ,'0'),'wedding_car',sysdate,sysdate)
	
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(ADSQL);
			for (int i = 1; i < 7; i++) {
				byte[] pic = getPictureByteArray("WebContent/img/ad_img/ad_"+i+".jpg");
				pstmt.setBytes(1,pic);
				pstmt.setString(2, "AD00"+i);
				pstmt.executeUpdate();
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