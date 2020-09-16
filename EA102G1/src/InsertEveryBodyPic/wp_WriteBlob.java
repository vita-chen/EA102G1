package InsertEveryBodyPic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class wp_WriteBlob {
	
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "ea102g1";
	private static final String PASSWORD = "123456";
	private static final String SQL = "INSERT INTO wed_photo_img(wed_photo_imgno, wed_photo_case_no, wed_photo_img)"
			+ "VALUES('WPP' || lpad(WPP_SEQ.NEXTVAL, 3, '0'), ?, ?)";
	private static final String update_venderImg = "UPDATE VENDER SET VEN_EVIDENCE_PIC=? WHERE VENDER_ID = ?";
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL);
			
			
			for(int i = 101; i < 104; i++) {
				pstmt.setString(1, "WPC001");
				String url = "WebContent/img/wp_img/v0"+i+".jpg";
				byte[] pic = getPictureByteArray(url);
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
			}
			for(int i = 104; i < 107; i++) {
				pstmt.setString(1, "WPC002");
				String url = "WebContent/img/wp_img/v0"+i+".jpg";
				byte[] pic = getPictureByteArray(url);
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
			}
			for(int i = 107; i < 110; i++) {
				pstmt.setString(1, "WPC003");
				String url = "WebContent/img/wp_img/v0"+i+".jpg";
				byte[] pic = getPictureByteArray(url);
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
			}
			for(int i = 102; i < 105; i++) {
				pstmt.setString(1, "WPC004");
				String url = "WebContent/img/wp_img/v0"+i+".jpg";
				byte[] pic = getPictureByteArray(url);
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
			}
			for(int i = 201; i < 204; i++) {
				pstmt.setString(1, "WPC005");
				String url = "WebContent/img/wp_img/v0"+i+".jpg";
				byte[] pic = getPictureByteArray(url);
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
			}
			for(int i = 205; i < 209; i++) {
				pstmt.setString(1, "WPC006");
				String url = "WebContent/img/wp_img/v0"+i+".jpg";
				byte[] pic = getPictureByteArray(url);
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
			}
			for(int i = 206; i < 207; i++) {
				pstmt.setString(1, "WPC007");
				String url = "WebContent/img/wp_img/v0"+i+".jpg";
				byte[] pic = getPictureByteArray(url);
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
			}
			for(int i = 206; i < 207; i++) {
				pstmt.setString(1, "WPC008");
				String url = "WebContent/img/wp_img/v0"+i+".jpg";
				byte[] pic = getPictureByteArray(url);
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
			}
//			for(int i = 206; i < 207; i++) {
//				pstmt.setString(1, "WPC009");
//				String url = "WebContent/img/wp_img/v0"+i+".jpg";
//				byte[] pic = getPictureByteArray(url);
//				pstmt.setBytes(2, pic);
//				pstmt.executeUpdate();
//				pstmt.clearParameters();
//				
//			}
//			for(int i = 301; i < 303; i++) {
//				pstmt.setString(1, "WPC010");
//				String url = "WebContent/img/wp_img/v0"+i+".jpg";
//				byte[] pic = getPictureByteArray(url);
//				pstmt.setBytes(2, pic);
//				pstmt.executeUpdate();
//				pstmt.clearParameters();
//				
//			}
			for(int i = 303; i < 305; i++) {
				pstmt.setString(1, "WPC011");
				String url = "WebContent/img/wp_img/v0"+i+".jpg";
				byte[] pic = getPictureByteArray(url);
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
			}
			for(int i = 306; i < 310; i++) {
				pstmt.setString(1, "WPC012");
				String url = "WebContent/img/wp_img/v0"+i+".jpg";
				byte[] pic = getPictureByteArray(url);
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
			}
			for(int i = 307; i < 310; i++) {
				pstmt.setString(1, "WPC013");
				String url = "WebContent/img/wp_img/v0"+i+".jpg";
				byte[] pic = getPictureByteArray(url);
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
			}
			for(int i = 306; i < 307; i++) {
				pstmt.setString(1, "WPC014");
				String url = "WebContent/img/wp_img/v0"+i+".jpg";
				byte[] pic = getPictureByteArray(url);
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
			}
			for(int i = 1;i<4;i++) { //廠商圖片
				pstmt = con.prepareStatement(update_venderImg);
				String url = "WebContent/img/wp_img/ven0"+i+".jpg";
				byte[] pic = getPictureByteArray(url);
				pstmt.setBytes(1,pic);
				pstmt.setString(2,"V01"+i);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
			
			

		} catch (ClassNotFoundException ce) {
			System.out.println(ce);
		} catch (SQLException se) {
			System.out.println(se);
		} 
		catch (IOException ie) {
			System.out.println(ie);
		} 
		finally {
			// 依建立順序關閉資源 (越晚建立越早關閉)
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
