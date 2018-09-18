package com.zhihao.tmall.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Page {

    private long start; //开始页数
    private int count; //每页显示个数
    private long pNum;
    private int offset = 5; 
    private long total; //总个数
    private long totalPage; //总页数
    private long curPage; //当前页
    private long nextPage; //下一页
    private long previousPage; //上一页
    private String id;
    private String param; //参数

    private List<Page> pages = new ArrayList<>();

    private static final int defaultCount = 5; //默认每页显示5条

    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public Page (){
        this.count = defaultCount;
        this.curPage = 1;
        this.pNum = 1;
        this.nextPage = 2;
        this.previousPage = 1;
    }
    public Page(int start, int count) {
        super();
        this.start = start;
        this.count = count;
    }

    public boolean isHasPrevious(){
        if(start==0)
            return false;
        return true;
    }
    public boolean isHasNext(){
        if(start==getLast())
            return false;
        return true;
    }

    public long getTotalPage(){
        long totalPage;
        // 假设总数是50，是能够被5整除的，那么就有10页
        if (0 == total % count)
            totalPage = total /count;
		// 假设总数是51，不能够被5整除的，那么就有11页
        else
            totalPage = total / count + 1;

        if(0==totalPage)
            totalPage = 1;
        return totalPage;
    }

    public long getLast(){
        long last;
        // 假设总数是50，是能够被5整除的，那么最后一页的开始就是45
        if (0 == total % count)
            last = total - count;
		// 假设总数是51，不能够被5整除的，那么最后一页的开始就是50
        else
            last = total - total % count;
        last = last<0?0:last;
        return last;
    }

    @Override
    public String toString() {
        return "Page [start=" + start + ", count=" + count + ", total=" + total + ", getStart()=" + getStart()
                + ", getCount()=" + getCount() + ", isHasPreviouse()=" + isHasPrevious() + ", isHasNext()="
                + isHasNext() + ", getTotalPage()=" + getTotalPage() + ", getLast()=" + getLast() + ", getCurPage()=" + getCurPage()+"]";
    }
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
        this.totalPage = getTotalPage();
        this.nextPage = (curPage+1)>totalPage?totalPage:(curPage+1);
    }
    public String getParam() {
        return param;
    }
    public void setParam(String param) {
        this.param = param;
    }
    public int getFirstPage() {
    	start = 0;
    	curPage = 1;
    	return 1;
    }
    public long getLastPage() {
    	start = getLast();
    	return curPage = getTotalPage();
    }
    public long getPreviousPage() {
		return previousPage;
	}
	public void setPreviousPage(long previousPage) {
		previousPage = previousPage < 1 ? 1 : previousPage;
		this.previousPage = previousPage;
	}
	public long getNextPage() {
    	return nextPage;
    }
    public void setNextPage(long nextPage) {
		this.nextPage = nextPage;
	}
    public long getPageNum() {
    	return pNum++;
    }
    public void setCurrentPage(String pageNum) {
    	if(pageNum!=null&&pageNum.startsWith("page")) {
    		String n = pageNum.substring(4,pageNum.length());
    		int num = Integer.parseInt(n);
    		this.start = (num-1) * defaultCount;
    		this.start = this.start < 0 ? 0 : this.start;
    		this.curPage = num;
    		setPreviousPage(curPage-1);
    		if(curPage >= 10)
    			pNum = curPage - offset;  
    	}
    }
    public long getCurPage() {
		return curPage;
	}
	public List<Page> getPageList() {
    	long count = totalPage - pNum + 1;
    	count = count > 10 ? 10 : count;
    	
    	for(int i=0; i<count; i++) {
    		pages.add(this);
    	}
    	return pages;
    }
}