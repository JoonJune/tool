package tool.modules.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tool.dto.DbInfoDto;
import tool.dto.DiffTableInfoDto;
import tool.dto.ResultDTO;

@Controller
@RequestMapping("/main")
public class MainController {

	
	@Autowired
	private MainService mainService;
	
	@RequestMapping("/checkDB")
	@ResponseBody
	public String checkDB(int id) {
		return mainService.checkDB(id);
	}
	
	@RequestMapping("/getDbInfoList")
	@ResponseBody
	public ResultDTO<DbInfoDto> getDbInfoList(DbInfoDto dbInfoDto) {
		return mainService.getDbInfoList(dbInfoDto);
	}
	
	@RequestMapping("/getDiffTableInfoList")
	@ResponseBody
	public ResultDTO<DiffTableInfoDto> getDiffTableInfoList(DiffTableInfoDto diffTableInfoDto) {
		return mainService.getDiffTableInfoList(diffTableInfoDto);
	}
	
	@RequestMapping("/saveDiffTableInfo")
	@ResponseBody
	public ResultDTO<String> saveDiffTableInfo(DiffTableInfoDto diffTableInfoDto) {
		return mainService.saveDiffTableInfo(diffTableInfoDto);
	}
	
	@RequestMapping("/exportFile")
	@ResponseBody
	public void exportFile() {
		mainService.exportFile();
	}
}
