package com.fish.proxy.bean.model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页参数封装类.
 * 
 * @author ZhangJunhua
 * @version v 0.1 2015年12月30日 下午1:29:05
 */
public class PageRequest {
	protected int pageIndex = 1;
	protected int pageSize = 0;
	protected int defaultPageSize = 10;

	protected String orderBy = null;
	protected String orderDir = null;

	protected boolean countTotal = true;

	public PageRequest() {
	}

	public PageRequest(int pageNo, int pageSize) {
		setPageIndex(pageNo);
		setPageSize(pageSize);
	}

	/**
	 * 获得当前页的页号, 序号从1开始, 默认为1.
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * 设置当前页的页号, 序号从1开始, 低于1时自动调整为1.
	 */
	public void setPageIndex(final int pageIndex) {
		this.pageIndex = pageIndex;

		if (pageIndex < 1) {
			this.pageIndex = 1;
		}
	}

	/**
	 * 获得每页的记录数量, 默认为10.
	 */
	public int getPageSize() {
		if (pageSize < 1) {
			return getDefaultPageSize();
		}
		return pageSize;
	}

	/**
	 * 设置每页的记录数量, 低于1时自动调整为1.
	 */
	public void setPageSize(final int pageSize) {
		if (pageSize < 1) {
			this.pageSize = getDefaultPageSize();
		} else {
			this.pageSize = pageSize;
		}
	}

	public int getDefaultPageSize() {
		return defaultPageSize;
	}

	public PageRequest setDefaultPageSize(final int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
		return this;
	}

	/**
	 * 获得排序字段, 无默认值. 多个排序字段时用','分隔.
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置排序字段, 多个排序字段时用','分隔.
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 获得排序方向, 无默认值.
	 */
	public String getOrderDir() {
		return orderDir;
	}

	/**
	 * 设置排序方式向.
	 * 
	 * @param orderDir 可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrderDir(final String orderDir) {
		if (StringUtils.isBlank(orderDir)) {
			return;
		}
		String lowcaseOrderDir = orderDir.toLowerCase();

		//检查order字符串的合法值
		String[] orderDirs = lowcaseOrderDir.split(",");
		for (String orderDirStr : orderDirs) {
			if (!Sort.DESC.equals(orderDirStr) && !Sort.ASC.equals(orderDirStr)) {
				throw new IllegalArgumentException("排序方向" + orderDirStr + "不是合法值");
			}
		}

		this.orderDir = lowcaseOrderDir;
	}

	/**
	 * 获得排序参数.
	 */
	public List<Sort> getSort() {
		if (StringUtils.isBlank(orderBy) || StringUtils.isBlank(orderDir)) {
			return null;
		}
		String[] orderBys = orderBy.split(",");
		String[] orderDirs = orderDir.split(",");
		if (orderBys.length != orderDirs.length) {
			throw new IllegalArgumentException("分页多重排序参数中,排序字段与排序方向的个数不相等");
		}

		List<Sort> orders = new ArrayList<Sort>();
		for (int i = 0; i < orderBys.length; i++) {
			orders.add(new Sort(orderBys[i], orderDirs[i]));
		}

		return orders;
	}

	public String getSortStr() {
		if (StringUtils.isBlank(orderBy) || StringUtils.isBlank(orderDir)) {
			return null;
		}
		String[] orderBys = orderBy.split(",");
		String[] orderDirs = orderDir.split(",");
		if (orderBys.length != orderDirs.length) {
			throw new IllegalArgumentException("分页多重排序参数中,排序字段与排序方向的个数不相等");
		}
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < orderBys.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(orderBys[i]);
			sb.append(" ");
			sb.append(orderDirs[i]);
		}

		return sb.toString();
	}

	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return (isNotBlank(orderBy) && isNotBlank(orderDir));
	}

	private static boolean isNotBlank(String cs) {
		if (cs == null || (cs.trim()).length() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 是否默认计算总记录数.
	 */
	public boolean isCountTotal() {
		return countTotal;
	}

	/**
	 * 设置是否默认计算总记录数.
	 */
	public void setCountTotal(boolean countTotal) {
		this.countTotal = countTotal;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置, 序号从0开始.
	 */
	public int getOffset() {
		return ((pageIndex - 1) * getPageSize());
	}

	/**
	* 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	* 用于Oracle.
	*/
	public int getStartRow() {
		return getOffset() + 1;
	}

	/**
	 * 根据pageNo和pageSize计算当前页最后一条记录在总结果集中的位置, 序号从1开始.
	 * 用于Oracle.
	 */
	public int getEndRow() {
		return pageSize * pageIndex;
	}

	public static class Sort {
		public static final String ASC = "asc";
		public static final String DESC = "desc";

		private final String property;
		private final String dir;

		public Sort(String property, String dir) {
			this.property = property;
			this.dir = dir;
		}

		public String getProperty() {
			return property;
		}

		public String getDir() {
			return dir;
		}
	}
	
	public Params getParams(Params params) {
        if (params == null) {
            params = new Params();
        }
        params.put("offset", getOffset());
        params.put("limit", getPageSize());
        if (isOrderBySetted()) {
            params.put("orderByClause", getSortStr());
        }
        return params;
    }

    public Params getParams() {
        return getParams(null);
    }

}
