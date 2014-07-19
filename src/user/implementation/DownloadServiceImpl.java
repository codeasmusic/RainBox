package user.implementation;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import com.bean.File;
import com.bean.User;
import com.hbase.HBaseOperation;

import user.service.DownloadService;

public class DownloadServiceImpl implements DownloadService{
	private HBaseOperation hbaseOp = new HBaseOperation();
	
	public DownloadServiceImpl(){
		System.out.println("linking");
	}

	public File getFileInfo(String userEmail, String md5) {
		ResultScanner rs = hbaseOp.getRecordBySubRowkey("file", userEmail + "_" + md5);
		String rowKey = null;
		String[] arr;
		File f = new File();
		f.setEmail(userEmail);
		f.setMd5(md5);
		
		for(Result r : rs){
			for(Cell c : r.rawCells()){
				rowKey = Bytes.toString(c.getRowArray(), c.getRowOffset(), c.getRowLength());
				arr = rowKey.split("_");
				f.setMd5(arr[1]);
				f.setUploadTime(arr[2]);
			}
			f.setFileName(Bytes.toString(r.getValue(Bytes.toBytes("info"), Bytes.toBytes("fileName"))));
			f.setSize(Bytes.toString(r.getValue(Bytes.toBytes("info"), Bytes.toBytes("size"))));
		}
		return f;
	}

	public User getUserInfo(String userEmail) {
		Result rs = hbaseOp.getRecord("user", userEmail);
		String nickName = Bytes.toString(rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("nickName")));
		String headName = Bytes.toString(rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("headName")));
		String level = Bytes.toString(rs.getValue(Bytes.toBytes("pan"), Bytes.toBytes("level")));
		
		User user = new User();
		user.setNickName(nickName);
		user.setHeadName(headName);
		user.setLevel(level);
		return user;
	}

}
