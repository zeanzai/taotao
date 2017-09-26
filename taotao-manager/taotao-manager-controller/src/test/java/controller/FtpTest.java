package controller;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.common.utils.FtpUtil;

public class FtpTest {

    /***
     * 此处需要修改windows的防火墙状态，将开启状态修改为关闭状态
     * <p>Title: testFtpClient</p>
     * <p>Description: </p>
     * @throws Exception
     */
    @Test
    public void testFtpClient() throws Exception {
        /***
         * 1. 创建一个ftpClient
         * 2. 创建一个连接
         * 3. 使用用户名和密码登录到ftp
         * 4. 上传文件
         * 5. 关闭连接*/

        /*//创建一个FtpClient对象
        FTPClient ftpClient = new FTPClient();
        //创建ftp连接。默认是21端口
        ftpClient.connect("192.168.1.101", 21);
        //登录ftp服务器，使用用户名和密码
        ftpClient.login("ftpuser", "ftpuser");
        //上传文件。
        //读取本地文件
        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\ShawnWang\\Desktop\\111.jpg"));
        //设置上传的路径
        ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
        //修改上传文件的格式
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        //第一个参数：服务器端文档名
        //第二个参数：上传文档的inputStream
        ftpClient.storeFile("hello1.jpg", inputStream);
        //关闭连接
        ftpClient.logout();*/


        FTPClient ftpClient = new FTPClient();
        ftpClient.connect("192.168.100.202", 21);
        ftpClient.login("ftpuser", "ftpuser");

        FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\img\\111.jpg"));

        // 在服务器上面建立文件夹，然后进行上传
        ftpClient.makeDirectory("/home/ftpuser/www/images2/");

        // 更换上传目录
        ftpClient.changeWorkingDirectory("/home/ftpuser/www/images2/");

        //
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.storeFile("hello1.jpg", fileInputStream);
        ftpClient.logout();
    }

	@Test
    public void testFtpUtil() throws Exception{
        // 测试时，注意关闭Windows的防火墙
		FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\img\\111.jpg"));
		
		// 此方法不太好用，没有办法创建新的目录
		FtpUtil.uploadFile("192.168.100.202", 21, "ftpuser", "ftpuser", "/home/ftpuser/www/images/", "2017/09/13", "1.jpg", fileInputStream);
	}

}
