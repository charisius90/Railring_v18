package utils;

import java.util.List;

public class Paging<P> {
	private int currentPage = 1;
	private int startPage = 1;
	private int sizePerPage = 5;
	private int totalCount;
	private int blockCount = 5;
	private String searchOption;
	private String searchKeyword;

	private List<P> list;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getSizePerPage() {
		return sizePerPage;
	}

	public void setSizePerPage(int sizePerPage) {
		this.sizePerPage = sizePerPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPageCount() {
		if (totalCount == 0) {
			return 0;
		}

		if (totalCount % sizePerPage == 0) {
			return totalCount / sizePerPage;
		}

		return totalCount / sizePerPage + 1;
	}

	public int getNextBlockPage() {
		int currentBlock = (currentPage - 1) / blockCount + 1;
		return currentBlock * blockCount + 1;
	}

	public int getPrevBlockPage() {
		return (currentPage - 1) / blockCount * blockCount;
	}

	public boolean isNext() {
		return (getNextBlockPage() - 1) < getTotalPageCount();
	}

	public boolean isPrev() {
		return getPrevBlockPage() > 0;
	}

	public int getBlockCount() {
		return blockCount;
	}

	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
	}

	public String getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public List<P> getList() {
		return list;
	}

	public void setList(List<P> list) {
		this.list = list;
	}

	public boolean isSearchKeyword() {
		return !(searchKeyword == null || "".equals(searchKeyword.trim()));
	}

	@Override
	public String toString() {
		return "Paging [currentPage=" + currentPage + ", startPage=" + startPage + ", sizePerPage=" + sizePerPage
				+ ", totalCount=" + totalCount + ", blockCount=" + blockCount + ", searchOption=" + searchOption
				+ ", searchKeyword=" + searchKeyword + ", list=" + list + "]";
	}
}
