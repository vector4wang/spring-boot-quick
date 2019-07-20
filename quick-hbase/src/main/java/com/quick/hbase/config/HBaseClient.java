package com.quick.hbase.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableExistsException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

@DependsOn("hbaseConfig")
@Component
public class HBaseClient {

	@Autowired
	private HbaseConfig config;

	private static Connection connection = null;
	private static Admin admin = null;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostConstruct
	private void init() {
		if (connection != null) {
			return;
		}
		try {
			connection = ConnectionFactory.createConnection(config.configuration());
			admin = connection.getAdmin();
		} catch (IOException e) {
			logger.error("HBase create connection failed: {}", e);
		}
	}

	/**
	 * create 'tableName','[Column Family 1]','[Column Family 2]'
	 * @param tableName
	 * @param columnFamilies 列族名
	 * @throws IOException
	 */
	public void createTable(String tableName, String... columnFamilies) throws IOException {
		TableName name = TableName.valueOf(tableName);
		boolean isExists = this.tableExists(tableName);
		if (isExists) {
			throw new TableExistsException(tableName + "is exists!");
		}
		TableDescriptorBuilder descriptorBuilder = TableDescriptorBuilder.newBuilder(name);
		List<ColumnFamilyDescriptor> columnFamilyList = new ArrayList<>();
		for (String columnFamily : columnFamilies) {
			ColumnFamilyDescriptor columnFamilyDescriptor = ColumnFamilyDescriptorBuilder
					.newBuilder(columnFamily.getBytes()).build();
			columnFamilyList.add(columnFamilyDescriptor);
		}
		descriptorBuilder.setColumnFamilies(columnFamilyList);
		TableDescriptor tableDescriptor = descriptorBuilder.build();
		admin.createTable(tableDescriptor);
	}

	/**
	 * put <tableName>,<rowKey>,<family:column>,<value>,<timestamp>
	 * @param tableName
	 * @param rowKey
	 * @param columnFamily
	 * @param column
	 * @param value
	 * @throws IOException
	 */
	public void insertOrUpdate(String tableName, String rowKey, String columnFamily, String column, String value)
			throws IOException {
		this.insertOrUpdate(tableName, rowKey, columnFamily, new String[]{column}, new String[]{value});
	}

