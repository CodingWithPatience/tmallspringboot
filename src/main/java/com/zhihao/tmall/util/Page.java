package com.zhihao.tmall.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Page {

    private long start; //开始页数
    private int count; //每页显示个数
    private long total; //总个数
    private long curPage; //当前页
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
        count = defaultCount;
        curPage = 1;
    }
    public Page(int start, int count) {
        super();
        this.start = start;
        this.count = count;
        this.curPage = 1;
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
                + isHasNext() + ", getTotalPage()=" + getTotalPage() + ", getLast()=" + getLast() + "]";
    }
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
    public String getParam() {
        return param;
    }
    public void setParam(String param) {
        this.param = param;
    }
    public int getFirstPage() {
    	start = 0;
    	return 1;
    }
    public long getLastPage() {
    	start = getLast();
    	return curPage = getTotalPage();
    }
    public long getNextPage() {
    	long pageNum = (curPage + 1) > getTotalPage() ? getTotalPage() : (curPage +1);
    	return pageNum;
    }
    public long getPerviousPage() {
    	long pageNum = (curPage - 1) < 1 ? 1 : (curPage - 1);
    	return pageNum;
    }
    public int getPage(int start) {
    	return start/defaultCount + 1;
    }
    public void setCurrentPage(String pageNum) {
    	if(pageNum!=null&&pageNum.startsWith("page")) {
    		String n = pageNum.substring(4,pageNum.length());
    		int num = Integer.parseInt(n);
    		this.start = num * defaultCount - 5;
    		this.start = this.start < 0 ? 0 : this.start;
    		this.curPage = num;
    	}
    }
    public List<Page> getPageList() {
    	for(int i=0; i<getTotalPage(); i++) {
    		pages.add(this);
    	}
    	return pages;
    }
}