package com.kosta.board.util;

public class PageInfo {
	private Integer curPage;
	private Integer allPage;
	private Integer startPage;
	private Integer endPage;
	
	public PageInfo() {}
	public PageInfo(Integer curPage) {
		this.curPage=curPage;
	}
	
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	public Integer getAllPage() {
		return allPage;
	}
	public void setAllPage(Integer allPage) {
		this.allPage = allPage;
	}
	public Integer getStartPage() {
		return startPage;
	}
	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}
	public Integer getEndPage() {
		return endPage;
	}
	public void setEndPage(Integer endPage) {
		this.endPage = endPage;
	}
	@Override
	public String toString() {
		return "PageInfo [curPage=" + curPage + ", allPage=" + allPage + ", startPage=" + startPage + ", endPage="
				+ endPage + "]";
	}
	
	

}
