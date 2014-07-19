package user.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import com.bean.Operation;
import com.hbase.HBaseOperation;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class EnterPersonInfoAction implements Action{
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> session = ctx.getSession();
		String userEmail= (String)session.get("userEmail");
		
		List<Operation> array = new ArrayList<Operation>();
		
		HBaseOperation hbase = new HBaseOperation();
		ResultScanner rs = hbase.getRecordBySubRowkey("log", userEmail);
		
		String rowKey;
		String arr[];
		for(Result r : rs){
			Operation op = new Operation();
			for(Cell c : r.rawCells()){
				rowKey = (Bytes.toString(c.getRowArray(), 
						c.getRowOffset(), c.getRowLength()) +"\t");
				arr = rowKey.split("_");
				op.setOpTime(arr[1]);
			}
			op.setOpType(Bytes.toString(r.getValue(Bytes.toBytes("content"), Bytes.toBytes("opType"))));
			op.setOpObject(Bytes.toString(r.getValue(Bytes.toBytes("content"), Bytes.toBytes("opObject"))));
			array.add(op);
		}
		
		for(Operation oper:array)
		{
			System.out.println(oper.getOpTime());
			System.out.println(oper.getOpType());
			System.out.println(oper.getOpObject());
		}
		
		ctx.put("operation", array);
		return "success";
	}	
}
