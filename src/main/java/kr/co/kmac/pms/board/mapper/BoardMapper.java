package kr.co.kmac.pms.board.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface BoardMapper {

	
	public List<Map<String, Object>> getCeoTalkList(Map<String, Object> map) throws DataAccessException;
	
	public Map<String, Object> getBoardTotalCNT(Map<String, Object> map) throws DataAccessException;
	
	public int doCeoTalkWrite(Map<String, Object> map) throws DataAccessException;
	
	public Map<String, Object> getCeoTalkDetail(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> getCeoTalkComment(Map<String, Object> map) throws DataAccessException;
	
	public Map<String, Object> getCeoBoardTotalCommentCNT(Map<String, Object> map) throws DataAccessException;
	
	public int doCeoTalkCommentWrite(Map<String, Object> map) throws DataAccessException;
	
	public int doDeleteBoard(Map<String, Object> map) throws DataAccessException;

	public int doCeoTalkUpdate(Map<String, Object> map) throws DataAccessException;
	
	public int deleteCeoTalkComment(Map<String, Object> map) throws DataAccessException;
}