	/**
	 * put <tableName>,<rowKey>,<family:column>,<value>,<timestamp>
	 * @param tableName
	 * @param rowKey
	 * @param columnFamily
	 * @param columns
	 * @param values
	 * @throws IOException
	 */
	public void insertOrUpdate(String tableName, String rowKey, String columnFamily, String[] columns, String[] values)
			throws IOException {
		Table table = connection.getTable(TableName.valueOf(tableName));
		Put put = new Put(Bytes.toBytes(rowKey));
		for (int i = 0; i < columns.length; i++) {
			put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columns[i]), Bytes.toBytes(values[i]));
			table.put(put);
		}
	}

	/**
	 * @param tableName
	 * @param rowKey
	 * @throws IOException
	 */
	public void deleteRow(String tableName, String rowKey) throws IOException {
		Table table = connection.getTable(TableName.valueOf(tableName));
		Delete delete = new Delete(rowKey.getBytes());
		table.delete(delete);
	}

	/**
	 * @param tableName
	 * @param rowKey
	 * @param columnFamily
	 * @throws IOException
	 */
	public void deleteColumnFamily(String tableName, String rowKey, String columnFamily) throws IOException {
		Table table = connection.getTable(TableName.valueOf(tableName));
		Delete delete = new Delete(rowKey.getBytes());
		delete.addFamily(Bytes.toBytes(columnFamily));
		table.delete(delete);
	}

	/**
	 * delete 'tableName','rowKey','columnFamily:column'
	 * @param tableName
	 * @param rowKey
	 * @param columnFamily
	 * @param column
	 * @throws IOException
	 */
	public void deleteColumn(String tableName, String rowKey, String columnFamily, String column) throws IOException {
		Table table = connection.getTable(TableName.valueOf(tableName));
		Delete delete = new Delete(rowKey.getBytes());
		delete.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
		table.delete(delete);
	}

	/**
	 * disable 'tableName' 之后 drop 'tableName'
	 * @param tableName
	 * @throws IOException
	 */
	public void deleteTable(String tableName) throws IOException {
		boolean isExists = this.tableExists(tableName);
		if (!isExists) {
			return;
		}
		TableName name = TableName.valueOf(tableName);
		admin.disableTable(name);
		admin.deleteTable(name);
	}

	/**
	 * get 'tableName','rowkey','family:column'
	 * @param tableName
	 * @param rowkey
	 * @param family
	 * @param column
	 * @return
	 */
	public String getValue(String tableName, String rowkey, String family, String column) {
		Table table = null;
		String value = "";
		if (StringUtils.isBlank(tableName) || StringUtils.isBlank(family) || StringUtils.isBlank(rowkey) || StringUtils
				.isBlank(column)) {
			return null;
		}
		try {
			table = connection.getTable(TableName.valueOf(tableName));
			Get g = new Get(rowkey.getBytes());
			g.addColumn(family.getBytes(), column.getBytes());
			Result result = table.get(g);
			List<Cell> ceList = result.listCells();
			if (ceList != null && ceList.size() > 0) {
				for (Cell cell : ceList) {
					value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	/**
	 * get 'tableName','rowKey'
	 * @param tableName
	 * @param rowKey
	 * @return
	 * @throws IOException
	 */
	public String selectOneRow(String tableName, String rowKey) throws IOException {
		Table table = connection.getTable(TableName.valueOf(tableName));
		Get get = new Get(rowKey.getBytes());
		Result result = table.get(get);
		NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> map = result.getMap();
		for (Cell cell : result.rawCells()) {
			String row = Bytes.toString(cell.getRowArray());
			String columnFamily = Bytes.toString(cell.getFamilyArray());
			String column = Bytes.toString(cell.getQualifierArray());
			String value = Bytes.toString(cell.getValueArray());
			// 可以通过反射封装成对象(列名和Java属性保持一致)
			System.out.println(row);
			System.out.println(columnFamily);
			System.out.println(column);
			System.out.println(value);
		}
		return null;
	}

	/**
	 * scan 't1',{FILTER=>"PrefixFilter('2015')"}
	 * @param tableName
	 * @param rowKeyFilter
	 * @return
	 * @throws IOException
	 */
	public String scanTable(String tableName, String rowKeyFilter) throws IOException {
		Table table = connection.getTable(TableName.valueOf(tableName));
		Scan scan = new Scan();
		if (!StringUtils.isEmpty(rowKeyFilter)) {
			RowFilter rowFilter = new RowFilter(CompareOperator.EQUAL, new SubstringComparator(rowKeyFilter));
			scan.setFilter(rowFilter);
		}
		ResultScanner scanner = table.getScanner(scan);
		try {
			for (Result result : scanner) {
				System.out.println(Bytes.toString(result.getRow()));
				for (Cell cell : result.rawCells()) {
					System.out.println(cell);
				}
			}
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		return null;
	}


	/**
	 * 判断表是否已经存在，这里使用间接的方式来实现
	 *
	 * admin.tableExists() 会报NoSuchColumnFamilyException， 有人说是hbase-client版本问题
	 * @param tableName
	 * @return
	 * @throws IOException
	 */
	public boolean tableExists(String tableName) throws IOException {
		TableName[] tableNames = admin.listTableNames();
		if (tableNames != null && tableNames.length > 0) {
			for (int i = 0; i < tableNames.length; i++) {
				if (tableName.equals(tableNames[i].getNameAsString())) {
					return true;
				}
			}
		}
		return false;
	}
}
