package com.dlt.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dlt.pojo.Good;
import com.dlt.pojo.Storage;
import com.dlt.pojo.User;

/*
 * 具体工具类，继承自抽象类DB_Utils， 加入项目所需方法重新封装
 */

public class DB_DAO extends DB_Utils {
	private static final String url = "jdbc:mysql://localhost:3306/storage";
	private static final String user = "root";
	private static final String password = "1234";
	
	public DB_DAO(){
		super(url, user, password);
	}
	
	public  List<User> getUsersBySql(String sql) {
		List<User> list = new ArrayList<User>();
		
		rs = excuteQuery(sql);
		try {
			while(rs != null && rs.next()){
				User tUser = new User(rs);
				list.add(tUser);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Good> getGoodsBySql(String sql) {
		List<Good> list = new ArrayList<Good>();
		rs = excuteQuery(sql);
		try {
			while(rs != null && rs.next()){
				Good tGood = new Good(rs);
				list.add(tGood);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Storage> getStoragesBySql(String sql) {
		List<Storage> list = new ArrayList<Storage>();
		rs = excuteQuery(sql);
		try {
			while(rs != null && rs.next()){
				Storage tStorage = new Storage(rs);
				list.add(tStorage);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean storeUser(User user) {
		String sql = "select * from tb_user where name='"+user.getName()+"';";
		List<User> list = getUsersBySql(sql);
		if(list.size() == 0){
			sql = "insert into tb_user(name, permission, password) values('"+user.getName()+"', "+user.getPermission()+", '"+user.getPassword()+"');";
			System.out.println(sql);
			excuteUpdate(sql);
			return true;
		}
		return false;
	}
	
	public boolean storeGood(Good good) {
		String sql = "select * from tb_good where name='"+good.getName()+"' and size="+good.getSize()+" and color='"+good.getColor()+"' and storageid="+good.getStorageid()+" and passman='"+good.getPassman()+"';";
		List<Good> list = getGoodsBySql(sql);
		sql = "";
		if(list.size() == 0){
			sql = "insert into tb_good(name, size, color, num, storageid, passman) values('"+good.getName()+"', "+good.getSize()+", '"+good.getColor()+"', "+good.getNum()+", "+good.getStorageid()+", '"+good.getPassman()+"');";
			
		}
		else if(list.size() == 1){
			Good tGood = list.get(0);
			sql = "update tb_good set num="+(tGood.getNum()+good.getNum())+" where id="+tGood.getId()+";";
		}
		System.out.println(sql);
		excuteUpdate(sql);
		return true;
	}
	
	public boolean storeStorage(Storage storage){
		String sql = "select * from tb_storage where address='"+storage.getAddress()+"';";
		List<Storage> list = getStoragesBySql(sql);
		if(list.size() == 0){
			sql = "insert into tb_storage(address) values('"+storage.getAddress()+"');";
			System.out.println(sql);
			excuteUpdate(sql);
			return true;
		}
		return false;
	}
	
	public Storage getStorageFromId(int id) {
		String sql = "select * from tb_storage where id="+id+";";
		List<Storage> list = getStoragesBySql(sql);
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	public Storage getStorageFromAddr(String addr) {
		String sql = "select * from tb_storage where address='"+addr+"';";
		List<Storage> list = getStoragesBySql(sql);
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	/*
	 *时间格式转换yyyy-MM-dd HH:mm:ss
	 */
	public static String formatDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	public static void main(String[] args) {
		DB_DAO test = new DB_DAO();
		User ttUser = new User(null, "dlt", 1, "1234");
		test.storeUser(ttUser);
		/*Storage ttStorage = new Storage(null, "daxuelu");
		test.storeStorage(ttStorage);
		Good ttGood = new Good(null, "cabbage", 12, "red", 12, test.getStorageFromAddr("daxuelu").getId(), "xiaoming");
		test.storeGood(ttGood);
	//	test.storeGood(ttGood);
		*/
		List<User> userList = test.getUsersBySql("select * from tb_user;");
		for (User tUser : userList) {
			System.out.println(tUser.toString());
		}
		/*
		List<Good> goodList = test.getGoodsBySql("select * from tb_good;");
		for (Good tGood : goodList) {
			System.out.println(tGood.toString());
		}
		List<Storage> storagesList = test.getStoragesBySql("select * from tb_storage;");
		for (Storage tStorage : storagesList) {
			System.out.println(tStorage.toString());
		}*/
		//System.out.println(test.excuteUpdate("update tb_info set info_payfor='1' where id=137;"));
		test.close();
	}
}
