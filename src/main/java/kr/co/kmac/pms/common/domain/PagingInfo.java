package kr.co.kmac.pms.common.domain;

import lombok.Data;

@Data
public class PagingInfo {

	private int pagingPage = 1;
	private int pagingNumberPer = 10;
	private int totalNumberOfEntries; 
	private int totalNumberOfPages;
	private String sortingColumn;
	private String ortingDirection;


}
 