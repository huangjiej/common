/**
 * 
 * Pagingnation.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.face;


/**
 * @author huangjiej_2
 * 2015年1月9日 上午8:36:05
 * 本类主要做为
 */
public class Pagingnation {

	
    /**
     * 创建分页控制器
     * @param currPage 目的页数
     * @param pageSize 每页大小
     */
    public Pagingnation(int currPage, int pageSize)
	{
		super();
		this.currPage = currPage;
		this.pageSize = pageSize;
	}
    
    /**
     * 取空分页控制器，此控制器不会进行分页处理
     * 主要用于 在别的地方中调用查询list的方法，不需要使用PageControl(此时不能传值为空，因为运行时会报错)的情况。
     * @return
     */
    public static Pagingnation non_pageControl()
    {
    	return new Pagingnation();
    }
    
    /**
	 * 取第一条记录
	 */
	public static final Pagingnation topone_pageControl()
	{
		Pagingnation pc = new Pagingnation(1, 1);
		  pc.setCountsize(false);
		  return pc;
	}

	public Pagingnation()
	{
		super();
	}



	/**
	 * 总记录数
	 */
	private int totalCount;

	/**
	 * 总页数
	 */
	private int pageCount;

	/**
	 * 当前页
	 */
	private int currPage;

	/**
	 * 每页大小
	 */
	private int pageSize;

	/**
	 * 是否获取记录条数
	 */
	private boolean countsize = true;

	/**
	 * 是否获取记录条数
	 */
	public boolean isCountsize() {
		return countsize;
	}

	/**
	 * 是否获取记录条数
	 */
	public void setCountsize(boolean countsize) {
		this.countsize = countsize;
	}

	/**
	 * 获取每页大小
	 */
	public int getPageSize()
	{
		return this.pageSize;
	}

	/**
	 * 设置每页大小
	 */
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}



	/**
	 * 获取当前页
	 */
	public int getCurrPage()
	{
		return this.currPage;
	}

	/**
	 * 设置当前页
	 */
	public void setCurrPage(int currPage)
	{
		this.currPage = currPage;
	}



	/**
	 * 获取总页数
	 */
	public int getPageCount()
	{
		return this.pageCount;
	}

	/**
	 * 设置总页数
	 */
	public void setPageCount(int pageCount)
	{
		this.pageCount = pageCount;
	}



	/**
	 * 获取总记录数
	 */
	public int getTotalCount()
	{
		return this.totalCount;
	}

	/**
	 * 设置总记录数
	 */
	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}


    
    
    
    /**
     * 校验
     * @return
     */
    private boolean validate()
	{
		
		return pageSize>0&&currPage>0;
	}
    
    /**
     * 取页内开始记录数
     * @return
     */
    private int getFirstResult()
	{
    	int begin = (currPage-1)*pageSize;
		return begin;
	}

	/**
     * 取页内最大记录数
     * @return
     */
    private int getMaxResult()
	{
    	int end  = currPage*pageSize;
		return end;
	}

	/**
     *重新计算总页数 
     */
    protected void calculatePageCount()
    {
    	pageCount=(totalCount+pageSize-1)/pageSize;
    	if(pageCount==0)
    		pageCount =1;
    	if(currPage>pageCount) currPage=pageCount;
    }

	
}
