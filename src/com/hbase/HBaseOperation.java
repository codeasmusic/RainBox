package com.hbase;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseOperation {
	static Configuration cfg = HBaseConfiguration.create();

	public HBaseOperation(){
		cfg.set("hbase.zookeeper.property.clientPort", "2181");
		cfg.set("hbase.zookeeper.quorum", "192.168.1.250");
	}
	
	//*******************************基本操作**************************************
	//建表
	public boolean createTable(String tableName, String[] familys){
		try {
			HBaseAdmin admin = new HBaseAdmin(cfg);
		
			if(admin.tableExists(tableName)){
				System.out.println("table exists.");
			}else{
				HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));
				for(int i = 0; i < familys.length; i++){
					tableDesc.addFamily(new HColumnDescriptor(familys[i]));
				}
				admin.createTable(tableDesc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//添加一条记录
	public boolean addRecord(String tableName, String rowKey,String family, String qualifier, String value){
		try{
			HTable table = new HTable(cfg, tableName);
			Put put = new Put(Bytes.toBytes(rowKey));
			put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
			table.put(put);
			System.out.println("insert " + rowKey + " to table " + tableName);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//获取一条记录
	public Result getRecord(String tableName, String rowKey){
		Result rs = null;
		try{
			HTable table = new HTable(cfg, tableName);
			Get get = new Get(rowKey.getBytes());
			rs = table.get(get);
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	
	//删除N条记录
	public boolean delRecord(String tableName, List<String> rowKey){
		try{
			HTable table = new HTable(cfg, tableName);
			List<Delete> list = new ArrayList<Delete>();
			for(int n = 0; n < rowKey.size(); n++){
				list.add(new Delete(rowKey.get(n).getBytes()));
			}
			table.delete(list);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//获取全部记录
	public ResultScanner getAllRecords(String tableName){
		ResultScanner rs = null;
		try{
			HTable table = new HTable(cfg, tableName);
			Scan s = new Scan();
			rs = table.getScanner(s);
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	
	//*********************************复杂操作**********************************
	//通过rowkey的部分键值查找记录
	 public ResultScanner getRecordBySubRowkey(String tableName, String subRowkey){
		 ResultScanner rs = null;
		 try{
			 HTable table = new HTable(cfg, tableName);
			 Scan s = new Scan();
			 Filter rowkeyFilter = new RowFilter(CompareOp.EQUAL, 
					 new SubstringComparator(subRowkey));
			 s.setFilter(rowkeyFilter);
			 rs = table.getScanner(s);

		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return rs;
	 }
	
	 //根据N个列的部分值查找记录
	 public ResultScanner getRecordByColumn(String tableName, String family, 
			 String[] qualifiers, String[] subValue){
		 ResultScanner rs = null;
		 byte[] f = Bytes.toBytes(family);
		 List<byte[]> qList = new ArrayList<byte[]>();
		 
		 for(int i = 0; i < qualifiers.length; i++){
			 qList.add(Bytes.toBytes(qualifiers[i]));
		 }
		 
		 try{
			 HTable table = new HTable(cfg, tableName);
			 Scan s = new Scan();
			 FilterList filters = new FilterList();
			 SingleColumnValueFilter singleFilter;
			 for(int i = 0; i < qList.size(); i++){
				 singleFilter = new SingleColumnValueFilter(f, qList.get(i), 
						 CompareOp.EQUAL, new SubstringComparator(subValue[i]));
				 singleFilter.setFilterIfMissing(true);
				 filters.addFilter(singleFilter);
			 }
			 
			 s.setFilter(filters);
			 rs = table.getScanner(s);

		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return rs;
	 }
	 
	 //根据rowKey的部分键值和N个列的部分值查找记录
	 public ResultScanner getRecordByRowkeyAndColumn(String tableName, String family, String rowKey,
			 String[] qualifiers, String[] values, boolean isSubValue){
		 
		 ResultScanner rs = null;
		 byte[] f = Bytes.toBytes(family);
		 FilterList filters = new FilterList();
		 
		 try{
			 HTable table = new HTable(cfg, tableName);
			 
			 Filter rowkeyFilter = new RowFilter(CompareOp.EQUAL, 
					 new SubstringComparator(rowKey));
			 
			 SingleColumnValueFilter singleFilter = null;
			 if(isSubValue){
				 for(int i = 0; i < qualifiers.length; i++){
					 singleFilter = new SingleColumnValueFilter(f, Bytes.toBytes(qualifiers[i]), 
							 CompareOp.EQUAL, new SubstringComparator(values[i]));
					 singleFilter.setFilterIfMissing(true);
					 filters.addFilter(singleFilter);
				 }
			 }
				 
			 else{
				 for(int i = 0; i < qualifiers.length; i++){
					 singleFilter = new SingleColumnValueFilter(f, Bytes.toBytes(qualifiers[i]), 
							 CompareOp.EQUAL, Bytes.toBytes(values[i]));
					 singleFilter.setFilterIfMissing(true);
					 filters.addFilter(singleFilter);
				 }
			 }
			 
			 Scan s = new Scan();
			 filters.addFilter(rowkeyFilter);
			 s.setFilter(filters);
			 rs = table.getScanner(s);

		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return rs;
	 }
	 
	 //根据N个列的整个值查找记录
	 public ResultScanner getByExactValue(String tableName, String family, String qualifier, String value){
		 ResultScanner rs = null;
		 byte[] f = Bytes.toBytes(family);
		 byte[] q = Bytes.toBytes(qualifier);
		 byte[] v = Bytes.toBytes(value);
		 
		 try{
			 HTable table = new HTable(cfg, tableName);
			 Scan s = new Scan();
			 
			 SingleColumnValueFilter singleFilter = new SingleColumnValueFilter(f, q, CompareOp.EQUAL, v);
			 s.setFilter(singleFilter);
			 rs = table.getScanner(s);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return rs;
	 }
}
