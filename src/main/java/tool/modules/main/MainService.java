package tool.modules.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tool.core.base.constants.SqlConstants;
import tool.core.utils.DBUtils;
import tool.dto.DbInfoDto;
import tool.dto.DiffTableInfoDto;
import tool.dto.ResultDTO;
import tool.dto.TableInfoDto;
import tool.mapper.local.LocalMapper;

@Service
public class MainService {
	
	@Autowired
	private LocalMapper localMapper;
	
	public String checkDB(int id) {
		String result = "";
		localMapper.initCheckDB();
		
		DiffTableInfoDto diffTableInfoDto = localMapper.selectOneDiffTableInfo(new DiffTableInfoDto(id));
		
		if (diffTableInfoDto != null) {
			DbInfoDto srcDb = new DbInfoDto("SRC", diffTableInfoDto.getFirstDbIp(), diffTableInfoDto.getFirstDbPort(), diffTableInfoDto.getFirstDbDriver(), diffTableInfoDto.getFirstDbUser(), diffTableInfoDto.getFirstDbPassword());
			DbInfoDto desDb = new DbInfoDto("DES", diffTableInfoDto.getSecondDbIp(), diffTableInfoDto.getSecondDbPort(), diffTableInfoDto.getSecondDbDriver(), diffTableInfoDto.getSecondDbUser(), diffTableInfoDto.getSecondDbPassword());
			
			insertData(diffTableInfoDto, srcDb);
			insertData(diffTableInfoDto, desDb);
			
			List<TableInfoDto> list = localMapper.dbDiff();
			
			if (list != null && !list.isEmpty()) {
				StringBuffer sb = new StringBuffer("");
				for (TableInfoDto dto : list) {
					sb.append(dto.getInsertSql()).append("</br>");
				}
				result = sb.toString();
			}
		}
		return result;
	}
	
	private void insertData(DiffTableInfoDto diffTableInfoDto, DbInfoDto dbInfoDto) {
		List<TableInfoDto> tableList = DBUtils.getList(dbInfoDto, dbInfoDto.getSql(SqlConstants.TABLE_INFO_SQL, diffTableInfoDto.getTbSchema()));
		localMapper.insertBatchTableInfo(tableList);
		
		List<TableInfoDto> indexList = DBUtils.getList(dbInfoDto, dbInfoDto.getSql(SqlConstants.INDEX_INFO_SQL, diffTableInfoDto.getTbSchema()));
		localMapper.insertBatchIndexInfo(indexList);
	}
	
	public ResultDTO<DbInfoDto> getDbInfoList(DbInfoDto dbInfoDto) {
		ResultDTO<DbInfoDto> result = new ResultDTO<DbInfoDto>();
		List<DbInfoDto> list = localMapper.selectDbInfo(dbInfoDto);
		result.setRows(list);
		return result;
	}
	
	public ResultDTO<DiffTableInfoDto> getDiffTableInfoList(DiffTableInfoDto diffTableInfoDto) {
		ResultDTO<DiffTableInfoDto> result = new ResultDTO<DiffTableInfoDto>();
		List<DiffTableInfoDto> list = localMapper.selectDiffTableInfo(diffTableInfoDto);
		result.setRows(list);
		return result;
	}
	
	public ResultDTO<String> saveDiffTableInfo(DiffTableInfoDto diffTableInfoDto) {
		ResultDTO<String> result = new ResultDTO<String>();
		int count = localMapper.insertDiffTableInfo(diffTableInfoDto);
		if (count == 1) {
			result.setRtnMesg("成功");
		} else {
			result.setRtnMesg("失败");
		}
		return result;
	}
	
	public void exportFile() {
		File file = new File("/file/" + new SimpleDateFormat("yyyymmddhhmmss").format(new Date()) + ".sql");
        if(!file.getParentFile().exists()){ //如果文件的目录不存在
            file.getParentFile().mkdirs(); //创建目录
        }
        
        //2: 实例化OutputString 对象
        OutputStream output = null;
		try {
			output = new FileOutputStream(file);
			//3: 准备好实现内容的输出
	        List<TableInfoDto> list = localMapper.dbDiff();
	        StringBuffer sb = new StringBuffer("");
			if (list != null && !list.isEmpty()) {
				for (TableInfoDto dto : list) {
					sb.append(dto.getInsertSql()).append("\n");
				}
			}
			//将字符串变为字节数组
			byte data[] = sb.toString().getBytes();
			output.write(data);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
        
	}
}
