package tool.mapper.local;

import java.util.List;

import tool.dto.TableInfoDto;
import tool.dto.DbInfoDto;
import tool.dto.DiffTableInfoDto;

public interface LocalMapper {
	
	List<DbInfoDto> selectDbInfo(DbInfoDto dbInfoDto);
	
	List<DiffTableInfoDto> selectDiffTableInfo(DiffTableInfoDto diffTableInfoDto);
	
	DiffTableInfoDto selectOneDiffTableInfo(DiffTableInfoDto diffTableInfoDto);

	List<TableInfoDto> dbDiff();
	
	int insertDiffTableInfo(DiffTableInfoDto diffTableInfoDto);
	
	int insertBatchTableInfo(List<TableInfoDto> list);
	
	int insertBatchIndexInfo(List<TableInfoDto> list);
	
	int initCheckDB();
}
