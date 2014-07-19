package user.implementation;


import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import com.hbase.HBaseOperation;

import user.service.FolderService;

public class FolderServiceImpl implements FolderService{
	private static HBaseOperation hbaseOp = new HBaseOperation();
	private static String tableName = "folder";
	private static String family = "info";
	private static long count = 0;

	public String createNewFolder(String folderName, String userEmail, 
			String upperFolderId, String setTime) {
		String folderId;
		synchronized (this) {
			folderId = setTime + count;
			count++;
			if(count > 10000)
				count = 0;
		}
		
		String rowKey = userEmail + "_" + folderId + "_" + upperFolderId;
		try{
			hbaseOp.addRecord(tableName, rowKey, family, "setTime", setTime);
			hbaseOp.addRecord(tableName, rowKey, family, "folderName", folderName);
		}catch(Exception e){
			e.printStackTrace();
		}
		JSONObject jsonObj = new JSONObject();
		jsonObj.element("newFolderId", folderId);
		return jsonObj.toString();
	}

	public boolean deleteFolder(String userEmail, String folderId, String upperFolderId) {
		List<String> rowKey = new ArrayList<String>(); 
		rowKey.add(userEmail + "_" + folderId + "_" + upperFolderId);
		try{
			hbaseOp.delRecord(tableName, rowKey);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/*修改文件夹名*/
	public boolean ModifyFolderName(String useremail, String folderID, String NewFolderName) {
		String localRowKey = null;
		ResultScanner rs;
		rs = hbaseOp.getRecordBySubRowkey("folder", useremail + "_" + folderID);
		for(Result r : rs){
			for(Cell c : r.rawCells()){
				localRowKey = Bytes.toString(c.getRowArray(), c.getRowOffset(), c.getRowLength());
			}
		}
		boolean success = hbaseOp.addRecord("folder", localRowKey, "info", "folderName", NewFolderName);
		return success;
	}
}
